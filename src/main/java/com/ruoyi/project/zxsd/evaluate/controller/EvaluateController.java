package com.ruoyi.project.zxsd.evaluate.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.zxsd.evaluate.domain.EvaluateEntity;
import com.ruoyi.project.zxsd.evaluate.service.IEvaluateService;
import io.swagger.annotations.Api;
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
@RequestMapping("/evaluate")
@Api(tags = {"【App端】12.1 评价"}, description = "评价")
public class EvaluateController extends BaseController {
    private final IEvaluateService evaluateService;

    @Autowired
    public EvaluateController(IEvaluateService evaluateService) {
        this.evaluateService = evaluateService;
    }


    /**
     * 新增评价
     */
    @Log(title = "12.1.1 新增评价", businessType = BusinessType.INSERT)
    @PostMapping("/addEvaluate")
    @ApiOperation(value = "12.1.1 新增评价", notes = "新增评价")
    public AjaxResult addEvaluate(@RequestBody EvaluateEntity evaluateEntity) {
        try{
            //新增菜单
            evaluateService.addEvaluate(evaluateEntity);
            return AjaxResult.success("评价成功");
        }catch (Exception e){
            logger.error("评价失败",e);
            return AjaxResult.error("评价失败");
        }
    }


    /**
     * 显示评价
     */
    @Log(title = "12.1.2 显示评价", businessType = BusinessType.INSERT)
    @PostMapping("/getEvaluate")
    @ApiOperation(value = "12.1.2 显示评价", notes = "显示评价")
    public AjaxResult getEvaluate(@RequestBody EvaluateEntity evaluateEntity) {
        //获取当前登录人的基本信息
        try{
            //查询司机
            List<EvaluateEntity> menuList = evaluateService.getEvaluate(evaluateEntity);
            return   AjaxResult.success("查询成功",menuList);
        }catch (Exception e){
            logger.error("查询失败",e);
            return  AjaxResult.error("查询失败");
        }
    }



}
