package com.ruoyi.project.zxsd.driver.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 司机信息 t_driver_info
 *
 * @author jiaab
 * @date 2020-05-19
 */
@Getter
@Setter
@ApiModel(value = "司机对象", description = "司机对象")
public class DriverInfoEntity extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private String userId;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String userName;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String userRealname;
    /**
     * 是否管理员(1代表是 0 代表否)
     */
    @ApiModelProperty(value = "是否管理员")
    private int isAdmin;
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idCard;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private int sex;
    /**
     * 座机号码
     */
    @ApiModelProperty(value = "座机号码")
    private String tel;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号",hidden = true)
    private int sortNo;
    /**
     * 角色,0管理员 1店长 2 司机 3客户
     */
    @ApiModelProperty(value = "角色")
    private int role;

    /**
     * 司机编号
     */
    @ApiModelProperty(value = "司机编号")
    private String driverNo;

    /**
     * 门店编号
     */
    @ApiModelProperty(value = "门店编号")
    private String storeNo;

    /**
     * 省编号
     */
    @ApiModelProperty(value = "省编号")
    private String provinceNo;

    /**
     * 区编号
     */
    @ApiModelProperty(value = "区编号")
    private String countyNo;

    /**
     * 司机省名称编码
     */
    @ApiModelProperty(value = "司机省名称编码")
    private String userCode;

    /**
     * 司机区名称编码
     */
    @ApiModelProperty(value = "司机区名称编码")
    private String provinceName;

    /**
     * 用户编码
     */
    @ApiModelProperty(value = "用户编码")
    private String countyName;

}
