package com.ruoyi.project.zxsd.goods.service.impl;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.zxsd.goods.domain.GoodsEntity;
import com.ruoyi.project.zxsd.goods.domain.GoodsPicEntity;
import com.ruoyi.project.zxsd.goods.mapper.GoodsInfoMapper;
import com.ruoyi.project.zxsd.goods.service.IGoodsInfoService;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
import com.ruoyi.project.zxsd.sys.mapper.SystemUserMapper;
import com.ruoyi.project.zxsd.sys.util.SystemCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图 业务层处理
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class GoodsServiceImpl implements IGoodsInfoService {
    /**
     * 用户Mapper
     */
    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final GoodsInfoMapper goodsInfoMapper;

    /**
     * 用户Mapper
     */
    @Autowired
    private final SystemUserMapper systemUserMapper;

    public GoodsServiceImpl(GoodsInfoMapper goodsInfoMapper, SystemUserMapper systemUserMapper) {
        this.goodsInfoMapper = goodsInfoMapper;
        this.systemUserMapper = systemUserMapper;
    }

    @Override
    @Transactional
    public void addGoodsList(List<GoodsEntity> goodsList) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        int sortNo = 0;
        try{
            sortNo = systemUserMapper.getMaxSortNo("t_goods_sku");
        }catch (Exception e){
            log.error("第一条数据不用处理", e);
        }
        for (int i = 0; i < goodsList.size(); i++) {
            GoodsEntity goodsEntity =  goodsList.get(i);

            goodsEntity.setCreateBy(systemUserEntity.getUserName());
            goodsEntity.setUpdateBy(systemUserEntity.getUserName());
            goodsEntity.setId(IdUtils.fastSimpleUUID());

            goodsEntity.setSortNo(++sortNo);
        }
        goodsInfoMapper.addGoodsList(goodsList);
    }

    @Override
    public List<GoodsEntity> getGoodsList(GoodsEntity goodsEntity) {
        List<String> picLists;
        List<GoodsEntity> goodsList = goodsInfoMapper.getGoodsList(goodsEntity);
        return goodsList;
    }

    @Override
    public List<GoodsEntity> getGoodsListT(GoodsEntity goodsEntity) {
        List<String> picLists;
        List<GoodsEntity> goodsList = goodsInfoMapper.getGoodsListT(goodsEntity);
        return goodsList;
    }

    @Override
    @Transactional
    public void addGoodsLists(GoodsEntity goodsEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        int sortNo = 0;
        try{
            sortNo = systemUserMapper.getMaxSortNo("t_goods_sku");
        }catch (Exception e){
            log.error("第一条数据不用处理", e);
        }
        goodsEntity.setSkuNo(SystemCodeUtil.getUserCode());
        goodsEntity.setCreateBy(systemUserEntity.getUserName());
        goodsEntity.setUpdateBy(systemUserEntity.getUserName());
        goodsEntity.setId(IdUtils.fastSimpleUUID());
        if(StringUtils.isEmpty(goodsEntity.getStatus())){
            goodsEntity.setStatus("1");
        }
        goodsEntity.setBrowseCount(0);
        goodsEntity.setSortNo(++sortNo);
        goodsInfoMapper.addGoodsLists(goodsEntity);
        sortNo = 0;
        try{
            sortNo = systemUserMapper.getMaxSortNo("t_goods_sku_pic");
        }catch (Exception e){
            log.error("第一条数据不用处理", e);
        }
        List<GoodsPicEntity> goodsPicEntityList = new ArrayList<GoodsPicEntity>();
        for(int i = 0;i<goodsEntity.getPicList().size();i++){
            GoodsPicEntity goodsPicEntity = new GoodsPicEntity();
            goodsPicEntity.setId(IdUtils.fastSimpleUUID());
            goodsPicEntity.setSkuNo(goodsEntity.getSkuNo());
            goodsPicEntity.setSurPicUrl(goodsEntity.getPicList().get(i));
            goodsPicEntity.setSortNo(++sortNo);
            goodsPicEntity.setCreateBy(loginUser.getUsername());
            goodsPicEntity.setUpdateBy(loginUser.getUsername());
            goodsPicEntityList.add(i, goodsPicEntity);
        }
        goodsInfoMapper.addGoodsPicList(goodsPicEntityList);
    }

    @Override
    public void delGoods(List<String> id) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        String updateBy = systemUserEntity.getUserName();
        goodsInfoMapper.delGoods(id,updateBy);
    }

    @Override
    public void trueGoods(List<String> id) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        String updateBy = systemUserEntity.getUserName();
        goodsInfoMapper.trueGoods(id,updateBy);
    }

    @Override
    public void falseGoods(List<String> id) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        String updateBy = systemUserEntity.getUserName();
        goodsInfoMapper.falseGoods(id,updateBy);
    }

    @Override
    public GoodsEntity getGood(GoodsEntity goodsEntity) {
        List<String> picLists;
        GoodsEntity goodsList = goodsInfoMapper.getGood(goodsEntity);
        picLists = goodsInfoMapper.getPicList(goodsList.getSkuNo());
        goodsList.setPicList(picLists);
        return goodsList;
    }


    @Override
    @Transactional
    public void editGoods(GoodsEntity goodsEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        goodsEntity.setUpdateBy(systemUserEntity.getUserName());
        goodsInfoMapper.delGoodsPir(goodsEntity);
        int sortNo = 0;
        try{
            sortNo = systemUserMapper.getMaxSortNo("t_goods_sku_pic");
        }catch (Exception e){
            log.error("第一条数据不用处理", e);
        }
        List<GoodsPicEntity> goodsPicEntityList = new ArrayList<GoodsPicEntity>();
        for(int i = 0;i<goodsEntity.getPicList().size();i++){
            GoodsPicEntity goodsPicEntity = new GoodsPicEntity();
            goodsPicEntity.setId(IdUtils.fastSimpleUUID());
            goodsPicEntity.setSkuNo(goodsEntity.getSkuNo());
            goodsPicEntity.setSurPicUrl(goodsEntity.getPicList().get(i));
            goodsPicEntity.setSortNo(++sortNo);
            goodsPicEntity.setCreateBy(loginUser.getUsername());
            goodsPicEntity.setUpdateBy(loginUser.getUsername());
            goodsPicEntityList.add(i, goodsPicEntity);
        }
        goodsInfoMapper.addGoodsPicList(goodsPicEntityList);
        goodsInfoMapper.editGoods(goodsEntity);
    }

}
