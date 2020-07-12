package com.ruoyi.project.zxsd.store.service;

import com.ruoyi.project.zxsd.store.domain.CountyEntity;
import com.ruoyi.project.zxsd.store.domain.ProvinceEntity;
import com.ruoyi.project.zxsd.store.domain.StoreInfoEntity;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author jiaab
 * @date 2020-05-15
 */
public interface IStoreInfoService {

    List<ProvinceEntity> getProvince();

    List<CountyEntity> getCounty(String provinceCode);

    List<StoreInfoEntity> getListStoreInfo(StoreInfoEntity storeInfoEntity);

    StoreInfoEntity getDetail(String id);

    void addStore(StoreInfoEntity storeInfoEntity);

    void editStore(StoreInfoEntity storeInfoEntity);

    void delStore(List<StoreInfoEntity> storeInfoEntity);

    StoreInfoEntity getStoreInfoByUserCode(String userId);

    List<StoreInfoEntity> getDetails();
}
