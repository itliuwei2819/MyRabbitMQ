package com.toolv.myrabbitmq.message.confirmmsg.producer;

import java.io.IOException;

public class ConfirmListener implements com.rabbitmq.client.ConfirmListener
{
	@Override
	public void handleAck(long deliveryTag, boolean multiple) throws IOException
	{
		System.out.println("收到ack");
	}

	@Override
	public void handleNack(long deliveryTag, boolean multiple) throws IOException
	{
		System.out.println("没有ack");
	}
}
