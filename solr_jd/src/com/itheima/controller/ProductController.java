package com.itheima.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itheima.pojo.ResultModel;
import com.itheima.service.ProductService;

@Controller
public class ProductController {
	private static final int ROWS = 60;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/list")
	public String search(String queryString,String catalog_name,String price,
			@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="0")int sort,Model model) throws Exception{
		//调用service
		ResultModel resultModel = productService.queryProduct(queryString, catalog_name, price, page, ROWS, sort);
		//把结果传递给jsp
		model.addAttribute("queryString",queryString);
		model.addAttribute("catalog_name",catalog_name);
		model.addAttribute("price",price);
		model.addAttribute("sort",sort);
		model.addAttribute("page",page);
		model.addAttribute("result",resultModel);
		
		return "product_list";
	}

}
