package com.toolv.myrabbitmq.message.confirmmsg.producer;

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

		channel.confirmSelect();
		channel.addConfirmListener(new ConfirmListener());

		String exchangeName = "confirm_exchange_name";
		String routingKey = "confirm_test_rk";

		for (int i = 0; i < 10; i++)
		{
			String msg = "Hello RabbitMQ - " + i;
			System.out.println("producer=>" + msg);
			channel.basicPublish(exchangeName, routingKey, null, msg.getBytes(StandardCharsets.UTF_8));
			TimeUnit.SECONDS.sleep(1);
		}

		channel.close();
		connection.close();
	}
}
