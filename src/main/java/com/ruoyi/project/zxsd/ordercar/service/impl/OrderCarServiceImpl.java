package com.ruoyi.project.zxsd.ordercar.service.impl;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.zxsd.ordercar.domain.OrderCarEntity;
import com.ruoyi.project.zxsd.ordercar.mapper.OrderCarMapper;
import com.ruoyi.project.zxsd.ordercar.service.IOrderCarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class OrderCarServiceImpl implements IOrderCarService {


    @Autowired
    private final OrderCarMapper orderCarMapper;

    public OrderCarServiceImpl(OrderCarMapper orderCarMapper) {
        this.orderCarMapper = orderCarMapper;
    }

    @Override
    public void addOrderCar(OrderCarEntity orderCarEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        orderCarEntity.setCreateBy(loginUser.getUsername());
        orderCarEntity.setUpdateBy(loginUser.getUsername());
        orderCarEntity.setUserCode(loginUser.getSystemUserEntity().getUserId());
        orderCarEntity.setId(IdUtils.fastSimpleUUID());
        orderCarMapper.addOrderCar(orderCarEntity);
    }

    @Override
    public List<OrderCarEntity> getOrderCar(OrderCarEntity orderCarEntity) {
        List<OrderCarEntity> orderList = orderCarMapper.getOrderCar(orderCarEntity);
        return orderList;
    }

    @Override
    public void delOrderCar(List<OrderCarEntity> orderCarEntity) {
        for(int i =0;i<orderCarEntity.size();i++){
            OrderCarEntity orderCarEntity1 = (OrderCarEntity)orderCarEntity.get(i);
            LoginUser loginUser = SecurityUtils.getLoginUser();
            orderCarEntity1.setUpdateBy(loginUser.getUsername());
            orderCarEntity1.setUserCode(loginUser.getSystemUserEntity().getUserId());
        }
        orderCarMapper.delOrderCar(orderCarEntity);
    }

    @Override
    public void editOrderCar(OrderCarEntity orderCarEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        orderCarEntity.setUpdateBy(loginUser.getUsername());
        orderCarEntity.setUserCode(loginUser.getSystemUserEntity().getUserId());
        orderCarMapper.editOrderCar(orderCarEntity);
    }
}
