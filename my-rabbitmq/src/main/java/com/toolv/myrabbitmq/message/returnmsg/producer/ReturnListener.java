package com.toolv.myrabbitmq.message.returnmsg.producer;

import java.io.IOException;

import com.rabbitmq.client.AMQP;

public class ReturnListener implements com.rabbitmq.client.ReturnListener
{

	@Override
	public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException
	{
		System.out.println("收到return消息");
	}
}
