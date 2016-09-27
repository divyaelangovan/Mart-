package com.niit.martzone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.niit.martzone.dao.CartDAO;
import com.niit.martzone.dao.CategoryDAO;
import com.niit.martzone.model.Category;
import com.niit.martzone.model.UtilClass;




@Controller
public class CategoryController {
	
	private CategoryDAO categoryDAO;
	
	@Autowired(required=true)
	@Qualifier(value="categoryDAO")
	public void setCategoryDAO(CategoryDAO ps){
		this.categoryDAO = ps;
	}
	
	@RequestMapping("home")
	public ModelAndView onLoadHome(Model model)
	{
		model.addAttribute("category", new Category());
		model.addAttribute("categoryList", this.categoryDAO.list());
		//model.addAttribute("cartSize", this.cartDAO.list().size());
		ModelAndView mv=new ModelAndView("home");
		mv.addObject("clickedHome","true");
		
		return mv;
	}
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public String listCategorys(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("categoryList", this.categoryDAO.list());
		model.addAttribute("clickedCategories","true");
		
		return "admin";
	}
	
	//For add and update category both
	@RequestMapping(value= "/category/add", method = RequestMethod.POST)
	public String addCategory(@ModelAttribute("category") Category category){
		UtilClass util=new UtilClass();
		String id=util.replace(category.getId(), ",", "");
		category.setId(id);
	
			categoryDAO.saveOrUpdate(category);
		
		return "redirect:/categories";
		
	}
	
	@RequestMapping("category/remove/{id}")
    public String deleteCategory(@PathVariable("id") String id,ModelMap model) throws Exception{
		
       try {
		categoryDAO.delete(id);
		model.addAttribute("message","Successfully Added");
	} catch (Exception e) {
		model.addAttribute("message",e.getMessage());
		e.printStackTrace();
	}
       //redirectAttrs.addFlashAttribute(arg0, arg1)
        return "redirect:/categories";
    }
 
    @RequestMapping("category/edit/{id}")
    public String editCategory(@PathVariable("id") String id, Model model){
    	System.out.println("editCategory");
        model.addAttribute("category", this.categoryDAO.get(id));
        model.addAttribute("listCategorys", this.categoryDAO.list());
        return "category";
    }
    
	}
