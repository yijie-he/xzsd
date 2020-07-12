package com.ruoyi.project.zxsd.order.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: gjy
 * @Date: 2020/5/19 08:26
 */
@Getter
@Setter
@ApiModel(value = "订单详情对象", description = "订单详情对象")
public class OrderDetailEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    /**
     * 下单人id
     */
    @ApiModelProperty(value = "下单人id")
    private String userCode;
    /**
     * 收货人姓名
     */
    @ApiModelProperty(value = "收货人姓名")
    private String shippingUser;
    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    private Double orderMoney;
    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private Double districtMoney;
    /**
     * 运费金额
     */
    @ApiModelProperty(value = "运费金额")
    private Double shippingMoney;
    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额")
    private Double paymentMoney;
    /**
     * 商品单价
     */
    @ApiModelProperty(value = "商品单价")
    private Double goodsPrice;

    /**
     * 发货时间
     */
    @ApiModelProperty(value = "发货时间")
    private Date shippingTime;
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    /**
     * 收货时间
     */
    @ApiModelProperty(value = "收货时间")
    private Date receiveTime;
    /**
     * 订单状态（0成功1取消）
     */
    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private int sortNo;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 书店编号
     */
    @ApiModelProperty(value = "书店编号")
    private String storeNo;
    /**
     * 商品SKU编码
     */
    @ApiModelProperty(value = "商品SKU编码")
    private String skuNo;
    /**
     * 书店名称
     */
    @ApiModelProperty(value = "书店名称")
    private String storeName;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 购买商品数量
     */
    @ApiModelProperty(value = "购买商品数量")
    private int goodsCnt;

    /**
     * 订单详情编码
     */
    @ApiModelProperty(value = "订单详情编码")
    private String orderDetailCode;

    /**
     * 确认取货标记0未取货1以取货
     */
    @ApiModelProperty(value = "确认取货标记")
    private int isReceived;

    /**
     * 到货标记
     */
    @ApiModelProperty(value = "到货标记")
    private int isShipped;

    /**
     * 剩余货量
     */
    @ApiModelProperty(value = "剩余货量")
    private int number;
    /**
     * 销量
     */
    @ApiModelProperty(value = "销量")
    private int number2;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号",hidden = true)
    private String phone;

    /**
     * 广告词
     */
    @ApiModelProperty(value = "广告词",hidden = true)
    private String advertising;

    /**
     * 图片路径
     */
    @ApiModelProperty(value = "图片路径",hidden = true)
    private String surPicUrl;

    /**
     * 单个图片路径
     */
    @ApiModelProperty(value = "单个图片路径",hidden = true)
    private String surUrl;

    /**
     * 商品详情
     */
    @ApiModelProperty(value = "商品详情",hidden = true)
    private String detail;

    /**
     * 是否评价
     */
    @ApiModelProperty(value = "是否评价",hidden = true)
    private int isEvi;
}
