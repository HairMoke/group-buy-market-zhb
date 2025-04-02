package com.hb.infrastructure.dao;

import com.hb.infrastructure.dao.po.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 用户拼单
 */
@Mapper
public interface IGroupBuyOrderDao {

    /**
     * 插入数据
     * @param groupBuyOrder
     */
    void insert(GroupBuyOrder groupBuyOrder);

    /**
     * 更新锁单量+
     * @param teamId
     * @return
     */
    int updateAddLockCount(String teamId);

    /**
     * 更新锁单量-
     * @param teamId
     * @return
     */
    int updateSubtractionLockCount(String teamId);

    /**
     * 查询进度
     * @param teamId
     * @return
     */
    GroupBuyOrder queryGroupBuyProgress(String teamId);

    GroupBuyOrder queryGroupBuyTeamByTeamId(String teamId);

    int updateAddCompleteCount(String teamId);

    int updateOrderStatus2COMPLETE(String teamId);

    List<GroupBuyOrder> queryGroupBuyProgressByTeamIds(Set<String> teamIds);

    Integer queryAllTeamCount(Set<String> teamIds);

    Integer queryAllTeamCompleteCount(Set<String> teamIds);

    Integer queryAllUserCount(Set<String> teamIds);
}
