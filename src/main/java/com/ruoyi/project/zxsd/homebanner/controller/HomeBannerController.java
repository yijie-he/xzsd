package com.ruoyi.project.zxsd.homebanner.controller;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.zxsd.homebanner.domain.HomeBannerEntity;
import com.ruoyi.project.zxsd.homebanner.service.IHomeBannerInfoService;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 后台端用户Controller
 *
 * @author wangdong
 * @date 2020.04.30
 */
@RestController
@RequestMapping("/homebanner")
@Api(tags = {"【后台端】7.1 轮播图管理"}, description = "轮播图管理")
public class HomeBannerController extends BaseController {

    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     */
    private final ServerConfig serverConfig;

    private final IHomeBannerInfoService homeBannerInfoService;

    @Autowired
    public HomeBannerController(ServerConfig serverConfig,IHomeBannerInfoService homeBannerInfoService) {
        this.serverConfig = serverConfig;
        this.homeBannerInfoService = homeBannerInfoService;
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 结果
     * @throws Exception 异常
     */
    @Log(title = "7.1.1 上传", businessType = BusinessType.OTHER)
    @PostMapping("/upload")
    @ApiOperation(value = "7.1.1 上传", notes = "文件上传")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            //获取文件名后缀
            String exName = FileUploadUtils.getExtension(file);

            String[] a = MimeTypeUtils.IMAGE_EXTENSION;
            boolean flag = false;
            for(int i = 0;i<a.length;i++){
                if(a[i].equals(exName)){
                    flag = true;
                }
            }
            if(!flag){
                return AjaxResult.error("文件类型不合法");
            }

            // 文件路径
            String fileName = FileUploadUtils.upload(filePath, file);
            // 完整的请求路径
            String url = serverConfig.getUrl() + fileName;
            // 返回成功消息
            AjaxResult ajax = AjaxResult.success();
            // 文件路径放入map
            ajax.put("fileName", fileName);
            // 完整的请求路径放入map
            ajax.put("url", url);
            // 返回
            return ajax;
        } catch (Exception e) {
            // 返回错误信息
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 新增轮播图
     */
    @Log(title = "7.1.2 新增轮播图", businessType = BusinessType.INSERT)
    @PostMapping("/addHomeBanner")
    @ApiOperation(value = "7.1.2 新增轮播图", notes = "新增轮播图")
    public AjaxResult addHomeBanner(@RequestBody HomeBannerEntity homeBannerEntity) {
        //判断权限
        String imgAddress = homeBannerEntity.getPicUrl();
        if(StringUtils.isEmpty(imgAddress)){
            return AjaxResult.error("访问路径不能为空");
        }
        String skuNo = homeBannerEntity.getSkuNo();
        if(StringUtils.isEmpty(skuNo)){
            return AjaxResult.error("关联商品不能为空不能为空");
        }

        Date startTime = homeBannerEntity.getStartTime();
        if(null == startTime){
            return AjaxResult.error("图片上架时间不能为空");
        }
        Date endDateTime = homeBannerEntity.getEndDateTime();
        if(null == endDateTime){
            return AjaxResult.error("图片下架时间不能为空");
        }

        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        int userRole = user.getRole();
        if(userRole!=1&&userRole!=0){
            return AjaxResult.success("权限不足");
        }
        try{

            homeBannerInfoService.addHomeBanner(homeBannerEntity);
            return AjaxResult.success("轮播图新增成功");
        }catch (Exception e){
            logger.error("轮播图新增失败",e);
            return AjaxResult.error("轮播图新增失败");
        }
    }

    /**
     * 查询轮播图
     */
    @Log(title = "7.1.3 查询轮播图", businessType = BusinessType.INSERT)
    @PostMapping("/getHomeBanner")
    @ApiOperation(value = "7.1.3 查询轮播图", notes = "查询轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页显示记录数", defaultValue = "10")
    })
    public TableDataInfo getHomeBanner(@RequestBody HomeBannerEntity homeBannerEntity) {
        Integer picStatus = homeBannerEntity.getPicStatus();
        startPage();
        try{
            List<HomeBannerEntity> homeBannerEntitylist = homeBannerInfoService.getHomeBanner(picStatus);
            return getDataTable(homeBannerEntitylist);
        }catch (Exception e){
            logger.error("轮播图查询失败",e);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.ERROR);
            rspData.setTotal(0L);
            rspData.setMsg("轮播图查询失败");
            return rspData;
        }
    }

    /**
     * 删除轮播图
     */
    @Log(title = "7.1.4 删除轮播图", businessType = BusinessType.INSERT)
    @PostMapping("/delHomeBanner")
    @ApiOperation(value = "7.1.4 删除轮播图", notes = "删除轮播图")
    @Transactional
    public AjaxResult delHomeBanner(@RequestBody List<HomeBannerEntity> homeBannerEntity) {
        //判断权限
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        int userRole = user.getRole();
        if(userRole!=1&&userRole!=0) {
            return AjaxResult.success("权限不足");
        }
        if(homeBannerEntity.get(0).getId()==null){
            return AjaxResult.error("请选择要删除的轮播图");
        }
        try{
            for(int i=0;i<homeBannerEntity.size();i++){
                try {
                    String id = homeBannerEntity.get(i).getId();
                    homeBannerInfoService.delHomeBanner(id);
                }catch (Exception e){
                    return AjaxResult.error("轮播图删除失败");
                }
            }
            return AjaxResult.success("轮播图删除成功");
        }catch (Exception e){
            logger.error("轮播图删除失败",e);
            return AjaxResult.error("轮播图删除失败");
        }
    }

    /**
     * 启用轮播图
     */
    @Log(title = "7.1.5 启用轮播图", businessType = BusinessType.INSERT)
    @PostMapping("/trueHomeBanner")
    @ApiOperation(value = "7.1.5 启用轮播图", notes = "启用轮播图")
    @Transactional
    public AjaxResult trueHomeBanner(@RequestBody List<HomeBannerEntity> homeBannerEntity) {
        //判断权限
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        int userRole = user.getRole();
        if(userRole!=1&&userRole!=0){
            return AjaxResult.success("权限不足");
        }
        if(homeBannerEntity.get(0).getId()==null){
            return AjaxResult.error("请选择要启用的轮播图");
        }
        try{
            for(int i=0;i<homeBannerEntity.size();i++){
                try {
                    String id = homeBannerEntity.get(i).getId();
                    homeBannerInfoService.trueHomeBanner(id);
                }catch (Exception e){
                    return AjaxResult.error("轮播图启用失败");
                }
            }
            return AjaxResult.success("轮播图启用成功");
        }catch (Exception e){
            logger.error("轮播图启用失败",e);
            return AjaxResult.error("轮播图启用失败");
        }
    }

    /**
     * 禁用轮播图
     */
    @Log(title = "7.1.6 禁用轮播图", businessType = BusinessType.INSERT)
    @PostMapping("/falseHomeBanner")
    @ApiOperation(value = "7.1.6 禁用轮播图", notes = "禁用轮播图")
    @Transactional
    public AjaxResult falseHomeBanner(@RequestBody List<HomeBannerEntity> homeBannerEntity) {
        //判断权限
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        int userRole = user.getRole();
        if(userRole!=1&&userRole!=0){
            return AjaxResult.success("权限不足");
        }
        if(homeBannerEntity.get(0).getId()==null){
            return AjaxResult.error("请选择要禁用的轮播图");
        }
        try{
            for(int i=0;i<homeBannerEntity.size();i++){
                try {
                    String id = homeBannerEntity.get(i).getId();
                    homeBannerInfoService.falseHomeBanner(id);
                }catch (Exception e){
                    return AjaxResult.error("轮播图禁用失败");
                }
            }
            return AjaxResult.success("轮播图禁用成功");
        }catch (Exception e){
            logger.error("轮播图禁用失败",e);
            return AjaxResult.error("轮播图禁用失败");
        }
    }
}
