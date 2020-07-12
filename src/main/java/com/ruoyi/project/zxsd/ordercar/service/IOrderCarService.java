package com.ruoyi.project.zxsd.ordercar.service;

import com.ruoyi.project.zxsd.ordercar.domain.OrderCarEntity;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author jiaab
 * @date 2020-05-15
 */
public interface IOrderCarService {

 /**
  * 新增
 */
 void addOrderCar(OrderCarEntity orderCarEntity);


 /**
  * 显示
 */
 List<OrderCarEntity> getOrderCar(OrderCarEntity orderCarEntity);

 /**
  * 删除
 */

 void delOrderCar(List<OrderCarEntity> orderCarEntity);

    /**
     * 更新数量
    */
 void editOrderCar(OrderCarEntity orderCarEntity);

}
