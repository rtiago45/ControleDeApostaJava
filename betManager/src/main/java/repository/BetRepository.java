package repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import model.BetVO;

public interface BetRepository extends MongoRepository<BetVO, String> {
    // Métodos de consulta personalizados, se necessário
}
