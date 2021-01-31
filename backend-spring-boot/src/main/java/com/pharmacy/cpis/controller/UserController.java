package com.pharmacy.cpis.controller;

import com.pharmacy.cpis.dto.UserAccDTO;
import com.pharmacy.cpis.service.IUserService;
import com.pharmacy.cpis.users.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    private IUserService userAccService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserAccDTO>> getUsersAccs() {

		List<UserAccount> usersACC = userAccService.findAll();

		// convert users to DTOs
		List<UserAccDTO> usersAccDTO = new ArrayList<>();
		for (UserAccount u : usersACC) {
			usersAccDTO.add(new UserAccDTO(u));
		}

		return new ResponseEntity<>(usersAccDTO, HttpStatus.OK);
	}

    @GetMapping("userAcc/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserAccDTO> getUserAcc(@PathVariable Long id) {

		UserAccount userAcc = userAccService.findOne(id);

		if (userAcc == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new UserAccDTO(userAcc), HttpStatus.OK);
	}

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserAccDTO> saveUserAcc(@RequestBody UserAccount userAcc) {

		UserAccount userAccForSave = new UserAccount();
		userAccForSave.setEmail(userAcc.getEmail());
		userAccForSave.setPassword(userAcc.getPassword());
		userAccForSave.setActive(true);

		userAcc = userAccService.save(userAccForSave);
		return new ResponseEntity<>(new UserAccDTO(userAcc), HttpStatus.CREATED);
	}

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserAccDTO> updateUserAcc(@RequestBody UserAccount userAcc) {
		// a userAcc must exist
		UserAccount userAccForUpdate = userAccService.findOne(userAcc.getId());

		if (userAccForUpdate == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		userAccForUpdate.setEmail(userAcc.getEmail());
		userAccForUpdate.setPassword(userAcc.getPassword());

		userAcc = userAccService.save(userAccForUpdate);
		return new ResponseEntity<>(new UserAccDTO(userAcc), HttpStatus.OK);
	}

	@DeleteMapping(value = "userAcc/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteUserAcc(@PathVariable Long id) {
		UserAccount userAcc = userAccService.findOne(id);

		if (userAcc != null) {
			userAccService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

    @GetMapping("/public")
    public Map<String, String> getPublicApi() {
        Map<String, String> fooObj = new HashMap<>();
        fooObj.put("key", "value");
        return fooObj;
    }

}
