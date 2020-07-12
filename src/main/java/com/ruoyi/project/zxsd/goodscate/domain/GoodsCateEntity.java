package com.ruoyi.project.zxsd.goodscate.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import com.ruoyi.project.zxsd.goods.domain.GoodsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 商品分类对象 t_code_cate
 *
 * @author jiaab
 * @date 2020-05-15
 */
@Getter
@Setter
@ApiModel(value = "商品分类对象", description = "商品分类对象")
public class GoodsCateEntity extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;


    /**
     * 分类代码
     */
    @ApiModelProperty(value = "分类代码")
    private String cateCode;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String cateName;

    /**
     * 上级分类代码
     */
    @ApiModelProperty(value = "上级分类代码")
    private String cateCodeParent;
    /**
     * 层级（第一层1，后续2,3等等）
     */
    @ApiModelProperty(value = "层级")
    private Integer level;

    /**
     * 父类标记1是0否
     */
    @ApiModelProperty(value = "父类标记")
    private int isParent;

    /**
     * 作废标记（1作废，0未作废）
     */
    @ApiModelProperty(value = "作废标记",hidden = true)
    private int isDeleted;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号",hidden = true)
    private int sortNo;

    @ApiModelProperty(value = "子菜单集合",hidden = true)
    private List<GoodsCateEntity> childMenu;

    @ApiModelProperty(value = "子商品集合",hidden = true)
    private List<GoodsEntity> goods;
}
