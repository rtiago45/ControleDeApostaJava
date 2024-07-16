package repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import model.BetVO;
import model.MultipleBetVO;

public interface MultipleBetRepository extends MongoRepository<MultipleBetVO, String> {
    // Métodos de consulta personalizados, se necessário
}
