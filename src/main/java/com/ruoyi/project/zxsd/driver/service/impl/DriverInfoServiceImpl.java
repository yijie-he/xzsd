package com.ruoyi.project.zxsd.driver.service.impl;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.zxsd.driver.domain.DriverInfoEntity;
import com.ruoyi.project.zxsd.driver.mapper.DriverInfoMapper;
import com.ruoyi.project.zxsd.driver.service.IDriverInfoService;
import com.ruoyi.project.zxsd.store.domain.StoreInfoEntity;
import com.ruoyi.project.zxsd.store.service.IStoreInfoService;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
import com.ruoyi.project.zxsd.sys.mapper.SystemUserMapper;
import com.ruoyi.project.zxsd.sys.util.SystemCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class DriverInfoServiceImpl implements IDriverInfoService {
    /**
     * 司机Mapper
     */
    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final DriverInfoMapper driverInfoMapper;

    /**
     * 门店Mapper
     */
    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final IStoreInfoService storeInfoService;

    /**
     * 用户Mapper
     */
    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final SystemUserMapper systemUserMapper;

    public DriverInfoServiceImpl(DriverInfoMapper driverInfoMapper,IStoreInfoService storeInfoService,SystemUserMapper systemUserMapper) {
        this.driverInfoMapper = driverInfoMapper;
        this.storeInfoService = storeInfoService;
        this.systemUserMapper = systemUserMapper;
    }


    @Override
    public void addDriver(DriverInfoEntity driverInfoEntity) {
        //生成司机编号
        driverInfoEntity.setDriverNo(SystemCodeUtil.getDriverNo());
        //获取当前登入人信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
//        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
            //查询当前登录人的门店信息
//        StoreInfoEntity storeInfoEntity=storeInfoService.getStoreInfoByUserCode(systemUserEntity.getUserId());
//        driverInfoEntity.setStoreNo(storeInfoEntity.getStoreNo());
//        driverInfoEntity.setProvinceNo(storeInfoEntity.getProvinceNo());
//        driverInfoEntity.setCountyNo(storeInfoEntity.getCountyNo());
        driverInfoEntity.setCreateBy(loginUser.getUsername());
        driverInfoEntity.setUpdateBy(loginUser.getUsername());
        driverInfoEntity.setId(IdUtils.fastSimpleUUID());
        int sortNo = 0;
        try {
            sortNo=systemUserMapper.getMaxSortNo("t_driver_info");
        }catch (Exception e){
            log.error("插入门店信息异常",e);
        }
        sortNo++;
        driverInfoEntity.setSortNo(sortNo);
        driverInfoMapper.addDriver(driverInfoEntity);
//        driverInfoEntity.setCreateBy(loginUser.getUsername());
//        driverInfoEntity.setUpdateBy(loginUser.getUsername());
    }

    @Override
    public List<DriverInfoEntity> getDriver(DriverInfoEntity driverInfoEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        String storeNo="";
        List<DriverInfoEntity> list =new ArrayList<>();
        //查询当前登录人的门店信息
        if((0==systemUserEntity.getIsAdmin())&& (1==systemUserEntity.getRole())) {
            try {
                StoreInfoEntity storeInfoEntity = storeInfoService.getStoreInfoByUserCode(systemUserEntity.getUserId());
                storeNo = storeInfoEntity.getStoreNo();
                driverInfoEntity.setStoreNo(storeNo);
                list = driverInfoMapper.getDriver(driverInfoEntity);
            }catch (Exception e){
                log.error("该门店下无司机", e);
            }

        }else if(1==systemUserEntity.getIsAdmin()){
            driverInfoEntity.setStoreNo("");
            list = driverInfoMapper.getDriver(driverInfoEntity);
        }
        return list;
    }

    @Override
    public void editDriver(DriverInfoEntity driverInfoEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        driverInfoEntity.setUpdateBy(loginUser.getUsername());
        driverInfoMapper.editDriver(driverInfoEntity);
    }

    @Override
    public void delDriver(List<DriverInfoEntity> driverInfoEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        driverInfoMapper.delDriver(driverInfoEntity,loginUser.getUsername());
    }
}
