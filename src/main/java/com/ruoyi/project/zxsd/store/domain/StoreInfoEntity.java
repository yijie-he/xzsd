package com.ruoyi.project.zxsd.store.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 门店信息 t_store_info
 *
 * @author jiaab
 * @date 2020-05-19
 */
@Getter
@Setter
@ApiModel(value = "门店对象", description = "门店对象")
public class StoreInfoEntity extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 门店编码
     */
    @ApiModelProperty(value = "门店编码")
    private String storeNo;

    /**
     * 门店名称
     */
    @ApiModelProperty(value = "门店名称")
    private String storeName;

    /**
     * 门店地址
     */
    @ApiModelProperty(value = "门店地址")
    private String storeAddress;

    /**
     * 门店电话
     */
    @ApiModelProperty(value = "门店电话")
    private String storePhone;

    /**
     * 店长
     */
    @ApiModelProperty(value = "店长")
    private String userCode;

    /**
     * 店长真实姓名
     */
    @ApiModelProperty(value = "店长真实姓名",hidden = true)
    private String userRealname;

    /**
     * id
     */
    @ApiModelProperty(value = "id",hidden = true)
    private String id;

    /**
     * 排序
     */
    @ApiModelProperty(value = "序号",hidden = true)
    private int sortNo;

    /**
     * 省名称
     */
    @ApiModelProperty(value = "省名称")
    private String province;

    /**
     * 省编码
     */
    @ApiModelProperty(value = "省编码")
    private String provinceNo;

    /**
     * 城市名称
     */
    @ApiModelProperty(value = "城市名称")
    private String county;

    /**
     * 城市编码
     */
    @ApiModelProperty(value = "城市编码")
    private String countyNo;

    /**
     * 星级
     */
    @ApiModelProperty(value = "星级")
    private int starLevel;

    /**
     * 营业执照
     */
    @ApiModelProperty(value = "营业执照编码")
    private String businessLicense;

    /**
     * 店长身份证号
     */
    @ApiModelProperty(value = "店长身份证号")
    private String identityCard;

    /**
     * 邀请码
     */
    @ApiModelProperty(value = "邀请码",hidden = true)
    private String inviteCode;
}
