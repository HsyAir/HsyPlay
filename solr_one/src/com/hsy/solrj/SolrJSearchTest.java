package com.hsy.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrJSearchTest {

	@Test
	public void testSearch() throws Exception {
		// 1）创建一个SolrServer对象。
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// 2）创建一个SolrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		// 3）参考后台设置各种查询条件
		solrQuery.set("q", "*:*");
		// 4）执行查询，得到查询结果
		QueryResponse query = solrServer.query(solrQuery);
		// 5）取查询结果的总记录数
		SolrDocumentList solrDocumentList = query.getResults();
		System.out.println("查询结果：" + solrDocumentList.getNumFound());
		// 6）取查询结果的商品列表
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("product_name"));
			System.out.println(solrDocument.get("product_catalog_name"));
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_description"));
		}
	}

	@Test
	public void testSearchDif() throws Exception {
		// 1）创建一个SolrServer对象。
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// 创建一个query对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("钻石");
		// 过滤条件
		query.setFilterQueries("product_catalog_name:幽默杂货");
		// 排序条件
		query.setSort("product_price", ORDER.asc);
		// 分页处理
		query.setStart(0);
		query.setRows(10);
		// 结果中域的列表
		query.setFields("id", "product_name", "product_price", "product_catalog_name", "product_picture");
		// 设置默认搜索域
		query.set("df", "product_keywords");
		// 高亮显示
		query.setHighlight(true);
		// 高亮显示的域
		query.addHighlightField("product_name");
		// 高亮显示的前缀
		query.setHighlightSimplePre("<em>");
		// 高亮显示的后缀
		query.setHighlightSimplePost("</em>");
		// 执行查询
		QueryResponse queryResponse = solrServer.query(query);
		// 取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		// 共查询到商品数量
		System.out.println("共查询到商品数量:" + solrDocumentList.getNumFound());
		// 遍历查询的结果
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			// 取高亮显示
			String productName = "";
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
			// 判断是否有高亮内容
			if (null != list) {
				productName = list.get(0);
			} else {
				productName = (String) solrDocument.get("product_name");
			}

			System.out.println(productName);
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_catalog_name"));
			System.out.println(solrDocument.get("product_picture"));

		}
	}

}
