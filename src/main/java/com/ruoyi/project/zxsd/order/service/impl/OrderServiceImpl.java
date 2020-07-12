package com.ruoyi.project.zxsd.order.service.impl;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.zxsd.driver.mapper.DriverInfoMapper;
import com.ruoyi.project.zxsd.goods.domain.GoodsEntity;
import com.ruoyi.project.zxsd.goods.mapper.GoodsInfoMapper;
import com.ruoyi.project.zxsd.goods.service.IGoodsInfoService;
import com.ruoyi.project.zxsd.order.domain.OrderDetailEntity;
import com.ruoyi.project.zxsd.order.domain.OrderEntity;
import com.ruoyi.project.zxsd.order.mapper.OrderMapper;
import com.ruoyi.project.zxsd.order.service.IOrderService;
import com.ruoyi.project.zxsd.store.domain.StoreInfoEntity;
import com.ruoyi.project.zxsd.store.mapper.StoreInfoMapper;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
import com.ruoyi.project.zxsd.sys.service.ISystemUserService;
import com.ruoyi.project.zxsd.sys.util.SystemCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired//自动装配方式，从S中去查找
    private final OrderMapper orderMapper;
    @Autowired//自动装配方式，从S中去查找
    private final StoreInfoMapper storeInfoMapper;
    @Autowired//自动装配方式，从S中去查找
    private final GoodsInfoMapper goodsMapper;
    @Autowired//自动装配方式，从S中去查找
    private final IGoodsInfoService goodsService;
    @Autowired//自动装配方式，从S中去查找
    private final ISystemUserService systemUserService;
    @Autowired//自动装配方式，从S中去查找
    private final DriverInfoMapper driverInfoMapper;


    public OrderServiceImpl(OrderMapper orderMapper, StoreInfoMapper storeInfoMapper, GoodsInfoMapper goodsMapper, IGoodsInfoService goodsService, ISystemUserService systemUserService, DriverInfoMapper driverInfoMapper) {
        this.orderMapper = orderMapper;
        this.goodsMapper = goodsMapper;
        this.storeInfoMapper = storeInfoMapper;
        this.goodsService = goodsService;
        this.systemUserService = systemUserService;
        this.driverInfoMapper = driverInfoMapper;
    }

    @Override
    @Transactional
    public void addOrder(List<OrderDetailEntity> orderDetailList) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        //订单拆分
        List<OrderEntity> orderList = new ArrayList();
        List<OrderDetailEntity> orderDetailResultList = new ArrayList();

        //确定拆分的订单数量，按门店编号去重。
        Set<String> set = new HashSet<String>();
        for(int i = 0;i<orderDetailList.size();i++){
            OrderDetailEntity orderDetailEntity = orderDetailList.get(i);
            GoodsEntity goodsEntity = new GoodsEntity();
            goodsEntity.setSkuNo(orderDetailEntity.getSkuNo());
            List<GoodsEntity> goodsList = goodsService.getGoodsList(goodsEntity);
            set.add((goodsList.get(0)).getOutsideNo());
        }

        for (String str : set) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setStoreNo(str);
            orderEntity.setId(IdUtils.fastSimpleUUID());
            orderEntity.setOrderNo(SystemCodeUtil.getUserCode());
            orderEntity.setCreateBy(systemUserEntity.getUserName());
            orderEntity.setUpdateBy(systemUserEntity.getUserName());
            orderEntity.setUserCode(systemUserEntity.getUserId());
            orderEntity.setShippingUser(systemUserEntity.getUserRealname());
            orderEntity.setShippingMoney(5.0);
            orderEntity.setDistrictMoney(0.0);
            orderEntity.setOrderStatus(0);

            double orderMoney = 0;
            for(int i = 0;i<orderDetailList.size();i++){
                OrderDetailEntity orderDetailEntity = orderDetailList.get(i);
                //根据SKUNO获取商品基本信息
                GoodsEntity goodsEntity = new GoodsEntity();
                goodsEntity.setSkuNo(orderDetailEntity.getSkuNo());
                List<GoodsEntity> goodsList = goodsService.getGoodsList(goodsEntity);
                GoodsEntity goodsInfoEntity = goodsList.get(0);
                orderEntity.setStoreName(goodsInfoEntity.getStoreName());

//                DriverInfoEntity driverInfoEntity = new  DriverInfoEntity();
//                driverInfoEntity.setStoreNo(goodsInfoEntity.getOutsideNo());
//                List<DriverInfoEntity> countyList= driverInfoMapper.getDriver(driverInfoEntity);
//                DriverInfoEntity driverInfoEntity1;
//                Random r = new Random();
//                int a = r.nextInt(countyList.size());
//                driverInfoEntity1 = countyList.get(a);
//                orderEntity.setStoreDriver(driverInfoEntity1.getDriverNo());

                if(str.equals(goodsInfoEntity.getOutsideNo())){
                    orderMoney = orderMoney+(goodsInfoEntity.getSellingPrice()*orderDetailEntity.getGoodsCnt());
                    orderEntity.setOrderMoney(orderMoney);
                    orderEntity.setPaymentMoney(orderMoney+orderEntity.getShippingMoney());
                    //构造订单明细实体
                    orderDetailEntity.setOrderDetailCode(SystemCodeUtil.getUserCode());
                    orderDetailEntity.setGoodsName(goodsInfoEntity.getSkuName());
                    orderDetailEntity.setGoodsPrice(goodsInfoEntity.getSellingPrice());
                    orderDetailEntity.setId(IdUtils.fastSimpleUUID());
                    orderDetailEntity.setOrderNo(orderEntity.getOrderNo());
                    orderDetailEntity.setCreateBy(systemUserEntity.getUserName());
                    orderDetailEntity.setUpdateBy(systemUserEntity.getUserName());

                    //获取库存和销量
                    String skuNo = orderDetailEntity.getSkuNo();
                    GoodsEntity good = new GoodsEntity();
                    good.setSkuNo(skuNo);
                    GoodsEntity goodsEntity1 = goodsMapper.getGood(good);
                    int amountCnt=goodsEntity1.getAmountCnt();
                    int saleCnt=goodsEntity1.getSaleCnt();
                    int goodsCnt = orderDetailEntity.getGoodsCnt();
                    orderDetailEntity.setNumber(amountCnt-goodsCnt);
                    orderDetailEntity.setNumber2(saleCnt+goodsCnt);
                    orderDetailResultList.add(orderDetailEntity);
                }
            }
            orderList.add(orderEntity);
        }

        //保存订单明细表
        orderMapper.addOrderDetail(orderDetailResultList);
        //保存订单主表信息
        orderMapper.addOrder(orderList);
        //减商品库存、加商品销量
        orderMapper.upSaleCnt(orderDetailResultList);
        //下单成功后，系统随机分配取货司机
    }

    @Override
    public List<OrderEntity> getOrderList(OrderEntity orderEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        int userRelo = systemUserEntity.getRole();
        if(userRelo == 3){
            orderEntity.setUserCode(systemUserEntity.getUserId());
        }
        if(userRelo == 1){
            String userId = systemUserEntity.getUserId();
            StoreInfoEntity storeInfoEntity = storeInfoMapper.getStoreInfoByUserCode(userId);
            orderEntity.setStoreNo(storeInfoEntity.getStoreNo());
        }
        List<OrderEntity> list = orderMapper.getOrderList(orderEntity);
        return list;
    }

    @Override
    public List<OrderDetailEntity> getOrderMenu(OrderDetailEntity orderDetailEntity) {
        List<OrderDetailEntity> list = orderMapper.getOrderMenu(orderDetailEntity);
        return list;
    }

    @Override
    public void editOrderMenu(OrderEntity orderEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        orderEntity.setUpdateBy(loginUser.getUsername());
        orderMapper.editOrderMenu(orderEntity);
    }

}
