package com.hb.domain.activity.service.discount;

import com.hb.domain.activity.adapter.repository.IActivityRepository;
import com.hb.domain.activity.model.valobj.DiscountTypeEnum;
import com.hb.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 定义抽象模板封装计算折扣优惠的执行过程。人群标签部分后续统一处理。
 */
@Slf4j
public abstract class AbstractDiscountCalculateService implements IDiscountCalculateService {

    @Resource
    protected IActivityRepository repository;


    @Override
    public BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        // 人群标签过滤
        if (DiscountTypeEnum.TAG.equals(groupBuyDiscount.getDiscountType())) {
            boolean isCrowdRange = filterTagId(userId, groupBuyDiscount.getTagId());
            if (!isCrowdRange) {
                log.info("折扣优惠计算拦截 userId:{}", userId);
                return originalPrice;
            }
        }
        // 折扣计算
        return doCalculate(originalPrice, groupBuyDiscount);
    }

    // 人群过滤，- 限定人群优惠
    private boolean filterTagId(String userId, String tagId) {
        return repository.isTagCrowdRange(tagId, userId);
    }

    protected abstract BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);


}
