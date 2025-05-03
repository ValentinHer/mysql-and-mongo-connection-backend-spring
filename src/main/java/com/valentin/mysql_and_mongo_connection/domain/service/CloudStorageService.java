package com.valentin.mysql_and_mongo_connection.domain.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudStorageService {
	void uploadPerfilImage(MultipartFile file, String filename);
	String getPerfilImagePresignedUrl(String filename);
	void deletePerfilImage(String filename);
}
