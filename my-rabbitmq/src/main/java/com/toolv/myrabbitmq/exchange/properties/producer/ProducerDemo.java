package com.toolv.myrabbitmq.exchange.properties.producer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.AMQBasicProperties;

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

		// 用于存放一些额外的数据，看具体业务场景
		Map<String,Object> headers = new HashMap<>();
		headers.put("code", "av170001");
		headers.put("bilibili_id", "6874572");

		AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
		AMQP.BasicProperties basicProperties = builder.deliveryMode(2).contentEncoding("UTF-8").expiration("10000").headers(headers).build();

		String exchangeName = "direct_exchange_name";
		String routingKey = "direct_test_rk";

		for (int i = 0; i < 10; i++)
		{
			String msg = "Hello RabbitMQ - " + i;
			System.out.println("producer=>" + msg);
			channel.basicPublish(exchangeName, routingKey, basicProperties, msg.getBytes(StandardCharsets.UTF_8));
			TimeUnit.SECONDS.sleep(1);
		}

		channel.close();
		connection.close();
	}
}
