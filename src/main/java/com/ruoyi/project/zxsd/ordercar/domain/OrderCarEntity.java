package com.ruoyi.project.zxsd.ordercar.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品信息信息 t_goods_sku
 *
 * @author jiaab
 * @date 2020-05-19
 */
@Getter
@Setter
@ApiModel(value = "热门商品对象", description = "热门商品对象")
public class OrderCarEntity extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * sku编号
     */
    @ApiModelProperty(value = "sku编号")
    private String skuNo;
    /**
     * sku名称
     */
    @ApiModelProperty(value = "用户编码")
    private String userCode;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer cnt;

    /**
     * 是否选中
     */
    @ApiModelProperty(value = "是否选中")
    private Integer isSelected;

    /**
     * 图片路径
     */
    @ApiModelProperty(value = "图片路径")
    private String surPicUrl;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String skuName;

    /**
     * 售价
     */
    @ApiModelProperty(value = "售价")
    private double sellingPrice;

    /**
     * 商品介绍
     */
    @ApiModelProperty(value = "商品介绍")
    private String detail;

    /**
     * 商品介绍
     */
    @ApiModelProperty(value = "商品介绍")
    private Integer goodsCnt;
}
