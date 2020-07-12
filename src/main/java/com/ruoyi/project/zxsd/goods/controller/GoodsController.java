package com.ruoyi.project.zxsd.goods.controller;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.zxsd.goods.domain.GoodsEntity;
import com.ruoyi.project.zxsd.goods.service.IGoodsInfoService;
import com.ruoyi.project.zxsd.goods.util.ExcelReader;
import com.ruoyi.project.zxsd.goods.util.ExcelWriter;
import com.ruoyi.project.zxsd.sys.domain.SystemUserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台端用户Controller
 *
 * @author wangdong
 * @date 2020.04.30
 */
@RestController
@RequestMapping("/goods")
@Api(tags = {"【后台端】9.1 商品管理"}, description = "商品管理")
public class GoodsController extends BaseController {

    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     */
    private final IGoodsInfoService goodsInfoService;

    @Autowired
    public GoodsController(IGoodsInfoService goodsInfoService) {
        this.goodsInfoService = goodsInfoService;
    }

    /**
     * 导入商品
     *
     * @param file 文件
     * @return 结果
     */
    @Log(title = "9.1.1 导入商品", businessType = BusinessType.OTHER)
    @PostMapping("/importGoods")
    @ApiOperation(value = "9.1.1 导入商品", notes = "导入商品")
    public AjaxResult importGoods(MultipartFile file){
       try {
           List<GoodsEntity> goodsList = ExcelReader.readExcel(file);
           if(goodsList != null){
               goodsInfoService.addGoodsList(goodsList);
               return  AjaxResult.success("商品导入成功");
           }else {
               logger.error("商品导入失败，解析后集合为空");
               return  AjaxResult.error("商品导入失败");
           }
       }catch (Exception e){
           logger.error("商品导入失败", e);
           return  AjaxResult.error("商品导入失败");
       }
    }

    /**
     * 导出商品
     *
     * @return 结果
     */
    @Log(title = "9.2.2 导出商品", businessType = BusinessType.OTHER)
    @PostMapping("/exportGoods")
    @ApiOperation(value = "9.2.2 导出商品", notes = "导出商品")
    public AjaxResult exportGoods(@RequestBody GoodsEntity goodsEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<GoodsEntity> goodsList = goodsInfoService.getGoodsList(goodsEntity);

            Workbook workbook = ExcelWriter.exportData(goodsList);
            try {
                //设置请求
                response.setCharacterEncoding("utf-8");
                response.setContentType("multipart/form-data");
                response.setHeader("Content-Disposition",
                        "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, "goodsList.xlsx"));
                //写入
                OutputStream output = response.getOutputStream();
                BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
                workbook.write(bufferedOutput);
                bufferedOutput.flush();
                bufferedOutput.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("商品导出失败", e);
            return AjaxResult.error("商品导出失败");
        }
        return AjaxResult.error("商品导出成功");
    }

    /**
     * 导出商品
     *
     * @return 结果
     */
    @Log(title = "9.3.3 下载模板", businessType = BusinessType.OTHER)
    @PostMapping("/downExcel")
    @ApiOperation(value = "9.3.3 下载模板", notes = "下载模板")
    public void downExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String fileName = "goodsExcel.xlsx";
        try {
            if(!FileUtils.isValidFilename(fileName)){
                throw new Exception(StringUtils.format("文件名称({})非法,不允许下载。", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, "goodsList.xlsx"));
            FileUtils.writeBytes(filePath, response.getOutputStream());
        }catch (Exception e){
            logger.error("下载文件失败", e);
        }
    }

    /**
     * 新增商品
     */
    @Log(title = "9.4.4 新增商品", businessType = BusinessType.INSERT)
    @PostMapping("/addGoods")
    @ApiOperation(value = "9.4.4 新增商品", notes = "新增商品")
    public AjaxResult addGoods(@RequestBody GoodsEntity goodsEntity) {
        //校验参数合法性
        String skuName = goodsEntity.getSkuName();
        if(StringUtils.isEmpty(skuName)){
            return  AjaxResult.error("商品名称不能为空");
        }

        String outsideNo = goodsEntity.getOutsideNo();
        if(StringUtils.isEmpty(outsideNo)){
            return new AjaxResult().error("商家编码不能为空");
        }
        String cateCode = goodsEntity.getCateCode();
        if(StringUtils.isEmpty(cateCode)){
            return new AjaxResult().error("分类编码不能为空");
        }

        double fixPrice = goodsEntity.getFixPrice();
        if(fixPrice <=0 ){
            return new AjaxResult().error("定价为空，或者不合法");
        }
        double sellingPrice = goodsEntity.getSellingPrice();
        if(sellingPrice <=0){
            return new AjaxResult().error("售价为空，或者不合法");
        }

        String author = goodsEntity.getAuthor();
        if(StringUtils.isEmpty(author)){
            return new AjaxResult().error("作者不能为空");
        }

        String isbn = goodsEntity.getIsbn();
        if(StringUtils.isEmpty(isbn)){
            return new AjaxResult().error("isbn编号不能为空");
        }

        int amountCnt = goodsEntity.getAmountCnt();
        if(amountCnt <= 0){
            return new AjaxResult().error("库存数量不能为空或小于零");
        }

        int saleCnt = goodsEntity.getSaleCnt();
        if(saleCnt < 0){
            return new AjaxResult().error("销售数量能为空或小于零");
        }

        List<String> picList = goodsEntity.getPicList();
        if(StringUtils.isEmpty(picList.get(0))){
            return new AjaxResult().error("图片至少上传一张");
        }

        String press = goodsEntity.getPress();
        if(StringUtils.isEmpty(press)){
            return new AjaxResult().error("出版社不能为空");
        }

       try {
           goodsInfoService.addGoodsLists(goodsEntity);
           return AjaxResult.success("商品新增成功");
       }catch (Exception e){
            logger.error("商品新增失败", e);
            return  AjaxResult.error("商品新增失败");
       }
    }
    /**
     * 查询商品
     */
    @Log(title = "9.5.5 查询商品", businessType = BusinessType.INSERT)
    @PostMapping("/getGoods")
    @ApiOperation(value = "9.5.5 查询商品", notes = "查询商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页显示记录数", defaultValue = "10")
    })
    public TableDataInfo getGoods(@RequestBody GoodsEntity goodsEntity) {
        startPage();
        try{
            List<GoodsEntity> driverList= goodsInfoService.getGoodsList(goodsEntity);
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
     * 查询商品重制版
     */
    @Log(title = "9.5.6 查询商品重制版", businessType = BusinessType.INSERT)
    @PostMapping("/getGoodsT")
    @ApiOperation(value = "9.5.5 查询商品重制版", notes = "查询商品重制版")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页显示记录数", defaultValue = "10")
    })
    public TableDataInfo getGoodsT(@RequestBody GoodsEntity goodsEntity) {
        startPage();
        try{
            List<GoodsEntity> driverList= goodsInfoService.getGoodsListT(goodsEntity);
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
     * 删除商品
     */
    @Log(title = "9.6.6 删除商品", businessType = BusinessType.INSERT)
    @PostMapping("/delGoods")
    @ApiOperation(value = "9.6.6 删除商品", notes = "删除商品")
    @Transactional
    public AjaxResult delGoods(@RequestBody List<GoodsEntity> goodsEntity) {
        List<String> id = new ArrayList<>() ;
        for(int i = 0; i<goodsEntity.size(); i++){
            id.add(goodsEntity.get(i).getId());
        }
        if(0==id.size()){
            return AjaxResult.error("请选择要删除的商品");
        }
        try{
            goodsInfoService.delGoods(id);
            return AjaxResult.success("删除商品成功");
        }catch (Exception e){
            logger.error("删除商品失败",e);
            return AjaxResult.error("删除商品失败");
        }
    }

    /**
     * 查询商品
     */
    @Log(title = "9.1.8 查询商品", businessType = BusinessType.INSERT)
    @PostMapping("/getGood")
    @ApiOperation(value = "9.1.8 查询商品", notes = "查询商品")
    public AjaxResult getGood(@RequestBody GoodsEntity goodsEntity) {
        try{
            GoodsEntity goods = goodsInfoService.getGood(goodsEntity);
            return new AjaxResult().success("商品信息查询成功",goods);
        }catch (Exception e){
            logger.error("商品信息查询失败",e);
            return new AjaxResult().error("商品信息查询失败");
        }
    }

    /**
     * 修改商品
     */
    @Log(title = "9.9.9 修改商品", businessType = BusinessType.INSERT)
    @PostMapping("/editGoods")
    @ApiOperation(value = "9.9.9 修改商品", notes = "修改商品")
    @Transactional
    public AjaxResult editGoods(@RequestBody GoodsEntity goodsEntity) {
        //校验参数合法性
        String skuNo =goodsEntity.getSkuNo();
        if(StringUtils.isEmpty(skuNo)){
            return  AjaxResult.error("商品编号不能为空");
        }

        String skuName = goodsEntity.getSkuName();
        if(StringUtils.isEmpty(skuName)){
            return  AjaxResult.error("商品名称不能为空");
        }

        String outsideNo = goodsEntity.getOutsideNo();
        if(StringUtils.isEmpty(outsideNo)){
            return AjaxResult.error("商家编码不能为空");
        }
        String cateCode = goodsEntity.getCateCode();
        if(StringUtils.isEmpty(cateCode)){
            return AjaxResult.error("分类编码不能为空");
        }

        double fixPrice = goodsEntity.getFixPrice();
        if(fixPrice <=0 ){
            return AjaxResult.error("定价为空，或者不合法");
        }
        double sellingPrice = goodsEntity.getSellingPrice();
        if(sellingPrice <=0){
            return AjaxResult.error("售价为空，或者不合法");
        }

        String author = goodsEntity.getAuthor();
        if(StringUtils.isEmpty(author)){
            return AjaxResult.error("作者不能为空");
        }

        String isbn = goodsEntity.getIsbn();
        if(StringUtils.isEmpty(isbn)){
            return AjaxResult.error("isbn编号不能为空");
        }

        String press = goodsEntity.getPress();
        if(StringUtils.isEmpty(press)){
            return new AjaxResult().error("出版社不能为空");
        }

        int amountCnt = goodsEntity.getAmountCnt();
        if(amountCnt <= 0){
            return new AjaxResult().error("库存数量不能为空或小于零");
        }

        int saleCnt = goodsEntity.getSaleCnt();
        if(saleCnt < 0){
            return AjaxResult.error("销售数量能为空或小于零");
        }

        List<String> picList = goodsEntity.getPicList();
        if(StringUtils.isEmpty(picList.get(0))){
            return AjaxResult.error("图片至少上传一张");
        }

        try {
            goodsInfoService.editGoods(goodsEntity);
            return AjaxResult.success("修改商品成功");
        }catch (Exception e){
            logger.error("修改商品失败",e);
            return AjaxResult.error("修改商品失败");
        }
    }

    /**
     * 上架商品
     */
    @Log(title = "9.7.7 上架商品", businessType = BusinessType.INSERT)
    @PostMapping("/trueGoods")
    @ApiOperation(value = "9.7.7 上架商品", notes = "上架商品")
    @Transactional
    public AjaxResult trueGoods(@RequestBody List<GoodsEntity> goodsEntity) {
        List<String> id = new ArrayList<>() ;
        for(int i = 0; i<goodsEntity.size(); i++){
            id.add(goodsEntity.get(i).getId());
        }
        //判断权限
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        int userRole = user.getRole();
        if(userRole!=1&&userRole!=0){
            return AjaxResult.success("权限不足");
        }
        if(id.size()==0){
            return AjaxResult.error("请选择要上架商品");
        }
        try{
            goodsInfoService.trueGoods(id);
            return AjaxResult.success("上架商品成功");
        }catch (Exception e){
            logger.error("上架商品失败",e);
            return AjaxResult.error("上架商品失败");
        }
    }

    /**
     * 下架商品
     */
    @Log(title = "9.8.8 下架商品", businessType = BusinessType.INSERT)
    @PostMapping("/falseGoods")
    @ApiOperation(value = "9.8.8 下架商品", notes = "下架商品")
    @Transactional
    public AjaxResult falseGoods(@RequestBody List<GoodsEntity> goodsEntity) {
        List<String> id = new ArrayList<>() ;
        for(int i = 0; i<goodsEntity.size(); i++){
            id.add(goodsEntity.get(i).getId());
        }
        //判断权限
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SystemUserEntity user = loginUser.getSystemUserEntity();
        int userRole = user.getRole();
        if(userRole!=1&&userRole!=0){
            return AjaxResult.success("权限不足");
        }
        if(id.size()==0){
            return AjaxResult.error("请选择要下架商品");
        }
        try{
            goodsInfoService.falseGoods(id);
            return AjaxResult.success("下架商品成功");
        }catch (Exception e){
            logger.error("下架商品失败",e);
            return AjaxResult.error("下架商品失败");
        }
    }
}
