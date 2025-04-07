package com.hb.domain.activity.model.valobj;


import com.hb.types.common.Constants;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupBuyActivityDiscountVO {

    /** 活动ID */
    private Long activityId;

    /** 活动名称 */
    private String activityName;

    /** 来源 */
    private String source;

    /** 渠道 */
    private String channel;

    /** 商品ID */
    private String goodsId;

    /** 折扣 */
    private GroupBuyDiscount groupBuyDiscount;

    /** 拼团方式（0自动成团、1达成目标拼团） */
    private Integer groupType;

    /** 拼团次数限制 */
    private Integer takeLimitCount;

    /** 拼团目标 */
    private Integer target;

    /** 拼团时长（分钟） */
    private Integer validTime;

    /** 活动状态（0创建、1生效、2过期、3废弃） */
    private Integer status;

    /** 活动开始时间 */
    private Date startTime;

    /** 活动结束时间 */
    private Date endTime;

    /** 人群标签规则标识 */
    private String tagId;

    /** 人群标签规则范围（多选；1可见限制、2参与限制） */
    private String tagScope;

    /**
     * 可见限制
     * 只要存在这样一个值， 那么首次获得的是默认值就是false
     * 可见限制；方法聚合到到类中，判断是否配置了1。如果配置了，那么默认这个对应的值的结果就是 false，之后在判断是否在人群范围内，如果在人群范围内则为 true。
     * @return
     */
    public boolean isVisible(){
        if(StringUtils.isBlank(this.tagScope)) return TagScopeEnumVO.VISIBLE.getAllow();
        String[] split = this.tagScope.split(Constants.SPLIT);
        if(split.length > 0 && Objects.equals(split[0], "1") && StringUtils.isNotBlank(split[0])) {
            return TagScopeEnumVO.VISIBLE.getRefuse();
        }
        return TagScopeEnumVO.VISIBLE.getAllow();
    }

    /**
     * 参与限制
     * 只要存在这样一个值，那么首次获得的默认值就是false
     * 参与限制；方法聚合到到类中，判断是否配置了2。如果配置了，那么默认这个对应的值的结果就是 false，之后在判断是否在人群范围内，如果在人群范围内则为 true。
     * @return
     */
    public boolean isEnable(){
        if(StringUtils.isBlank(this.tagScope)) return TagScopeEnumVO.VISIBLE.getAllow();
        String[] split = this.tagScope.split(Constants.SPLIT);
        if(split.length == 2 && Objects.equals(split[1], "2") && StringUtils.isNotBlank(split[1])) {
            return TagScopeEnumVO.ENABLE.getRefuse();
        }
        if(split.length == 1 && Objects.equals(split[0], "2")) {
            return TagScopeEnumVO.ENABLE.getRefuse();
        }
        return TagScopeEnumVO.ENABLE.getAllow();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupBuyDiscount{
        /** 折扣标题 */
        private String discountName;

        /** 折扣描述 */
        private String discountDesc;

        /** 折扣类型（0:base、1:tag） */
        private DiscountTypeEnum discountType;

        /** 营销优惠计划（ZJ:直减、MJ:满减、N元购） */
        private String marketPlan;

        /** 营销优惠表达式 */
        private String marketExpr;

        /** 人群标签，特定优惠限定 */
        private String tagId;
    }
}
