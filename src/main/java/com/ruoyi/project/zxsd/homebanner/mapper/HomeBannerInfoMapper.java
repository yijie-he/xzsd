package com.ruoyi.project.zxsd.homebanner.mapper;

import com.ruoyi.project.zxsd.homebanner.domain.HomeBannerEntity;
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
public interface HomeBannerInfoMapper {

    void addHomeBanner(HomeBannerEntity homeBannerEntity);

    List<HomeBannerEntity> getHomeBanner(@Param("picStatus") Integer picStatus);

    void delHomeBanner(@Param("id") String id,@Param("updateBy")String updateBy);

    void trueHomeBanner(@Param("id") String id,@Param("updateBy") String updateBy);

    void falseHomeBanner(@Param("id") String id,@Param("updateBy") String updateBy);
}
