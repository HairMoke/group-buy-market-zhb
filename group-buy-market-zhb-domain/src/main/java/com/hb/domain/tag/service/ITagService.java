package com.hb.domain.tag.service;

/**
 * 人群标签服务接口
 */
public interface ITagService {

    /**
     * 执行人群标签批次任务
     * @param tagId
     * @param batchId
     */
    void execTagBatchJob(String tagId, String batchId);
}
