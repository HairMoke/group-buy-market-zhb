package com.hb.domain.activity.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketProductEntity {

    /**  用户id */
    private String userId;
    /** 商品id */
    private String goodsId;
    /** 渠道 */
    private String source;
    /** 来源 */
    private String channel;
}
