package com.ruoyi.project.zxsd.homebanner.service;

import com.ruoyi.project.zxsd.homebanner.domain.HomeBannerEntity;

import java.util.List;

/**
 * 轮播图Service接口
 *
 * @author jiaab
 * @date 2020-05-15
 */
public interface IHomeBannerInfoService {

    void addHomeBanner(HomeBannerEntity homeBannerEntity);

    List<HomeBannerEntity> getHomeBanner(Integer picStatus);

    void delHomeBanner(String id);

    void trueHomeBanner(String id);

    void falseHomeBanner(String id);
}
