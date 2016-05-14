package com.liumy.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.liumy.entity.Users;
import com.liumy.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

	@Resource(name = "userService")
	private UserService service;

	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public ModelAndView hello2() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "HelloMVC");
		mv.setViewName("users");
		return mv;
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ModelAndView count() {
		System.out.println("-----------------------------------------------------------------------------------");
		int c = service.userCount();

		ModelAndView mv = new ModelAndView();
		mv.addObject("message", c);
		mv.setViewName("users");
		return mv;
	}
	
	@RequestMapping(value="saveUser")
	public String saveUser(){
		Users users=new Users();
		users.setAge(10);
		users.setNice_name("22");
		users.setUser_name("222");
		service.save(users);
		return null;
	}
}
