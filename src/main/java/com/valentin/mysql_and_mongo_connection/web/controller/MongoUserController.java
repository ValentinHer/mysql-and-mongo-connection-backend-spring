package com.valentin.mysql_and_mongo_connection.web.controller;

import com.valentin.mysql_and_mongo_connection.domain.service.MongoUserService;
import com.valentin.mysql_and_mongo_connection.web.dto.request.MongoUserReqDTO;
import com.valentin.mysql_and_mongo_connection.web.dto.response.MongoUserResDTO;
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
@RequestMapping("/mongo/users")
public class MongoUserController {
	private final MongoUserService mongoUserService;

	public MongoUserController(MongoUserService mongoUserService) {
		this.mongoUserService = mongoUserService;
	}

	@PostMapping
	public ResponseEntity<SuccessResponse> save(@RequestParam("file") MultipartFile file,
												@RequestParam("name") String name,
												@RequestParam("password") String password,
												@RequestParam("date_signup") String dateSignup,
												@RequestParam("description") String description) throws IOException {
		MongoUserReqDTO user = new MongoUserReqDTO();

		// Separar dateSignup y convertir datos a Integer
		List<Integer> date = Arrays.stream(dateSignup.split("/"))
						  .map(Integer::parseInt)
						  .toList();

		user.setName(name);
		user.setPassword(password);
		user.setDescription(description);
		user.setDateSignup(LocalDate.of(date.get(2), date.get(1), date.get(0)));

		mongoUserService.save(file, user);

		SuccessResponse response = new SuccessResponse();
		response.setStatus(HttpStatus.CREATED.value());
		response.setMessage("Usuario Registrado Existosamente");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<MongoUserResDTO>> getAll(){
		return ResponseEntity.ok(mongoUserService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MongoUserResDTO> getById(@PathVariable("id") String id){
		return ResponseEntity.ok(mongoUserService.getById(id));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<SuccessResponse> update(@RequestParam(value = "file", required = false) MultipartFile file,
												  @RequestParam(value = "name", required = false) String name,
												  @RequestParam(value = "password", required = false) String password,
												  @RequestParam(value = "date_signup", required = false) String dateSignup,
												  @RequestParam(value = "description", required = false) String description,
												  @PathVariable("id") String id) {
		MongoUserReqDTO user = new MongoUserReqDTO();

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

		mongoUserService.update(file, user, id);

		SuccessResponse response = new SuccessResponse();
		response.setStatus(HttpStatus.OK.value());
		response.setMessage("Usuario Actualizado Exitosamente");

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessResponse> remove(@PathVariable("id") String id){
		mongoUserService.delete(id);

		SuccessResponse response = new SuccessResponse();
		response.setStatus(HttpStatus.OK.value());
		response.setMessage("Usuario Eliminado Exitosamente");

		return ResponseEntity.ok(response);
	}
}
