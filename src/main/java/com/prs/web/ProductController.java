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

import com.prs.business.product.Product;
import com.prs.business.product.ProductRepository;
import com.prs.util.JsonResponse;

@CrossOrigin
@Controller
@RequestMapping("/Products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllProducts() {
		try {
			return JsonResponse.getInstance(productRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Product list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getProduct(@PathVariable int id) {
		try {
			Optional<Product> product = productRepository.findById(id);
			if (product.isPresent())
				return JsonResponse.getInstance(product.get());
			else
				return JsonResponse.getErrorInstance("Product not found for id: " + id);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting product:  " + e.getMessage());
		}
	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addProduct(@RequestBody Product product) {
		return saveProduct(product);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updateProduct(@RequestBody Product product) {
		return saveProduct(product);
	}

	private @ResponseBody JsonResponse saveProduct(@RequestBody Product product) {
		try {
			productRepository.save(product);
			return JsonResponse.getInstance(product);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removeProduct(@RequestBody Product product) {
		try {
			productRepository.delete(product);
			return JsonResponse.getInstance(product);
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
//import com.prs.business.product.Product;
//import com.prs.business.product.ProductRepository;
//import com.prs.util.PRSMaintenanceReturn;
//
//@CrossOrigin	// Prevents errors such as 'Response for preflight has invalid HTTP status code 403.'
//@Controller		
//@RequestMapping(path="/Products")
//public class ProductController extends BaseController {
//	@Autowired
//	private ProductRepository productRepository;
//
//	@GetMapping(path="/List")
//	public @ResponseBody Iterable<Product> getAllProducts() {
//		return productRepository.findAll();
//	}
//	
//	@GetMapping(path="/Get")
//	public @ResponseBody List<Product> getProduct(@RequestParam int id) {
//		Optional<Product> product = productRepository.findById(id);
//		return getReturnArray(product);
//	}
//
//	@PostMapping(path="/Add") 
//	public @ResponseBody PRSMaintenanceReturn addNewPurchaseRequest (@RequestBody Product product) {
//		try {
//			productRepository.save(product);
//			return PRSMaintenanceReturn.getMaintReturn(product);
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(product, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return PRSMaintenanceReturn.getMaintReturnError(product, e.getMessage());
//		}
//	}
//	
//	@GetMapping(path="/Remove") // Map ONLY GET Requests
//	public @ResponseBody PRSMaintenanceReturn deleteProduct (@RequestParam int id) {
//		
//		Optional<Product> product = productRepository.findById(id);
//		try {
//			productRepository.delete(product.get());
//			return PRSMaintenanceReturn.getMaintReturn(product.get());
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(product, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			return PRSMaintenanceReturn.getMaintReturnError(product, e.toString());
//		}
//		
//	}
//
//	@PostMapping(path="/Change") 
//	public @ResponseBody PRSMaintenanceReturn updateProduct (@RequestBody Product product) {
//		try {
//			productRepository.save(product);
//			return PRSMaintenanceReturn.getMaintReturn(product);
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(product, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			return PRSMaintenanceReturn.getMaintReturnError(product, e.toString());
//		}
//		
//	}
//}


//package com.prs.web;
//
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
//import com.prs.business.product.Product;
//import com.prs.business.product.ProductRepository;
//import com.prs.util.JsonResponse;
//
//@Controller
//@RequestMapping("/Products")
//public class ProductController {
//	
//	@Autowired
//	private ProductRepository productRepository;
//	
//	@GetMapping("/List")
//	public @ResponseBody JsonResponse getAllProducts() {
//		try {	
//			return JsonResponse.getInstance(productRepository.findAll());
//		}
//		catch (Exception e) {
//			return JsonResponse.getErrorInstance("Product list failure:" + e.getMessage(), e);
//		}
//	}
//	
//	@GetMapping("/Get/{id}")
//	public @ResponseBody JsonResponse getProduct(@PathVariable int id) {
//		try {
//			Optional<Product> product = productRepository.findById(id);
//			if (product.isPresent())
//				return JsonResponse.getInstance(product.get());
//			else
//				return JsonResponse.getErrorInstance("Product not found for id: " + id, null);
//		}
//		catch (Exception e) {
//			return JsonResponse.getErrorInstance("Error getting product:  " + e.getMessage(), null);
//		}
//	}
//	
//	@PostMapping("/Add")
//	public @ResponseBody JsonResponse addProduct(@RequestBody Product product) {
//		return addProduct(product);
//	}
//
//	@PostMapping("/Change")
//	public @ResponseBody JsonResponse updateProduct(@RequestBody Product product) {
//		return updateProduct(product);
//	}
//
//	private @ResponseBody JsonResponse saveProduct(@RequestBody Product product) {
//		try {
//			productRepository.save(product);
//			return JsonResponse.getInstance(product);
//		} catch (DataIntegrityViolationException ex) {
//			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
//		} catch (Exception ex) {
//			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
//		}
//	}
//	
//	@PostMapping("/Remove")
//	public @ResponseBody JsonResponse removeProduct(@RequestBody Product product) {
//		try {
//			productRepository.delete(product);
//			return JsonResponse.getInstance(product);
//		} catch (Exception ex) {
//			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
//		}
//	}
//	
//}




//package com.prs.web;
//
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
//import com.prs.business.product.Product;
//import com.prs.business.product.ProductRepository;
//
//@Controller
//@RequestMapping(path = "/Products")
//public class ProductController {
//	@Autowired
//	private ProductRepository productRepository;
//	
//	@GetMapping(path = "/List")
//	public @ResponseBody Iterable<Product> getAllProducts() {
//		try  {
//			return productRepository.findAll();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	@GetMapping(path = "/Get")
//	public @ResponseBody Optional<Product> getProduct (@RequestParam int id) {
//		Optional<Product> p = productRepository.findById(id);
//		return p;
//	}
//
//	@PostMapping(path = "/Add")
//	public @ResponseBody Product addNewProduct (@RequestBody Product product) {
//		try {
//			productRepository.save(product);
//		} catch (Exception e) {
//			product = null;
//		}
//		return product;
//	}
//	
//	@PostMapping(path = "/Change")
//	public @ResponseBody Product updateProduct (@RequestBody Product product) {
//		
//		try {
//			productRepository.save(product);
//		} catch (Exception e) {
//			product = null;
//		}
//		return product;
//	}
//
//	@GetMapping(path = "/Remove")
//	public @ResponseBody String deleteProduct (@RequestParam int id) {
//		
//		Optional<Product> product = productRepository.findById(id);
//		try {
//			Product p = product.get();
//			productRepository.delete(p);
//		} catch (Exception e) {
//			product = null;
//		}
//		return "product deleted";
//	}
//	
//}







// OG CODE
//package com.prs.web;
//
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
//import com.prs.business.product.Product;
//import com.prs.business.product.ProductRepository;
//
//
//@Controller
//@RequestMapping("/Products")
//public class ProductController {
//	@Autowired
//	private ProductRepository productRepository; // variable that calls out CRUD methods
//	
//	@GetMapping("/List")
//	public @ResponseBody Iterable<Product> getAllProducts() {
//		Iterable<Product> products =productRepository.findAll();
//		return products; // return in iterable list of vendors		
//	}
//	
//	@GetMapping("/Get")
//	public @ResponseBody Optional<Product> getProduct(@RequestParam int id) {
//		Optional<Product> product = productRepository.findById(id);
//		return product; // return in iterable list of vendors		
//	}
//	
//	@PostMapping("/Add")
//	public @ResponseBody Product addProduct(@RequestBody Product product) {
//		return productRepository.save(product);
//	}
//	
//	@PostMapping("/Change")
//	public @ResponseBody Product updateProduct(@RequestBody Product product) {
//		return productRepository.save(product);
//	}
//	
//	@PostMapping("/Remove")
//	public @ResponseBody String removeProduct(@RequestBody Product product) {
//		productRepository.delete(product);
//		return "product deleted";
//	}
//	
//}
