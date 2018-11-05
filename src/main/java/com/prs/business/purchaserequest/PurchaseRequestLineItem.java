package com.prs.business.purchaserequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.prs.business.product.Product;

@Entity
public class PurchaseRequestLineItem {
	@Id //Id annotation
	@GeneratedValue (strategy = GenerationType.IDENTITY) //IDENTITY is a constant inside GenerationType
	private int id;
	@ManyToOne
	@JoinColumn(name = "purchaseRequestID")
	private PurchaseRequest purchaseRequest;
	@ManyToOne
	@JoinColumn(name = "productID")
	private Product product;
	private int quantity;
	
	public PurchaseRequestLineItem(int id, PurchaseRequest purchaserequest, Product product, int quantity) {
		super();
		this.id = id;
		this.purchaseRequest = purchaserequest;
		this.product = product;
		this.quantity = quantity;
	}

	public PurchaseRequestLineItem(PurchaseRequest purchaserequest, Product product, int quantity) {
		super();
		this.purchaseRequest = purchaserequest;
		this.product = product;
		this.quantity = quantity;
	}

	public PurchaseRequestLineItem() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaserequest) {
		this.purchaseRequest = purchaserequest;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "PurchaseRequestLineItem [id=" + id + ", purchaserequest=" + purchaseRequest + ", product=" + product
				+ ", quantity=" + quantity + "]";
	}
	
}
