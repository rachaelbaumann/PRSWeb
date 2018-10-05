package com.prs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestRepository;
import com.prs.business.user.User;
import com.prs.business.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PRSPurchaseRequestTest extends PrsWebApplicationTests {
	@Autowired
	private PurchaseRequestRepository purchaserequestRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testPurchaseRequestCrudFunctions() {
		// get all products
		Iterable<PurchaseRequest> purchaserequests = purchaserequestRepository.findAll();
		assertNotNull(purchaserequests);
		
		
		// add a product
		Optional<User> u = userRepository.findById(1);
		PurchaseRequest p1 = new PurchaseRequest(u.get(), "description", "justification", LocalDateTime.now(), "deliverymode", "status", 100.00, LocalDateTime.now(), "rejection");
		assertNotNull(purchaserequestRepository.save(p1));
		int id = p1.getId();
		
		// get product and validate product name is correct
		Optional<PurchaseRequest> p2 = purchaserequestRepository.findById(id);
		assertEquals(p2.get().getDescription(), "description");
		
		// update the product
		p2.get().setDescription("newPurchaseRequest");
		assertNotNull(purchaserequestRepository.save(p2.get()));
		
		// remove the product
		purchaserequestRepository.delete(p2.get());//returns nothing!
		assertThat(!(purchaserequestRepository.findById(id)).isPresent()); //assert that it's NOT present...
	}
}
