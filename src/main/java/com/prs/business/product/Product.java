package com.prs.business.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.prs.business.vendor.Vendor;

@Entity
public class Product {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "vendorID")
	private Vendor vendor; //vendor attribute
	private String partNumber;
	private String name;
	private double price;
	private String unit;
	private String photoPath;
	
	public Product(int iD, Vendor vendor, String partNumber, String name, double price, String unit, String photoPath) {
		super();
		id = iD;
		this.vendor = vendor;
		this.partNumber = partNumber;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.photoPath = photoPath;
	}

	public Product(Vendor vendor, String partNumber, String name, double price, String unit, String photoPath) {
		super();
		this.vendor = vendor;
		this.partNumber = partNumber;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.photoPath = photoPath;
	}


	public Product() {
		super();
	}
	

	public int getId() {
		return id;
	}

	public void setId(int iD) {
		id = iD;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}


	@Override
	public String toString() {
		return "ID: " + id + "\t" 
			 + "Vendor: " + vendor + "\t"
			 + partNumber + "\t" + name + "\t" + price + "\t" + unit + "\t" + photoPath; 
	}

	
}
