package com.toolv.myrabbitmq.dead.producer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerDemo
{
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException
	{
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.219.101");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");

		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();

		String exchangeName = "dead_exchange_name";
		String routingKey = "dead_rk_name.save";

		// 设置消息10秒超时，用于配合测试死信队列
		AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
		builder.expiration("10000");
		AMQP.BasicProperties properties = builder.build();

		for (int i = 0; i < 10; i++)
		{
			String msg = "Hello RabbitMQ - " + i;
			System.out.println("producer=>" + msg);
			channel.basicPublish(exchangeName, routingKey, properties, msg.getBytes(StandardCharsets.UTF_8));
			TimeUnit.SECONDS.sleep(1);
		}

		channel.close();
		connection.close();
	}
}
