package com.hb.types.design.framework.tree;

/**
 * 策略映射器
 *
 StrategyMapper#get 方法用于获取每一个要执行的节点，相当于一个流程走完进入到下一个流程的过程。这在我们处理复杂的业务代码时是非常重要的，避免把所有逻辑都写到一个类的方法中。
 泛型 T - 入参、D - 上下文、R - 返参。
 */
public interface StrategyMapper<T, D, R> {


    /**
     * 获取待执行策略
     *
     * @param requestParameter 入参
     * @param dynamicContext   上下文
     * @return 返参
     * @throws Exception 异常
     */
    StrategyHandler<T, D, R> get(T requestParameter, D dynamicContext) throws Exception;
}
