package com.hb.domain.trade.adapter.port;

import com.hb.domain.trade.model.entity.NotifyTaskEntity;

/**
 * 交易接口服务接口
 */

public interface ITradePort {

    String groupBuyNotify(NotifyTaskEntity notifyTaskEntity) throws Exception;

}
