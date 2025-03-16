package com.hb.domain.activity.service.trial.node;

import com.alibaba.fastjson.JSON;
import com.hb.domain.activity.adapter.repository.IActivityRepository;
import com.hb.domain.activity.model.GroupBuyActivityDiscountVO;
import com.hb.domain.activity.model.SkuVO;
import com.hb.domain.activity.model.entity.MarketProductEntity;
import com.hb.domain.activity.model.entity.TrialBalanceEntity;
import com.hb.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.hb.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import com.hb.domain.activity.service.trial.thread.QueryGroupBuyActivityDiscountVOThreadTask;
import com.hb.domain.activity.service.trial.thread.QuerySkuVOFromDBThreadTask;
import com.hb.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.*;


@Slf4j
@Service
public class MarketNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private EndNode endNode;


    /**
     * multiThread 方法中，启动对异步数据的查询处理，之后在使用动态上下文承接数据。
     threadPoolExecutor 线程池配置的是 CallerRunsPolicy 策略。当线程池中的任务队列已满，并且没有空闲线程可以执行新任务时，
     CallerRunsPolicy 会将任务回退到调用者线程中运行。这种策略适用于不希望丢失任务且可以接受调用者线程被阻塞的场景。
     【有时候面试不会直接问八股，而是结合这样的场景来问。】
     * @param requestParameter
     * @param dynamicContext
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    @Override
    protected void multiThread(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        // 异步查询活动配置
        QueryGroupBuyActivityDiscountVOThreadTask queryGroupBuyActivityDiscountVOThreadTask = new QueryGroupBuyActivityDiscountVOThreadTask(requestParameter.getSource(), requestParameter.getChannel(), repository);
        FutureTask<GroupBuyActivityDiscountVO> groupBuyActivityDiscountVOFutureTask = new FutureTask<>(queryGroupBuyActivityDiscountVOThreadTask);
        threadPoolExecutor.execute(groupBuyActivityDiscountVOFutureTask);

        // 异步查询商品信息，在实际生产中， 商品有同步库或者调用接口，这里暂时使用DB方式查询
        QuerySkuVOFromDBThreadTask querySkuVOFromDBThreadTask = new QuerySkuVOFromDBThreadTask(requestParameter.getGoodsId(), repository);
        FutureTask<SkuVO> skuVOFutureTask = new FutureTask<>(querySkuVOFromDBThreadTask);
        threadPoolExecutor.execute(skuVOFutureTask);

        // 写入上下文，对于一些复杂的场景， 获取数据的操作，有时候会在下N个节点获取，这样前置查询数据，可以提高接口响应效率
        dynamicContext.setGroupBuyActivityDiscountVO(groupBuyActivityDiscountVOFutureTask.get(timeout, TimeUnit.MINUTES));
        dynamicContext.setSkuVO(skuVOFutureTask.get(timeout, TimeUnit.MINUTES));

        log.info("拼团商品查询是算服务-MarketNode userId:{} 异步线程加载数据「GroupBuyActivityDiscountVO、SkuVO」完成 ", requestParameter.getUserId());
    }

    /**
     *
     doApply 受理业务流程的实现放在后续在处理。
     * @param requestParameter
     * @param dynamicContext
     * @return
     * @throws Exception
     */
    @Override
    public TrialBalanceEntity doApply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("拼团商品查询试算服务-MarketNode userId:{} requestParameter:{}", requestParameter.getUserId(), JSON.toJSONString(requestParameter));

        // todo 拼团优惠试算

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }
}
