package com.hb.domain.activity.adapter.repository;

import com.hb.domain.activity.model.GroupBuyActivityDiscountVO;
import com.hb.domain.activity.model.SkuVO;

public interface IActivityRepository {


    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String source, String channel);

    SkuVO querySkuByGoodsId(String goodsId);

}
