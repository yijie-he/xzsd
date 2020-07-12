package com.ruoyi.project.zxsd.ordercar.mapper;

import com.ruoyi.project.zxsd.ordercar.domain.OrderCarEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户Mapper接口
 *
 * @author wangdong
 * @date 2020-05-02
 */
@Repository
@Mapper
public interface OrderCarMapper {


    /**
     * 新增
    */
    void addOrderCar(OrderCarEntity orderCarEntity);

    /**
     * 查看
    */
    List<OrderCarEntity> getOrderCar(OrderCarEntity orderCarEntity);

    /**
     * 删除
    */

    void delOrderCar(@Param("orderCarEntity") List<OrderCarEntity> orderCarEntity);

    /**
     * 更新数量
    */
    void editOrderCar(OrderCarEntity orderCarEntity);

}
