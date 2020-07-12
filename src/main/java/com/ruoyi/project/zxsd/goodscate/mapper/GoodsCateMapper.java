package com.ruoyi.project.zxsd.goodscate.mapper;

import com.ruoyi.project.zxsd.goodscate.domain.GoodsCateEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品分类对象Mapper接口
 *
 * @author wangdong
 * @date 2020-05-02
 */
@Repository
@Mapper
public interface GoodsCateMapper {
    //增加
    void addGoodsCate(GoodsCateEntity goodsCateEntity);

    int getCateCodeParent(String cateCodeParent);

    void setParent(@Param("codeParent") String codeParent,@Param("updateBy") String cateCodeParent);

    List<GoodsCateEntity> getGoodsCate();

    void editGoodsCate(GoodsCateEntity goodsCateEntity);

    int sonGoodsCate(GoodsCateEntity goodsCateEntity);

    void delGoodsCate(GoodsCateEntity goodsCateEntity);

    List<GoodsCateEntity> getGoodCate(GoodsCateEntity goodsCateEntity);

    List<GoodsCateEntity> getGoodCates();

    GoodsCateEntity getGoodsCateName(GoodsCateEntity goodsCateEntity);
}
