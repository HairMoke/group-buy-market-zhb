package com.hb.types.design.framework.tree;

import lombok.Getter;
import lombok.Setter;

/**
 * 通过调用策略映射器get方法，控制节点流程的走向。
 * @param <T>
 * @param <D>
 * @param <R>
 */
public abstract class AbstractStrategyRouter<T, D, R> implements StrategyMapper<T, D, R>, StrategyHandler<T, D, R> {

    @Getter
    @Setter
    protected StrategyHandler<T, D, R> defaultStrategyHandler = StrategyHandler.DEFAULT;

    public R router(T requestParameter, D dynamicParameter) throws Exception{
        StrategyHandler<T, D, R> strategyHandler = get(requestParameter, dynamicParameter);
        if(null != strategyHandler) {
            return strategyHandler.apply(requestParameter, dynamicParameter);
        }
        return defaultStrategyHandler.apply(requestParameter, dynamicParameter);
    }
}
