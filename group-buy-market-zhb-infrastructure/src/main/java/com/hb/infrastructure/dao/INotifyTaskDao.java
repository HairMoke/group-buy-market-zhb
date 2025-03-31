package com.hb.infrastructure.dao;

import com.hb.infrastructure.dao.po.NotifyTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * 回调任务
 */
@Mapper
public interface INotifyTaskDao {

    void insert(NotifyTask notifyTask);
}
