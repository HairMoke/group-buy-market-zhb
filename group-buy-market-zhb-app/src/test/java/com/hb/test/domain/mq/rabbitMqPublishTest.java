package com.hb.test.domain.mq;

import com.alibaba.fastjson.JSON;
import com.hb.infrastructure.event.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class rabbitMqPublishTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.config.producer.exchange}")
    private String exchangeName;

    @Value("${spring.rabbitmq.config.producer.topic_team_success.routing_key}")
    private String routingKey;


    @Resource
    private EventPublisher publisher;

    @Test
    public void publish() {
        String message = new String("我是一条测试消息2");
        try {
//            rabbitTemplate.convertAndSend(exchangeName,routingKey,message, m -> {
//                // 持久化消息配置
//                m.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
//                return m;
//            });
            publisher.publish(routingKey, JSON.toJSONString(message));
        } catch (Exception e) {
            log.error("发送MQ消息失败 team_success message:{}", message, e);
            throw e;
        }
    }

}
