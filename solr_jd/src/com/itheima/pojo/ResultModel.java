package com.itheima.pojo;

import java.util.List;

public class ResultModel {
	// ��Ʒ�б�
	private List<ProductModel> productList;
	// ��Ʒ����
	private Long recordCount;
	// ��ҳ��
	private int pageCount;
	// ��ǰҳ
	private int curPage;
	
	public List<ProductModel> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductModel> productList) {
		this.productList = productList;
	}
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
}