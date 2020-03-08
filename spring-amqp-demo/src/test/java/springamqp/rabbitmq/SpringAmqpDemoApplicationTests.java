package springamqp.rabbitmq;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringAmqpDemoApplicationTests
{
	@Autowired
	private RabbitAdmin rabbitAdmin;

	@Test
	void contextLoads()
	{
	}

	/**
	 * 声明交换机&队列&绑定
	 */
	@Test
	void declareTest()
	{
		// 声明一个Topic类型的队列，并且绑定交换机，其他的交换机类型同理
		TopicExchange topicExchange = new TopicExchange("test.admin.topic.exchange.name", false, false, null);
		Queue queue = new Queue("test.admin.topic.queue.name", false, false, false, null);

		rabbitAdmin.declareExchange(topicExchange);
		rabbitAdmin.declareQueue(queue);

		Binding binding = BindingBuilder.bind(queue).to(topicExchange).with("test.admin.*");
		rabbitAdmin.declareBinding(binding);

		rabbitAdmin.purgeQueue(queue.getName());
	}

	/**
	 * 清空队列
	 */
	@Test
	void purgeQueueTest()
	{
		rabbitAdmin.purgeQueue("test.admin.topic.queue.name");
	}

	@Resource
	private Exchange apiLogExchange;
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	void sendMessageTest()
	{
		MessageProperties messageProperties = new MessageProperties();
		Map<String, Object> headers = messageProperties.getHeaders();
		headers.put("desc", "信息描述");
		headers.put("type", "自定义消息类型。。");

		Message message = new Message("玩家[阿伟]调用了[xxx]接口，上传文件[xxx.avi]成功。".getBytes(), messageProperties);
		rabbitTemplate.convertAndSend(apiLogExchange.getName(), "test_log_api.upload", message, msg ->
		{
			System.out.println("发送前对消息进行再次处理");
			Map<String, Object> headerMap = msg.getMessageProperties().getHeaders();
			headerMap.put("desc", "啊，我被修改了");
			headerMap.put("attr", "我是新来的");
			return msg;
		});

	}

}
