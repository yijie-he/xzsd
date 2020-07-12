package com.ruoyi.project.zxsd.store.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jiaab
 * @date 2020-05-19
 */
@Getter
@Setter
@ApiModel(value = "省对象", description = "省对象")
public class ProvinceEntity extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 省编码
     */
    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    /**
     * 省名称
     */
    @ApiModelProperty(value = "省名称")
    private String provinceName;

}
