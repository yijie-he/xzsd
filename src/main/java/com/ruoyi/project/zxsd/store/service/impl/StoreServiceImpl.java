package com.ruoyi.project.zxsd.store.service.impl;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.zxsd.store.domain.CountyEntity;
import com.ruoyi.project.zxsd.store.domain.ProvinceEntity;
import com.ruoyi.project.zxsd.store.domain.StoreInfoEntity;
import com.ruoyi.project.zxsd.store.mapper.StoreInfoMapper;
import com.ruoyi.project.zxsd.store.service.IStoreInfoService;
import com.ruoyi.project.zxsd.store.util.StoreCodeUtil;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
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
public class StoreServiceImpl implements IStoreInfoService {
    /**
     * 用户Mapper
     */
    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final StoreInfoMapper storeInfoMapper;


    public StoreServiceImpl(StoreInfoMapper storeInfoMapper) {
        this.storeInfoMapper = storeInfoMapper;
    }

    @Override
    public List<ProvinceEntity> getProvince() {
        return storeInfoMapper.getProvince();
    }

    @Override
    public List<CountyEntity> getCounty(String provinceCode) {
        return storeInfoMapper.getCounty(provinceCode);
    }

    @Override
    public List<StoreInfoEntity> getListStoreInfo(StoreInfoEntity storeInfoEntity) {
        List<StoreInfoEntity> list = storeInfoMapper.getListStoreInfo(storeInfoEntity);
        return list;
    }

    @Override
    public StoreInfoEntity getDetail(String id) {
        return storeInfoMapper.getDetail(id);
    }

    @Override
    public void addStore(StoreInfoEntity storeInfoEntity) {
        //获取当前登录人的基本信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        //获取门店编码
        storeInfoEntity.setStoreNo(StoreCodeUtil.getStoreNo());
        //获取门店邀请码
        storeInfoEntity.setInviteCode(StoreCodeUtil.getInviteCode());
        //获取用户姓名
        storeInfoEntity.setCreateBy(user.getUserName());
        storeInfoEntity.setUpdateBy(user.getUserName());
        //设置id
        storeInfoEntity.setId(IdUtils.fastSimpleUUID());
        //设置序号
        int sortNo = 0;
        try {
            sortNo = storeInfoMapper.getMaxSortNo("t_store_info");
        }catch (Exception e){
            log.error("插入数据异常", e);
        }
        storeInfoEntity.setSortNo(sortNo+1);
        storeInfoMapper.addStore(storeInfoEntity);
    }

    @Override
    public void editStore(StoreInfoEntity storeInfoEntity) {
        //获取当前登录人的基本信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        //获取用户姓名
        storeInfoEntity.setUpdateBy(user.getUserName());
        //放入门店编号
        storeInfoMapper.editStore(storeInfoEntity);
    }

    @Override
    public void delStore(List<StoreInfoEntity> storeInfoEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        storeInfoMapper.delStore(storeInfoEntity,loginUser.getUsername());
    }

    @Override
    public StoreInfoEntity getStoreInfoByUserCode(String userId) {
        return storeInfoMapper.getStoreInfoByUserCode(userId);
    }

    @Override
    public List<StoreInfoEntity> getDetails() {
        return storeInfoMapper.getDetails();
    }
}
