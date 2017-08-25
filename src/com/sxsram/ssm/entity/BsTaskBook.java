package com.sxsram.ssm.entity;

public class BsTaskBook extends BaseEntity{
	// functionDesc text, -- 功能描述
	// refBook text, -- 参考文献
	private String functionDesc; // 功能描述
	private String refBook; // 参考文献

	public String getFunctionDesc() {
		return functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}

	public String getRefBook() {
		return refBook;
	}

	public void setRefBook(String refBook) {
		this.refBook = refBook;
	}
}
