package com.valentin.mysql_and_mongo_connection.web.dto.response;

import java.time.LocalDate;

public class MongoUserResDTO {
	private String _id;
	private String name;
	private LocalDate date_signup;
	private String image_url;
	private String description;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
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
