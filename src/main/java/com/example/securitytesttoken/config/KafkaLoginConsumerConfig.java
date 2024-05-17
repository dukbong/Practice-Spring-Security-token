package com.example.securitytesttoken.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

@Configuration
@EnableKafka
public class KafkaLoginConsumerConfig {
	
	@Value("${kafka.bootstrap.addr}")
	private String bootStrapAddr;

	@Bean
	public ConsumerFactory<String, Authentication> consumerFactoryLoginSuccess() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapAddr);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "login-sucess");
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		return new DefaultKafkaConsumerFactory<>(configProps);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Authentication> kafkaListenerContainerFactoryLoginSuccess() {
		ConcurrentKafkaListenerContainerFactory<String, Authentication> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactoryLoginSuccess());
		return factory;
	}
	
	@Bean
	public ConsumerFactory<String, Authentication> consumerFactoryLoginFailure() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapAddr);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "login-failure");
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		return new DefaultKafkaConsumerFactory<>(configProps);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Authentication> kafkaListenerContainerFactoryLoginFailure() {
		ConcurrentKafkaListenerContainerFactory<String, Authentication> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactoryLoginFailure());
		return factory;
	}
}
