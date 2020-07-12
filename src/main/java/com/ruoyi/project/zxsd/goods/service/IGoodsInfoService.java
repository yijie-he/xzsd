package com.ruoyi.project.zxsd.goods.service;

import com.ruoyi.project.zxsd.goods.domain.GoodsEntity;

import java.util.List;

/**
 * 轮播图Service接口
 *
 * @author jiaab
 * @date 2020-05-15
 */
public interface IGoodsInfoService {

    void addGoodsList(List<GoodsEntity> goodsList);

    List<GoodsEntity> getGoodsList(GoodsEntity goodsEntity);

    List<GoodsEntity> getGoodsListT(GoodsEntity goodsEntity);

    void addGoodsLists(GoodsEntity goodsEntity);

    void delGoods(List<String> id);

    void trueGoods(List<String> id);

    void falseGoods(List<String> id);

    GoodsEntity getGood(GoodsEntity goodsEntity);

    void editGoods(GoodsEntity goodsEntity);

}
