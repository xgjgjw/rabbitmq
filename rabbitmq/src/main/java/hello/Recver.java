package hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import rabbitmqutil.ConnectionUtil;

public class Recver {
    private final static String QUEUE = "testhello";//队列的名字

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE,false,false,false,null);
        //定义消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        //接收消息
        channel.basicConsume(QUEUE,true,queueingConsumer);
        //获取消息
        while (true) {
            //如果没有消息则等待，有的话就获取执行后销毁，一次性
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String massage = new String(delivery.getBody());
            System.out.println(massage);
        }
    }
}
