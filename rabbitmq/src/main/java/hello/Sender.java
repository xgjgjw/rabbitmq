package hello;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmqutil.ConnectionUtil;


public class Sender {
    private final static String QUEUE = "testhello";//queue name
    public static void main(String[] args) throws Exception{
        //1.获取连接
        Connection connection = ConnectionUtil.getConnection();
        //2.创建通道
        Channel channel = connection.createChannel();
        //3.声明队列
            //arg1-queue name
            //arg2-是否持久化队列,我们的队列模式是在内存中的,如果 rabbitmq 重启会丢失,如果我们设置为 true, 则会保存到 erlang 自带的数据库中,重启后会重新读取
            //arg3-是否排外,false有两个作用,第一个当我们的连接关闭后是否会自动删除队列,作用二 是否私有当前队列,如果私有了,其他通道不可以访问当前队列,如果为 true, 一般是一个队列只适用于一个消费者的时候
            //arg4-是否自动删除
            //arg4-other param
        channel.queueDeclare(QUEUE,false,false,false,null);
        //4.发送内容
        channel.basicPublish("",QUEUE,null,"发送的消息".getBytes());
        //5.关闭连接
        channel.close();
        connection.close();
    }
}
