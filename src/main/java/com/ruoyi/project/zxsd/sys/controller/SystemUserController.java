package com.ruoyi.project.zxsd.sys.controller;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.zxsd.store.domain.StoreInfoEntity;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
import com.ruoyi.project.zxsd.sys.service.ISystemUserService;
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
 * 后台端用户Controller
 *
 * @author wangdong
 * @date 2020.04.30
 */
@RestController
@RequestMapping("/systemuser")
@Api(tags = {"【后台端】3.1 用户管理"}, description = "用户管理")
public class SystemUserController extends BaseController {
    private final ISystemUserService systemUserService;
    @Autowired
    public SystemUserController(ISystemUserService systemUserService) {

        this.systemUserService = systemUserService;
    }

    /**
     * 新增用户
     */
    @Log(title = "3.1.1 新增用户", businessType = BusinessType.INSERT)
    @PostMapping("/adduser")
    @ApiOperation(value = "3.1.1 新增用户", notes = "新增用户")
    public AjaxResult adduser(@RequestBody SystemUserEntity systemUserEntity) {
        String userName = systemUserEntity.getUserName();
        if(StringUtils.isEmpty(userName)){
            return new AjaxResult().error("账号不能为空");
        }
        String userRealName = systemUserEntity.getUserRealname();
        if(StringUtils.isEmpty(userRealName)){
            return new AjaxResult().error("姓名不能为空");
        }
        String password = systemUserEntity.getPassword();
        if(StringUtils.isEmpty(password)){
            return new AjaxResult().error("密码不能为空");
        }
        String idCard = systemUserEntity.getIdCard();
        if(StringUtils.isEmpty(idCard)){
            return new AjaxResult().error("身份证号不能为空");
        }
//        String inviteCode = systemUserEntity.getInviteCode();
//        if(StringUtils.isEmpty(inviteCode)){
//            return new AjaxResult().error("邀请码不能为空");
//        }
        try{
            /**
             *查询用户是否存在
             */
            SystemUserEntity userInfo = systemUserService.getSystemUserByUserName(userName);
            if(userInfo != null){
                return new AjaxResult().error("用户已存在");
            }
        }catch (Exception e){
            logger.error("查询用户信息异常，新增用户失败",e);
            return new AjaxResult().error("查询用户信息异常，新增用户失败");
        }
        try{
            systemUserService.insertSystemUser(systemUserEntity);
        }catch (Exception e){
            logger.error("新增用户失败",e);
            return new AjaxResult().error("新增用户失败");
        }
        return new AjaxResult().success("新增用户成功");
    }
    /**
     * 修改用户
     */
    @Log(title = "3.1.2 修改用户", businessType = BusinessType.INSERT)
    @PostMapping("/edituser")
    @ApiOperation(value = "3.1.2 修改用户", notes = "修改用户")
    public AjaxResult edituser(@RequestBody SystemUserEntity systemUserEntity) {
        String userId = systemUserEntity.getUserId();
        if(StringUtils.isEmpty(userId)){
            return new AjaxResult().error("用户ID不能为空");
        }
        String userRealName = systemUserEntity.getUserRealname();
        if(StringUtils.isEmpty(userRealName)){
            return new AjaxResult().error("姓名不能为空");
        }
        String idCard = systemUserEntity.getIdCard();
        if(StringUtils.isEmpty(idCard)){
            return new AjaxResult().error("身份证号不能为空");
        }
        try{
            systemUserService.editSystemUser(systemUserEntity);
        }catch (Exception e){
            logger.error("修改用户失败",e);
            return new AjaxResult().error("修改用户失败");
        }
        return new AjaxResult().success("修改用户成功");
    }

    /**
     * 删除用户
     */
    @Log(title = "3.1.3 删除用户", businessType = BusinessType.INSERT)
    @PostMapping("/deluser")
    @ApiOperation(value = "3.1.3 删除用户", notes = "删除用户")
    public AjaxResult deluser(@RequestBody List<SystemUserEntity> systemUserEntity) {
        try{
            if(systemUserEntity==null){
                return new AjaxResult().error("用户ID不能为空");
            }
            systemUserService.deleteSystemUser(systemUserEntity);
        }catch (Exception e){
            logger.error("删除用户失败",e);
            return new AjaxResult().error("删除用户失败");
        }
        return new AjaxResult().success("删除用户成功");
    }

    /**
     * 查询用户
     */
    @Log(title = "3.1.4 查询用户", businessType = BusinessType.INSERT)
    @PostMapping("/finduser")
    @ApiOperation(value = "3.1.4 查询用户", notes = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页显示记录数", defaultValue = "10")
    })
    public TableDataInfo getuser(@RequestBody SystemUserEntity systemUserEntity) {
        startPage();
        try {
            List<SystemUserEntity> userList=systemUserService.getSystemUser(systemUserEntity);
            return getDataTable(userList);
        }catch (Exception e){
            logger.error("查询失败",e);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.ERROR);
            rspData.setTotal(0L);
            rspData.setMsg("查询失败");
            return rspData;
        }
    }

    /**
     * 查询用户
     */
    @Log(title = "3.1.5 查询用户", businessType = BusinessType.INSERT)
    @PostMapping("/findUser")
    @ApiOperation(value = "3.1.5 查询用户", notes = "查询用户")
    public AjaxResult getUser(@RequestBody SystemUserEntity systemUserEntity) {
        try{
            List<SystemUserEntity> getUser = systemUserService.getUser(systemUserEntity);
            return new AjaxResult().success("查询用户成功",getUser);
        }catch (Exception e){
            logger.error("查询用户失败",e);
            return new AjaxResult().error("查询用户失败");
        }
    }

    /**
     * 查询用户
     */
    @Log(title = "3.1.6 查询地址", businessType = BusinessType.INSERT)
    @PostMapping("/getAddress")
    @ApiOperation(value = "3.1.6 查询地址", notes = "查询地址")
    public AjaxResult getAddress() {
        try{
            StoreInfoEntity getUser = systemUserService.getAddress();
            return new AjaxResult().success("查询地址成功",getUser);
        }catch (Exception e){
            logger.error("查询用户失败",e);
            return new AjaxResult().error("查询地址失败");
        }
    }

    /**
     * 修改密码
     */
    @Log(title = "3.1.7 修改密码", businessType = BusinessType.INSERT)
    @PostMapping("/editPwd")
    @ApiOperation(value = "3.1.7 修改密码", notes = "修改密码")
    public AjaxResult editPwd(@RequestBody SystemUserEntity systemUserEntity) {
        if(StringUtils.isEmpty(systemUserEntity.getPassword())){
            return new AjaxResult().success("密码为空无法修改");
        }
        try{
            systemUserService.editPwd(systemUserEntity);
            return new AjaxResult().success("修改密码成功");
        }catch (Exception e){
            logger.error("查询用户失败",e);
            return new AjaxResult().error("修改密码失败");
        }
    }

    /**
     * 修改邀请码
     */
    @Log(title = "3.1.8 修改邀请码", businessType = BusinessType.INSERT)
    @PostMapping("/editInviteCode")
    @ApiOperation(value = "3.1.8 修改邀请吗", notes = "修改邀请码")
    public AjaxResult editInviteCode(@RequestBody SystemUserEntity systemUserEntity) {
        if(StringUtils.isEmpty(systemUserEntity.getInviteCode())){
            return new AjaxResult().success("新邀请码为空无法修改");
        }

        try {
            List<StoreInfoEntity> storeInfoEntity = systemUserService.getInviteCode(systemUserEntity);
            if(storeInfoEntity.size()!=1){
                return new AjaxResult().error("邀请码不存在");
            }
        }catch (Exception e){
            logger.error("查询店铺失败",e);
            return new AjaxResult().error("修改邀请码失败");
        }
        try{
            systemUserService.editInviteCode(systemUserEntity);
            return new AjaxResult().success("修改邀请码成功");
        }catch (Exception e){
            logger.error("修改邀请码失败",e);
            return new AjaxResult().error("修改邀请码失败");
        }
    }
}
