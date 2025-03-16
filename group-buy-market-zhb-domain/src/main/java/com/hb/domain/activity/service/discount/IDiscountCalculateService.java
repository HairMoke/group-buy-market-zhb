package com.hb.domain.activity.service.discount;

import com.hb.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

/**
 * 定义折扣计算的接口，包括用户ID、商品原始价格、折扣计划配置。
 *
 * 用户ID，主要用于后续做人群标签的过滤使用。
 */
public interface IDiscountCalculateService {
    /**
     * 折扣计算
     *
     * @param userId           用户ID
     * @param originalPrice    商品原始价格
     * @param groupBuyDiscount 折扣计划配置
     * @return 商品优惠价格
     */
    BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);
}
