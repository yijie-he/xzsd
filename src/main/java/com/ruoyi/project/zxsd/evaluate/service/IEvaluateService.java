package com.ruoyi.project.zxsd.evaluate.service;

import com.ruoyi.project.zxsd.evaluate.domain.EvaluateEntity;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author jiaab
 * @date 2020-05-15
 */
public interface IEvaluateService {



 //新增评价
 void addEvaluate(EvaluateEntity evaluateEntity);

 //显示评价
 List<EvaluateEntity> getEvaluate(EvaluateEntity evaluateEntity);
}
