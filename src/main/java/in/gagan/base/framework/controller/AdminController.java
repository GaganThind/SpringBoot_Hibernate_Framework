package in.gagan.base.framework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gagan.base.framework.dto.UserDTO;
import in.gagan.base.framework.service.AdminService;

/**
 * This controller class provides the functionality for the admin module.
 * 
 * @author gaganthind
 *
 */
@RestController
@RequestMapping(path = "/v1/admin")
public class AdminController {
	
	private final AdminService adminSvc;

	@Autowired
	public AdminController(AdminService adminSvc) {
		this.adminSvc = adminSvc;
	}

	/**
	 * This method returns all the users currently present in the system.
	 * 
	 * @return ResponseEntity<List<UserDTO>> - List of all the users in the system.
	 */
	@GetMapping(value = "/users", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> fetchAllUsers() {
		List<UserDTO> userDTOs = this.adminSvc.fetchAllUsers();
		return new ResponseEntity<List<UserDTO>>(userDTOs, HttpStatus.OK);
	}

}