package es.pastorg.mpgstrava.background;

import es.pastorg.mpgstrava.repository.ClientComments;
import es.pastorg.mpgstrava.repository.ClientCommentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@EnableKafka
@Component
public class KafkaSend {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplateClientComments;
    @Autowired
    private ClientCommentsRepository ClientCommentsRepository;
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value(value = "${kafka.TOPIC_CONFIG_ID}")
    private String TOPIC_CONFIG_ID;
    @Value(value = "${kafka.TOPIC_COMMENTS_EVENT}")
    private String TOPIC_COMMENTS_EVENT;

    private static final Logger logger = LoggerFactory.getLogger(KafkaSend.class);

    public void sendMessage(ClientComments rBodyEvent) {

        ListenableFuture<SendResult<String, String >> futuretest =
                kafkaTemplateClientComments.
                        send(TOPIC_COMMENTS_EVENT,
                        rBodyEvent.getClass().getName(),
                        rBodyEvent.toString());


        futuretest.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.debug("String Sent message=[" + rBodyEvent.toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                logger.debug("String Unable to send message=["
                        + rBodyEvent.toString() + "] due to : " + ex.getMessage());
            }
        });

        ClientCommentsRepository.save(rBodyEvent);

    }

}
