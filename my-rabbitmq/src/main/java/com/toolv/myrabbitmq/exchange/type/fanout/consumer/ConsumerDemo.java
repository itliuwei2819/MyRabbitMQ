package com.toolv.myrabbitmq.exchange.type.fanout.consumer;

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

		// 声明交换机
		String exchangeName = "fanout_exchange_name";
		channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true, false, false, null);

		// 声明队列
		String queueName = "test001";
		channel.queueDeclare(queueName, true, false, false, null);

		// 将队列、交换机、路由键绑定到一起
		String routingKey = "must be non-null..shit";
		channel.queueBind(queueName, exchangeName, routingKey);

		channel.basicConsume(queueName, true, new FanoutConsumer());
	}
}
