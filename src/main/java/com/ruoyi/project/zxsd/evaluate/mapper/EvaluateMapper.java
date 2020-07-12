package com.ruoyi.project.zxsd.evaluate.mapper;

import com.ruoyi.project.zxsd.evaluate.domain.EvaluateEntity;
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
public interface EvaluateMapper {

    /**
     * 新增评价
     */
    void addEvaluate(EvaluateEntity evaluateEntity);

    /**
     * 显示评价
     */
    List<EvaluateEntity> getEvaluate(EvaluateEntity evaluateEntity);

    void changeOrderEvi(EvaluateEntity evaluateEntity);
}
