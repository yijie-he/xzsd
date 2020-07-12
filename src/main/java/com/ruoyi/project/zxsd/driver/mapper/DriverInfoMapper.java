package com.ruoyi.project.zxsd.driver.mapper;

import com.ruoyi.project.zxsd.driver.domain.DriverInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 司机管理Mapper接口
 *
 * @author wangdong
 * @date 2020-05-02
 */
@Repository
@Mapper
public interface DriverInfoMapper {

    void addDriver(DriverInfoEntity driverInfoEntity);

    List<DriverInfoEntity> getDriver(DriverInfoEntity driverInfoEntity);

    void editDriver(DriverInfoEntity driverInfoEntity);

    void delDriver(@Param("driverInfoEntity") List<DriverInfoEntity> driverInfoEntity,@Param("updateBy") String updateBy);
}
