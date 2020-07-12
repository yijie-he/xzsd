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
@ApiModel(value = "区县对象", description = "区县对象")
public class CountyEntity extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 区县编码
     */
    @ApiModelProperty(value = "区县编码")
    private String countyCode;

    /**
     * 区县名称
     */
    @ApiModelProperty(value = "区县名称")
    private String countyName;

}
