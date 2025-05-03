package com.valentin.mysql_and_mongo_connection.web.dto.response;

import java.time.LocalDate;

public class MysqlUserResDTO {
	private String id_user;
	private String name;
	private LocalDate date_signup;
	private String image_url;
	private String description;

	public String getId_user() {
		return id_user;
	}

	public void setId_user(String id_user) {
		this.id_user = id_user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate_signup() {
		return date_signup;
	}

	public void setDate_signup(LocalDate date_signup) {
		this.date_signup = date_signup;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
