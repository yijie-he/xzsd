package com.ruoyi.project.zxsd.evaluate.service.impl;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.zxsd.evaluate.domain.EvaluateEntity;
import com.ruoyi.project.zxsd.evaluate.mapper.EvaluateMapper;
import com.ruoyi.project.zxsd.evaluate.service.IEvaluateService;
import com.ruoyi.project.zxsd.sys.util.SystemCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class EvaluateServiceImpl implements IEvaluateService {


    @Autowired
    private final EvaluateMapper evaluateMapper;

    public EvaluateServiceImpl(EvaluateMapper evaluateMapper) {
        this.evaluateMapper = evaluateMapper;
    }

    @Override
    @Transactional
    public void addEvaluate(EvaluateEntity evaluateEntity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        evaluateEntity.setCreateBy(loginUser.getUsername());
        evaluateEntity.setUpdateBy(loginUser.getUsername());
        evaluateEntity.setUserCode(loginUser.getSystemUserEntity().getUserId());
        evaluateEntity.setId(IdUtils.fastSimpleUUID());
        evaluateEntity.setEvaCode(SystemCodeUtil.getUserCode());
        evaluateMapper.changeOrderEvi(evaluateEntity);
        evaluateMapper.addEvaluate(evaluateEntity);
    }

    @Override
    public List<EvaluateEntity> getEvaluate(EvaluateEntity evaluateEntity) {
        List<EvaluateEntity> orderList = evaluateMapper.getEvaluate(evaluateEntity);
        return orderList;
    }
}
