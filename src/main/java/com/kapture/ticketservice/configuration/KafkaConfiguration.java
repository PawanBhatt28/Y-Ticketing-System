package com.kapture.ticketservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaConfiguration {

	@Bean
	public NewTopic createTopic() {
		return new NewTopic("topic-ticket", 3, (short)1);
	}
}
