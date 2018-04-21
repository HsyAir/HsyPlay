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
		//����һ��SolrQuery����
		SolrQuery query = new SolrQuery();
		//���ݲ������ò�ѯ����
		if(quertString != null && !"".equals(quertString)){
			query.setQuery(quertString);
		}else{
			query.setQuery("*:*");
		}
		//��Ʒ�������
		if(catalog_name != null && !"".equals(catalog_name)){
			query.addFilterQuery("product_catalog_name:"+catalog_name);
		}
		//�۸��������
		if(price !=null&&!"".equals(price)){
			String[] strings = price.split("-");
			query.addFilterQuery("product_price:["+strings[0]+" TO "+strings[1]+" ]");
		}
		//��ҳ����
		query.setStart((page-1)*rows);
		query.setRows(rows);
		//�������� 0������  1������
		if(sort != 1){
			query.setSort("product_price",ORDER.asc);
		}else{
			query.setSort("product_price",ORDER.desc);
		}
		//����Ĭ��������
		query.set("df", "product_keywords");
		//����������ʾ
		query.setHighlight(true);
		query.addHighlightField("product_name");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		
		//����dao��ѯ
		ResultModel resultModel = productDao.queryProduct(query);
		
		//������ҳ��
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
