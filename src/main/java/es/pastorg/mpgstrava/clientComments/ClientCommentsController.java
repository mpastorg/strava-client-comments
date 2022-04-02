package es.pastorg.mpgstrava.clientComments;

import es.pastorg.mpgstrava.background.KafkaSend;
import es.pastorg.mpgstrava.repository.ClientComments;
import es.pastorg.mpgstrava.repository.ClientCommentsRepository;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ClientCommentsController {
    @Autowired ClientCommentsRepository clientCommentsRepository;

    @Autowired
    KafkaSend kafkaSend;


    private static final Logger logger = LoggerFactory.getLogger(ClientCommentsController.class);

    /**
     * Expecting a json object with 2 fields: email and comments
     * Saves the information in the database with no validations
     * If the fields don't exist may fail
     * Publishing a message in Kafka with the received comments
     * Expected kafka consumers are for sending ack to the customer and warning to admin
     * @param clientComments
     * @return
     */
    @ApiOperation(value = "Send comments and suggestions.",
            notes="No validations executed, just saving for future usage."
    )
    @PostMapping("/strava/comments")
    ClientComments clientComments(@RequestBody ClientComments clientComments) {
        UUID uuid = UUID.randomUUID();
        clientComments.setRowtableid(uuid);
        logger.debug("mpgclientComments:"+clientComments.toString());
        ClientComments retClientComments = clientCommentsRepository.save(clientComments);
        kafkaSend.sendMessage(clientComments);
        return retClientComments;
    }

}
