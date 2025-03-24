package com.hb.types.design.framework.link.model2.chain;


import com.hb.types.design.framework.link.model2.handler.ILogicHandler;

/**
 * 业务链路
 */
public class BusinessLinkedList<T, D, R> extends LinkedList<ILogicHandler> implements ILogicHandler<T, D, R> {

    public BusinessLinkedList(String name) {
        super(name);
    }

    @Override
    public R apply(T requestParameter, D dynamicContext) throws Exception {
        Node<ILogicHandler> current = this.first;
        do {
            ILogicHandler<T, D, R> item = current.item;
            R apply = item.apply(requestParameter, dynamicContext);
            if (null != apply) return apply;

            current = current.next;
        } while (null != current);

        return null;
    }
}
