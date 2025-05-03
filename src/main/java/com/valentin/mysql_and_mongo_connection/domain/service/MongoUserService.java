package com.valentin.mysql_and_mongo_connection.domain.service;

import com.valentin.mysql_and_mongo_connection.web.dto.request.MongoUserReqDTO;
import com.valentin.mysql_and_mongo_connection.web.dto.response.MongoUserResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MongoUserService {
	List<MongoUserResDTO> getAll();
	MongoUserResDTO getById(String id);
	MongoUserResDTO save(MultipartFile file, MongoUserReqDTO user) ;
	MongoUserResDTO update(MultipartFile file, MongoUserReqDTO user, String id);
	void delete(String id);
}
