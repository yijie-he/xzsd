package com.ruoyi.project.zxsd.sys.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.zxsd.sys.domain.SystemMenuEntity;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
import com.ruoyi.project.zxsd.sys.service.ISystemMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台端用户Controller
 *
 * @author wangdong
 * @date 2020.04.30
 */
@RestController
@RequestMapping("/sysmenu")
@Api(tags = {"【后台端】2.1 菜单管理"}, description = "菜单管理")
public class SystemMenuController extends BaseController {
    private final ISystemMenuService sysMenuService;
    @Autowired
    public SystemMenuController(ISystemMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }
    /**
     * 新增菜单
     */
    @Log(title = "2.1.1 新增菜单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation(value = "2.1.1 新增菜单", notes = "新增菜单")
    public AjaxResult add(@RequestBody SystemMenuEntity systemMenuEntity) {
        AjaxResult ajaxResult = null;
        try{
            //新增菜单
            sysMenuService.insertSysMenu(systemMenuEntity);
            return new AjaxResult().success("菜单新增成功");
        }catch (Exception e){
            logger.error("菜单新增失败",e);
            return new AjaxResult().error("菜单新增失败");
        }
    }

    /**
     * 查询菜单
     */
    @Log(title = "2.1.2 查询菜单", businessType = BusinessType.INSERT)
    @PostMapping("/getMenu")
    @ApiOperation(value = "2.1.2 查询菜单", notes = "查询菜单")
    public AjaxResult getMenu() {
        AjaxResult ajaxResult = null;
        //获取当前登录人的基本信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        int userRole = user.getRole();
        List<SystemMenuEntity> menuList = sysMenuService.getMenuByUserRole(userRole);
        return ajaxResult.success("查询成功",menuList);

    }

    /**
     * 修改菜单
     */
    @Log(title = "2.1.3 修改菜单", businessType = BusinessType.INSERT)
    @PostMapping("/editMenu")
    @ApiOperation(value = "2.1.3 修改菜单", notes = "修改菜单")
    public AjaxResult editMenu(@RequestBody SystemMenuEntity systemMenuEntity) {
        AjaxResult ajaxResult = null;
        //获取当前登录人的基本信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        int userRole = user.getRole();
        if(userRole!=0){
            return ajaxResult.success("非管理员权限不足");
        }else{
            String menuName = systemMenuEntity.getMenuName();
            if(StringUtils.isEmpty(menuName)){
                return new AjaxResult().error("菜单名称不能为空");
            }
            try {
                sysMenuService.editMenuByUserRole(systemMenuEntity);
            }catch (Exception e){
                logger.error("修改菜单失败",e);
                return new AjaxResult().error("修改菜单失败");
            }

        }
        return ajaxResult.success("修改菜单成功");
    }

    /**
     * 删除菜单
     */
    @Log(title = "2.1.4 删除菜单", businessType = BusinessType.INSERT)
    @PostMapping("/delMenu")
    @ApiOperation(value = "2.1.4 删除菜单", notes = "删除菜单")
    public AjaxResult delMenu(@RequestBody SystemMenuEntity systemMenuEntity) {
        String menuCode = systemMenuEntity.getMenuCode();
        AjaxResult ajaxResult = null;
        //获取当前登录人的基本信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        int userRole = user.getRole();
        //判断权限
        if(userRole!=1&&userRole!=0){
            return ajaxResult.success("非管理员店长权限不足");
        }else{
            //查看菜单编号是否为空
//            String menuCode=systemMenuEntity.getMenuCode();
            if(StringUtils.isEmpty(menuCode)){
                return AjaxResult.error("未选择菜单");
            }else {
                try {
                    //查找是否有子菜单
                    int getCount=sysMenuService.getMenuByMenuCode(menuCode);
                    //为零可以删除
                    if(getCount==0){
                        try {
                            System.out.println(menuCode);
                            sysMenuService.delMenuByMenuCode(menuCode);
                            return ajaxResult.success("删除菜单成功");
                        }catch (Exception e){
                            logger.error("删除菜单失败",e);
                            return AjaxResult.error("删除菜单失败");
                        }
                        //否则不能删除
                    }else{
                        return AjaxResult.error("目录下有菜单，删除失败");
                    }
                }catch (Exception e){
                    return AjaxResult.error("删除菜单失败");
                }
            }

        }

    }
}
