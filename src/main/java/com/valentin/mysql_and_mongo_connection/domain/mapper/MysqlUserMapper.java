package com.valentin.mysql_and_mongo_connection.domain.mapper;

import com.valentin.mysql_and_mongo_connection.persistence.entity.MysqlUser;
import com.valentin.mysql_and_mongo_connection.web.dto.request.MysqlUserReqDTO;
import com.valentin.mysql_and_mongo_connection.web.dto.response.MysqlUserResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MysqlUserMapper {

	@Mapping(target = "id", ignore = true)
	MysqlUser toMysqlUser(MysqlUserReqDTO mysqlUserReqDTO);

	@Mappings({
			@Mapping(source = "id", target = "id_user"),
			@Mapping(source = "dateSignup", target = "date_signup"),
			@Mapping(source = "imageName", target = "image_url"),
	})
	MysqlUserResDTO toMysqlUserResDto(MysqlUser user);

	List<MysqlUserResDTO> toMysqlUsersResDto(List<MysqlUser> users);
}
