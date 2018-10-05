package com.prs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.product.Product;
import com.prs.business.product.ProductRepository;
import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestLineItem;
import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;
import com.prs.business.purchaserequest.PurchaseRequestRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PRSPurchaseRequestLineItemTest extends PrsWebApplicationTests {
	@Autowired
	private PurchaseRequestLineItemRepository purchaserequestlineitemRepository;
	
	@Autowired
	private PurchaseRequestRepository purchaserequestRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void testPurchaseRequestineItemCrudFunctions() {
		// get all products
		Iterable<PurchaseRequestLineItem> purchaserequestlineitems = purchaserequestlineitemRepository.findAll();
		assertNotNull(purchaserequestlineitems);
		
		
		// add a product
		Optional<PurchaseRequest> pr = purchaserequestRepository.findById(1);
		Optional<Product> p = productRepository.findById(2);
		PurchaseRequestLineItem p1 = new PurchaseRequestLineItem(pr.get(), p.get(), 10);
		assertNotNull(purchaserequestlineitemRepository.save(p1));
		int id = p1.getId();
		
		// get product and validate product name is correct
		Optional<PurchaseRequestLineItem> p2 = purchaserequestlineitemRepository.findById(id);
		assertEquals(p2.get().getQuantity(), 10);
		
		// update the product
		p2.get().setQuantity(10);
		assertNotNull(purchaserequestlineitemRepository.save(p2.get()));
		
		// remove the product
		purchaserequestlineitemRepository.delete(p2.get());//returns nothing!
		assertThat(!(purchaserequestlineitemRepository.findById(id)).isPresent()); //assert that it's NOT present...
	}
}
