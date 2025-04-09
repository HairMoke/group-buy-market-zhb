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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class TeamStockOccupyRuleFilter implements ILogicHandler<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeLockRuleFilterBackEntity apply(TradeLockRuleCommandEntity requestParameter, TradeLockRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("交易规则过滤-组队库存校验{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());

        // 1. teamId 为空，则为首次开团， 不做拼团组队目标量库存限制,抢占库存是在一个 team 已经创建完成后，再有用户开始参与抢占时候，这个时候要做库存的缓存扣减处理。
        String teamId = requestParameter.getTeamId();
        if(StringUtils.isBlank(teamId)) {
            return TradeLockRuleFilterBackEntity.builder()
                    .userTakeOrderCount(dynamicContext.getUserTakeOrderCount())
                    .build();
        }

        // 2. 抢占库存，；通过抢占 Redis 缓存库存，来降低对数据库的操作压力。从上下文中获取到组队的目标量，有效期时间，以及组队的key和恢复量key
        GroupBuyActivityEntity groupBuyActivity = dynamicContext.getGroupBuyActivity();
        Integer target = groupBuyActivity.getTarget();
        Integer validTime = groupBuyActivity.getValidTime();
        String teamStockKey = dynamicContext.generateTeamStockKey(teamId);
        // 这个恢复量的用途是我们扣了redis中组队的任务缓存，但这个时候，发生异常了。那么我们要记录一个这样的数据。等做库存使用对比量的时候，可以用 target 目标量 + 恢复量一起来比。
        String recoveryTeamStockKey = dynamicContext.generateRecoveryTeamStockKey(teamId);


        boolean status = repository.occupyTeamStock(teamStockKey, recoveryTeamStockKey, target, validTime);

        if(!status) {
            log.warn("交易规则过滤-组队库存校验{} activityId:{} 抢占失败:{}", requestParameter.getUserId(), requestParameter.getActivityId(), teamStockKey);
            throw new AppException(ResponseCode.E0008);
        }

        return TradeLockRuleFilterBackEntity.builder()
                .userTakeOrderCount(dynamicContext.getUserTakeOrderCount())
                .recoveryTeamStockKey(recoveryTeamStockKey)
                .build();

    }
}
