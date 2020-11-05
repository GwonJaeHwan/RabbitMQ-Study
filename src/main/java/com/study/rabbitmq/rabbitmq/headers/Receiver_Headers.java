package com.study.rabbitmq.rabbitmq.headers;

import com.rabbitmq.client.*;
import com.study.rabbitmq.rabbitmq.RabbitMQManager;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Receiver_Headers {
	private final static String EXCHANGE_NAME = "headers-exchange";

	public static void main(String[] args) throws Exception {
		RabbitMQManager rabbitMQManager = new RabbitMQManager();
		Connection connection = rabbitMQManager.makeConnection();
		Channel channel = connection.createChannel();

		String queueName = channel.queueDeclare().getQueue();
		Map<String, Object> healthArgs = new HashMap<>();
		healthArgs.put("x-match", "all"); //any or all
		healthArgs.put("animal_1", "rabbit");
		healthArgs.put("animal_2", "turtle");

		channel.queueBind(queueName, EXCHANGE_NAME, "", healthArgs);

		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body,"UTF-8");
				log.info("Received => " + message);
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}

	private static void doWork(String task) throws InterruptedException {
		for (char ch : task.substring(4).toCharArray()) {
			Thread.sleep(10000);
		}
	}
}
