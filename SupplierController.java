package com.niit.martzone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.niit.martzone.dao.SupplierDAO;
import com.niit.martzone.model.Supplier;
import com.niit.martzone.model.UtilClass;
@Controller
public class SupplierController {
	
	private SupplierDAO supplierDAO;
	
	@Autowired(required=true)
	@Qualifier(value="supplierDAO")
	public void setSupplierDAO(SupplierDAO ps){
		this.supplierDAO = ps;
	}
	
	@RequestMapping(value = "/suppliers", method = RequestMethod.GET)
	public String listCategorys(Model model) {
		model.addAttribute("supplier", new Supplier());
		model.addAttribute("supplierList", this.supplierDAO.list());
		model.addAttribute("clickedSuppliers","true");
		return "admin";
	}
	
	//For add and update category both
	@RequestMapping(value= "/supplier/add", method = RequestMethod.POST)
	public String addSupplier(@ModelAttribute("supplier") Supplier supplier){
		
		UtilClass util=new UtilClass();
		String id=util.replace(supplier.getId(),",","");
		supplier.setId(id);
			supplierDAO.saveOrUpdate(supplier);
		
		return "redirect:/suppliers";
		
	}

	@RequestMapping(value="listSupplier",method= RequestMethod.GET)
	public @ResponseBody String getGsons()
	{
		Gson gson = new Gson();
	System.out.print("gson");
	List<Supplier> supplierList = supplierDAO.list();
		String sdata = gson.toJson(supplierList);
	    return sdata;
	}
	
	@RequestMapping("supplier/remove/{id}")
    public String deleteSupplier(@PathVariable("id") String id,ModelMap model) throws Exception{
		
       try {
		supplierDAO.delete(id);
		model.addAttribute("message","Successfully Added");
	} catch (Exception e) {
		model.addAttribute("message",e.getMessage());
		e.printStackTrace();
	}
       //redirectAttrs.addFlashAttribute(arg0, arg1)
        return "redirect:/suppliers";
    }
 
    @RequestMapping("supplier/edit/{id}")
    public String editSupplier(@PathVariable("id") String id, Model model){
    	System.out.println("editSupplier");
        model.addAttribute("supplier", this.supplierDAO.get(id));
        model.addAttribute("listSuppliers", this.supplierDAO.list());
        return "supplier";
    }
	}
