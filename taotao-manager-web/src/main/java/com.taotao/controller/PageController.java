package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
	
	
	@RequestMapping("/")
	private String goindexpage(){
		
		return "index";
	}
	
	@RequestMapping("/{page}")
	private String gootherpage(@PathVariable String page){
		
		return page;
	}

	@RequestMapping("/rest/page/item-edit")
	private String UpdateItem(){

		return "item-edit";
	}

	@RequestMapping("/rest/page/item-param-edit")
	private String UpdateParam(){
		return "item-param-edit";
	}

}
