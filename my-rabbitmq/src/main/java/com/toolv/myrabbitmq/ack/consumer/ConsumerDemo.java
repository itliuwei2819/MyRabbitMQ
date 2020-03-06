package com.toolv.myrabbitmq.ack.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConsumerDemo
{
	public static void main(String[] args) throws IOException, TimeoutException
	{
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.219.101");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");

		Connection connection = connectionFactory.newConnection();

		Channel channel = connection.createChannel();

		// 设置每次只接收一个消息
		channel.basicQos(0, 1, false);

		// 声明交换机
		String exchangeName = "ack_exchange_name";
		channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true, false, false, null);

		// 声明队列
		String queueName = "test001";
		channel.queueDeclare(queueName, true, false, false, null);

		// 将队列、交换机、路由键绑定到一起
		String routingKey = "ack_test_rk.*";
		channel.queueBind(queueName, exchangeName, routingKey);

		// autoAck一定要关闭
		channel.basicConsume(queueName, false, new AckConsumer(channel));
	}
}
