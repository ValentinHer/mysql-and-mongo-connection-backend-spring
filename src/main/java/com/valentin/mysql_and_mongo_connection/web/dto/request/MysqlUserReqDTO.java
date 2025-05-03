package com.valentin.mysql_and_mongo_connection.web.dto.request;

import java.time.LocalDate;

public class MysqlUserReqDTO {
	private String name;
	private String password;
	private LocalDate dateSignup;
	private String imageName;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDateSignup() {
		return dateSignup;
	}

	public void setDateSignup(LocalDate dateSignup) {
		this.dateSignup = dateSignup;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
