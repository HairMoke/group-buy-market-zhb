package com.hb.domain.activity.service;

import com.hb.domain.activity.model.entity.MarketProductEntity;
import com.hb.domain.activity.model.entity.TrialBalanceEntity;

public interface IIndexGroupBuyMarketService {

    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception;
}
