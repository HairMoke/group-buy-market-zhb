package com.hb.types.design.framework.link.model1;


/**
 * ILogicChainArmory 装配链，提供添加节点方法和获取节点。
 * @param <T>
 * @param <D>
 * @param <R>
 */
public interface ILogicChainArmory <T, D, R>{

    ILogicLink<T, D, R> next();

    ILogicLink<T, D, R> appendNext(ILogicLink<T, D, R> next);

}
