package com.hb.domain.trade.adapter.repository;

import com.hb.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import com.hb.domain.trade.model.entity.*;
import com.hb.domain.trade.model.valobj.GroupBuyProgressVO;

/**
 * 交易仓储服务接口
 */
public interface ITradeRepository {

    /**
     * 查询，未被支付消费完成的营销优惠订单
     * @param userId
     * @param outTradeNo
     * @return
     */
    MarketPayOrderEntity queryMarketPayOrderEntityByOutTradeNo(String userId, String outTradeNo);

    /**
     * 查询拼团进度
     * @param teamId
     * @return
     */
    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    /**
     * 锁定，营销预支付订单；商品下单前，预购锁定
     * @param groupBuyOrderAggregate
     * @return
     */
    MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

    GroupBuyActivityEntity queryGroupBuyActivityEntityByActivityId(Long activityId);

    Integer queryOrderCountByActivityId(Long activityId, String userId);
}
