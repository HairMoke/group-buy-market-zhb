package com.hb.domain.trade.service.lock.filter;

import com.hb.domain.trade.adapter.repository.ITradeRepository;
import com.hb.domain.trade.model.entity.GroupBuyActivityEntity;
import com.hb.domain.trade.model.entity.TradeLockRuleCommandEntity;
import com.hb.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import com.hb.domain.trade.service.lock.factory.TradeLockRuleFilterFactory;
import com.hb.types.design.framework.link.model2.handler.ILogicHandler;
import com.hb.types.enums.ResponseCode;
import com.hb.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户参与限制，规则过滤
 *
 如果拼团活动配置了对用户的参与次数限制，那么需要在用户参与活动前，做好数量的校验拦截。

 比如用户可以参与3次，那么库表里会记录3条数据；活动ID_用户ID_1、活动ID_用户ID_2、活动ID_用户ID_3，这样可以在库表层面做最强的防护拦截，不会让一个用户无限的参与活动。
 */
@Slf4j
@Service
public class UserTakeLimitRuleFilter implements ILogicHandler<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> {


    @Resource
    private ITradeRepository repository;


    @Override
    public TradeLockRuleFilterBackEntity apply(TradeLockRuleCommandEntity requestParameter, TradeLockRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("交易规则过滤-用户参与次数校验{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());

        GroupBuyActivityEntity groupBuyActivity = dynamicContext.getGroupBuyActivity();

        // 查询用户在一个拼团活动上参与的次数
        Integer count = repository.queryOrderCountByActivityId(requestParameter.getActivityId(), requestParameter.getUserId());

        if(null != groupBuyActivity.getTakeLimitCount() && count >= groupBuyActivity.getTakeLimitCount()) {
            log.info("用户参与次数校验，已达可参与上限 activityId:{}", requestParameter.getActivityId());
            throw new AppException(ResponseCode.E0103);
        }

        return TradeLockRuleFilterBackEntity.builder()
                .userTakeOrderCount(count)
                .build();
    }
}
