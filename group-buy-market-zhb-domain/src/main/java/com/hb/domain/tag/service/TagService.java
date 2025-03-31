package com.hb.domain.tag.service;


import com.hb.domain.tag.adapter.repository.ITagRepository;
import com.hb.domain.tag.model.entity.CrowdTagsJobEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 人群标签服务
 */
@Slf4j
@Service
public class TagService implements ITagService{

    @Resource
    private ITagRepository repository;

    @Override
    public void execTagBatchJob(String tagId, String batchId) {
        log.info("人群标签批次任务 tagId:{} batchId:{}", tagId, batchId);

        // 1. 查询批次任务
        CrowdTagsJobEntity crowdTagsJobEntity = repository.queryCrowdTagsJobEntity(tagId, batchId);

        // 2. 采集用户数据 —— 这部分需要采集用户的消费类数据，后续有用户发起拼单后再处理。
        // 通过采集人群标签任务获取人群数据，暂时没有这类业务数据，所以先模拟一个用户数据，你也可以调整这里的数据为你需要的。

        // 3. 数据写入记录
        List<String> userIdList = new ArrayList<String>(){{
            add("xiaofuge");
            add("liergou");
            add("xfg01");
            add("xfg02");
            add("xfg03");
            add("xfg04");
            add("xfg05");
            add("xfg06");
            add("xfg07");
            add("xfg08");
            add("xfg09");
        }};

        // 4. 一般人群标签的处理在公司中，会有专门的数据数仓团队通过脚本方式写入到数据库，就不用这样一个个或者批次来写。
        /**
         * 采集数据后，repository.addCrowdTagsUserId(tagId, userId); 写入到数据库表。注意 addCrowdTagsUserId 方法，写入后还会做 BitMap 存储。
         */
        for (String userId : userIdList) {
            repository.addCrowdTagsUserId(tagId, userId);
        }

        /**
         * 这些操作完成后，会更新统计量。注意，目前的统计量更新是不准的，因为执行 addCrowdTagsUserId 操作，会有主键冲突，主键冲突直接拦截不会抛异常。那么更新人群标签的统计量会继续增加。你可以思考下这里要怎么处理，课程后续也会继续处理。
         */
        // 5. 更新人群标签统计量
        repository.updateCrowdTagsStatistics(tagId, userIdList.size());
    }
}
