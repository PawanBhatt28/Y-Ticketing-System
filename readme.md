# KaptureCRM Centralized Kafka Configuration Utility

**Author:** Yadunandan Bhat

The `kapturecrm-kafka` module is a centralized Kafka producer utility designed to simplify Kafka message publishing across multiple microservices in a consistent, reliable, and fail-safe manner. It provides a configurable and extensible mechanism to send messages to different Kafka servers with support for failover, custom serializers, message headers, and key-based routing.

---

## 📦 Maven Dependency
To use this Kafka producer in your microservice, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.kapturecrm</groupId>
    <artifactId>kapturecrm-kafka</artifactId>
    <version>{version}</version>
</dependency>
```

---

## 📤 Producing Events to Kafka

### Kafka Producer Configuration Parameters
**Default (Not Customizable)**
```
DELIVERY_TIMEOUT_MS_CONFIG     | {spring.kafka.delivery.timeout.ms:0}
REQUEST_TIMEOUT_MS_CONFIG      | {spring.kafka.request.timeout.ms:0}
LINGER_MS_CONFIG               | {spring.kafka.linger.ms:0}
RETRIES_CONFIG                 | {spring.kafka.retries:0}
SECURITY_PROTOCOL              | {spring.kafka.security.protocol}
SASL_MECHANISM                 | {spring.kafka.sasl-mechanism}
SASL_JAAS_CONFIG               | {spring.kafka.jaas-config}
MAX_REQUEST_SIZE_CONFIG        | {spring.kafka.max.request.size:15728640}
INTERCEPTOR_CLASSES_CONFIG     | KafkaProducerInterceptor.class
```
**Required (Customizable)**

```
KEY_SERIALIZER_CLASS_CONFIG    | {CustomSerializer}.class
BOOTSTRAP_SERVERS_CONFIG       | Server_IP
```

### Sample Producer Code
```java
import com.kapturecrm.kafka.producers.Producer;

public class SampleClass {

    @Autowired
    private Producer kafkaProducer;

    public void sampleMethod() {
        String kafkaTopic = "SAMPLE_TOPIC";
        SamplePOJO data = new SamplePOJO();

        // Default Serializer - StringSerializer.class
        kafkaProducer.send(kafkaTopic, data, JsonSerializer.class);
    }
}
```

---

## 📥 Consuming Events from Kafka

### Kafka Consumer Configuration Parameters

**Default (Customizable)**:
```
BOOTSTRAP_SERVERS_CONFIG        | {spring.kafka.consumer.bootstrap-servers.ip:}
KEY_DESERIALIZER_CLASS_CONFIG   | StringDeserializer.class
SECURITY_PROTOCOL               | {spring.kafka.security.protocol}
SASL_MECHANISM                  | {spring.kafka.sasl-mechanism}
SASL_JAAS_CONFIG                | {spring.kafka.jaas-config}
ENABLE_AUTO_COMMIT_CONFIG       | "true"
AUTO_COMMIT_INTERVAL_MS_CONFIG  | "1000"
INTERCEPTOR_CLASSES_CONFIG      | KafkaProducerInterceptor.class
```

**Required**:
```
GROUP_ID_CONFIG                 | groupId
VALUE_DESERIALIZER_CLASS_CONFIG | {CustomDeserializer}.class
```

### Sample Consumer Code 1
```java
import com.kapturecrm.kafka.configuration.DefaultKafkaConfiguration;

public class KafkaConfiguration {

    @Autowired
    private DefaultKafkaConfiguration defaultKafkaConfiguration;

    public Map<String, Object> getConsumerConfig() {
        Map<String, Object> config = defaultKafkaConfiguration.getBaseConsumerConfiguration();
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1500");
        return config;
    }

    public ConcurrentKafkaListenerContainerFactory<String, SamplePOJO> listenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SamplePOJO> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getConsumerConfig()));
        factory.setConcurrency(consumerConcurrency);
        return factory;
    }
}
```

### Sample Consumer Code 2
```java
import com.kapturecrm.kafka.configuration.DefaultKafkaConfiguration;

public class KafkaConfiguration {

    @Autowired
    private DefaultKafkaConfiguration defaultKafkaConfiguration;

    public ConcurrentKafkaListenerContainerFactory<String, SamplePOJO> listenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SamplePOJO> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(defaultKafkaConfiguration.createKafkaConsumerFactory("KAFKA_TOPIC", SamplePOJO.class, true));
        factory.setConcurrency(consumerConcurrency);
        return factory;
    }
}
```

---

## ✉️ Basic Send Methods (Producer)

- **Send a string message**
  ```java
  void send(String topic, String message);
  ```

- **Send a string message with key**
  ```java
  void send(String topic, String key, String message);
  ```

- **Send a string message with key and headers**
  ```java
  void send(String topic, String key, String message, Iterable<Header> headers);
  ```

- **Send an object with custom serializer**
  ```java
  <T> void send(String topic, T message, Class<?> serializerClass);
  ```

- **Send an object with key and custom serializer**
  ```java
  <T> void send(String topic, String key, T message, Class<?> serializerClass);
  ```

- **Send an object with key, headers, and custom serializer**
  ```java
  <T> void send(String topic, String key, T message, Iterable<Header> headers, Class<?> serializerClass);
  ```

---

## 🌐 Sending to Specific Server

- **Send string to specific server**
  ```java
  void sendTo(String topic, String message, String serverName);
  ```

- **Send object with custom serializer to specific server**
  ```java
  <T> void sendTo(String topic, T message, Class<?> serializerClass, String serverName);
  ```

- **Send object with key and custom serializer to specific server**
  ```java
  <T> void sendTo(String topic, String key, T message, Class<?> serializerClass, String serverName);
  ```

- **Send string with key to specific server**
  ```java
  void sendTo(String topic, String key, String message, String serverName);
  ```

- **Send string with key and headers to specific server**
  ```java
  void sendTo(String topic, String key, String message, Iterable<Header> headers, String serverName);
  ```

- **Send object with key, headers, and serializer to specific server**
  ```java
  <T> void sendTo(String topic, String key, T message, Iterable<Header> headers, Class<?> serializerClass, String serverName);
  ```

---

## 🧩 Sending to Specific Instance

- **Send string with key and headers to instance**
  ```java
  void sendToInstance(String topic, String key, String message, Iterable<Header> headers, String instance);
  ```

- **Send object with key, headers, and serializer to instance**
  ```java
  <T> void sendToInstance(String topic, String key, T message, Iterable<Header> headers, Class<?> serializerClass, String instance);
  ```

---

## 🛠 Kafka Controller Endpoints

- **Get all registered Kafka consumers**
  ```http
  GET /noauth/kafka/consumers
  Response: Set<String>
  ```
- **Pause all Kafka consumers**
  ```http
  POST /noauth/kafka/consumers/pause?containerId={String:Optional}
  Response: Map<String, String>
  ```

- **Resume all Kafka consumers or a specific consumer**
  ```http
  POST /noauth/kafka/consumers/resume?containerId={String:Optional}
  Response: Map<String, String>
  ```

- **Get and resume containers that have been paused for more than X minutes**
  ```http
  POST /noauth/kafka/consumers/resume-paused-containers?timeSincePause={Long:Required}
  Response: Map<String, String>
  ```

- **Get containers paused for more than X minutes**
  ```http
  GET /noauth/kafka/consumers/paused-containers?timeSincePause={Long:Required}
  Response: Map<String, Long>
  ```

- **Get status of all Kafka consumers**
  ```http
  GET /noauth/kafka/consumers/status
  Response: Map<String, String>
  ```

- **Get all metrics for a specific Kafka consumer**
  ```http
  GET /noauth/kafka/consumers/metric?id={String:required}
  Response: Map<String, Object>
  ```

---
