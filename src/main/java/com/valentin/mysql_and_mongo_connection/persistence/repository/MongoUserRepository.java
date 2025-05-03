package com.valentin.mysql_and_mongo_connection.persistence.repository;

import com.valentin.mysql_and_mongo_connection.persistence.entity.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserRepository extends MongoRepository<MongoUser, String> {
}
