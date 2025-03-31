package com.hb.domain.activity.service.discount.impl;

import com.hb.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.hb.domain.activity.service.discount.AbstractDiscountCalculateService;
import com.hb.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 满减，拿到折扣的配置表达式，拆分 100,10，之后判断商品金额是否满足100元，满足100元则可以减去10元。不过这里要知道，如果商品最终折扣价格不足1分钱，要按照1分钱计算。
 */
@Slf4j
@Service("MJ")
public class MJCalculateService extends AbstractDiscountCalculateService {

    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("优惠策略折扣计算:{},{}", groupBuyDiscount.getDiscountType().getCode(),groupBuyDiscount.getDiscountType().getInfo());

        // 折扣表达式 - 100,10 满100减10元
        String marketExpr = groupBuyDiscount.getMarketExpr();
        String[] split = marketExpr.split(Constants.SPLIT);
        BigDecimal x = new BigDecimal(split[0].trim());
        BigDecimal y = new BigDecimal(split[1].trim());

        // 不满足最低满减约束，则按照原价
        if (originalPrice.compareTo(x) < 0) {
            return originalPrice;
        }

        // 折扣价格
        BigDecimal deductionPrice = originalPrice.subtract(y);

        // 判断折扣后金额，最低支付1分钱
        if(deductionPrice.compareTo(BigDecimal.ZERO)<=0) {
            return new BigDecimal("0.01");
        }
        return deductionPrice;
    }

}
