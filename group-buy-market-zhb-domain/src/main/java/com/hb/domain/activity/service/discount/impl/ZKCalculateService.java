package com.hb.domain.activity.service.discount.impl;

import com.hb.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.hb.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 折扣
 */
@Slf4j
@Service("ZK")
public class ZKCalculateService extends AbstractDiscountCalculateService {
    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("优惠策略折扣计算:{}", groupBuyDiscount.getDiscountType().getCode());

        // 折扣表达式 —— 折扣的百分比
        String marketExpr = groupBuyDiscount.getMarketExpr();

        // 折扣价格 + 四舍五入
        BigDecimal deductionPrice = originalPrice.multiply(new BigDecimal(marketExpr)).setScale(0, RoundingMode.DOWN);

        // 判断折扣后金额， 最低支付一分钱
        if(deductionPrice.compareTo(BigDecimal.ZERO) <= 0){
            return new BigDecimal("0.01");
        }
        return deductionPrice;
    }
}
