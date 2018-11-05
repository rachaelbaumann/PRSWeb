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

import com.prs.business.vendor.Vendor;
import com.prs.business.vendor.VendorRepository;
import com.prs.util.JsonResponse;

@CrossOrigin
@Controller
@RequestMapping("/Vendors")
public class VendorController {

	@Autowired
	private VendorRepository vendorRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllVendors() {
		try {
			return JsonResponse.getInstance(vendorRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Vendor list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getVendor(@PathVariable int id) {
		try {
			Optional<Vendor> vendor = vendorRepository.findById(id);
			if (vendor.isPresent())
				return JsonResponse.getInstance(vendor.get());
			else
				return JsonResponse.getErrorInstance("Vendor not found for id: " + id);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting vendor:  " + e.getMessage(), e);
		}
	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addVendor(@RequestBody Vendor vendor) {
		return saveVendor(vendor);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updateVendor(@RequestBody Vendor vendor) {
		return saveVendor(vendor);
	}

	private @ResponseBody JsonResponse saveVendor(@RequestBody Vendor vendor) {
		try {
			vendorRepository.save(vendor);
			return JsonResponse.getInstance(vendor);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removeVendor(@RequestBody Vendor vendor) {
		try {
			vendorRepository.delete(vendor);
			return JsonResponse.getInstance(vendor);
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
//import com.prs.business.vendor.Vendor;
//import com.prs.business.vendor.VendorRepository;
//import com.prs.util.PRSMaintenanceReturn;
//
//@CrossOrigin	// Prevents errors such as 'Response for preflight has invalid HTTP status code 403.'
//@Controller		
//@RequestMapping(path="/Vendors")
//public class VendorController extends BaseController {
//	@Autowired
//	private VendorRepository vendorRepository;
//
//	@GetMapping(path="/List")
//	public @ResponseBody Iterable<Vendor> getAllVendors() {
//		return vendorRepository.findAll();
//	}
//	
//	@GetMapping(path="/Get")
//	public @ResponseBody List<Vendor> getVendor(@RequestParam int id) {
//		Optional<Vendor> v = vendorRepository.findById(id);
//		return getReturnArray(v);
//	}
//
//	@PostMapping(path="/Add") 
//	public @ResponseBody PRSMaintenanceReturn addNewVendor (@RequestBody Vendor vendor) {
//		try {
//			vendorRepository.save(vendor);
//			return PRSMaintenanceReturn.getMaintReturn(vendor);
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(vendor, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return PRSMaintenanceReturn.getMaintReturnError(vendor, e.getMessage());
//		}
//	}
//	
//	@GetMapping(path="/Remove") // Map ONLY GET Requests
//	public @ResponseBody PRSMaintenanceReturn deleteVendor (@RequestParam int id) {
//		
//		Optional<Vendor> vendor = vendorRepository.findById(id);
//		try {
//			vendorRepository.delete(vendor.get());
//			return PRSMaintenanceReturn.getMaintReturn(vendor.get());
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(vendor, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			return PRSMaintenanceReturn.getMaintReturnError(vendor, e.toString());
//		}
//		
//	}
//
//	@PostMapping(path="/Change") 
//	public @ResponseBody PRSMaintenanceReturn updateVendor (@RequestBody Vendor vendor) {
//		try {
//			vendorRepository.save(vendor);
//			return PRSMaintenanceReturn.getMaintReturn(vendor);
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(vendor, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			return PRSMaintenanceReturn.getMaintReturnError(vendor, e.toString());
//		}
//		
//	}
//
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
//import com.prs.business.vendor.Vendor;
//import com.prs.business.vendor.VendorRepository;
//import com.prs.util.JsonResponse;
//
//@Controller
//@RequestMapping("/Vendors")
//public class VendorController {
//	
//	@Autowired
//	private VendorRepository vendorRepository;
//	
//	@GetMapping("/List")
//	public @ResponseBody JsonResponse getAllVendors() {
//		try {	
//			return JsonResponse.getInstance(vendorRepository.findAll());
//		}
//		catch (Exception e) {
//			return JsonResponse.getErrorInstance("Vendor list failure:" + e.getMessage(), e);
//		}
//	}
//	
//	@GetMapping("/Get/{id}")
//	public @ResponseBody JsonResponse getVendor(@PathVariable int id) {
//		try {
//			Optional<Vendor> vendor = vendorRepository.findById(id);
//			if (vendor.isPresent())
//				return JsonResponse.getInstance(vendor.get());
//			else
//				return JsonResponse.getErrorInstance("Vendor not found for id: " + id, null);
//		}
//		catch (Exception e) {
//			return JsonResponse.getErrorInstance("Error getting vendor:  " + e.getMessage(), null);
//		}
//	}
//	
//	@PostMapping("/Add")
//	public @ResponseBody JsonResponse addVendor(@RequestBody Vendor vendor) {
//		return addVendor(vendor);
//	}
//
//	@PostMapping("/Change")
//	public @ResponseBody JsonResponse updateVendor(@RequestBody Vendor vendor) {
//		return updateVendor(vendor);
//	}
//
//	private @ResponseBody JsonResponse saveVendor(@RequestBody Vendor vendor) {
//		try {
//			vendorRepository.save(vendor);
//			return JsonResponse.getInstance(vendor);
//		} catch (DataIntegrityViolationException ex) {
//			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
//		} catch (Exception ex) {
//			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
//		}
//	}
//	
//	@PostMapping("/Remove")
//	public @ResponseBody JsonResponse removeVendor(@RequestBody Vendor vendor) {
//		try {
//			vendorRepository.delete(vendor);
//			return JsonResponse.getInstance(vendor);
//		} catch (Exception ex) {
//			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
//		}
//	}
//	
//}



//package com.prs.web;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.prs.business.vendor.Vendor;
//import com.prs.business.vendor.VendorRepository;
//import com.prs.util.PRSMaintenanceReturn;
//
//@CrossOrigin
//@Controller
//@RequestMapping(path="/Vendors")
//public class VendorController extends BaseController {
//	@Autowired
//	private VendorRepository vendorRepository;
//	
//	@GetMapping(path="/List")
//	public @ResponseBody Iterable<Vendor> getAllVendors() {
//		return vendorRepository.findAll();
//	}
//	
//	@GetMapping(path="/Get") 
//	public @ResponseBody List<Vendor> getVendor (@RequestParam int id) {
//
//		Optional<Vendor> v = vendorRepository.findById(id);
//		return getReturnArray(v);
//	}
//
//	@PostMapping(path="/Add") 
//	public @ResponseBody PRSMaintenanceReturn addNewVendor (@RequestBody Vendor vendor) {
//		try {
//			vendorRepository.save(vendor);
//			System.out.println("Vendor saved:  " + vendor);
//		}
//		catch (Exception e) {
//			vendor = null;
//		}
//		return PRSMaintenanceReturn.getMaintReturn(vendor);
//	}
//	
//	@GetMapping(path="/Remove") 
//	public @ResponseBody PRSMaintenanceReturn deleteVendor (@RequestParam int id) {
//		Optional<Vendor> vendor = vendorRepository.findById(id);
//		try {
//			Vendor v = vendor.get();
//			vendorRepository.delete(v);
//		}
//		catch (Exception e) {
//			vendor = null;
//		}
//		return PRSMaintenanceReturn.getMaintReturn(vendor);
//	}
//	
//	@PostMapping(path="/Change") 
//	public @ResponseBody PRSMaintenanceReturn updateVendor (@RequestBody Vendor vendor) {
//		try {
//			vendorRepository.save(vendor);
//		}
//		catch (Exception e) {
//			vendor = null;
//		}
//		
//		return PRSMaintenanceReturn.getMaintReturn(vendor);
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
//import com.prs.business.vendor.Vendor;
//import com.prs.business.vendor.VendorRepository;
//
//@Controller
//@RequestMapping("/Vendors")
//public class VendorController {
//	@Autowired
//	private VendorRepository vendorRepository; // variable that calls out CRUD methods
//	
//	@GetMapping("/List")
//	public @ResponseBody Iterable<Vendor> getAllVendors() {
//		Iterable<Vendor> vendors = vendorRepository.findAll();
//		return vendors; // return in iterable list of vendors		
//	}
//	
//	@GetMapping("/Get")
//	public @ResponseBody Optional<Vendor> getVendor(@RequestParam int id) {
//		Optional<Vendor> vendor = vendorRepository.findById(id);
//		return vendor; // return in iterable list of vendors		
//	}
//	
//	@PostMapping("/Add")
//	public @ResponseBody Vendor addVendor(@RequestBody Vendor vendor) {
//		return vendorRepository.save(vendor);
//	}
//	
//	@PostMapping("/Change")
//	public @ResponseBody Vendor updateVendor(@RequestBody Vendor vendor) {
//		return vendorRepository.save(vendor);
//	}
//	
//	@PostMapping("/Remove")
//	public @ResponseBody String removeVendor(@RequestBody Vendor vendor) {
//		vendorRepository.delete(vendor);
//		return "vendor deleted";
//	}
//	
//}
