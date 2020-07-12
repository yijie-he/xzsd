package com.ruoyi.project.zxsd.goodscate.service.impl;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.zxsd.goodscate.domain.GoodsCateEntity;
import com.ruoyi.project.zxsd.goodscate.mapper.GoodsCateMapper;
import com.ruoyi.project.zxsd.goodscate.service.IGoodsCateService;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
import com.ruoyi.project.zxsd.sys.mapper.SystemUserMapper;
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
public class GoodsCateServiceImpl implements IGoodsCateService {
    /**
     * 用户Mapper
     */
    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final GoodsCateMapper goodsCateMapper;

    /**
     * 用户Mapper
     */
    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final SystemUserMapper systemUserMapper;

    public GoodsCateServiceImpl(GoodsCateMapper goodsCateMapper,SystemUserMapper systemUserMapper) {
        this.goodsCateMapper = goodsCateMapper;
        this.systemUserMapper = systemUserMapper;
    }

    @Override
    @Transactional
    public void addGoodsCate(GoodsCateEntity goodsCateEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        goodsCateEntity.setCreateBy(systemUserEntity.getUserName());
        goodsCateEntity.setUpdateBy(systemUserEntity.getUserName());
        goodsCateEntity.setCateCode(SystemCodeUtil.getMenuCode());
        goodsCateEntity.setId(IdUtils.fastSimpleUUID());

        int sortNo = 0;
        try {
            sortNo = systemUserMapper.getMaxSortNo("t_code_cate");
        }catch (Exception e){
            log.error("第一条数据序号异常，可不用处理",e);
        }
        goodsCateEntity.setSortNo(sortNo+1);

        int parentLevel=1;
        if(StringUtils.isNotEmpty(goodsCateEntity.getCateCodeParent())){
            parentLevel = goodsCateMapper.getCateCodeParent(goodsCateEntity.getCateCodeParent());
            goodsCateEntity.setLevel(parentLevel+1);
            goodsCateEntity.setIsParent(0);
            //设置父层级IsParent为1
            goodsCateMapper.setParent(goodsCateEntity.getCateCodeParent(),systemUserEntity.getUserName());
        }else{
            goodsCateEntity.setLevel(parentLevel);
            goodsCateEntity.setIsParent(1);
        }


        goodsCateMapper.addGoodsCate(goodsCateEntity);
    }

    @Override
    public List<GoodsCateEntity> getGoodsCate() {
        List<GoodsCateEntity> goodsCateList = goodsCateMapper.getGoodsCate();
        List<GoodsCateEntity> oneList = new ArrayList<>();
        for (int i = 1; i < goodsCateList.size(); i++) {
            if (goodsCateList.get(i).getLevel()==1){
                oneList.add(goodsCateList.get(i));
            }
        }
        //为一级菜单设置子菜单准备递归
        for (GoodsCateEntity menu:oneList) {
            //获取父菜单下所有子菜单调用getChildList
            List<GoodsCateEntity> childList = getChildList(String.valueOf(menu.getCateCode()),goodsCateList);
            menu.setChildMenu(childList);
        }
        return oneList;
    }

    @Override
    public void editGoodsCate(GoodsCateEntity goodsCateEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        goodsCateEntity.setUpdateBy(systemUserEntity.getUserName());
        goodsCateMapper.editGoodsCate(goodsCateEntity);
    }

    @Override
    public int sonGoodsCate(GoodsCateEntity goodsCateEntity) {
        return goodsCateMapper.sonGoodsCate(goodsCateEntity);
    }

    @Override
    public void delGoodsCate(GoodsCateEntity goodsCateEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        goodsCateEntity.setUpdateBy(systemUserEntity.getUserName());
        goodsCateMapper.delGoodsCate(goodsCateEntity);
    }

    @Override
    public List<GoodsCateEntity> getGoodCate(GoodsCateEntity goodsCateEntity) {
        List<GoodsCateEntity> goodsCateList = goodsCateMapper.getGoodCate(goodsCateEntity);
        return goodsCateList;
    }

    @Override
    public List<GoodsCateEntity> getGoodCates() {
        List<GoodsCateEntity> goodsCateList = goodsCateMapper.getGoodCates();
        return goodsCateList;
    }

    @Override
    public GoodsCateEntity getGoodsCateName(GoodsCateEntity goodsCateEntity) {
        return goodsCateMapper.getGoodsCateName(goodsCateEntity);
    }

    public List<GoodsCateEntity> getChildList(String oneMenuCode, List<GoodsCateEntity> menuList){
        //构建子菜单
        List<GoodsCateEntity> childList = new ArrayList<>();
        //遍历传入的list
        for (GoodsCateEntity menu:menuList) {
            //所有菜单的父id与传入的根节点id比较，若相等则为该级菜单的子菜单
            if (String.valueOf(menu.getCateCodeParent()).equals(oneMenuCode)){
                childList.add(menu);
            }
        }
        //递归
        for (GoodsCateEntity m:childList) {
            m.setChildMenu(getChildList(String.valueOf(m.getCateCode()),menuList));
        }
        if (childList.size() == 0){
            return null;
        }
        return childList;
    }
}
