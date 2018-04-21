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
		// 1������һ��SolrServer����
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// 2������һ��SolrQuery����
		SolrQuery solrQuery = new SolrQuery();
		// 3���ο���̨���ø��ֲ�ѯ����
		solrQuery.set("q", "*:*");
		// 4��ִ�в�ѯ���õ���ѯ���
		QueryResponse query = solrServer.query(solrQuery);
		// 5��ȡ��ѯ������ܼ�¼��
		SolrDocumentList solrDocumentList = query.getResults();
		System.out.println("��ѯ�����" + solrDocumentList.getNumFound());
		// 6��ȡ��ѯ�������Ʒ�б�
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
		// 1������һ��SolrServer����
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// ����һ��query����
		SolrQuery query = new SolrQuery();
		// ���ò�ѯ����
		query.setQuery("��ʯ");
		// ��������
		query.setFilterQueries("product_catalog_name:��Ĭ�ӻ�");
		// ��������
		query.setSort("product_price", ORDER.asc);
		// ��ҳ����
		query.setStart(0);
		query.setRows(10);
		// ���������б�
		query.setFields("id", "product_name", "product_price", "product_catalog_name", "product_picture");
		// ����Ĭ��������
		query.set("df", "product_keywords");
		// ������ʾ
		query.setHighlight(true);
		// ������ʾ����
		query.addHighlightField("product_name");
		// ������ʾ��ǰ׺
		query.setHighlightSimplePre("<em>");
		// ������ʾ�ĺ�׺
		query.setHighlightSimplePost("</em>");
		// ִ�в�ѯ
		QueryResponse queryResponse = solrServer.query(query);
		// ȡ��ѯ���
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		// ����ѯ����Ʒ����
		System.out.println("����ѯ����Ʒ����:" + solrDocumentList.getNumFound());
		// ������ѯ�Ľ��
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			// ȡ������ʾ
			String productName = "";
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
			// �ж��Ƿ��и�������
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
