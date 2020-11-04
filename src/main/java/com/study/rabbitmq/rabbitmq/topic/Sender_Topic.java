package com.study.rabbitmq.rabbitmq.topic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.rabbitmq.rabbitmq.RabbitMQManager;

@Slf4j
public class Sender_Topic {
	private final static String QUEUE_NAME = "topic-queue";
	private final static String EXCHANGE_NAME = "topic-exchange";
	private final static String MESSAGE = "topic-message";

	public static void main(String[] args) throws Exception {
		RabbitMQManager rabbitMQManager = new RabbitMQManager();
		try(Connection connection  = rabbitMQManager.makeConnection();
			Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME,false,false,false,null);
			channel.basicQos(1);
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
			for (int i = 1; i <= 5; i++) {
				StringBuilder tempMessage = new StringBuilder(MESSAGE);
				for (int j = 0; j < i; j++) {
					tempMessage.append(".");
				}
				channel.basicPublish(EXCHANGE_NAME, "animal.rabbit",null, tempMessage.toString().getBytes("UTF-8"));
				log.info("Sent => " + tempMessage.toString());
			}
		}
	}
}
