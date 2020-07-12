package com.ruoyi.project.zxsd.hotgoods.controller;


import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.zxsd.hotgoods.domain.HotGoodsEntity;
import com.ruoyi.project.zxsd.hotgoods.service.IHotGoodsService;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
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
 * @author gdz
 * @date 2020.04.30
 */
@RestController
@RequestMapping("/hotgoods")
@Api(tags = {"【后台端】10.1 热门商品"}, description = "热门商品")
public class HotGoodsController extends BaseController {

    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     */
    private final IHotGoodsService hotGoodsService;

    @Autowired
    public HotGoodsController(IHotGoodsService hotGoodsService) {
        this.hotGoodsService = hotGoodsService;
    }
    /**
     * 新增热门商品
     */
    @Log(title = "10.1.1 新增热门商品", businessType = BusinessType.INSERT)
    @PostMapping("/addhotGoods")
    @ApiOperation(value = "9.1.1 新增热门商品", notes = "新增热门商品")
    public AjaxResult addHotGoods(@RequestBody HotGoodsEntity hotGoodsEntity) {
        //判断权限
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        int userRole = user.getRole();
        if(userRole!=1&&userRole!=0){
            return AjaxResult.success("权限不足");
        }
        try{
            //新增菜单
            HotGoodsEntity hotGood = null;
            try {
                hotGood = hotGoodsService.getHotGood(hotGoodsEntity);
            }catch (Exception e){
                logger.error("查询热门商品失败", e);
                return AjaxResult.success("查询热门商品失败");
            }
            try {
                if(StringUtils.isNotEmpty(hotGood.getSkuNo())){
                    return AjaxResult.success("该商品已存在，无法插入");
                }
            }catch (Exception e){
                logger.error("表中无该商品数据，可插入", e);
            }

            hotGoodsService.addHotGoods(hotGoodsEntity);
            return AjaxResult.success("新增热门商品成功");
        }catch (Exception e){
            logger.error("新增热门商品失败",e);
            return AjaxResult.error("新增热门商品失败");
        }
    }

    /**
     * 删除热门商品
     */
    @Log(title = "10.1.2 删除热门商品", businessType = BusinessType.INSERT)
    @PostMapping("/delhotGoods")
    @ApiOperation(value = "10.1.2 删除热门商品", notes = "删除热门商品")
    public AjaxResult delhotGoods(@RequestBody List<HotGoodsEntity> hotGoodsEntity) {
        try{
            if(hotGoodsEntity != null){
                hotGoodsService.delhotGoods(hotGoodsEntity);
                return AjaxResult.success("删除热门商品成功");
            }else {
                logger.error("删除热门商品失败，解析后集合为空");
                return AjaxResult.error("删除热门商品失败");
            }
        }catch (Exception e){
            logger.error("删除热门商品失败",e);
            return AjaxResult.error("删除热门商品失败");
        }

    }

    /**
     * 查询热门商品
     */
    @Log(title = "10.1.3 查询热门商品", businessType = BusinessType.OTHER)
    @PostMapping("/getHotGoods")
    @ApiOperation(value = "10.1.3 查询热门商品", notes = "查询热门商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页显示记录数", defaultValue = "10")
    })
    public TableDataInfo getHotGoods(@RequestBody HotGoodsEntity hotGoodsEntity) {
        startPage();
        try{
            //查询司机
            List<HotGoodsEntity> hotGoods= hotGoodsService.getHotGoods(hotGoodsEntity);
            return getDataTable(hotGoods);
        }catch (Exception e){
            logger.error("热门商品信息查询失败",e);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.ERROR);
            rspData.setTotal(0L);
            rspData.setMsg("热门商品信息查询失败");
            return rspData;
        }
    }

    /**
     * 查询热门商品
     */
    @Log(title = "10.1.3 查询热门商品", businessType = BusinessType.OTHER)
    @PostMapping("/getHotGoodsT")
    @ApiOperation(value = "10.1.3 查询热门商品", notes = "查询热门商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页显示记录数", defaultValue = "10")
    })
    public TableDataInfo getHotGoodsT(@RequestBody HotGoodsEntity hotGoodsEntity) {
        startPage();
        try{
            //查询司机
            List<HotGoodsEntity> hotGoods= hotGoodsService.getHotGoodsT(hotGoodsEntity);
            return getDataTable(hotGoods);
        }catch (Exception e){
            logger.error("热门商品信息查询失败",e);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.ERROR);
            rspData.setTotal(0L);
            rspData.setMsg("热门商品信息查询失败");
            return rspData;
        }
    }
}
