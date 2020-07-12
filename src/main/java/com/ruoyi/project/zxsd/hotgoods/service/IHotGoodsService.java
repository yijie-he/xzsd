package com.ruoyi.project.zxsd.hotgoods.service;

import com.ruoyi.project.zxsd.hotgoods.domain.HotGoodsEntity;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author jiaab
 * @date 2020-05-15
 */
public interface IHotGoodsService {

    void addHotGoods(HotGoodsEntity hotGoodsEntity);

    void delhotGoods(List<HotGoodsEntity> hotGoodsEntity);

    List<HotGoodsEntity> getHotGoods(HotGoodsEntity hotGoodsEntity);

    List<HotGoodsEntity> getHotGoodsT(HotGoodsEntity hotGoodsEntity);

    HotGoodsEntity getHotGood(HotGoodsEntity hotGoodsEntity);
}
