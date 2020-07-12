package com.ruoyi.project.zxsd.evaluate.domain;

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
@ApiModel(value = "评价对象", description = "评价对象")
public class EvaluateEntity extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 商品sku */
    @ApiModelProperty(value = "商品sku")
    private String skuNo;

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private String userCode;

    /** 星级 */
    @ApiModelProperty(value = "星级")
    private int userStar;

    /** 图片地址 */
    @ApiModelProperty(value = "图片地址")
    private String userImg;

    /** 评价 */
    @ApiModelProperty(value = "评价")
    private String userEvaluate;

    /** UUID */
    @ApiModelProperty(value = "UUID")
    private String id;

    /** 序号（从0开始） */
    @ApiModelProperty(value = "序号")
    private int sortNo;

    /** 评价编号 */
    @ApiModelProperty(value = "评价编号")
    private String evaCode;

    /**
     * 最低星级
     */
    @ApiModelProperty(value = "最低星级")
    private Integer minValue;

    /**
     * 最高星级
     */
    @ApiModelProperty(value = "最高星级")
    private Integer maxValue;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;
}
