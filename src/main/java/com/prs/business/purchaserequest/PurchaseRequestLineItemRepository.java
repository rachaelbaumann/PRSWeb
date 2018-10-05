package com.prs.business.purchaserequest;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRequestLineItemRepository extends CrudRepository<PurchaseRequestLineItem, Integer> {

//	Iterable<PurchaseRequestLineItem> findAllByPurchaseRequestId(int id);

}
