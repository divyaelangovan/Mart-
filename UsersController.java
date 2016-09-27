package com.niit.martzone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niit.martzone.dao.UsersDAO;
import com.niit.martzone.model.Users;





@Controller
public class UsersController {

	   @Autowired
		UsersDAO usersDAO;


		@RequestMapping("/isValidUser")
		public ModelAndView showMessage(@RequestParam(value = "username") String username,
				@RequestParam(value = "password") String password) {
			System.out.println("in controller");

			String message;
			ModelAndView mv ;
			
			if (usersDAO.isValidUser(username,password)) 
			{
				message = "Successfully Logged in";
				 mv = new ModelAndView("signedin");
			} else{
				message="Please enter a valid username and password";
				mv=new ModelAndView("signin");
			}

			mv.addObject("message", message);
			mv.addObject("username", username);
			return mv;
		}
		@RequestMapping("/register")
		public ModelAndView registerUser(@ModelAttribute Users users) {
			System.out.println("in reg controller");
			ModelAndView mv;
			String msg;
			msg="You have registered successfully, Please log in to continue";
			usersDAO.saveOrUpdate(users);
		  
			mv= new ModelAndView("/signin");
			mv.addObject("msg",msg);
			return mv;
		 }

		@RequestMapping("signup")
	public ModelAndView onLoadSignUp()
	{
		ModelAndView mv=new ModelAndView("signup");
	
		return mv;
	}
	@RequestMapping("productView")
	public ModelAndView onLoadProductView()
	{
		ModelAndView mv=new ModelAndView("productView");
	
		return mv;
	}
	


	@RequestMapping("signedin")
	public ModelAndView onLoadSignedin()
	{
		ModelAndView mv=new ModelAndView("signedin");
	
		return mv;
	}
	@RequestMapping("signin")
	public ModelAndView onLoadSignin()
	{
		ModelAndView mv=new ModelAndView("signin");
	
		return mv;
	}
	@RequestMapping("admin")
	public ModelAndView onLoadAdmin()
	{
		ModelAndView mv=new ModelAndView("admin");
	
		return mv;
	}
	@RequestMapping("403")
	public ModelAndView onLoaderror()
	{
		ModelAndView mv=new ModelAndView("403");
	
		return mv;
	}

	}


