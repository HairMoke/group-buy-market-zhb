package com.hb.infrastructure.dao;

import com.hb.infrastructure.dao.po.GroupBuyOrderList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户拼单明细
 */
@Mapper
public interface IGroupBuyOrderListDao {

    /**
     * 插入拼单明细
     * @param groupBuyOrderListReq
     */
    void insert(GroupBuyOrderList groupBuyOrderListReq);

    /**
     * 查询交易记录
     * @param groupBuyOrderListReq
     * @return
     */
    GroupBuyOrderList queryGroupBuyOrderRecordByOutTradeNo(GroupBuyOrderList groupBuyOrderListReq);

    Integer queryOrderCountByActivityId(GroupBuyOrderList groupBuyOrderListReq);

    int updateOrderStatus2COMPLETE(GroupBuyOrderList groupBuyOrderListReq);

    List<String> queryGroupBuyCompleteOrderOutTradeNoListByTeamId(String teamId);
}
