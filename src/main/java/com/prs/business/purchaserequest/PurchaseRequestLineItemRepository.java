package com.prs.business.purchaserequest;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRequestLineItemRepository extends CrudRepository<PurchaseRequestLineItem, Integer> {

//	Iterable<PurchaseRequestLineItem> findAllByPurchaseRequestId(int id);
	List<PurchaseRequestLineItem> findAllByPurchaseRequestId(int purchaseRequestID);

}
