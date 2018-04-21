package com.itheima.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.dao.ProductDao;
import com.itheima.pojo.ResultModel;
import com.itheima.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Override
	public ResultModel queryProduct(String quertString, String catalog_name, String price, int page, int rows,
			int sort) throws Exception{
		//创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		//根据参数设置查询条件
		if(quertString != null && !"".equals(quertString)){
			query.setQuery(quertString);
		}else{
			query.setQuery("*:*");
		}
		//商品分类过滤
		if(catalog_name != null && !"".equals(catalog_name)){
			query.addFilterQuery("product_catalog_name:"+catalog_name);
		}
		//价格区间过滤
		if(price !=null&&!"".equals(price)){
			String[] strings = price.split("-");
			query.addFilterQuery("product_price:["+strings[0]+" TO "+strings[1]+" ]");
		}
		//分页条件
		query.setStart((page-1)*rows);
		query.setRows(rows);
		//排序条件 0：升序  1：降序
		if(sort != 1){
			query.setSort("product_price",ORDER.asc);
		}else{
			query.setSort("product_price",ORDER.desc);
		}
		//设置默认搜索域
		query.set("df", "product_keywords");
		//开启高亮显示
		query.setHighlight(true);
		query.addHighlightField("product_name");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		
		//调用dao查询
		ResultModel resultModel = productDao.queryProduct(query);
		
		//计算总页数
		Long recordCount = resultModel.getRecordCount();
		long pageCount = recordCount/rows;
		if(recordCount% rows>0){
			pageCount++;
		}
		resultModel.setPageCount((int)pageCount);
		resultModel.setCurPage(page);
		
		return resultModel;
	}

}
