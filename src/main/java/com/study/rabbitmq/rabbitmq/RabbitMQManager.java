package com.study.rabbitmq.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQManager {

	public Connection makeConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("133.186.135.196");
		factory.setPort(5672);
		factory.setUsername("user1");
		factory.setPassword("031289");
		return factory.newConnection();
	}
}
