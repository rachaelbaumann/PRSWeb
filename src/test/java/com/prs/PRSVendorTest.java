package com.prs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.vendor.Vendor;
import com.prs.business.vendor.VendorRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PRSVendorTest extends PrsWebApplicationTests {
	@Autowired
	private VendorRepository vendorRepository;
	
	@Test
	public void testVendorCrudFunctions() {
		// get all vendors
		Iterable<Vendor> vendors = vendorRepository.findAll();
		assertNotNull(vendors);
		
		// add a vendor
		Vendor v1 = new Vendor("code", "name", "address", "city", "st", "zip", "phone", "email", true);
		assertNotNull(vendorRepository.save(v1));
		int id = v1.getId();
		
		// get user and validate vendor is correct
		Optional<Vendor> v2 = vendorRepository.findById(id);
		assertEquals(v2.get().getName(), "name");
		
		// update the vendor
		v2.get().setName("newName");
		assertNotNull(vendorRepository.save(v2.get()));
		
		// remove the vendor
		vendorRepository.delete(v2.get());//returns nothing!
		assertThat(!(vendorRepository.findById(id)).isPresent()); //assert that it's NOT present...
	}
}
