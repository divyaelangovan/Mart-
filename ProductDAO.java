package com.niit.martzone.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.niit.martzone.model.Product;

public interface ProductDAO {


	public List<Product> list();

	public Product get(String id);

	public void saveOrUpdate(Product product);

	public void delete(String id);

	public MultipartFile getImage();


}
