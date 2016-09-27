package com.niit.martzone.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.niit.martzone.dao.CategoryDAO;
import com.niit.martzone.dao.ProductDAO;
import com.niit.martzone.dao.SupplierDAO;
import com.niit.martzone.model.Category;
import com.niit.martzone.model.Product;
import com.niit.martzone.model.Supplier;
import com.niit.martzone.model.UtilClass;

@Controller
public class ProductController {

	@Autowired(required=true)
	private ProductDAO productDAO;

	@Autowired(required = true)
	private CategoryDAO categoryDAO;

	@Autowired(required = true)
	private SupplierDAO supplierDAO;
	private Path path;
	/*
	 * @Autowired(required=true)
	 * 
	 * @Qualifier(value="productDAO") public void setProductDAO(ProductDAO ps){
	 * this.productDAO = ps; }
	 */

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String listProducts(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("category", new Category());
		model.addAttribute("supplier", new Supplier());
		model.addAttribute("productList", this.productDAO.list());
		model.addAttribute("categoryList", this.categoryDAO.list());
		model.addAttribute("supplierList", this.supplierDAO.list());
		model.addAttribute("clickedProducts","true");
		return "admin";
	}

	// For add and update product both
	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
	public String addProduct(@Valid @ModelAttribute("product") Product product,HttpServletRequest request, BindingResult result) {
		
		UtilClass util=new UtilClass();
		String id=util.replace(product.getId(), ",", "");
		product.setId(id);
		Category category = categoryDAO.getByName(product.getCategory().getName());
		categoryDAO.saveOrUpdate(category);  // why to save??

		Supplier supplier = supplierDAO.getByName(product.getSupplier().getName());
		supplierDAO.saveOrUpdate(supplier); // Why to save??
		
		
		
		product.setCategory(category);
		product.setSupplier(supplier);
		
		product.setCategory_id(category.getId());
		product.setSupplier_id(supplier.getId());
		productDAO.saveOrUpdate(product);

		return "redirect:/products";

	}

	@RequestMapping("product/remove/{id}")
	public String removeProduct(@PathVariable("id") String id, ModelMap model) throws Exception {

		try {
			productDAO.delete(id);
			model.addAttribute("message", "Successfully Added");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		// redirectAttrs.addFlashAttribute(arg0, arg1)
		return "redirect:/products";
	}

	@RequestMapping("product/edit/{id}")
	public String editProduct(@PathVariable("id") String id, Model model) {
		System.out.println("editProduct");
		model.addAttribute("product", this.productDAO.get(id));
		model.addAttribute("listProducts", this.productDAO.list());
		model.addAttribute("categoryList", this.categoryDAO.list());
		model.addAttribute("supplierList", this.supplierDAO.list());
		
	
		return "product";
	}
	@RequestMapping("product/get/{id}")
	public String getSelectedProduct(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		System.out.println("getSelectedProduct");
		redirectAttributes.addFlashAttribute("selectedProduct", this.productDAO.get(id));
	//	model.addAttribute("categoryList", this.categoryDAO.list());
	//model.addAttribute("clickedProductViews", "true");
		return "redirect:/backToHome";
	
	}
	@RequestMapping(value="/backToHome",method=RequestMethod.GET)
	public String backToHome(@ModelAttribute("selectedProduct") final Object selectedProduct,Model model)
	{
		model.addAttribute("selectedProduct",selectedProduct);
		model.addAttribute("categoryList",this.categoryDAO.list());
		model.addAttribute("clickedProductViews", "true");
		return "home";
	}
	
	@RequestMapping("singleProductView")
	public ModelAndView adProducts() {
		
		//productDAO.saveOrUpdate(product);
         //List<Product> productlist = productDAO.list();
		
		ModelAndView mv = new ModelAndView("singleProductView");
		//mv.addObject("productList", productlist);
		return mv;
	 }
	@RequestMapping("upload")
	public ModelAndView uploadImage() {
		
		//productDAO.saveOrUpdate(product);
         //List<Product> productlist = productDAO.list();
		
		ModelAndView mv = new ModelAndView("upload");
		//mv.addObject("productList", productlist);
		return mv;
	 }

}
	
	

