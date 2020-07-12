package com.ruoyi.project.zxsd.sys.service;

import com.ruoyi.project.zxsd.store.domain.StoreInfoEntity;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author jiaab
 * @date 2020-05-15
 */
public interface ISystemUserService {
    SystemUserEntity getSystemUserByUserName(String userName);

    void insertSystemUser(SystemUserEntity systemUserEntity);

    void editSystemUser(SystemUserEntity systemUserEntity);

    void deleteSystemUser(List<SystemUserEntity> systemUserEntity);

    List<SystemUserEntity> getSystemUser(SystemUserEntity systemUserEntity);

    List<SystemUserEntity> getUser(SystemUserEntity systemUserEntity);

    StoreInfoEntity getAddress();

    void editPwd(SystemUserEntity systemUserEntity);

    void editInviteCode(SystemUserEntity systemUserEntity);

    List<StoreInfoEntity> getInviteCode(SystemUserEntity systemUserEntity);
}
