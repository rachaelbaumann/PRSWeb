package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.vendor.Vendor;
import com.prs.business.vendor.VendorRepository;

@Controller
@RequestMapping("/Vendors")
public class VendorController {
	@Autowired
	private VendorRepository vendorRepository; // variable that calls out CRUD methods
	
	@GetMapping("/List")
	public @ResponseBody Iterable<Vendor> getAllVendors() {
		Iterable<Vendor> vendors = vendorRepository.findAll();
		return vendors; // return in iterable list of vendors		
	}
	
	@GetMapping("/Get")
	public @ResponseBody Optional<Vendor> getVendor(@RequestParam int id) {
		Optional<Vendor> vendor = vendorRepository.findById(id);
		return vendor; // return in iterable list of vendors		
	}
	
	@PostMapping("/Add")
	public @ResponseBody Vendor addVendor(@RequestBody Vendor vendor) {
		return vendorRepository.save(vendor);
	}
	
	@PostMapping("/Change")
	public @ResponseBody Vendor updateVendor(@RequestBody Vendor vendor) {
		return vendorRepository.save(vendor);
	}
	
	@PostMapping("/Remove")
	public @ResponseBody String removeVendor(@RequestBody Vendor vendor) {
		vendorRepository.delete(vendor);
		return "vendor deleted";
	}
	
}
