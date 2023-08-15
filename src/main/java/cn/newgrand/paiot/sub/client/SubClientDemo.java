package cn.newgrand.paiot.sub.client;

import com.rabbitmq.client.*;

/**
 * 类  名：cn.newgrand.paiot.sub.client.SubClientDemo
 * 类描述：订阅客户端demo
 * 创建人：许豪
 * 创建时间：2023/8/15 14:00
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author 许豪
 * @version 1.0.0
 * @Date 2023/8/15 14:00
 */
public class SubClientDemo {

    // region 以下参数通过【消息转发-AMQP订阅-消费组-详情-订阅参数】获取

    /**
     * 租户ID
     */
    private static final String TENANT_ID = "***";

    /**
     * 地址
     */
    private static final String HOST = "***";

    /**
     * 端口
     */
    private static final Integer PORT = 0;

    /**
     * 账号
     */
    private static final String USERNAME = "***";

    /**
     * 密码
     */
    private static final String PASSWORD = "***";

    /**
     * 消费组ID
     */
    private static final String CONSUMER_GROUP_ID = "***";

    // endregion

    public static void main(String[] args) {
        Connection conn = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setPort(PORT);
            factory.setUsername(USERNAME);
            factory.setPassword(PASSWORD);
            factory.setVirtualHost(TENANT_ID);

            conn = factory.newConnection();
            channel = conn.createChannel();
            Consumer callback = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println("received message -> " + new String(body));
                }
            };
            channel.basicConsume(CONSUMER_GROUP_ID, true, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
