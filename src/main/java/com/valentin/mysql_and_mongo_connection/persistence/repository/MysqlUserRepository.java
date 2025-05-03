package com.valentin.mysql_and_mongo_connection.persistence.repository;

import com.valentin.mysql_and_mongo_connection.persistence.entity.MysqlUser;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MysqlUserRepository extends ListCrudRepository<MysqlUser, String> {
}
