package com.ruoyi.project.zxsd.store.mapper;

import com.ruoyi.project.zxsd.store.domain.CountyEntity;
import com.ruoyi.project.zxsd.store.domain.ProvinceEntity;
import com.ruoyi.project.zxsd.store.domain.StoreInfoEntity;
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
public interface StoreInfoMapper {

    List<ProvinceEntity> getProvince();

    List<CountyEntity> getCounty(String provinceCode);

    List<StoreInfoEntity> getListStoreInfo(StoreInfoEntity storeInfoEntity);

    StoreInfoEntity getDetail(String id);

    void addStore(StoreInfoEntity storeInfoEntity);

    int getMaxSortNo(@Param("tableName") String tableName);

    void editStore(StoreInfoEntity storeInfoEntity);

    void delStore(@Param("storeInfoEntity") List<StoreInfoEntity> storeInfoEntity,@Param("updateBy") String updateBy);

    StoreInfoEntity getStoreInfoByUserCode(@Param("userId") String userId);

    List<StoreInfoEntity> getDetails();
}
