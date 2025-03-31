package com.hb.infrastructure.adapter.repository;

import com.hb.domain.tag.adapter.repository.ITagRepository;
import com.hb.domain.tag.model.entity.CrowdTagsJobEntity;
import com.hb.infrastructure.dao.ICrowdTagsDao;
import com.hb.infrastructure.dao.ICrowdTagsDetailDao;
import com.hb.infrastructure.dao.ICrowdTagsJobDao;
import com.hb.infrastructure.dao.po.CrowdTags;
import com.hb.infrastructure.dao.po.CrowdTagsDetail;
import com.hb.infrastructure.dao.po.CrowdTagsJob;
import com.hb.infrastructure.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.redisson.api.RBitSet;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 人群标签仓储
 */
@Repository
public class TagRepository implements ITagRepository {

    @Resource
    private ICrowdTagsDao crowdTagsDao;

    @Resource
    private ICrowdTagsDetailDao crowdTagsDetailDao;

    @Resource
    private ICrowdTagsJobDao crowdTagsJobDao;

    @Resource
    private IRedisService redisService;

    @Override
    public CrowdTagsJobEntity queryCrowdTagsJobEntity(String tagId, String batchId) {
        CrowdTagsJob crowdTagsJob = new CrowdTagsJob();
        crowdTagsJob.setTagId(tagId);
        crowdTagsJob.setBatchId(batchId);

        CrowdTagsJob crowdTagsJobRes = crowdTagsJobDao.queryCrowdTagsJob(crowdTagsJob);
        if(null == crowdTagsJobRes) {
            return null;
        }

        return CrowdTagsJobEntity.builder()
                .tagType(crowdTagsJobRes.getTagType())
                .tagRule(crowdTagsJobRes.getTagRule())
                .statStartTime(crowdTagsJobRes.getStatStartTime())
                .statEndTime(crowdTagsJobRes.getStatEndTime())
                .build();
    }


    /**
     *
     执行完写库后，开始把数据写入到人群标签。
     不过注意人群标签的存储不是字符串，所以要转行为长整型进行存放。
     * @param tagId
     * @param userId
     */
    @Override
    public void addCrowdTagsUserId(String tagId, String userId) {
        CrowdTagsDetail crowdTagsDetailReq = new CrowdTagsDetail();
        crowdTagsDetailReq.setTagId(tagId);
        crowdTagsDetailReq.setUserId(userId);

        try{
            crowdTagsDetailDao.addCrowdTagsUserId(crowdTagsDetailReq);

            // 获取BitSet
            RBitSet bitSet = redisService.getBitSet(tagId);
            bitSet.set(redisService.getIndexFromUserId(userId), true);
        } catch (DuplicateKeyException ignore){
            // 忽略唯一索引冲突
        }
    }

    @Override
    public void updateCrowdTagsStatistics(String tagId, int count) {
        CrowdTags crowdTagsReq = new CrowdTags();
        crowdTagsReq.setTagId(tagId);
        crowdTagsReq.setStatistics(count);

        crowdTagsDao.updateCrowdTagsStatistics(crowdTagsReq);
    }
}
