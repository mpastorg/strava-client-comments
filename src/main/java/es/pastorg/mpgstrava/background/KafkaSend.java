package es.pastorg.mpgstrava.background;

import es.pastorg.KafkaTopicConfig;
//import es.pastorg.mpgstrava.repository.AthleteActivityEmail;
import es.pastorg.mpgstrava.repository.ClientComments;
import es.pastorg.mpgstrava.repository.ClientCommentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger logger = LoggerFactory.getLogger(KafkaSend.class);

    public void sendMessage(ClientComments rBodyEvent) {

        ListenableFuture<SendResult<String, String >> futuretest =
                kafkaTemplateClientComments.
                        send(KafkaTopicConfig.TOPIC_ATHLETE_ACTIVITY_EVENT,
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

//    public void sendEmailConfirmation(AthleteActivityEmail athleteActivityEmail){
//        ListenableFuture<SendResult<String, String >> futuretest =
//                kafkaTemplate.send(KafkaTopicConfig.TOPIC_ATHLETE_EMAIL,
//                        athleteActivityEmail.toString());
//
//
//        futuretest.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                logger.debug(athleteActivityEmail.toString() +
//                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
//            }
//            @Override
//            public void onFailure(Throwable ex) {
//                logger.debug("String Unable to send UUID message=["
//                        + athleteActivityEmail.toString() + "] due to : " + ex.getMessage());
//            }
//        });
//
//    }

}
