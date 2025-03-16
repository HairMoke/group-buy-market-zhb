package com.hb.domain.activity.service.discount;

import com.hb.domain.activity.model.valobj.DiscountTypeEnum;
import com.hb.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

/**
 * 定义抽象模板封装计算折扣优惠的执行过程。人群标签部分后续统一处理。
 */
public abstract class AbstractDiscountCalculateService implements IDiscountCalculateService {

    @Override
    public BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        // 人群标签过滤
        if (DiscountTypeEnum.TAG.equals(groupBuyDiscount.getDiscountType())) {
            boolean isCrowdRange = filterTagId(userId, groupBuyDiscount.getTagId());
            if (!isCrowdRange) {
                return originalPrice;
            }
        }
        // 折扣计算
        return doCalculate(originalPrice, groupBuyDiscount);
    }

    // 人群过滤，- 限定人群优惠
    private boolean filterTagId(String userId, String tagId) {
        // todo 后续开发
        return true;
    }

    protected abstract BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);


}
