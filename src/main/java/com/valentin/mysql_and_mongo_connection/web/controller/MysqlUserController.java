package com.valentin.mysql_and_mongo_connection.web.controller;

import com.valentin.mysql_and_mongo_connection.domain.service.MysqlUserService;
import com.valentin.mysql_and_mongo_connection.web.dto.request.MysqlUserReqDTO;
import com.valentin.mysql_and_mongo_connection.web.dto.response.MysqlUserResDTO;
import com.valentin.mysql_and_mongo_connection.web.utils.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/mysql/users")
public class MysqlUserController {
	private final MysqlUserService mysqlUserService;

	public MysqlUserController(MysqlUserService mysqlUserService) {
		this.mysqlUserService = mysqlUserService;
	}

	@Operation(summary = "Create a new user in MySql")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "User created successfully"),
			@ApiResponse(responseCode = "400", description = "Error to save the user or Profile image not provided or Error to save the profile image"),
	})
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<SuccessResponse> save(@RequestPart("file") MultipartFile file,
												@RequestPart("name") String name,
												@RequestPart("password") String password,
												@RequestPart("date_signup") String dateSignup,
												@RequestPart("description") String description) throws IOException {
		MysqlUserReqDTO user = new MysqlUserReqDTO();

		// Separar dateSignup y convertir datos a Integer
		List<Integer> date = Arrays.stream(dateSignup.split("-"))
								   .map(Integer::parseInt)
								   .toList();

		user.setName(name);
		user.setPassword(password);
		user.setDescription(description);
		user.setDateSignup(LocalDate.of(date.get(0), date.get(1), date.get(2)));

		mysqlUserService.save(file, user);

		SuccessResponse response = new SuccessResponse();
		response.setStatus(HttpStatus.CREATED.value());
		response.setMessage("Usuario Registrado Existosamente");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Operation(summary = "Get all users in MySql")
	@ApiResponse(responseCode = "200", description = "OK")
	@GetMapping
	public ResponseEntity<List<MysqlUserResDTO>> getAll(){
		return ResponseEntity.ok(mysqlUserService.getAll());
	}

	@Operation(summary = "Get a user by ID from MySql")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "User not found")
	})
	@GetMapping("/{id}")
	public ResponseEntity<MysqlUserResDTO> getById(@PathVariable("id") String id){
		return ResponseEntity.ok(mysqlUserService.getById(id));
	}

	@Operation(summary = "Update partially a user by ID in MySql")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "User updated successfully"),
			@ApiResponse(responseCode = "400", description = "Error to update the profile image or Error to update the user"),
			@ApiResponse(responseCode = "404", description = "User not found")
	})
	@PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<SuccessResponse> update(@RequestPart(value = "file", required = false) MultipartFile file,
												  @RequestPart(value = "name", required = false) String name,
												  @RequestPart(value = "password", required = false) String password,
												  @RequestPart(value = "date_signup", required = false) String dateSignup,
												  @RequestPart(value = "description", required = false) String description,
												  @PathVariable("id") String id) {
		MysqlUserReqDTO user = new MysqlUserReqDTO();

		if(dateSignup != null){
			// Separar dateSignup y convertir datos a Integer
			List<Integer> date = Arrays.stream(dateSignup.split("-"))
									   .map(Integer::parseInt)
									   .toList();
			user.setDateSignup(LocalDate.of(date.get(0), date.get(1), date.get(2)));
		}

		user.setName(name);
		user.setPassword(password);
		user.setDescription(description);

		mysqlUserService.update(file, user, id);

		SuccessResponse response = new SuccessResponse();
		response.setStatus(HttpStatus.OK.value());
		response.setMessage("Usuario Actualizado Exitosamente");

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Delete a user by ID in MySql")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "User deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Error to delete the profile image"),
			@ApiResponse(responseCode = "404", description = "User not found")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessResponse> remove(@PathVariable("id") String id){
		mysqlUserService.delete(id);

		SuccessResponse response = new SuccessResponse();
		response.setStatus(HttpStatus.OK.value());
		response.setMessage("Usuario Eliminado Exitosamente");

		return ResponseEntity.ok(response);
	}
}
