package com.hb.api;

import com.hb.api.dto.LockMarketPayOrderRequestDTO;
import com.hb.api.dto.LockMarketPayOrderResponseDTO;
import com.hb.api.dto.SettlementMarketPayOrderRequestDTO;
import com.hb.api.dto.SettlementMarketPayOrderResponseDTO;
import com.hb.api.response.Response;

/**
 * 营销交易服务接口
 */
public interface IMarketTradeService {

    /**
     * 营销锁单
     *
     * @param lockMarketPayOrderRequestDTO 锁单商品信息
     * @return 锁单结果信息
     */
    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO);

    /**
     * 营销结算
     * @param requestDTO
     * @return
     */
    Response<SettlementMarketPayOrderResponseDTO> settlementMarketPayOrder(SettlementMarketPayOrderRequestDTO requestDTO);


}
