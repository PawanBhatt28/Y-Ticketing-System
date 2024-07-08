package com.kapture.ticketservice.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfiguration {
	
	@Bean
	public RedissonClient createRedissonClient() {
		Config config = new Config();
		int port = 6379;
		String host = "localhost";
		config.useSingleServer().setAddress("redis://"+host+":"+port);
		config.setNettyThreads(0);
		config.setThreads(0);
		
		RedissonClient redissonClient = Redisson.create(config);
		return redissonClient;
	}
}
