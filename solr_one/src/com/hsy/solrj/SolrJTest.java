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
		// 1������һ��SolrServer����ʹ��HttpSolrServer
		//����solr��������url
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// 2������һ��SolrInputDocument����
		SolrInputDocument document = new SolrInputDocument();
		// 3�����ĵ������������
		//�ĵ��б�����id�򣬶���ÿ��������ȶ����ʹ��
		document.addField("id", "a03");
		document.addField("title", "��������00001");
		document.addField("name", "���ĵ�����");
		// 4�����ĵ�����д��������
		solrServer.add(document);
		// 5���ύ
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
