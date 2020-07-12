package com.ruoyi.project.zxsd.sys.mapper;

import com.ruoyi.project.zxsd.sys.domain.SystemMenuEntity;
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
public interface SystemMenuMapper {

    void insertSysMenu(SystemMenuEntity systemMenuEntity);

    List<SystemMenuEntity> getMenuByUserRole(@Param("userRole") int userRole);

    int getParentLevel(String parentMenuCode);

    void editMenuByUserRole(SystemMenuEntity systemMenuEntity);

    int getMenuByMenuCode(@Param("menuCode") String menuCode);

    void delMenuByMenuCode(@Param("menuCode") String menuCode,@Param("updateBy") String updateBy);
}
