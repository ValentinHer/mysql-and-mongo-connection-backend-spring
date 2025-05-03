package com.valentin.mysql_and_mongo_connection.domain.mapper;

import com.valentin.mysql_and_mongo_connection.persistence.entity.MongoUser;
import com.valentin.mysql_and_mongo_connection.web.dto.request.MongoUserReqDTO;
import com.valentin.mysql_and_mongo_connection.web.dto.response.MongoUserResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MongoUserMapper {

	@Mapping(target = "id", ignore = true)
	MongoUser toMongoUser(MongoUserReqDTO mongoUserReqDTO);

	@Mappings({
			@Mapping(source = "id", target = "_id"),
			@Mapping(source = "dateSignup", target = "date_signup"),
			@Mapping(source = "imageName", target = "image_url"),
	})
	MongoUserResDTO toMongoUserResDto(MongoUser user);

	List<MongoUserResDTO> toMongoUsersResDto(List<MongoUser> users);
}
