package com.niit.martzone.dao;

import java.util.List;

import com.niit.martzone.model.Supplier;

public interface SupplierDAO {


	public List<Supplier> list();

	public Supplier get(String id);
	
	public Supplier getByName(String name);

	public void saveOrUpdate(Supplier supplier);

	public void delete(String id);


}
