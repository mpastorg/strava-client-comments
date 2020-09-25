package es.pastorg.mpgstrava.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ClientCommentsRepository extends CrudRepository<ClientComments, UUID> {
}
