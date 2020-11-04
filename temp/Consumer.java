package com.study.rabbitmq.rabbitmq;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumer {

	private final static String QUEUE_NAME = "HELLO MQ";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("133.186.135.196");
		connectionFactory.setPort(5672);
		connectionFactory.setUsername("user1");
		connectionFactory.setPassword("031289");
		try(Connection connection = connectionFactory.newConnection();
			Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
				System.out.println("Received message => " + message);
			};
			channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
		} catch (TimeoutException | IOException e) {
			e.printStackTrace();
		}
	}
}
