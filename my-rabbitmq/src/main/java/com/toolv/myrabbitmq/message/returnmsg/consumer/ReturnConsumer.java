package com.toolv.myrabbitmq.message.returnmsg.consumer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class ReturnConsumer extends DefaultConsumer
{
	public ReturnConsumer(Channel channel)
	{
		super(channel);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
	{
		String s = new String(body, StandardCharsets.UTF_8);
		System.out.println(s);
	}
}
