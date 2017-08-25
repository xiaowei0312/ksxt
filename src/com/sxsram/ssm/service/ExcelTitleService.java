package com.sxsram.ssm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sxsram.ssm.dao.TitleDao;
import com.sxsram.ssm.entity.ExcelStudent;
import com.sxsram.ssm.entity.ExcelTitle;
import com.sxsram.ssm.util.QueryCondition;
import com.sxsram.ssm.util.QueryConditionAbstractItem;
import com.sxsram.ssm.util.QueryConditionItem;
import com.sxsram.ssm.util.QueryConditionOp;
import com.sxsram.ssm.util.QueryConditionOrItems;
import com.sxsram.ssm.util.StringUtil;

@Service("excelTitleService")
public class ExcelTitleService {
	@Autowired
	private TitleDao titleDao;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 3)
	public void batchAddTitle(List<List<Object>> listob) throws Exception {
		for (int i = 0; i < listob.size(); i++) {
			List<Object> lo = listob.get(i);
			if (String.valueOf(lo.get(0)).startsWith("##")) {
				continue;
			}

			// ExcelTitle vo = new
			// ExcelTitle(StringEscapeUtils.escapeJavaScript(String.valueOf(lo.get(5))),
			// StringEscapeUtils.escapeJavaScript(String.valueOf(lo.get(6))),
			// StringEscapeUtils.escapeJavaScript(String.valueOf(lo.get(0))),
			// StringEscapeUtils.escapeJavaScript(String.valueOf(lo.get(1))),
			// StringEscapeUtils.escapeJavaScript(String.valueOf(lo.get(4))),
			// StringEscapeUtils.escapeJavaScript(String.valueOf(lo.get(2))),
			// StringEscapeUtils.escapeJavaScript(String.valueOf(lo.get(3)))
			// );
			ExcelTitle vo = new ExcelTitle(StringEscapeUtils.escapeJava(String.valueOf(lo.get(6))),
					StringEscapeUtils.escapeJava(String.valueOf(lo.get(7))),
					StringEscapeUtils.escapeJava(String.valueOf(lo.get(0))),
					StringEscapeUtils.escapeJava(String.valueOf(lo.get(2))),
					StringEscapeUtils.escapeJava(String.valueOf(lo.get(5))),
					StringEscapeUtils.escapeJava(String.valueOf(lo.get(3))),
					StringEscapeUtils.escapeJava(String.valueOf(lo.get(4))),
					StringEscapeUtils.escapeJava(String.valueOf(lo.get(1))));
			titleDao.insert(vo, "testPaper");
		}
	}
}