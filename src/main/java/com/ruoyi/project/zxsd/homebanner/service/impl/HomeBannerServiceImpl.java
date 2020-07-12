package com.ruoyi.project.zxsd.homebanner.service.impl;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.zxsd.homebanner.domain.HomeBannerEntity;
import com.ruoyi.project.zxsd.homebanner.mapper.HomeBannerInfoMapper;
import com.ruoyi.project.zxsd.homebanner.service.IHomeBannerInfoService;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
import com.ruoyi.project.zxsd.sys.mapper.SystemUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图 业务层处理
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class HomeBannerServiceImpl implements IHomeBannerInfoService {
    /**
     * 用户Mapper
     */
    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final HomeBannerInfoMapper homeBannerInfoMapper;

    /**
     * 用户Mapper
     */
    @Autowired
    private final SystemUserMapper systemUserMapper;

    public HomeBannerServiceImpl(HomeBannerInfoMapper homeBannerInfoMapper,SystemUserMapper systemUserMapper) {
        this.homeBannerInfoMapper = homeBannerInfoMapper;
        this.systemUserMapper = systemUserMapper;
    }

    @Override
    public void addHomeBanner(HomeBannerEntity homeBannerEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        homeBannerEntity.setUpdateBy(user.getUserName());
        homeBannerEntity.setCreateBy(user.getUserName());
        homeBannerEntity.setId(IdUtils.fastSimpleUUID());
        int sortNo = 0;
        try{
            sortNo = systemUserMapper.getMaxSortNo("t_runpic_info");
        }catch (Exception e){
            log.error("插入数据为第一行，可不用处理",e);
        }
        homeBannerEntity.setSortNo(sortNo+1);
        homeBannerInfoMapper.addHomeBanner(homeBannerEntity);
    }

    @Override
    public List<HomeBannerEntity> getHomeBanner(Integer picStatus) {
        return homeBannerInfoMapper.getHomeBanner(picStatus);
    }

    @Override
    public void delHomeBanner(String id) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        String updateBy =user.getUserName();
        homeBannerInfoMapper.delHomeBanner(id,updateBy);
    }

    @Override
    public void trueHomeBanner(String id) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        String updateBy =user.getUserName();
        homeBannerInfoMapper.trueHomeBanner(id,updateBy);
    }

    @Override
    public void falseHomeBanner(String id) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        String updateBy =user.getUserName();
        homeBannerInfoMapper.falseHomeBanner(id,updateBy);
    }
}
