package com.toolv.myrabbitmq.ack.consumer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class QosConsumer extends DefaultConsumer
{
	private Channel channel;
	public QosConsumer(Channel channel)
	{
		super(channel);
		this.channel = channel;
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
	{
		String s = new String(body, StandardCharsets.UTF_8);
		System.out.println(s);
		// multiple参数为是否批量确认，通常Qos都是单次处理1条，所以这里设置false
		channel.basicAck(envelope.getDeliveryTag(),false);
	}
}
