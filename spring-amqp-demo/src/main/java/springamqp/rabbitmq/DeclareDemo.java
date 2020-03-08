package springamqp.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用@Bean注解方式声明队列、交换机以及绑定关系
 */
@Configuration
public class DeclareDemo
{
	@Bean
	public TopicExchange apiLogExchange()
	{
		System.out.println("我被实例化了");
		return new TopicExchange("test_log", true, false, null);
	}

	@Bean
	public Queue apiLogQueue()
	{
		return new Queue("test_log_api", true, false, false, null);
	}

	@Bean
	public Binding apiLogBinding()
	{
		return BindingBuilder.bind(apiLogQueue()).to(apiLogExchange()).with("test_log_api.#");
	}
}
