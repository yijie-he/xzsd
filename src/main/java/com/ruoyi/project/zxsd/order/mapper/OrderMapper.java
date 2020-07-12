package com.ruoyi.project.zxsd.order.mapper;

import com.ruoyi.project.zxsd.order.domain.OrderDetailEntity;
import com.ruoyi.project.zxsd.order.domain.OrderEntity;
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
public interface OrderMapper {


    void addOrder(@Param("orderList") List<OrderEntity> orderList);

    void addOrderDetail(@Param("orderDetailResultList") List<OrderDetailEntity> orderDetailResultList);

    void upSaleCnt(@Param("orderDetailResultList") List<OrderDetailEntity> orderDetailResultList);

    List<OrderEntity> getOrderList(OrderEntity orderEntity);

    List<OrderDetailEntity> getOrderMenu(OrderDetailEntity orderDetailEntity);

    void editOrderMenu(OrderEntity orderEntity);
}
