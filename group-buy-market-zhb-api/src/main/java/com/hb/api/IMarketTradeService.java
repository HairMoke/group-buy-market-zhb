package com.hb.api;

import com.hb.api.dto.LockMarketPayOrderRequestDTO;
import com.hb.api.dto.LockMarketPayOrderResponseDTO;
import com.hb.api.response.Response;

/**
 * 营销交易服务接口
 */
public interface IMarketTradeService {

    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO);

}
