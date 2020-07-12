package com.ruoyi.project.zxsd.goodscate.service;

import com.ruoyi.project.zxsd.goodscate.domain.GoodsCateEntity;

import java.util.List;

/**
 * 商品分类Service接口
 *
 * @author jiaab
 * @date 2020-05-15
 */
public interface IGoodsCateService {

    void addGoodsCate(GoodsCateEntity goodsCateEntity);

    List<GoodsCateEntity> getGoodsCate();

    void editGoodsCate(GoodsCateEntity goodsCateEntity);

    int sonGoodsCate(GoodsCateEntity goodsCateEntity);

    void delGoodsCate(GoodsCateEntity goodsCateEntity);

    List<GoodsCateEntity> getGoodCate(GoodsCateEntity goodsCateEntity);

    List<GoodsCateEntity> getGoodCates();

    GoodsCateEntity getGoodsCateName(GoodsCateEntity goodsCateEntity);
}
