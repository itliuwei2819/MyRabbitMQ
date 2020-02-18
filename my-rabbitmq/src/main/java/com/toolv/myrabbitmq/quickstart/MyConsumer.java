package com.toolv.myrabbitmq.quickstart;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class MyConsumer
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

		String queueName = "test001";
		channel.queueDeclare(queueName, true, false, false, null);

		Consumer consumer = new Consumer()
		{
			@Override
			public void handleConsumeOk(String consumerTag)
			{

			}

			@Override
			public void handleCancelOk(String consumerTag)
			{

			}

			@Override
			public void handleCancel(String consumerTag) throws IOException
			{

			}

			@Override
			public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig)
			{

			}

			@Override
			public void handleRecoverOk(String consumerTag)
			{

			}

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
			{
				String s = new String(body, StandardCharsets.UTF_8);
				System.out.println(s);
			}
		};

		channel.basicConsume(queueName, true, consumer);
	}
}
