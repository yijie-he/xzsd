package com.ruoyi.project.zxsd.client.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户对象 t_sys_menu u，sys_user su
 * u.user_code = su.user_id
 * @author jiaab
 * @date 2020-05-15
 */
@Getter
@Setter
@ApiModel(value = "客户对象", description = "客户对象")
public class ClientEntity extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;


    /**
     * ID
     */
    @ApiModelProperty(value = "ID",hidden = true)
    private String id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID",hidden = true)
    private String userId;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String userName;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称",hidden = true)
    private String nickName;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",hidden = true)
    private String password;
    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String userRealname;
    /**
     * 是否管理员(1代表是 0 代表否)
     */
    @ApiModelProperty(value = "是否管理员",hidden = true)
    private int isAdmin;
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号",hidden = true)
    private String idCard;


    /**
     * 座机号码
     */
    @ApiModelProperty(value = "座机号码",hidden = true)
    private String tel;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码",hidden = true)
    private String phone;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱",hidden = true)
    private String email;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",hidden = true)
    private String remark;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号",hidden = true)
    private int sortNo;
    /**
     * 角色,0管理员 1店长 2 司机 3客户
     */
    @ApiModelProperty(value = "角色",hidden = true)
    private int role;

    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称",hidden = true)
    private String picStatusName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别",hidden = true)
    private Integer sex;

}
