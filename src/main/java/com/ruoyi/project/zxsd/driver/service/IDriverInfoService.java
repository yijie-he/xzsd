package com.ruoyi.project.zxsd.driver.service;

import com.ruoyi.project.zxsd.driver.domain.DriverInfoEntity;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author jiaab
 * @date 2020-05-15
 */
public interface IDriverInfoService {

    void addDriver(DriverInfoEntity driverInfoEntity);

    List<DriverInfoEntity> getDriver(DriverInfoEntity driverInfoEntity);

    void editDriver(DriverInfoEntity driverInfoEntity);

    void delDriver(List<DriverInfoEntity> driverInfoEntity);
}
