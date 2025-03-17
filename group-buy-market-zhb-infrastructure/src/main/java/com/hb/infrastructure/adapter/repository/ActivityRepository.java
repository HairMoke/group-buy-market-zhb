package com.hb.infrastructure.adapter.repository;

import com.hb.domain.activity.adapter.repository.IActivityRepository;
import com.hb.domain.activity.model.valobj.DiscountTypeEnum;
import com.hb.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.hb.domain.activity.model.valobj.SCSkuActivityVO;
import com.hb.domain.activity.model.valobj.SkuVO;
import com.hb.infrastructure.dao.IGroupBuyActivateDao;
import com.hb.infrastructure.dao.IGroupBuyDiscountDao;
import com.hb.infrastructure.dao.ISCSkuActivityDao;
import com.hb.infrastructure.dao.ISkuDao;
import com.hb.infrastructure.dao.po.GroupBuyActivity;
import com.hb.infrastructure.dao.po.GroupBuyDiscount;
import com.hb.infrastructure.dao.po.SCSkuActivity;
import com.hb.infrastructure.dao.po.Sku;
import com.hb.infrastructure.dcc.DCCService;
import com.hb.infrastructure.redis.IRedisService;
import org.redisson.api.RBitSet;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IGroupBuyActivateDao groupBuyActivateDao;

    @Resource
    private IGroupBuyDiscountDao groupBuyDiscountDao;

    @Resource
    private ISkuDao skuDao;

    @Resource
    private ISCSkuActivityDao skuActivityDao;

    @Resource
    private IRedisService redisService;

    @Resource
    private DCCService dccService;


    @Override
    public GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId) {
        GroupBuyActivity groupBuyActivityRes = groupBuyActivateDao.queryValidGroupBuyActivityId(activityId);
        if(null == groupBuyActivityRes){
            return null;
        }

        String discountId = groupBuyActivityRes.getDiscountId();

        GroupBuyDiscount groupBuyDiscountRes = groupBuyDiscountDao.queryGroupBuyDiscountByActivityDiscountId(discountId);
        if(null == groupBuyDiscountRes){
            return null;
        }

        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = GroupBuyActivityDiscountVO.GroupBuyDiscount.builder()
                .discountName(groupBuyDiscountRes.getDiscountName())
                .discountDesc(groupBuyDiscountRes.getDiscountDesc())
                .discountType(DiscountTypeEnum.get(groupBuyDiscountRes.getDiscountType()))
                .marketPlan(groupBuyDiscountRes.getMarketPlan())
                .marketExpr(groupBuyDiscountRes.getMarketExpr())
                .tagId(groupBuyDiscountRes.getTagId())
                .build();

        return GroupBuyActivityDiscountVO.builder()
                .activityId(groupBuyActivityRes.getActivityId())
                .activityName(groupBuyActivityRes.getActivityName())
                .groupBuyDiscount(groupBuyDiscount)
                .groupType(groupBuyActivityRes.getGroupType())
                .takeLimitCount(groupBuyActivityRes.getTakeLimitCount())
                .target(groupBuyActivityRes.getTarget())
                .validTime(groupBuyActivityRes.getValidTime())
                .status(groupBuyActivityRes.getStatus())
                .startTime(groupBuyActivityRes.getStartTime())
                .endTime(groupBuyActivityRes.getEndTime())
                .tagId(groupBuyActivityRes.getTagId())
                .tagScope(groupBuyActivityRes.getTagScope())
                .build();
    }

    @Override
    public SkuVO querySkuByGoodsId(String goodsId) {
        Sku sku = skuDao.querySkuByGoodsId(goodsId);
        if(null == sku) return null;
        return SkuVO.builder()
                .goodsId(sku.getGoodsId())
                .goodsName(sku.getGoodsName())
                .originalPrice(sku.getOriginalPrice())
                .build();
    }


    @Override
    public SCSkuActivityVO querySCSkuActivityBySCGoodsId(String source, String channel, String goodsId) {
        SCSkuActivity scSkuActivityReq = new SCSkuActivity();
        scSkuActivityReq.setSource(source);
        scSkuActivityReq.setChannel(channel);
        scSkuActivityReq.setGoodsId(goodsId);

        SCSkuActivity scSkuActivity = skuActivityDao.querySCSkuActivityBySCGoodsId(scSkuActivityReq);
        if(null == scSkuActivity) {
            return null;
        }

        return SCSkuActivityVO.builder()
                .source(scSkuActivity.getSource())
                .chanel(scSkuActivity.getChannel())
                .activityId(scSkuActivity.getActivityId())
                .goodsId(scSkuActivity.getGoodsId())
                .build();
    }

    @Override
    public boolean isTagCrowdRange(String tagId, String userId) {
        RBitSet bitSet = redisService.getBitSet(tagId);
        if(!bitSet.isExists()) return true;
        // 判断用户是否在人群中
        return bitSet.get(redisService.getIndexFromUserId(userId));
    }

    @Override
    public boolean downgradeSwitch() {
        return dccService.isDowngradeSwitch();
    }

    @Override
    public boolean curRange(String userId) {
        return dccService.isCutRange(userId);
    }
}
