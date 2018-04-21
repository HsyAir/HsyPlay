package com.itheima.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.itheima.pojo.ResultModel;

public interface ProductDao {

	public ResultModel queryProduct(SolrQuery query) throws Exception;
}
