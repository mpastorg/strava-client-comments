package es.pastorg;

import es.pastorg.mpgstrava.background.ClientCommentsSerializer;
import es.pastorg.mpgstrava.repository.ClientComments;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaTopicConfig {


    @Value(value = "${kafka.bootstrapAddress}")
    //@Value(value = "mpgubu18:31094,mpgubumac02:31094,mpgubumac01:31094")
    private String bootstrapAddress;

    public static final String TOPIC_CONFIG_ID = "mpg_group_id";
    public static final String TOPIC_ATHLETE_ACTIVITY_EVENT = "topic-notifications";

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put("acks","all");
        return new DefaultKafkaProducerFactory<String, String>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        TopicBuilder.name(TOPIC_ATHLETE_ACTIVITY_EVENT)
                .partitions(2)
                .replicas(2)
                .build();
        return new KafkaTemplate<>(producerFactory());
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
        kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
    @Bean
    public ConsumerFactory<String,String> consumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                TOPIC_CONFIG_ID);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return new DefaultKafkaConsumerFactory<>(props);
    }


}