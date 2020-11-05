package com.study.rabbitmq.rabbitmq.headers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.rabbitmq.rabbitmq.RabbitMQManager;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Sender_Headers {
	private final static String QUEUE_NAME = "headers-queue";
	private final static String EXCHANGE_NAME = "headers-exchange";
	private final static String MESSAGE = "headers-message";

	public static void main(String[] args) throws Exception {
		RabbitMQManager rabbitMQManager = new RabbitMQManager();
		try(Connection connection = rabbitMQManager.makeConnection();
			Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME,false,false,false,null);
			channel.basicQos(1);
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);

			Map<String, Object> headers = new HashMap<>();
			headers.put("animal_1", "rabbit");
			headers.put("animal_2", "turtle");
			AMQP.BasicProperties properties =  new AMQP.BasicProperties.Builder().headers(headers).build();
			for (int i = 1; i <= 5; i++) {
				StringBuilder tempMessage = new StringBuilder(MESSAGE);
				for (int j = 0; j < i; j++) {
					tempMessage.append(".");
				}
				channel.basicPublish(EXCHANGE_NAME, "", properties, tempMessage.toString().getBytes("UTF-8"));
				log.info("Sent => " + tempMessage.toString());
			}
		}
	}
}
