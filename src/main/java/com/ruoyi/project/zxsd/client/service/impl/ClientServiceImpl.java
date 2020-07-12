package com.ruoyi.project.zxsd.client.service.impl;

import com.ruoyi.project.zxsd.client.domain.ClientEntity;
import com.ruoyi.project.zxsd.client.mapper.ClientMapper;
import com.ruoyi.project.zxsd.client.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class ClientServiceImpl implements IClientService {
    /**
     * 用户Mapper
     */
    @Autowired//自动装配方式，从Spring IoC容器中去查找
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }


    @Override
    public List<ClientEntity> getUserByUserRole(ClientEntity clientEntity) {
        List<ClientEntity> menuList = clientMapper.getUserByUserRole(clientEntity);
        return menuList;
    }
}
