package com.ruoyi.project.zxsd.client.service;

import com.ruoyi.project.zxsd.client.domain.ClientEntity;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author jiaab
 * @date 2020-05-15
 */
public interface IClientService {


    /**
     * todo  查询
     * @author gjy
     * @param clientEntity 1
     * @return java.util.List<com.ruoyi.project.zxsd.client.domain.ClientEntity>
     * @date 2020/5/25
    */
    List<ClientEntity> getUserByUserRole(ClientEntity clientEntity);
}
