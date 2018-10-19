package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.user.User;
import com.prs.business.user.UserRepository;
import com.prs.util.JsonResponse;

@CrossOrigin
@Controller
@RequestMapping("/Users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllUsers() {
		try {
			return JsonResponse.getInstance(userRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("User list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getUser(@PathVariable int id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent())
				return JsonResponse.getInstance(user.get());
			else
				return JsonResponse.getErrorInstance("User not found for id: " + id, null);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting user:  " + e.getMessage(), null);
		}
	}

	@PostMapping("/Login")
	public @ResponseBody JsonResponse authenticate(@RequestBody User user) {
		try {
			User u = userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
			return JsonResponse.getInstance(u);

		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error authenticating user:  " + e.getMessage(), null);
		}

	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addUser(@RequestBody User user) {
		return saveUser(user);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updateUser(@RequestBody User user) {
		return saveUser(user);
	}

	private @ResponseBody JsonResponse saveUser(@RequestBody User user) {
		try {
			userRepository.save(user);
			return JsonResponse.getInstance(user);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removeUser(@RequestBody User user) {
		try {
			userRepository.delete(user);
			return JsonResponse.getInstance(user);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}
}

//package com.prs.web;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.prs.business.user.User;
//import com.prs.business.user.UserRepository;
//import com.prs.util.PRSMaintenanceReturn;
//
//@CrossOrigin	// Prevents errors such as 'Response for preflight has invalid HTTP status code 403.'
//@Controller		
//@RequestMapping(path="/Users")
//public class UserController extends BaseController {
//	@Autowired
//	private UserRepository userRepository;
//
//	@GetMapping(path="/List")
//	public @ResponseBody Iterable<User> getAllUsers() {
//		return userRepository.findAll();
//	}
//	
//	@GetMapping(path="/Get")
//	public @ResponseBody List<User> getUser(@RequestParam int id) {
//		Optional<User> u = userRepository.findById(id);
//		return getReturnArray(u);
//	}
//
//	@PostMapping(path="/Add") 
//	public @ResponseBody PRSMaintenanceReturn addNewUser (@RequestBody User user) {
//		try {
//			userRepository.save(user);
//			return PRSMaintenanceReturn.getMaintReturn(user);
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(user, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return PRSMaintenanceReturn.getMaintReturnError(user, e.getMessage());
//		}
//	}
//	
//	@GetMapping(path="/Remove") // Map ONLY GET Requests
//	public @ResponseBody PRSMaintenanceReturn deleteUser (@RequestParam int id) {
//		
//		Optional<User> user = userRepository.findById(id);
//		try {
//			userRepository.delete(user.get());
//			return PRSMaintenanceReturn.getMaintReturn(user.get());
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(user, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			return PRSMaintenanceReturn.getMaintReturnError(user, e.toString());
//		}
//		
//	}
//
//	@PostMapping(path="/Change") 
//	public @ResponseBody PRSMaintenanceReturn updateUser (@RequestBody User user) {
//		try {
//			userRepository.save(user);
//			return PRSMaintenanceReturn.getMaintReturn(user);
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(user, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			return PRSMaintenanceReturn.getMaintReturnError(user, e.toString());
//		}
//		
//	}
//
//	@GetMapping(path="/Authenticate") // Map ONLY GET Requests
//	public @ResponseBody List<User> authenticate (@RequestParam String username, @RequestParam String password) {
//		User u = userRepository.findByUserNameAndPassword(username, password);
//		return getReturnArray(u);
//	}	
//}

//package com.prs.web;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.prs.business.user.User;
//import com.prs.business.user.UserRepository;
//import com.prs.util.JsonResponse;
//
//@Controller
//@RequestMapping("/Users")
//public class UserController {
//	
//	@Autowired
//	private UserRepository userRepository;
//	
//	@GetMapping("/List")
//	public @ResponseBody JsonResponse getAllUsers() {
//		try {	
//			return JsonResponse.getInstance(userRepository.findAll());
//		}
//		catch (Exception e) {
//			return JsonResponse.getErrorInstance("User list failure:"+e.getMessage(), e);
//		}
//	}
//	
//	@GetMapping("/Get/{id}")
//	public @ResponseBody JsonResponse getUser(@PathVariable int id) {
//		try {
//			Optional<User> user = userRepository.findById(id);
//			if (user.isPresent())
//				return JsonResponse.getInstance(user.get());
//			else
//				return JsonResponse.getErrorInstance("User not found for id: " + id, null);
//		}
//		catch (Exception e) {
//			return JsonResponse.getErrorInstance("Error getting user:  " + e.getMessage(), null);
//		}
//	}
//	
//	@PostMapping("/Login")
//	public @ResponseBody JsonResponse authenticate(@RequestBody User user) { //pass in a User object
//		// get an instance of user from the database then return that user
//		try {
//			User u = userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
//			return JsonResponse.getInstance(u);
//		} catch(Exception e) {
//			return JsonResponse.getErrorInstance("Error authenticating user:  " + e.getMessage(), null);
//		}
//		
//	}
//	
//	@PostMapping("/Add")
//	public @ResponseBody JsonResponse addUser(@RequestBody User user) {
//		return saveUser(user);
//	}
//
//	@PostMapping("/Change")
//	public @ResponseBody JsonResponse updateUser(@RequestBody User user) {
//		return saveUser(user);
//	}
//
//	private @ResponseBody JsonResponse saveUser(@RequestBody User user) {
//		try {
//			userRepository.save(user);
//			return JsonResponse.getInstance(user);
//		} catch (DataIntegrityViolationException ex) {
//			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
//		} catch (Exception ex) {
//			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
//		}
//	}
//	
//	@PostMapping("/Remove")
//	public @ResponseBody JsonResponse removeUser(@RequestBody User user) {
//		try {
//			userRepository.delete(user);
//			return JsonResponse.getInstance(user);
//		} catch (Exception ex) {
//			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
//		}
//	}
//	
//}

// OG CODE
//package com.prs.web;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.prs.business.user.User;
//import com.prs.business.user.UserRepository;
//
//@Controller
//@RequestMapping("/Users") // call user controller
//public class UserController {
//	
//	@Autowired
//	private UserRepository userRepository; // variable that calls out CRUD methods
//
//	@GetMapping("/List")
//	public @ResponseBody Iterable<User> getAllUsers() {
//		Iterable<User> users = userRepository.findAll();
//		return users; // return in iterable list of users		
//	}
//	
//	@GetMapping("/Get")
//	public @ResponseBody Optional<User> getUser(@RequestParam int id) {
//		Optional<User> user = userRepository.findById(id);
//		return user; // return in iterable list of users		
//	}
//	
//	@PostMapping("/Add")
//	public @ResponseBody User addUser(@RequestBody User user) {
//		return userRepository.save(user);
//	}
//	
//	@PostMapping("/Change")
//	public @ResponseBody User updateUser(@RequestBody User user) {
//		return userRepository.save(user);
//	}
//	
//	@PostMapping("/Remove")
//	public @ResponseBody String removeUser(@RequestBody User user) {
//		userRepository.delete(user);
//		return "user deleted";
//	}
//}
