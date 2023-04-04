/*
 * Boostrap for rabbitMQSender and modified as per the requirement
 */

package com.in.logMonitor.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.in.logMonitor.models.LogMonitorData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public RabbitMQSender(AmqpTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Value("${logmonitor.rabbitmq.exchange}")
	private String exchange;

	@Value("${logmonitor.rabbitmq.routingkey}")
	private String routingkey;

	public void send(LogMonitorData logmonitordata) {
		rabbitTemplate.convertAndSend(exchange, routingkey, logmonitordata);
		log.info("Send msg => {}", logmonitordata);

	}
}
