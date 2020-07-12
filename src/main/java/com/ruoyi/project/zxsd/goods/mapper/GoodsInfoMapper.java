package com.ruoyi.project.zxsd.goods.mapper;

import com.ruoyi.project.zxsd.goods.domain.GoodsEntity;
import com.ruoyi.project.zxsd.goods.domain.GoodsPicEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 轮播图Mapper接口
 *
 * @author wangdong
 * @date 2020-05-02
 */
@Repository
@Mapper
public interface GoodsInfoMapper {

    void addGoodsList(@Param("goodsList") List<GoodsEntity> goodsList);

    List<GoodsEntity> getGoodsList(GoodsEntity goodsEntity);

    List<GoodsEntity> getGoodsListT(GoodsEntity goodsEntity);

    void addGoodsLists(GoodsEntity goodsEntity);

    void addGoodsPicList(@Param("goodsPicEntityList") List<GoodsPicEntity> goodsPicEntityList);

    void delGoods(@Param("id") List<String> id,@Param("updateBy") String updateBy);

    void trueGoods(@Param("id") List<String> id,@Param("updateBy") String updateBy);

    void falseGoods(@Param("id") List<String> id,@Param("updateBy") String updateBy);

    List<String> getPicList(String skuNo);

    GoodsEntity getGood(GoodsEntity goodsEntity);

    void editGoods(GoodsEntity goodsEntity);

    void delGoodsPir(GoodsEntity goodsEntity);

//    void editGoods(GoodsEntity goodsEntity);
}
