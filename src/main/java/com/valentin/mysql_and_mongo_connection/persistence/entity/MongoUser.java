package com.valentin.mysql_and_mongo_connection.persistence.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "users")
public class MongoUser {
	@Id
	private String id;

	private String name;
	private String password;

	@Field(name = "date_signup")
	private LocalDate dateSignup;

	@Field(name = "image_name")
	private String imageName;

	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
