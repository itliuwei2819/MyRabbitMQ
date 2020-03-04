package com.toolv.myrabbitmq.exchange.type.fanout.consumer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class FanoutConsumer implements Consumer
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
}
