package com.valentin.mysql_and_mongo_connection.domain.service;

import com.valentin.mysql_and_mongo_connection.web.dto.request.MysqlUserReqDTO;
import com.valentin.mysql_and_mongo_connection.web.dto.response.MysqlUserResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface MysqlUserService {
	List<MysqlUserResDTO> getAll();
	MysqlUserResDTO getById(String id);
	MysqlUserResDTO save(MultipartFile file, MysqlUserReqDTO user);
	MysqlUserResDTO update(MultipartFile file, MysqlUserReqDTO user, String id);
	void delete(String id);
}
