package com.santa.log_service.repo;

import com.santa.log_service.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepo extends MongoRepository<Log, String> {
}
