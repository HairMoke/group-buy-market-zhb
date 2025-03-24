package com.hb.types.design.framework.link.model1;

/**
 *
 ILogicLink 继承 ILogicChainArmory，并提供一个受理业务逻辑的方法。
 * @param <T>
 * @param <D>
 * @param <R>
 */
public interface ILogicLink<T, D, R> extends ILogicChainArmory<T, D, R>{
    R apply(T requestParameter, D dynamicContext) throws Exception;
}
