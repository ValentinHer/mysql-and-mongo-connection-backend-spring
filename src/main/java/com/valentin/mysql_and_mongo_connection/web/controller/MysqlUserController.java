package com.valentin.mysql_and_mongo_connection.web.controller;

import com.valentin.mysql_and_mongo_connection.domain.service.MysqlUserService;
import com.valentin.mysql_and_mongo_connection.web.dto.request.MysqlUserReqDTO;
import com.valentin.mysql_and_mongo_connection.web.dto.response.MysqlUserResDTO;
import com.valentin.mysql_and_mongo_connection.web.utils.SuccessResponse;
import org.springframework.http.HttpStatus;
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

	@PostMapping
	public ResponseEntity<SuccessResponse> save(@RequestParam("file") MultipartFile file,
												@RequestParam("name") String name,
												@RequestParam("password") String password,
												@RequestParam("date_signup") String dateSignup,
												@RequestParam("description") String description) throws IOException {
		MysqlUserReqDTO user = new MysqlUserReqDTO();

		// Separar dateSignup y convertir datos a Integer
		List<Integer> date = Arrays.stream(dateSignup.split("/"))
								   .map(Integer::parseInt)
								   .toList();

		user.setName(name);
		user.setPassword(password);
		user.setDescription(description);
		user.setDateSignup(LocalDate.of(date.get(2), date.get(1), date.get(0)));

		mysqlUserService.save(file, user);

		SuccessResponse response = new SuccessResponse();
		response.setStatus(HttpStatus.CREATED.value());
		response.setMessage("Usuario Registrado Existosamente");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<MysqlUserResDTO>> getAll(){
		return ResponseEntity.ok(mysqlUserService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MysqlUserResDTO> getById(@PathVariable("id") String id){
		return ResponseEntity.ok(mysqlUserService.getById(id));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<SuccessResponse> update(@RequestParam(value = "file", required = false) MultipartFile file,
												  @RequestParam(value = "name", required = false) String name,
												  @RequestParam(value = "password", required = false) String password,
												  @RequestParam(value = "date_signup", required = false) String dateSignup,
												  @RequestParam(value = "description", required = false) String description,
												  @PathVariable("id") String id) {
		MysqlUserReqDTO user = new MysqlUserReqDTO();

		if(dateSignup != null){
			// Separar dateSignup y convertir datos a Integer
			List<Integer> date = Arrays.stream(dateSignup.split("/"))
									   .map(Integer::parseInt)
									   .toList();
			user.setDateSignup(LocalDate.of(date.get(2), date.get(1), date.get(0)));
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

	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessResponse> remove(@PathVariable("id") String id){
		mysqlUserService.delete(id);

		SuccessResponse response = new SuccessResponse();
		response.setStatus(HttpStatus.OK.value());
		response.setMessage("Usuario Eliminado Exitosamente");

		return ResponseEntity.ok(response);
	}
}
