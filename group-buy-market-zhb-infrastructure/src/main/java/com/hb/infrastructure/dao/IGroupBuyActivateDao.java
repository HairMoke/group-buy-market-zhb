package com.hb.infrastructure.dao;

import com.hb.infrastructure.dao.po.GroupBuyActivity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IGroupBuyActivateDao {

    List<GroupBuyActivity> queryGroupBuyActiveList();
}
