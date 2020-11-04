package com.study.rabbitmq.rabbitmq.fanout;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.study.rabbitmq.rabbitmq.RabbitMQManager;

@Slf4j
public class Receiver_Fanout {
	private final static String EXCHANGE_NAME = "fanout-exchange";

	public static void main(String[] args) throws Exception {
		RabbitMQManager rabbitMQManager = new RabbitMQManager();
		Connection connection = rabbitMQManager.makeConnection();
		Channel channel = connection.createChannel();

		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "");

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
