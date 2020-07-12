package com.ruoyi.project.zxsd.sys.mapper;

import com.ruoyi.project.zxsd.store.domain.StoreInfoEntity;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
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
public interface SystemUserMapper {

    SystemUserEntity getSystemUserByUserName(String userName);

    void insertSystemUser(SystemUserEntity systemUserEntity);

    void insertSystemUserInfo(SystemUserEntity systemUserEntity);

    int getMaxSortNo(@Param("tableName") String tableName);

    void editSystemUserInfo(SystemUserEntity systemUserEntity);

    void editSystemUser(SystemUserEntity systemUserEntity);

    void deleteSystemUser(@Param("systemUserEntity")List<SystemUserEntity> systemUserEntity,@Param("userName") String userName);

    void deleteSystemUserInfo(@Param("systemUserEntity") List<SystemUserEntity> systemUserEntity,@Param("userName") String userName);

    List<SystemUserEntity> getSystemUser(SystemUserEntity systemUserEntity);

    List<SystemUserEntity> getUser(SystemUserEntity systemUserEntity);

    StoreInfoEntity getAddress(String userName);

    void editPwd(SystemUserEntity systemUserEntity);
}
