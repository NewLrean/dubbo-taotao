package com.taotao;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.io.IOException;

/**
 * Created by 蒋志鹏 on 2018/7/9.
 */
public class ActionMQTest {

@Test
    public void testQueueProducer() throws JMSException {
        ConnectionFactory connectionFactory=new
                ActiveMQConnectionFactory("tcp://192.168.25.153:61616");
        Connection connection=connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue=session.createQueue("test-queue");
        MessageProducer producer=session.createProducer(queue);
        TextMessage textMessage=session.createTextMessage("this id my first queue test");
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();

    }

    @Test
    public void testQueueConsumer() throws JMSException, IOException {
        ConnectionFactory connectionFactory=new
                ActiveMQConnectionFactory("tcp://192.168.25.153:61616");
        Connection connection=connectionFactory.createConnection();
        connection.start();
        Session  session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue=session.createQueue("test-queue");

        MessageConsumer consumer=session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage= (TextMessage) message;
                String text=null;
                try {
                    text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();

        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicProducer() throws JMSException {
        ConnectionFactory connectionFactory=new
                ActiveMQConnectionFactory("tcp://192.168.25.153:61616");
        Connection connection=connectionFactory.createConnection();
        connection.start();
        Session  session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("text-topuc");
        MessageProducer producer=session.createProducer(topic);
        TextMessage textMessage = session.createTextMessage("this is my topic test");

        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();

}


@Test
    public void testTopicConsumer() throws JMSException, IOException {
        ConnectionFactory connectionFactory=new
                ActiveMQConnectionFactory("tcp://192.168.25.153:61616");
        Connection connection=connectionFactory.createConnection();
        connection.start();
        Session  session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("text-topuc");
        MessageConsumer consumer= session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage){
                    TextMessage textMessage= (TextMessage) message;
                    String text = null;
                    try {
                        text = textMessage.getText();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                    System.out.println(text);
                }
            }
        });
        System.out.println("topic的消费端03。。。。。");
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }



    @Test
    public void testQueueProducer2(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:mybatis/spring-mybatis.xml");
        JmsTemplate jmsTemplate = ac.getBean(JmsTemplate.class);
        Topic topic = (Topic) ac.getBean("topic");
        jmsTemplate.send(topic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage=session.createTextMessage("1531124330723002");
                return textMessage;
            }
        });
    }


}
