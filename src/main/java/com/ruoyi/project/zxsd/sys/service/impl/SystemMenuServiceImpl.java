package com.ruoyi.project.zxsd.sys.service.impl;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.zxsd.sys.domain.SystemMenuEntity;
import com.ruoyi.project.zxsd.sys.mapper.SystemMenuMapper;
import com.ruoyi.project.zxsd.sys.mapper.SystemUserMapper;
import com.ruoyi.project.zxsd.sys.service.ISystemMenuService;
import com.ruoyi.project.zxsd.sys.util.SystemCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class SystemMenuServiceImpl implements ISystemMenuService {
    /**
     * 用户Mapper
     */
    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final SystemMenuMapper sysMenuMapper;
    /**
     * 用户Mapper
     */
    @Autowired
    private final SystemUserMapper sysUserMapper;

    public SystemMenuServiceImpl(SystemMenuMapper sysMenuMapper,SystemUserMapper sysUserMapper) {
        this.sysMenuMapper = sysMenuMapper;
        this.sysUserMapper = sysUserMapper;
    }
    @Override
    @Transactional
    public void insertSysMenu(SystemMenuEntity systemMenuEntity) {
        String menuCode = SystemCodeUtil.getMenuCode();
        systemMenuEntity.setMenuCode(menuCode);
        LoginUser loginUser = SecurityUtils.getLoginUser();
        systemMenuEntity.setCreateBy(loginUser.getUsername());
        systemMenuEntity.setUpdateBy(loginUser.getUsername());

        int sortNo = sysUserMapper.getMaxSortNo("t_sys_menu");
        systemMenuEntity.setSortNo(sortNo+1);

        systemMenuEntity.setId(IdUtils.fastSimpleUUID());

        int parentLevel=0;
        if(StringUtils.isNotEmpty(systemMenuEntity.getParentMenuCode())){
            parentLevel = sysMenuMapper.getParentLevel(systemMenuEntity.getParentMenuCode());
            systemMenuEntity.setLevel(parentLevel+1);
        }else{
            systemMenuEntity.setLevel(parentLevel);
        }

        sysMenuMapper.insertSysMenu(systemMenuEntity);
    }

    @Override
    public List<SystemMenuEntity> getMenuByUserRole(int userRole) {
        List<SystemMenuEntity> menuList = sysMenuMapper.getMenuByUserRole(userRole);
        List<SystemMenuEntity> oneList = new ArrayList<>();
        for (int i = 0; i < menuList.size(); i++) {
            if (menuList.get(i).getLevel()==0){
                oneList.add(menuList.get(i));
            }
        }
        //为一级菜单设置子菜单准备递归
        for (SystemMenuEntity menu:oneList) {
            //获取父菜单下所有子菜单调用getChildList
            List<SystemMenuEntity> childList = getChildList(String.valueOf(menu.getMenuCode()),menuList);
            menu.setChildMenu(childList);
        }
        return oneList;
    }

    @Override
    public void editMenuByUserRole(SystemMenuEntity systemMenuEntity) {
        if(StringUtils.isNotEmpty(systemMenuEntity.getParentMenuCode())){
            systemMenuEntity.setType(1);
        }else{
            systemMenuEntity.setType(2);
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        systemMenuEntity.setUpdateBy(loginUser.getUsername());
        sysMenuMapper.editMenuByUserRole(systemMenuEntity);
    }

    @Override
    public int getMenuByMenuCode(String menuCode) {
        return sysMenuMapper.getMenuByMenuCode(menuCode);
    }

    @Override
    public void delMenuByMenuCode(String menuCode) {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        String updateBy=loginUser.getUsername();
        sysMenuMapper.delMenuByMenuCode(menuCode,updateBy);
    }

    public List<SystemMenuEntity> getChildList(String oneMenuCode,List<SystemMenuEntity> menuList){
        //构建子菜单
        List<SystemMenuEntity> childList = new ArrayList<>();
        //遍历传入的list
        for (SystemMenuEntity menu:menuList) {
            //所有菜单的父id与传入的根节点id比较，若相等则为该级菜单的子菜单
            if (String.valueOf(menu.getParentMenuCode()).equals(oneMenuCode)){
                childList.add(menu);
            }
        }
        //递归
        for (SystemMenuEntity m:childList) {
            m.setChildMenu(getChildList(String.valueOf(m.getMenuCode()),menuList));
        }
        if (childList.size() == 0){
            return null;
        }
        return childList;
    }
}
