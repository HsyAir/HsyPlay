package com.hsy.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {

	@Test
	public void addDocument() throws Exception{
		// 1）创建一个SolrServer对象，使用HttpSolrServer
		//参数solr服务器的url
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// 2）创建一个SolrInputDocument对象
		SolrInputDocument document = new SolrInputDocument();
		// 3）向文档对象中添加域
		//文档中必须有id域，而且每个域必须先定义后使用
		document.addField("id", "a03");
		document.addField("title", "测试数据00001");
		document.addField("name", "新文档名称");
		// 4）把文档对象写入索引库
		solrServer.add(document);
		// 5）提交
		solrServer.commit();
	}
	
	@Test
	public void deleteDocumentById() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		solrServer.deleteById("a01");
		solrServer.commit();
		
	}
	@Test
	public void deleteDocumentByQuery() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		solrServer.deleteByQuery("id:2");
		solrServer.commit();
		
	}
	
	
	
}
