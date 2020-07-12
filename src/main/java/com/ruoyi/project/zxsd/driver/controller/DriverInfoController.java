package com.ruoyi.project.zxsd.driver.controller;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.zxsd.driver.domain.DriverInfoEntity;
import com.ruoyi.project.zxsd.driver.service.IDriverInfoService;
import com.ruoyi.project.zxsd.store.domain.StoreInfoEntity;
import com.ruoyi.project.zxsd.store.service.IStoreInfoService;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
import com.ruoyi.project.zxsd.sys.service.impl.SystemUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台端门店Controller
 *
 * @author wangdong
 * @date 2020.04.30
 */
@RestController
@RequestMapping("/driver")
@Api(tags = {"【后台端】5.1 司机管理"}, description = "司机管理管理")
public class DriverInfoController extends BaseController {
    private final IDriverInfoService driverInfoService;
    /**
     * 门店Mapper
     */
    private final IStoreInfoService storeInfoService;

    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final SystemUserServiceImpl systemUserService;

    @Autowired
    public DriverInfoController(IDriverInfoService driverInfoService, IStoreInfoService storeInfoService, SystemUserServiceImpl systemUserService) {
        this.driverInfoService = driverInfoService;
        this.storeInfoService = storeInfoService;
        this.systemUserService = systemUserService;
    }

    /**
     * 新增司机
     */
    @Log(title = "5.1.1 新增司机", businessType = BusinessType.INSERT)
    @PostMapping("/addDriver")
    @ApiOperation(value = "5.1.1 新增司机", notes = "新增司机")
    public AjaxResult addDriver(@RequestBody DriverInfoEntity driverInfoEntity) {
        String userName = driverInfoEntity.getUserName();
        if(StringUtils.isEmpty(userName)){
            return AjaxResult.error("司机账号不能为空");
        }
        String storeNo = driverInfoEntity.getStoreNo();
        if(StringUtils.isEmpty(storeNo)){
            return AjaxResult.error("门店编号不能为空");
        }
        String provinceNo = driverInfoEntity.getProvinceNo();
        if(StringUtils.isEmpty(provinceNo)){
            return AjaxResult.error("省不能为空");
        }
        String countyNo = driverInfoEntity.getCountyNo();
        if(StringUtils.isEmpty(countyNo)){
            return AjaxResult.error("市区不能为空");
        }
        //获取当前登入人信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();

        if((0==systemUserEntity.getIsAdmin())&& (1==systemUserEntity.getRole())) {
            //查询当前登录人的门店信息
            StoreInfoEntity storeInfoEntity;
            try {
                storeInfoEntity = storeInfoService.getStoreInfoByUserCode(systemUserEntity.getUserId());
                if(!storeNo.equals(storeInfoEntity.getStoreNo())){
                    return new AjaxResult().error("查询店长失败或该店长门下无店铺");
                }
                SystemUserEntity getUser = new SystemUserEntity();
                getUser.setUserName(userName);
                getUser.setRole(2);
                List<SystemUserEntity> userDetail = systemUserService.getUser(getUser);

                int count = userDetail.size();
                if(count!=1){
                    return new AjaxResult().error("无该司机用户");
                }else{
                    driverInfoEntity.setUserId(userDetail.get(0).getUserId());
                }
            } catch (Exception e) {
                logger.error("查询店长失败", e);
                return new AjaxResult().error("查询店长失败");
            }
        }else {
                return new AjaxResult().error("非店长无法操作");
        }
        try{
            driverInfoService.addDriver(driverInfoEntity);
            return new AjaxResult().success("新增司机成功");
        }catch (Exception e){
            logger.error("新增司机失败",e);
            return new AjaxResult().error("新增司机失败");
        }
    }

    /**
     * 查询
     */
    @Log(title = "5.1.2 查询司机", businessType = BusinessType.INSERT)
    @PostMapping("/getDriver")
    @ApiOperation(value = "5.1.2 查询司机", notes = "查询司机")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页显示记录数", defaultValue = "10")
    })
    public TableDataInfo getDriver(@RequestBody DriverInfoEntity driverInfoEntity) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        if((0==systemUserEntity.getIsAdmin())&& (1<systemUserEntity.getRole())) {
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.ERROR);
            rspData.setTotal(0L);
            rspData.setMsg("权限不足，查询司机失败");
            return rspData;
        }
        try{
            List<DriverInfoEntity> driverInfoEntityList= driverInfoService.getDriver(driverInfoEntity);
            return getDataTable(driverInfoEntityList);
        }catch (Exception e){
            logger.error("查询司机失败",e);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.ERROR);
            rspData.setTotal(0L);
            rspData.setMsg("查询司机失败");
            return rspData;
        }
    }

    /**
     * 详细查询
     */
    @Log(title = "5.1.3 详细司机查询", businessType = BusinessType.INSERT)
    @PostMapping("/getDriverMsg")
    @ApiOperation(value = "5.1.3 详细司机查询", notes = "详细司机查询")
    public AjaxResult getDriverMsg(@RequestBody DriverInfoEntity driverInfoEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        if((0==systemUserEntity.getIsAdmin())&& (1<systemUserEntity.getRole())) {
            return new AjaxResult().error("用户权限权限不足");
        }
        try{
            List<DriverInfoEntity> driverInfoEntityList= driverInfoService.getDriver(driverInfoEntity);
            driverInfoEntity = driverInfoEntityList.get(0);
            return new AjaxResult().success("查询成功",driverInfoEntity);
        }catch (Exception e){
            logger.error("查询司机详情失败",e);
            return new AjaxResult().error("查询司机详情失败");
        }
    }

    /**
     * 新增司机
     */
    @Log(title = "5.1.4 修改司机", businessType = BusinessType.INSERT)
    @PostMapping("/editDriver")
    @ApiOperation(value = "5.1.4 修改司机", notes = "修改司机")
    public AjaxResult editDriver(@RequestBody DriverInfoEntity driverInfoEntity) {
        String userId = driverInfoEntity.getUserId();
        if(StringUtils.isEmpty(userId)){
            return AjaxResult.error("未选择一条数据");
        }
        //获取当前登入人信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity systemUserEntity = loginUser.getSystemUserEntity();
        if((0==systemUserEntity.getIsAdmin())&& (1<systemUserEntity.getRole())) {

            return new AjaxResult().error("权限不足");
        }
        try{
            driverInfoService.editDriver(driverInfoEntity);
            return new AjaxResult().success("修改司机信息成功");
        }catch (Exception e){
            logger.error("新增司机失败",e);
            return new AjaxResult().error("修改司机信息成功");
        }
    }

    /**
     * 删除司机
     */
    @Log(title = "5.1.5 删除司机", businessType = BusinessType.INSERT)
    @PostMapping("/delDriver")
    @ApiOperation(value = "5.1.5 删除司机", notes = "删除司机")
    public AjaxResult delDriver(@RequestBody List<DriverInfoEntity> driverInfoEntity) {
        String id = driverInfoEntity.get(0).getId();
        if(StringUtils.isEmpty(id)){
            return AjaxResult.error("未选择一条数据");
        }
        try{
            driverInfoService.delDriver(driverInfoEntity);
        }catch (Exception e){
            logger.error("删除店铺失败",e);
            return new AjaxResult().error("删除司机失败");
        }
        return new AjaxResult().success("删除司机成功");
    }
}
