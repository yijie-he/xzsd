package com.ruoyi.project.zxsd.order.service;

import com.ruoyi.project.zxsd.order.domain.OrderDetailEntity;
import com.ruoyi.project.zxsd.order.domain.OrderEntity;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author jiaab
 * @date 2020-05-15
 */
//@Repository
//@Mapper
public interface IOrderService {

    void addOrder(List<OrderDetailEntity> orderDetailEntity);

    List<OrderEntity> getOrderList(OrderEntity orderEntity);

    List<OrderDetailEntity> getOrderMenu(OrderDetailEntity orderDetailEntity);

    void editOrderMenu(OrderEntity orderEntity);
}
