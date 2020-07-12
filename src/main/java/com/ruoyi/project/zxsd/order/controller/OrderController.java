package com.ruoyi.project.zxsd.order.controller;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.zxsd.goods.domain.GoodsEntity;
import com.ruoyi.project.zxsd.goods.mapper.GoodsInfoMapper;
import com.ruoyi.project.zxsd.order.domain.OrderDetailEntity;
import com.ruoyi.project.zxsd.order.domain.OrderEntity;
import com.ruoyi.project.zxsd.order.service.IOrderService;
import com.ruoyi.project.zxsd.order.util.ExcelOrderWriter;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 后台端门店Controller
 *
 * @author gdz
 * @date 2020.04.30
 */
@RestController
@RequestMapping("/order")
@Api(tags = {"【后台端】11.1 订单管理"}, description = "订单管理")
public class OrderController extends BaseController {

    private final IOrderService orderService;
    private final GoodsInfoMapper goodsMapper;

    @Autowired
    public OrderController(IOrderService orderService, GoodsInfoMapper goodsMapper) {
        this.orderService = orderService;
        this.goodsMapper = goodsMapper;
    }

    /**
     * 新增订单
     */
    @Log(title = "11.1.1 新增订单", businessType = BusinessType.INSERT)
    @PostMapping("/addOrder")
    @ApiOperation(value = "11.1.1 新增订单", notes = "新增订单")
    public AjaxResult addOrder(@RequestBody List<OrderDetailEntity> orderDetailEntity) {
        //判断权限
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        int userRole = user.getRole();
        if(userRole!=3&&userRole!=0){
            return AjaxResult.success("权限不足");
        }
        for(int i =0;i<orderDetailEntity.size();i++){
            OrderDetailEntity orderDetailEntity1 = orderDetailEntity.get(i);
            if(StringUtils.isEmpty(orderDetailEntity1.getSkuNo())){
                return AjaxResult.error("商品编号不能为空");
            }
            if(orderDetailEntity1.getGoodsCnt()<1){
                return AjaxResult.error("商品数量不能为空");
            }
        }
        try{
            for(int i =0;i<orderDetailEntity.size();i++){
                OrderDetailEntity orderDetailEntity2 = orderDetailEntity.get(i);
                String skuNo = orderDetailEntity2.getSkuNo();
                GoodsEntity goods =new GoodsEntity();
                goods.setSkuNo(skuNo);
                GoodsEntity goodsEntity = goodsMapper.getGood(goods);
                int amountCnt=goodsEntity.getAmountCnt();
                int goodsCnt = orderDetailEntity2.getGoodsCnt();
                if(goodsCnt>amountCnt){
                    return AjaxResult.error("库存不足");
                }
            }
            //新增菜单
            if(orderDetailEntity != null){
                orderService.addOrder(orderDetailEntity);
                return AjaxResult.success("订单新增成功");
            }else {
                logger.error("订单新增失败，解析后集合为空");
                return AjaxResult.error("订单新增失败");
            }
        }catch (Exception e){
            logger.error("订单新增失败",e);
            return AjaxResult.error("订单新增失败");
        }
    }


    /**
     * 查询订单
     */
    @Log(title = "11.1.2 查询订单", businessType = BusinessType.OTHER)
    @PostMapping("/getOrderList")
    @ApiOperation(value = "11.1.2 查询订单", notes = "查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页显示记录数", defaultValue = "10")
    })
    public TableDataInfo getOrderList(@RequestBody OrderEntity orderEntity) {
        startPage();
        try{
            //查询
            List<OrderEntity> driverList= orderService.getOrderList(orderEntity);
            return getDataTable(driverList);
        }catch (Exception e){
            logger.error("商品信息查询失败",e);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.ERROR);
            rspData.setTotal(0L);
            rspData.setMsg("商品信息查询失败");
            return rspData;
        }
    }

    /**
     * 查询菜单
     */
    @Log(title = "11.1.3 查询详情", businessType = BusinessType.INSERT)
    @PostMapping("/getOrderMenu")
    @ApiOperation(value = "11.1.3 查询详情", notes = "查询详情")
    public AjaxResult getOrderMenu(@RequestBody OrderDetailEntity orderDetailEntity) {
//        if(StringUtils.isEmpty(orderDetailEntity.getId())) {
//            String orderNo = orderDetailEntity.getOrderNo();
//            if (StringUtils.isEmpty(orderNo)) {
//                return AjaxResult.error("请选择一条数据");
//            }
//        }
        try{
            //查询
            List<OrderDetailEntity> driverList= orderService.getOrderMenu(orderDetailEntity);
          return   AjaxResult.success("查询成功",driverList);
        }catch (Exception e){
            logger.error("删除菜单失败",e);
            return   AjaxResult.error("查询失败");
        }
    }

    /**
     * 查询菜单
     */
    @Log(title = "11.1.5 修改订单状态", businessType = BusinessType.INSERT)
    @PostMapping("/editOrderMenu")
    @ApiOperation(value = "11.1.5 修改订单状态", notes = "修改订单状态")
    public AjaxResult editOrderMenu(@RequestBody OrderEntity orderEntity) {
            String orderNo = orderEntity.getOrderNo();
            if(StringUtils.isEmpty(orderNo)) {
                return AjaxResult.error("未选择数据，修改失败");
            }
        try{
            //查询
            orderService.editOrderMenu(orderEntity);
            return   AjaxResult.success("修改成功");
        }catch (Exception e){
            logger.error("修改订单失败",e);
            return   AjaxResult.error("修改订单失败");
        }

    }

    /**
     * 导出订单
     */
    @Log(title = "11.1.4 导出订单", businessType = BusinessType.INSERT)
    @PostMapping("/exportGoods")
    @ApiOperation(value = "11.1.4 导出订单", notes = "导出订单")
    public void exportOrder(@RequestBody OrderEntity orderEntity, HttpServletRequest request, HttpServletResponse response) {
        try{
            List<OrderEntity> goodsList = orderService.getOrderList(orderEntity);
            Workbook workbook = ExcelOrderWriter.exportData(goodsList);
            try{
                response.setCharacterEncoding("utf-8");
                response.setContentType("multipart/from-data");
                response.setHeader("Content-Disposition","attachment;fileName="+ FileUtils.setFileDownloadHeader(request, "OrderList.xlsx"));
                //写入
                OutputStream output = response.getOutputStream();
                BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
                workbook.write(bufferedOutPut);
                bufferedOutPut.flush();
                bufferedOutPut.close();
                output.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }catch (Exception e){
            logger.error("订单导出失败",e);
        }
    }

}
