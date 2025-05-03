package com.valentin.mysql_and_mongo_connection.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class MysqlUser {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(nullable = false, columnDefinition = "VARCHAR(100)")
	private String name;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String password;

	@Column(name = "date_signup", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDate dateSignup;

	@Column(name = "image_name", nullable = false, columnDefinition = "VARCHAR(100)")
	private String imageName;

	@Column(nullable = false, columnDefinition = "TEXT")
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
