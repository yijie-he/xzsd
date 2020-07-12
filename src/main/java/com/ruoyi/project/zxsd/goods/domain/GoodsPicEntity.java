package com.ruoyi.project.zxsd.goods.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品信息信息 t_goods_sku_pic
 *
 * @author jiaab
 * @date 2020-05-19
 */
@Getter
@Setter
@ApiModel(value = "商品图片对象", description = "商品图片对象")
public class GoodsPicEntity extends BaseEntity{

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
     * 路径
     */
    @ApiModelProperty(value = "路径")
    private String surPicUrl;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private int sortNo;

}
