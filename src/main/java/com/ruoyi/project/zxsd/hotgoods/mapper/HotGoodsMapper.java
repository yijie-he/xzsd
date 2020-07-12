package com.ruoyi.project.zxsd.hotgoods.mapper;

import com.ruoyi.project.zxsd.hotgoods.domain.HotGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户Mapper接口
 *
 * @author wangdong
 * @date 2020-05-02
 */
@Repository
@Mapper
public interface HotGoodsMapper {

    HotGoodsEntity addGoods(String skuNo);

    void addHotGoods(HotGoodsEntity hotGoodsEntity);

    List<HotGoodsEntity> getHotGoods(HotGoodsEntity hotGoodsEntity);

    List<HotGoodsEntity> getHotGoodsT(HotGoodsEntity hotGoodsEntity);

    void delhotGoods(@Param("hotGoodsEntity") List<HotGoodsEntity> hotGoodsEntity);

    HotGoodsEntity getHotGood(HotGoodsEntity hotGoodsEntity);
}
