package com.itheima.service;

import com.itheima.pojo.ResultModel;

public interface ProductService {

	ResultModel queryProduct(String quertString,String catalog_name,
			String price,int page,int rows, int sort)throws Exception;
}
