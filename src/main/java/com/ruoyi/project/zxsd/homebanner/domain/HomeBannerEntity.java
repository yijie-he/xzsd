package com.ruoyi.project.zxsd.homebanner.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 轮播图 t_store_info
 *
 * @author jiaab
 * @date 2020-05-19
 */
@Getter
@Setter
@ApiModel(value = "轮播图对象", description = "轮播图对象")
public class HomeBannerEntity extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 轮播图片路径
     */
    @ApiModelProperty(value = "轮播图片路径")
    private String picUrl;

    /**
     * 轮播商品编号
     */
    @ApiModelProperty(value = "轮播商品编号")
    private String skuNo;

    /**
     * 0-启用，1-禁用
     */
    @ApiModelProperty(value = "图片状态")
    private Integer picStatus;

    /**
     * UUID
     */
    @ApiModelProperty(value = "UUID")
    private String id;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private int sortNo;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String skuId;
    /**
     * 有效期起
     */
    @ApiModelProperty(value = "有效期起")
    private Date startTime;

    /**
     * 有效期止
     */
    @ApiModelProperty(value = "有效期止")
    private Date endDateTime;


}
