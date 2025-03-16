package com.hb.domain.tag.adapter.repository;

import com.hb.domain.tag.model.entity.CrowdTagsJobEntity;

/**
 * 人群标签仓储接口
 */
public interface ITagRepository {

    CrowdTagsJobEntity queryCrowdTagsJobEntity(String tagId, String batchId);

    void addCrowdTagsUserId(String tagId, String userId);

    void updateCrowdTagsStatistics(String tagId, int count);


}
