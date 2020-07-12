package com.ruoyi.project.zxsd.sys.service.impl;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.common.enums.TerminalEnum;
import com.ruoyi.project.zxsd.store.domain.StoreInfoEntity;
import com.ruoyi.project.zxsd.store.mapper.StoreInfoMapper;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
import com.ruoyi.project.zxsd.sys.mapper.SystemUserMapper;
import com.ruoyi.project.zxsd.sys.service.ISystemUserService;
import com.ruoyi.project.zxsd.sys.util.SystemCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class SystemUserServiceImpl implements ISystemUserService {
    /**
     * 用户Mapper
     */
    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final SystemUserMapper sysUserMapper;

    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final StoreInfoMapper storeInfoMapper;
    public SystemUserServiceImpl(SystemUserMapper sysUserMapper,StoreInfoMapper storeInfoMapper) {
        this.sysUserMapper = sysUserMapper;
        this.storeInfoMapper = storeInfoMapper;
    }


    @Override
    public SystemUserEntity getSystemUserByUserName(String userName) {
        SystemUserEntity systemUserEntity = sysUserMapper.getSystemUserByUserName(userName);
        return systemUserEntity;
    }

    @Override
    @Transactional//回滚操作
    public void insertSystemUser(SystemUserEntity systemUserEntity) {
        /**
         * 获取当前登录用户信息
         */
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String userName = loginUser.getUsername();
        systemUserEntity.setCreateBy(userName);
        systemUserEntity.setUpdateBy(userName);

        systemUserEntity.setPassword(SecurityUtils.encryptPassword(!"".equals(systemUserEntity.getPassword()) ? systemUserEntity.getPassword() : TerminalEnum.BACKEND.getPassword()));
        String userId = SystemCodeUtil.getUserCode();
        systemUserEntity.setUserId(userId);
        String id = IdUtils.fastSimpleUUID();
        systemUserEntity.setId(id);

        sysUserMapper.insertSystemUser(systemUserEntity);

        int sortNo = sysUserMapper.getMaxSortNo("t_sys_user");
        systemUserEntity.setSortNo(sortNo+1);
        sysUserMapper.insertSystemUserInfo(systemUserEntity);
    }

    @Override
    @Transactional
    public void editSystemUser(SystemUserEntity systemUserEntity) {

        sysUserMapper.editSystemUser(systemUserEntity);
        sysUserMapper.editSystemUserInfo(systemUserEntity);
    }

    @Override
    @Transactional
    public void deleteSystemUser(List<SystemUserEntity> systemUserEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String userName = loginUser.getUsername();
        sysUserMapper.deleteSystemUserInfo(systemUserEntity,userName);
        sysUserMapper.deleteSystemUser(systemUserEntity,userName);
    }

    /**
     * 查询
     */
    @Override
    @Transactional
    public List<SystemUserEntity> getSystemUser(SystemUserEntity systemUserEntity) {
        return sysUserMapper.getSystemUser(systemUserEntity);
    }

    @Override
    public List<SystemUserEntity> getUser(SystemUserEntity systemUserEntity) {
        return sysUserMapper.getUser(systemUserEntity);
    }

    @Override
    public StoreInfoEntity getAddress() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String userName = loginUser.getUsername();
        return sysUserMapper.getAddress(userName);
    }

    @Override
    public void editPwd(SystemUserEntity systemUserEntity) {
        systemUserEntity.setPassword(SecurityUtils.encryptPassword(!"".equals(systemUserEntity.getPassword()) ? systemUserEntity.getPassword() : TerminalEnum.BACKEND.getPassword()));
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String userName = loginUser.getUsername();
        systemUserEntity.setUserName(userName);
        sysUserMapper.editPwd(systemUserEntity);
    }

    @Override
    public void editInviteCode(SystemUserEntity systemUserEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String userName = loginUser.getUsername();
        systemUserEntity.setUserName(userName);
        sysUserMapper.editPwd(systemUserEntity);
    }

    @Override
    public List<StoreInfoEntity> getInviteCode(SystemUserEntity systemUserEntity) {
        StoreInfoEntity storeInfoEntity =new StoreInfoEntity();
        storeInfoEntity.setInviteCode(systemUserEntity.getInviteCode());
        return storeInfoMapper.getListStoreInfo(storeInfoEntity);
    }

}
