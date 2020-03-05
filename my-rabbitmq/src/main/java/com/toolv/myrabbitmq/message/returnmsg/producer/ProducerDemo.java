package com.toolv.myrabbitmq.message.returnmsg.producer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

		channel.addReturnListener(new ReturnListener());

		String exchangeName = "confirm_exchange_name";
		String routingKey = "confirm_test";

		for (int i = 0; i < 1; i++)
		{
			String msg = "Hello RabbitMQ - " + i;
			System.out.println("producer=>" + msg);
			// mandatory 开启路由不可达监听器的重要参数，默认false
			channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes(StandardCharsets.UTF_8));
			TimeUnit.SECONDS.sleep(1);
		}

		channel.close();
		connection.close();
	}
}
