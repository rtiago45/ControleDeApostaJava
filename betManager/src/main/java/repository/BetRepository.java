package repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import model.betVO;

public interface BetRepository extends MongoRepository<betVO, String> {
    // Métodos de consulta personalizados, se necessário
}
