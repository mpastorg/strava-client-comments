package es.pastorg.mpgstrava.clientComments;

import es.pastorg.mpgstrava.repository.ClientComments;
import es.pastorg.mpgstrava.repository.ClientCommentsRepository;
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

    private static final Logger logger = LoggerFactory.getLogger(ClientCommentsController.class);

    @PostMapping("/strava/comments")
    ClientComments clientComments(@RequestBody ClientComments clientComments) {
        UUID uuid = UUID.randomUUID();
        clientComments.setRowtableid(uuid);
        logger.debug(clientComments.toString());
        return clientCommentsRepository.save(clientComments);
    }

}
