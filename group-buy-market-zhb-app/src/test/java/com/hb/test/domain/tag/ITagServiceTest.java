package com.hb.test.domain.tag;


import com.hb.domain.tag.service.TagService;
import com.hb.infrastructure.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBitSet;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ITagServiceTest {

    @Resource
    private TagService tagService;

    @Resource
    private IRedisService redisService;


    /**
     * 要是测试的时候总是到tag节点就挂掉了，先测试test_get_tag_bitmap()看看在不在redis中，redis中的会过期，要是不存在，执行test_tag_job()方法把数据重新往redis写一遍
     */
    @Test
    public void test_tag_job(){
        tagService.execTagBatchJob("RQ_KJHKL98UU78H66554GFDV","10001");
    }

    @Test
    public void test_get_tag_bitmap() {
        RBitSet bitSet = redisService.getBitSet("RQ_KJHKL98UU78H66554GFDV");
        // 是否存在
        log.info("xiaofuge 存在，预期结果为 true，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("xiaofuge")));
        log.info("gudebai 不存在，预期结果为 false，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("gudebai")));
        log.info("xfg04 存在，预期结果为 true，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("xfg04")));
        log.info("xiaofuge04 存在，预期结果为 true，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("xiaofuge04")));
    }


    @Test
    public void test_redis_connection(){
        redisService.setValue("test", "test Value",10);
    }

}
