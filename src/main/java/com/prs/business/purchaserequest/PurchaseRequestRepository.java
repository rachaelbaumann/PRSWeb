package com.prs.business.purchaserequest;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRequestRepository extends CrudRepository<PurchaseRequest, Integer> {

	//Iterable<PurchaseRequest> findAllByUserIdNotAndStatus(int id, String string);
	List<PurchaseRequest> findAllByUserIdNotAndStatus(int id, String status);

}
