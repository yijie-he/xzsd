package com.ruoyi.project.zxsd.goodscate.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.zxsd.goodscate.domain.GoodsCateEntity;
import com.ruoyi.project.zxsd.goodscate.service.IGoodsCateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品分类对象Controller
 *
 * @author wangdong
 * @date 2020.04.30
 */
@RestController
@RequestMapping("/goodscate")
@Api(tags = {"【后台端】8.1 商品分类管理"}, description = "商品分类管理")
public class GoodsCateController extends BaseController {
    private final IGoodsCateService goodsCateService;
    @Autowired
    public GoodsCateController(IGoodsCateService goodsCateService) {
        this.goodsCateService = goodsCateService;
    }

    /**
     * 新增商品分类
     */
    @Log(title = "8.1.1 新增商品分类", businessType = BusinessType.INSERT)
    @PostMapping("/addGoodsCate")
    @ApiOperation(value = "8.1.1 新增商品分类", notes = "新增商品分类")
    public AjaxResult addGoodsCate(@RequestBody GoodsCateEntity goodsCateEntity) {

        String cateName = goodsCateEntity.getCateName();
        if(StringUtils.isEmpty(cateName)){
            return new AjaxResult().error("分类名称不能为空");
        }

        try{
            //新增菜单
            goodsCateService.addGoodsCate(goodsCateEntity);
            return new AjaxResult().success("新增商品分类成功");
        }catch (Exception e){
            logger.error("菜单新增失败",e);
            return new AjaxResult().error("新增商品分类失败");
        }
    }

    /**
     * 查询商品分类
     */
    @Log(title = "8.1.2 查询商品分类", businessType = BusinessType.INSERT)
    @PostMapping("/getGoodsCate")
    @ApiOperation(value = "8.1.2 查询商品分类", notes = "查询商品分类")
    public AjaxResult getGoodsCate() {
        AjaxResult ajaxResult = null;
        List<GoodsCateEntity> menuList = goodsCateService.getGoodsCate();
        return ajaxResult.success("查询成功",menuList);

    }

    /**
     * 查询商品分类
     */
    @Log(title = "8.1.5 查询商品单个分类", businessType = BusinessType.INSERT)
    @PostMapping("/getGoodCate")
    @ApiOperation(value = "8.1.5 查询商品单个分类", notes = "查询商品单个分类")
    public AjaxResult getGoodCate(@RequestBody GoodsCateEntity goodsCateEntity) {
        AjaxResult ajaxResult = null;
        List<GoodsCateEntity> menuList = goodsCateService.getGoodCate(goodsCateEntity);
        return ajaxResult.success("查询成功",menuList);

    }

    /**
     * 查询商品父分类
     */
    @Log(title = "8.1.6 查询商品父分类", businessType = BusinessType.INSERT)
    @PostMapping("/getGoodCates")
    @ApiOperation(value = "8.1.6 查询商品父分类", notes = "查询商品父分类")
    public AjaxResult getGoodCates() {
        AjaxResult ajaxResult = null;
        List<GoodsCateEntity> menuList = goodsCateService.getGoodCates();
        return ajaxResult.success("查询成功",menuList);

    }

    /**
     * 修改商品分类
     */
    @Log(title = "8.1.3 修改商品分类", businessType = BusinessType.INSERT)
    @PostMapping("/editGoodsCate")
    @ApiOperation(value = "8.1.3 修改商品分类", notes = "修改商品分类")
    public AjaxResult editGoodsCate(@RequestBody GoodsCateEntity goodsCateEntity) {
        AjaxResult ajaxResult = null;
        String cateName = goodsCateEntity.getCateName();
        if(StringUtils.isEmpty(cateName)){
            return new AjaxResult().error("分类名称不能为空");
        }
        String id = goodsCateEntity.getId();
        if(StringUtils.isEmpty(id)){
            return new AjaxResult().error("未选择分类");
        }
        try {
            goodsCateService.editGoodsCate(goodsCateEntity);
        }catch (Exception e){
            logger.error("修改商品分类失败",e);
            return new AjaxResult().error("修改商品分类失败");
        }
        return ajaxResult.success("修改商品分类成功");
    }

    /**
     * 删除商品分类
     */
    @Log(title = "8.1.4 删除商品分类", businessType = BusinessType.INSERT)
    @PostMapping("/delGoodsCate")
    @ApiOperation(value = "8.1.4 删除商品分类", notes = "删除商品分类")
    public AjaxResult delGoodsCate(@RequestBody GoodsCateEntity goodsCateEntity) {
        AjaxResult ajaxResult = null;
        String cateCode = goodsCateEntity.getCateCode();
        if(StringUtils.isEmpty(cateCode)){
            return new AjaxResult().error("未选择分类");
        }
        int isParent = goodsCateEntity.getIsParent();
        if(isParent==1){
            return new AjaxResult().error("二级分类无法增加子分类");
        }
        try {
            int getCount = goodsCateService.sonGoodsCate(goodsCateEntity);
            if(getCount>0){
                return new AjaxResult().success("该分类下有子类无法删除");
            }
        }catch (Exception e){
            logger.error("查找商品子类失败",e);
            return new AjaxResult().error("查找商品子类失败");
        }
        try {
            goodsCateService.delGoodsCate(goodsCateEntity);
        }catch (Exception e){
            logger.error("删除商品分类失败",e);
            return new AjaxResult().error("删除商品分类失败");
        }
        return ajaxResult.success("删除商品分类成功");
    }

    /**
     * 查询商品分类名称
     */
    @Log(title = "8.1.7 查询商品分类名称", businessType = BusinessType.INSERT)
    @PostMapping("/getGoodsCateName")
    @ApiOperation(value = "8.1.7 查询商品分类名称", notes = "查询商品分类名称")
    public AjaxResult getGoodsCateName(@RequestBody GoodsCateEntity goodsCateEntity) {
        AjaxResult ajaxResult = null;
        String cateCode = goodsCateEntity.getCateCode();
        if(StringUtils.isEmpty(cateCode)){
            return new AjaxResult().error("未选择分类");
        }
        try {
            GoodsCateEntity goodsCate = goodsCateService.getGoodsCateName(goodsCateEntity);
            return ajaxResult.success("查询商品分类名称成功",goodsCate);
        }catch (Exception e){
            logger.error("查询商品分类名称失败",e);
            return new AjaxResult().error("查询商品分类名称失败");
        }

    }
}
