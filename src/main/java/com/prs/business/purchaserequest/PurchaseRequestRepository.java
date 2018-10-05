package com.prs.business.purchaserequest;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRequestRepository extends CrudRepository<PurchaseRequest, Integer> {

//	Iterable<PurchaseRequest> findAllByUserIdNotAndStatus(int id, String string);
	
}
