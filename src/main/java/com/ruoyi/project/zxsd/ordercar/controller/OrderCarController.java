package com.ruoyi.project.zxsd.ordercar.controller;


import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.zxsd.ordercar.domain.OrderCarEntity;
import com.ruoyi.project.zxsd.ordercar.service.IOrderCarService;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
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
@RequestMapping("/orderCar")
@Api(tags = {"【APP端】11.1 购物车"}, description = "购物车")
public class OrderCarController extends BaseController {

    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     */
    private final IOrderCarService orderCarService;

    @Autowired
    public OrderCarController(IOrderCarService orderCarService) {
        this.orderCarService = orderCarService;
    }
    /**
     * 添加购物车
     */
    @Log(title = "12.1.1 添加购物车", businessType = BusinessType.INSERT)
    @PostMapping("/addHotGoods")
    @ApiOperation(value = "12.1.1 添加购物车", notes = "添加购物车")
    public AjaxResult addHotGoods(@RequestBody OrderCarEntity orderCarEntity) {
        //判断权限
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        int userRole = user.getRole();
        if(userRole!=3&&userRole!=0){
            return AjaxResult.success("权限不足");
        }
        List<OrderCarEntity> menuList;
        try {
             menuList = orderCarService.getOrderCar(orderCarEntity);
        }catch (Exception e){
            logger.error("查询购物车失败",e);
            return AjaxResult.error("查询购物车失败");
        }
        try{
            //新增菜单
            if(menuList.size()==0){
                orderCarService.addOrderCar(orderCarEntity);
            }else {
                orderCarEntity.setCnt(orderCarEntity.getCnt()+menuList.get(0).getCnt());
                orderCarService.editOrderCar(orderCarEntity);
            }
            return AjaxResult.success("加入购物车成功");
        }catch (Exception e){
            logger.error("加入购物车失败",e);
            return AjaxResult.error("加入购物车失败");
        }
    }

    /**
     * 查询购物车
     */
    @Log(title = "12.1.2 查询购物车", businessType = BusinessType.INSERT)
    @PostMapping("/getOrderCar")
    @ApiOperation(value = "12.1.2 查询购物车", notes = "查询购物车")
    public AjaxResult getOrderCar(@RequestBody OrderCarEntity orderCarEntity) {
        AjaxResult ajaxResult = null;
        //获取当前登录人的基本信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        String userCode = user.getUserId();
        orderCarEntity.setUserCode(userCode);
        List<OrderCarEntity> menuList = orderCarService.getOrderCar(orderCarEntity);
        ajaxResult = AjaxResult.success("查询成功",menuList);
        return  ajaxResult;

    }
    /**
     * 删除购物车
     */
    @Log(title = "12.1.3 删除购物车", businessType = BusinessType.INSERT)
    @PostMapping("/delOrderCar")
    @ApiOperation(value = "12.1.3 删除购物车", notes = "删除购物车")
    public AjaxResult delOrderCar(@RequestBody List<OrderCarEntity> orderCarEntity) {
        try{
            if(orderCarEntity != null){
                orderCarService.delOrderCar(orderCarEntity);
                return AjaxResult.success("删除购物车成功");
            }else {
                logger.error("删除购物车失败，解析后集合为空");
                return AjaxResult.error("删除购物车失败");
            }
        }catch (Exception e){
            logger.error("删除购物车失败",e);
            return AjaxResult.error("删除购物车失败");
        }
    }

    /**
     * 更新数量
     */
    @Log(title = "12.1.4 更新操作", businessType = BusinessType.INSERT)
    @PostMapping("/editOrderCar")
    @ApiOperation(value = "12.1.4 更新操作", notes = "更新操作")
    public AjaxResult editOrderCar(@RequestBody OrderCarEntity orderCarEntity) {
        try{
            orderCarService.editOrderCar(orderCarEntity);
            return new AjaxResult().success("更新数量成功");
        }catch (Exception e){
            logger.error("更新数量失败",e);
            return new AjaxResult().error("更新数量失败");
        }

    }
}
