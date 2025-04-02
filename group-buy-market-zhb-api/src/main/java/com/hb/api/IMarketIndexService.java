package com.hb.api;

import com.hb.api.dto.GoodsMarketRequestDTO;
import com.hb.api.dto.GoodsMarketResponseDTO;
import com.hb.api.response.Response;

public interface IMarketIndexService {

    Response<GoodsMarketResponseDTO> queryGroupBuyMarketConfig(GoodsMarketRequestDTO goodsMarketRequestDTO);
}
