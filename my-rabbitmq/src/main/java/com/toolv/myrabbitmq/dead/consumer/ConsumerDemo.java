package com.toolv.myrabbitmq.dead.consumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
		String exchangeName = "dead_exchange_name";
		channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true, false, false, null);

		// 死信交换机&队列声明&绑定
		channel.exchangeDeclare("dlx_exchange_name", BuiltinExchangeType.TOPIC, true, false, false, null);
		channel.queueDeclare("dlx_queue_name", true, false, false, null);
		channel.queueBind("dlx_queue_name", "dlx_exchange_name", "#");

		// 声明队列 & 绑定死信交换机
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "dlx_exchange_name");
		String queueName = "dead_queue_name";
		channel.queueDeclare(queueName, true, false, false, arguments);

		// 将队列、交换机、路由键绑定到一起
		String routingKey = "dead_rk_name.#";
		channel.queueBind(queueName, exchangeName, routingKey);

		channel.basicConsume(queueName, true, new DeadConsumer());
	}
}
