package com.huayu.irla.manage.service.courseware.jms.producer;
import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;

import com.huayu.irla.manage.service.courseware.jms.adapter.Product;


public class MessageProducer {
    private ActiveMQQueue destination;
    
    private List<JmsTemplate> jmsTemplate;

    private Product product;
    
    //原子整型计数（CAS），可以不使用同步
   // private AtomicInteger current = new AtomicInteger(0);
    
    //轮询算法解决负载均衡
    private JmsTemplate findJmsTemplate(){
            //int cur = current.getAndIncrement();
            //int index = cur%jmsTemplate.size();
            //  int index = cur%jmsTemplate.size();
            return jmsTemplate.get(0);
    }
    
    //发送消息
    public void sendMessage(Product product){
        this.findJmsTemplate().convertAndSend(destination, product);
    }

    public ActiveMQQueue getDestination() {
        return destination;
    }

    public void setDestination(ActiveMQQueue destination) {
        this.destination = destination;
    }

    public List<JmsTemplate> getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(List<JmsTemplate> jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
    
  } 
   