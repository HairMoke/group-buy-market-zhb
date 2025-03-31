package com.hb.domain.trade.service;

import com.hb.domain.trade.model.entity.TradePaySettlementEntity;
import com.hb.domain.trade.model.entity.TradePaySuccessEntity;

/**
 * 拼团交易结算服务接口
 */
public interface ITradeSettlementOrderService {

    /**
     * 营销结算
     * @param tradePaySuccessEntity  交易支付订单实体对象
     * @return 交易结算订单实体
     */
    TradePaySettlementEntity settlementMarketPayOrder(TradePaySuccessEntity tradePaySuccessEntity);
}
