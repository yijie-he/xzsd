package com.ruoyi.project.zxsd.store.controller;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.zxsd.store.domain.CountyEntity;
import com.ruoyi.project.zxsd.store.domain.ProvinceEntity;
import com.ruoyi.project.zxsd.store.domain.StoreInfoEntity;
import com.ruoyi.project.zxsd.store.service.IStoreInfoService;
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
 * 后台端门店Controller
 *
 * @author wangdong
 * @date 2020.04.30
 */
@RestController
@RequestMapping("/store")
@Api(tags = {"【后台端】4.1 门店管理"}, description = "门店管理")
public class StoreInfoController extends BaseController {
    private final IStoreInfoService storeInfoService;
    private final ISystemUserService systemUserService;
    @Autowired
    public StoreInfoController(IStoreInfoService storeInfoService, ISystemUserService systemUserService) {
        this.storeInfoService = storeInfoService;
        this.systemUserService = systemUserService;
    }

    /**
     * 查询省
     */
    @Log(title = "4.1.1 查询省", businessType = BusinessType.INSERT)
    @PostMapping("/getProvince")
    @ApiOperation(value = "4.1.1 查询省", notes = "查询省")
    public AjaxResult getProvince() {
        AjaxResult ajaxResult = null;
        try{
            //省信息查询·
            List<ProvinceEntity> provinceList = storeInfoService.getProvince();
            return new AjaxResult().success("省信息查询成功",provinceList);
        }catch (Exception e){
            logger.error("省信息查询失败",e);
            return new AjaxResult().error("省信息查询失败");
        }
    }

    /**
     * 查询区县
     */
    @Log(title = "4.1.2 查询区县", businessType = BusinessType.INSERT)
    @PostMapping("/getCounty")
    @ApiOperation(value = "4.1.2 查询区县", notes = "查询区县")
    public AjaxResult getCounty(@RequestBody ProvinceEntity provinceEntity) {
        AjaxResult ajaxResult = null;
        String provinceCode = provinceEntity.getProvinceCode();
        try{
            //省信息查询
            List<CountyEntity> countyList = storeInfoService.getCounty(provinceCode);
            return new AjaxResult().success("区县信息查询成功",countyList);
        }catch (Exception e){
            logger.error("省信息查询失败",e);
            return new AjaxResult().error("区县信息查询失败");
        }
    }

    /**
     * 查询区县
     */
    @Log(title = "4.1.3 查询店铺", businessType = BusinessType.OTHER)
    @PostMapping("/getListStoreInfo")
    @ApiOperation(value = "4.1.3 查询店铺", notes = "查询店铺")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页显示记录数", defaultValue = "10")
    })
    public TableDataInfo getListStoreInfo(@RequestBody StoreInfoEntity storeInfoEntity) {
        startPage();
        try{
            //省信息查询
            List<StoreInfoEntity> storeList = storeInfoService.getListStoreInfo(storeInfoEntity);
            return getDataTable(storeList);
        }catch (Exception e){
            logger.error("省信息查询失败",e);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.ERROR);
            rspData.setTotal(0L);
            rspData.setMsg("省信息查询失败");
            return rspData;
        }
    }

    /**
     * 查看详情
     */
    @Log(title = "4.1.4 查看详情", businessType = BusinessType.INSERT)
    @PostMapping("/getDetail")
    @ApiOperation(value = "4.1.4 查看详情", notes = "查看详情")
    public AjaxResult getDetail(String id) {
        AjaxResult ajaxResult = null;
        try{
            //省信息查询
            StoreInfoEntity getStoreEntity = storeInfoService.getDetail(id);
            return new AjaxResult().success("查看详情成功",getStoreEntity);
        }catch (Exception e){
            logger.error("省信息查询失败",e);
            return new AjaxResult().error("查看详情失败");
        }
    }

    /**
     * 查询门店详情
     */
    @Log(title = "4.1.6 查询门店详情", businessType = BusinessType.INSERT)
    @PostMapping("/getDetails")
    @ApiOperation(value = "4.1.6 查询门店详情", notes = "查询门店详情")
    public AjaxResult getDetails() {
        try{
            //省信息查询
            List<StoreInfoEntity> getStoreEntity = storeInfoService.getDetails();
            return new AjaxResult().success("查看详情成功",getStoreEntity);
        }catch (Exception e){
            logger.error("省信息查询失败",e);
            return new AjaxResult().error("查看详情失败");
        }
    }

    /**
     * 新增店铺
     */
    @Log(title = "4.1.5 新增店铺", businessType = BusinessType.INSERT)
    @PostMapping("/addStore")
    @ApiOperation(value = "4.1.5 新增店铺", notes = "新增店铺")
    public AjaxResult addStore(@RequestBody StoreInfoEntity storeInfoEntity) {
        AjaxResult ajaxResult = null;
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        if(user.getRole()!=0){
            return new AjaxResult().error("权限不足");
        }
        String storeName = storeInfoEntity.getStoreName();
        if(StringUtils.isEmpty(storeName)){
            return new AjaxResult().error("门店名称不能为空");
        }
        String storePhone = storeInfoEntity.getStorePhone();
        if(StringUtils.isEmpty(storePhone)){
            return new AjaxResult().error("联系电话不能为空");
        }
        String userCode = storeInfoEntity.getUserCode();
        if(StringUtils.isEmpty(userCode)){
            return new AjaxResult().error("店长编号为空");
        }
        String businessLicense = storeInfoEntity.getBusinessLicense();
        if(StringUtils.isEmpty(businessLicense)){
            return new AjaxResult().error("营业执照编号不能为空");
        }
        String provinceNo = storeInfoEntity.getProvinceNo();
        if(StringUtils.isEmpty(provinceNo)){
            return new AjaxResult().error("省不能为空");
        }
        String countyNo = storeInfoEntity.getCountyNo();
        if(StringUtils.isEmpty(countyNo)){
            return new AjaxResult().error("区不能为空");
        }
        String storeAddress = storeInfoEntity.getStoreAddress();
        if(StringUtils.isEmpty(storeAddress)){
            return new AjaxResult().error("地址不能为空");
        }
        try{
            try {
                StoreInfoEntity getStore = storeInfoService.getStoreInfoByUserCode(userCode);
                SystemUserEntity getUser = new SystemUserEntity();
                getUser.setUserId(userCode);
                getUser.setRole(1);
                List<SystemUserEntity> userDetail = systemUserService.getUser(getUser);
                int count = userDetail.size();
                if(count!=1){
                    return new AjaxResult().error("无该店长用户");
                }
                try {
                    if(StringUtils.isNotEmpty(getStore.getStoreNo())){
                        return new AjaxResult().success("该店长门下已有店铺，新增店铺失败");
                    }
                }catch (Exception e){
                    logger.warn("店长门下无店铺",e);
                }

            }catch (Exception e){
                logger.error("查询店长店铺失败",e);
                return new AjaxResult().error("查询店长店铺失败");
            }
            storeInfoService.addStore(storeInfoEntity);
            return new AjaxResult().success("新增店铺成功");
        }catch (Exception e){
            logger.error("新增店铺失败",e);
            return new AjaxResult().error("新增店铺失败");
        }
    }

    /**
     * 修改店铺
     */
    @Log(title = "4.1.6 修改店铺", businessType = BusinessType.INSERT)
    @PostMapping("/editStore")
    @ApiOperation(value = "4.1.6 修改店铺", notes = "修改店铺")
    public AjaxResult editStore(@RequestBody StoreInfoEntity storeInfoEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        if(user.getRole()!=0){
            return new AjaxResult().error("权限不足");
        }
        String storeName = storeInfoEntity.getStoreName();
        if(StringUtils.isEmpty(storeName)){
            return AjaxResult.error("门店名称不能为空");
        }
        String storePhone = storeInfoEntity.getStorePhone();
        if(StringUtils.isEmpty(storePhone)){
            return AjaxResult.error("联系电话不能为空");
        }
        String userCode = storeInfoEntity.getUserCode();
        if(StringUtils.isEmpty(userCode)){
            return AjaxResult.error("店长编号为空");
        }
        String businessLicense = storeInfoEntity.getBusinessLicense();
        if(StringUtils.isEmpty(businessLicense)){
            return AjaxResult.error("营业执照编号不能为空");
        }
        String province = storeInfoEntity.getProvince();
        if(StringUtils.isEmpty(province)){
            return AjaxResult.error("省不能为空");
        }
        String county = storeInfoEntity.getCounty();
        if(StringUtils.isEmpty(county)){
            return AjaxResult.error("区不能为空");
        }
        String storeAddress = storeInfoEntity.getStoreAddress();
        if(StringUtils.isEmpty(storeAddress)){
            return AjaxResult.error("地址不能为空");
        }
        try{
            storeInfoService.editStore(storeInfoEntity);
            return new AjaxResult().success("修改店铺成功");
        }catch (Exception e){
            logger.error("修改店铺失败",e);
            return new AjaxResult().error("修改店铺失败");
        }
    }

    /**
     * 删除店铺
     */
    @Log(title = "4.1.7 删除店铺", businessType = BusinessType.INSERT)
    @PostMapping("/delStore")
    @ApiOperation(value = "4.1.7 删除店铺", notes = "删除店铺")
    public AjaxResult delStore(@RequestBody List<StoreInfoEntity> storeInfoEntity) {
        String id = storeInfoEntity.get(0).getId();
        if(StringUtils.isEmpty(id)){
            return AjaxResult.error("未选择一条数据");
        }
        AjaxResult ajaxResult = null;
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        if(user.getRole()!=0){
            return new AjaxResult().error("权限不足");
        }
        try{
            storeInfoService.delStore(storeInfoEntity);
        }catch (Exception e){
            logger.error("删除店铺失败",e);
            return new AjaxResult().error("删除店铺失败");
        }
        return new AjaxResult().success("删除店铺成功");
    }
}
