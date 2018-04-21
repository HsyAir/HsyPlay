package com.itheima.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itheima.dao.ProductDao;
import com.itheima.pojo.ProductModel;
import com.itheima.pojo.ResultModel;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SolrServer solrServer;
	
	@Override
	public ResultModel queryProduct(SolrQuery query) throws Exception {
		//执行查询
		QueryResponse queryResponse = solrServer.query(query);
		//取查询结果的总记录数
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		long numFound = solrDocumentList.getNumFound();
		//取查询结果中的商品列表
		List<ProductModel> productList = new ArrayList<ProductModel>();
		//取高亮结果
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		for (SolrDocument solrDocument : solrDocumentList) {
			ProductModel productModel = new ProductModel();
			productModel.setCatalog_name((String)solrDocument.get("product_catalog_name"));
			String name="";
			//取高亮结果
			List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
			
			if(list != null && list.size() > 0){
				name=list.get(0);
			}else{
				name = (String)solrDocument.get("product_name");
			}
			
			productModel.setName(name);
			productModel.setPicture((String)solrDocument.get("product_picture"));
			productModel.setPid((String)solrDocument.get("id"));
			productModel.setPrice((float)solrDocument.get("product_price"));
			//添加到商品列表
			productList.add(productModel);
		}
		ResultModel resultModel = new ResultModel();
		//总记录数
		resultModel.setRecordCount(numFound);
		//商品列表添加到resultmodel中
		resultModel.setProductList(productList);
		
		return resultModel;
	}

}
