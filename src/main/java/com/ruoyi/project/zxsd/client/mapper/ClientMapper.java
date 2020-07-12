package com.ruoyi.project.zxsd.client.mapper;

import com.ruoyi.project.zxsd.client.domain.ClientEntity;
import org.apache.ibatis.annotations.Mapper;
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
public interface ClientMapper {


    /**
     * todo  查询
     * @author gjy
     * @param clientEntity 1
     * @return java.util.List<com.ruoyi.project.zxsd.client.domain.ClientEntity>
     * @date 2020/5/25
    */
    List<ClientEntity> getUserByUserRole(ClientEntity clientEntity);
}
