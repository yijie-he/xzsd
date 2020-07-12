package com.ruoyi.project.zxsd.hotgoods.service.impl;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.zxsd.hotgoods.domain.HotGoodsEntity;
import com.ruoyi.project.zxsd.hotgoods.mapper.HotGoodsMapper;
import com.ruoyi.project.zxsd.hotgoods.service.IHotGoodsService;
import com.ruoyi.project.zxsd.store.mapper.StoreInfoMapper;
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
public class HotGoodsServiceImpl implements IHotGoodsService {


    @Autowired
    private final HotGoodsMapper hotgoodsMapper;

    @Autowired//自动装配方式，从S中去查找
    private final StoreInfoMapper storeInfoMapper;

    public HotGoodsServiceImpl(HotGoodsMapper hotgoodsMapper, StoreInfoMapper storeInfoMapper) {
        this.hotgoodsMapper = hotgoodsMapper;
        this.storeInfoMapper = storeInfoMapper;
    }


    @Override
    public void addHotGoods(HotGoodsEntity hotGoodsEntity) {
        String skuNo = hotGoodsEntity.getSkuNo();
        HotGoodsEntity hotGoodsEntity1= hotgoodsMapper.addGoods(skuNo);
        String skuName = hotGoodsEntity1.getSkuName();
        hotGoodsEntity.setSkuName(skuName);
        Double sellingPrice = hotGoodsEntity1.getSellingPrice();
        String detail = hotGoodsEntity1.getDetail();
        hotGoodsEntity.setSellingPrice(sellingPrice);
        hotGoodsEntity.setDetail(detail);
        LoginUser loginUser = SecurityUtils.getLoginUser();
        hotGoodsEntity.setCreateBy(loginUser.getUsername());
        hotGoodsEntity.setUpdateBy(loginUser.getUsername());
        int sortNo =-1;
        try{
            sortNo = storeInfoMapper.getMaxSortNo("t_home_hot_goods");
        }catch (Exception e){
            log.error("新增热门商品信息异常", e);
        }
        hotGoodsEntity.setSortNo(sortNo+1);
        hotGoodsEntity.setId(IdUtils.fastSimpleUUID());
        hotgoodsMapper.addHotGoods(hotGoodsEntity);
    }

    @Override
    public void delhotGoods(List<HotGoodsEntity> hotGoodsEntity) {
        for(int i =0;i<hotGoodsEntity.size();i++){
            HotGoodsEntity hotGoodsEntity1 = (HotGoodsEntity)hotGoodsEntity.get(i);
            LoginUser loginUser = SecurityUtils.getLoginUser();
            hotGoodsEntity1.setUpdateBy(loginUser.getUsername());
        }
        hotgoodsMapper.delhotGoods(hotGoodsEntity);
    }


    @Override
    public List<HotGoodsEntity> getHotGoods(HotGoodsEntity hotGoodsEntity) {
        List<HotGoodsEntity> getHotGoods = hotgoodsMapper.getHotGoods(hotGoodsEntity);
        return getHotGoods;
    }

    @Override
    public List<HotGoodsEntity> getHotGoodsT(HotGoodsEntity hotGoodsEntity) {
        List<HotGoodsEntity> getHotGoods = hotgoodsMapper.getHotGoodsT(hotGoodsEntity);
        return getHotGoods;
    }

    @Override
    public HotGoodsEntity getHotGood(HotGoodsEntity hotGoodsEntity) {
        return hotgoodsMapper.getHotGood(hotGoodsEntity);
    }
}
