package com.valentin.mysql_and_mongo_connection.domain.service.implementation;

import com.valentin.mysql_and_mongo_connection.domain.mapper.MongoUserMapper;
import com.valentin.mysql_and_mongo_connection.domain.service.CloudStorageService;
import com.valentin.mysql_and_mongo_connection.domain.service.MongoUserService;
import com.valentin.mysql_and_mongo_connection.persistence.entity.MongoUser;
import com.valentin.mysql_and_mongo_connection.persistence.repository.MongoUserRepository;
import com.valentin.mysql_and_mongo_connection.web.dto.request.MongoUserReqDTO;
import com.valentin.mysql_and_mongo_connection.web.dto.response.MongoUserResDTO;
import com.valentin.mysql_and_mongo_connection.web.exception.BadRequestException;
import com.valentin.mysql_and_mongo_connection.web.exception.CloudStorageException;
import com.valentin.mysql_and_mongo_connection.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class MongoUserServiceImpl implements MongoUserService {
	private final MongoUserRepository repository;
	private final MongoUserMapper mapper;
	private final PasswordEncoder passwordEncoder;
	private final CloudStorageService cloudStorageService;

	private static final Logger logger = LoggerFactory.getLogger(MongoUserServiceImpl.class);

	@Autowired
	public MongoUserServiceImpl(MongoUserRepository repository, MongoUserMapper mapper, PasswordEncoder passwordEncoder, CloudStorageService cloudStorageService) {
		this.repository = repository;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
		this.cloudStorageService = cloudStorageService;
	}

	// Obtener usuarios almacenados en la base de datos
	// y obtener una URL firmada de la imagen de perfil de
	// cada usuario
	@Override
	public List<MongoUserResDTO> getAll() {
		return repository.findAll()
						 .stream()
						 .map(user -> {
							 MongoUserResDTO newUser = mapper.toMongoUserResDto(user);

							 try {
								 String urlImage = cloudStorageService.getPerfilImagePresignedUrl(newUser.getImage_url());
								 newUser.setImage_url(urlImage);

								 logger.info("Usuario obtenido con exito: {}", newUser.get_id());
							 } catch (IllegalArgumentException | CloudStorageException e) {
								 logger.error("Error al obtener los datos de la imagen: {}", e.getMessage(), e);

								 newUser.setImage_url(null);
							 }

							 return newUser;
						 })
						 .toList();
	}

	// Obtener un usuario de la base de datos
	// y obtener una URL firmada de la imagen de perfil
	@Override
	public MongoUserResDTO getById(String id) {
		MongoUserResDTO userFound = repository.findById(id)
											  .map(mapper::toMongoUserResDto)
											  .orElseThrow(() -> {
												  logger.error("Usuario {} no encontrado", id);
												  return new NotFoundException("Usuario No Encontrado");
											  });

		try {
			String urlImage = cloudStorageService.getPerfilImagePresignedUrl(userFound.getImage_url());
			userFound.setImage_url(urlImage);

			logger.info("Usuario obtenido con exito: {}", userFound.get_id());
		} catch (IllegalArgumentException | CloudStorageException e) {
			logger.error("Error al obtener los datos de la imagen: {}", e.getMessage(), e);

			userFound.setImage_url(null);
		}

		return userFound;
	}

	// Almacenar usario en la base de datos y guardar la imagen de perfil en AWS S3
	@Override
	public MongoUserResDTO save(MultipartFile file, MongoUserReqDTO user) {

		String passwordHashed = passwordEncoder.encode(user.getPassword());
		user.setPassword(passwordHashed);

		// Renombrar el archivo
		String originalName = file.getOriginalFilename();
		String filename = UUID.randomUUID()
							  .toString() + "-" + originalName;
		user.setImageName(filename);

		// Convertir UserReqDTO a Entity
		MongoUser newUser = mapper.toMongoUser(user);

		try {
			//Guardar la imagen de perfil en AWS S3
			cloudStorageService.uploadPerfilImage(file, filename);

			MongoUser userSaved = repository.save(newUser);
			logger.info("Usuario guardado con exito: {}", userSaved.getId());

			return mapper.toMongoUserResDto(userSaved);
		} catch (IllegalArgumentException e) {
			logger.error("Imagen de perfil no proporcionado: {}", e.getMessage(), e);
			throw new BadRequestException("Imagen de perfil no proporcionado, inténtalo de nuevo");
		} catch (CloudStorageException e) {
			logger.error("Error al guardar la imagen de perfil del usuario: {}", e.getMessage(), e);
			throw new BadRequestException("Error al guardar la imagen de perfil, inténtalo de nuevo");
		} catch (DataAccessException e) {
			logger.error("Error al guardar el usuario en la base de datos: {}", e.getMessage(), e);
			throw new BadRequestException("Error al guardar el usuario, inténtalo de nuevo");
		}
	}

	// Actualizar datos del usuario, actualizando la imagen de perfil en AWS S3
	@Override
	public MongoUserResDTO update(MultipartFile file, MongoUserReqDTO user, String id) {
		MongoUser userFound = repository.findById(id)
											  .orElseThrow(() -> {
												  logger.error("Usuario {} no encontrado", id);
												  return new NotFoundException("Usuario No Encontrado");
											  });

		try {
			if(user.getName() != null && !user.getName().isEmpty()){
				userFound.setName(user.getName());
			}
			if(user.getPassword() != null && !user.getPassword().isEmpty()) {
				String passwordHashed = passwordEncoder.encode(user.getPassword());
				userFound.setPassword(passwordHashed);
			}
			if(user.getDateSignup() != null){
				userFound.setDateSignup(user.getDateSignup());
			}
			if(user.getDescription() != null && !user.getDescription().isEmpty()){
				userFound.setDescription(user.getDescription());
			}
			if(file != null){
				cloudStorageService.uploadPerfilImage(file, userFound.getImageName());
			}

			MongoUser userUpdated = repository.save(userFound);
			logger.info("Usuario {} actualizado exitosamente", userUpdated.getId());

			return getById(id);
		} catch (IllegalArgumentException | CloudStorageException e) {
			logger.error("Error al actualizar la imagen de perfil del usuario: {}", e.getMessage(), e);
			throw new BadRequestException("Error al actualizar la imagen de perfil, inténtalo de nuevo");
		} catch (DataAccessException e) {
			logger.error("Error al actualizar el usuario en la base de datos: {}", e.getMessage(), e);
			throw new BadRequestException("Error al actualizar el usuario, inténtalo de nuevo");
		}
	}

	// Eliminar el usuario y la imagen de perfil en AWS S3
	@Override
	public void delete(String id) {
		repository.findById(id)
				  .ifPresentOrElse(user -> {
					  try {
						  cloudStorageService.deletePerfilImage(user.getImageName());
						  repository.deleteById(id);

						  logger.info("Usuario {} eliminado", id);
					  } catch (IllegalArgumentException | CloudStorageException e) {
						  throw new BadRequestException("Error al eliminar la imagen de perfil, inténtalo de nuevo");
					  }
				  }, () -> {
					  logger.error("Usuario {} no encontrado", id);
					  throw new NotFoundException("Usuario No Encontrado");
				  });
	}
}
