package com.sxsram.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sxsram.ssm.entity.BsTitle;
import com.sxsram.ssm.entity.BsTitleQueryVo;
import com.sxsram.ssm.entity.CollageMajor;
import com.sxsram.ssm.entity.CollageMajorQueryVo;
import com.sxsram.ssm.service.BsTitleService;
import com.sxsram.ssm.service.CollageMajorService;
import com.sxsram.ssm.util.JsonResult;
import com.sxsram.ssm.util.NullStringToEmptyAdapterFactory;
import com.sxsram.ssm.util.Pagination;
import com.sxsram.ssm.util.QueryCondition;
import com.sxsram.ssm.util.QueryConditionAbstractItem;
import com.sxsram.ssm.util.QueryConditionItem;
import com.sxsram.ssm.util.QueryConditionOp;
import com.sxsram.ssm.util.QueryConditionOrItems;
import com.sxsram.ssm.util.StringUtil;

@Controller()
@RequestMapping(value = "/major", method = { RequestMethod.GET, RequestMethod.POST })
public class MajorController {
	@Autowired
	private BsTitleService bsTitleService;
	@Autowired
	private CollageMajorService collageMajorService;

	@RequestMapping(value = "/majorManagement", method = { RequestMethod.GET, RequestMethod.POST })
	public String dirManagement() {
		return "major/majorManagement";
	}

	@RequestMapping(value = "/taskBookManagement", method = { RequestMethod.GET, RequestMethod.POST })
	public String classManagement() {
		return "bs/taskBookManagement";
	}

	class PageObj {
		Integer totalCount;
		Object objList;

		public PageObj() {
		}

		public PageObj(Integer totalCount, Object objList) {
			this.totalCount = totalCount;
			this.objList = objList;
		}
	}

	@RequestMapping(value = "/getMajorListAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getMajorListAjax(HttpSession session, Model model, Integer pageNo, Integer pageSize, String searchKey,
			String statusSelect, String typeSelect, Integer endTimeOrderBy, Integer timeOrderBy, String reqType,
			String parentId) {
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
				.setDateFormat("yyyy-MM-dd HH:mm").create();

		JsonResult jsonResult = new JsonResult("0", "0");

		List<CollageMajor> majors = null;
		Integer totalNum = 0;

		CollageMajorQueryVo collageMajorQueryVo = new CollageMajorQueryVo();
		List<QueryConditionAbstractItem> whereCondList = new ArrayList<QueryConditionAbstractItem>();
		whereCondList.add(new QueryConditionItem("major.delFlag", 0 + "", QueryConditionOp.EQ));
		if (!StringUtils.isEmpty(searchKey)) {
			QueryConditionOrItems queryConditionOrItems = new QueryConditionOrItems();
			queryConditionOrItems.getItems().add(new QueryConditionItem("major.name", searchKey, QueryConditionOp.LIKE));
			// queryConditionOrItems.getItems().add(new
			// QueryConditionItem("dep.name", searchKey,
			// QueryConditionOp.LIKE));
			// queryConditionOrItems.getItems().add(new
			// QueryConditionItem("col.name", searchKey,
			// QueryConditionOp.LIKE));
			whereCondList.add(queryConditionOrItems);
		}
		if (!StringUtils.isEmpty(reqType)) {
			if (reqType.equals("loadCollage")) {
				whereCondList.add(new QueryConditionItem("major.parentId", "", QueryConditionOp.ISNULL));
			} else if (reqType.equals("loadDep")) {
				whereCondList.add(new QueryConditionItem("major.parentId", parentId + "", QueryConditionOp.EQ));
			} else if (reqType.equals("loadMajor")) {
				whereCondList.add(new QueryConditionItem("major.parentId", parentId + "", QueryConditionOp.EQ));
			}
		}

		try {
			collageMajorQueryVo.setQueryCondition(new QueryCondition(whereCondList));
			totalNum = collageMajorService.findCollageMajors(collageMajorQueryVo).size();
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			return gson.toJson(jsonResult);
		}

		Map<String, String> orderByMap = new HashMap<String, String>();
		// if (orderAmountOrderBy != null) {
		// if (orderAmountOrderBy == 0) {
		// orderByMap.put("totalAmount", "desc");
		// } else {
		// orderByMap.put("totalAmount", "asc");
		// }
		// }
		//
		if (timeOrderBy != null) {
			if (timeOrderBy == 0) {
				orderByMap.put("lmtTime", "desc");
			} else {
				orderByMap.put("lmtTime", "asc");
			}
		}
		try {
			collageMajorQueryVo.setPagination(new Pagination(pageSize, pageNo, 0, orderByMap));
			majors = collageMajorService.findCollageMajors(collageMajorQueryVo);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			return gson.toJson(jsonResult);
		}

		if (totalNum == null)
			totalNum = 0;
		jsonResult.resultObj = new PageObj(totalNum, majors);
		return gson.toJson(jsonResult);
	}

	// @RequestMapping(value = "/getMajorListAjax", method = {
	// RequestMethod.GET, RequestMethod.POST })
	// @ResponseBody
	// public String getTitleListAjax(HttpSession session, Model model, Integer
	// pageNo, Integer pageSize, String searchKey,
	// String statusSelect, String typeSelect, Integer endTimeOrderBy, Integer
	// timeOrderBy, String reqType,
	// String parentId) {
	// Gson gson = new GsonBuilder().registerTypeAdapterFactory(new
	// NullStringToEmptyAdapterFactory())
	// .setDateFormat("yyyy-MM-dd HH:mm").create();
	//
	// JsonResult jsonResult = new JsonResult("0", "0");
	//
	// List<CollageMajor> majors = null;
	// Integer totalNum = 0;
	//
	// CollageMajorQueryVo collageMajorQueryVo = new CollageMajorQueryVo();
	// List<QueryConditionAbstractItem> whereCondList = new
	// ArrayList<QueryConditionAbstractItem>();
	// whereCondList.add(new QueryConditionItem("major.delFlag", 0 + "",
	// QueryConditionOp.EQ));
	// if (!StringUtils.isEmpty(searchKey)) {
	// QueryConditionOrItems queryConditionOrItems = new
	// QueryConditionOrItems();
	// queryConditionOrItems.getItems()
	// .add(new QueryConditionItem("major.name", searchKey,
	// QueryConditionOp.LIKE));
	// queryConditionOrItems.getItems().add(new QueryConditionItem("dep.name",
	// searchKey, QueryConditionOp.LIKE));
	// queryConditionOrItems.getItems().add(new QueryConditionItem("col.name",
	// searchKey, QueryConditionOp.LIKE));
	// whereCondList.add(queryConditionOrItems);
	// }
	// if (!StringUtils.isEmpty(reqType)) {
	// if (reqType.equals("loadCollage")) {
	// whereCondList.add(new QueryConditionItem("major.parentId", "",
	// QueryConditionOp.ISNULL));
	// } else if (reqType.equals("loadDep")) {
	// whereCondList.add(new QueryConditionItem("major.parentId", parentId + "",
	// QueryConditionOp.EQ));
	// }else if (reqType.equals("loadMajor")) {
	// whereCondList.add(new QueryConditionItem("major.parentId", parentId + "",
	// QueryConditionOp.EQ));
	// }
	// }
	//
	// if (!StringUtil.isEmpty(statusSelect) && !statusSelect.equals("-2")) {
	// whereCondList.add(new QueryConditionItem("status", statusSelect,
	// QueryConditionOp.EQ));
	// }
	//
	// if (!StringUtil.isEmpty(typeSelect) && !typeSelect.equals("-2")) {
	// whereCondList.add(new QueryConditionItem("t.bsDirId", typeSelect,
	// QueryConditionOp.EQ));
	// }
	// try {
	// collageMajorQueryVo.setQueryCondition(new QueryCondition(whereCondList));
	// totalNum =
	// collageMajorService.findCollageMajors(collageMajorQueryVo).size();
	// } catch (Exception e) {
	// e.printStackTrace();
	// jsonResult.logicCode = "-1";
	// jsonResult.resultMsg = e.getMessage();
	// return gson.toJson(jsonResult);
	// }
	//
	// Map<String, String> orderByMap = new HashMap<String, String>();
	// if (timeOrderBy != null) {
	// if (timeOrderBy == 0) {
	// orderByMap.put("major.lmtTime", "desc");
	// } else {
	// orderByMap.put("major.lmtTime", "asc");
	// }
	// }
	// try {
	// collageMajorQueryVo.setPagination(new Pagination(pageSize, pageNo, 0,
	// orderByMap));
	// majors = collageMajorService.findCollageMajors(collageMajorQueryVo);
	// } catch (Exception e) {
	// e.printStackTrace();
	// jsonResult.logicCode = "-1";
	// jsonResult.resultMsg = e.getMessage();
	// return gson.toJson(jsonResult);
	// }
	//
	// if (totalNum == null)
	// totalNum = 0;
	// jsonResult.resultObj = new PageObj(totalNum, majors);
	// return gson.toJson(jsonResult);
	// }

	@RequestMapping(value = "/updateTitleAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String updateQAEntityAjax(HttpSession session, Model model, String id, String name, String bsDirId,
			String taskBookId, String extra, String beginTime, String endTime, String delFlag) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (id == null) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到id，非法操作!";
			return gson.toJson(jsonResult);
		}

		BsTitle bsTitle = new BsTitle();
		bsTitle.setId(Integer.valueOf(id));
		if (!StringUtils.isEmpty(name))
			bsTitle.setName(name);
		if (!StringUtil.isEmpty(bsDirId))
			bsTitle.setBsDirId(Integer.valueOf(bsDirId));
		if (!StringUtil.isEmpty(taskBookId))
			bsTitle.setTaskBookId(Integer.valueOf(taskBookId));
		if (!StringUtil.isEmpty(extra))
			bsTitle.setExtra(extra);
		if (!StringUtil.isEmpty(delFlag)) {
			bsTitle.setDelFlag(Integer.valueOf(delFlag));
		}

		try {
			bsTitleService.updateBsTitle(bsTitle);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/deleteTitleAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String deleteQAEntityAjax(HttpSession session, Model model, String id) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		if (id == null) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "找不到id，非法操作!";
			return gson.toJson(jsonResult);
		}

		BsTitle bsTitle = new BsTitle();
		bsTitle.setId(Integer.valueOf(id));
		bsTitle.setDelFlag(1);
		try {
			bsTitleService.updateBsTitle(bsTitle);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}

	@RequestMapping(value = "/addTitleAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String addCommodityAjax(HttpSession session, Model model, String name, String bsDirId, String taskBookId,
			String extra, String delFlag) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		BsTitle bsTitle = new BsTitle();
		if (!StringUtils.isEmpty(name))
			bsTitle.setName(name);
		if (!StringUtil.isEmpty(bsDirId))
			bsTitle.setBsDirId(Integer.valueOf(bsDirId));
		if (!StringUtil.isEmpty(extra))
			bsTitle.setExtra(extra);
		if (!StringUtil.isEmpty(delFlag)) {
			bsTitle.setDelFlag(Integer.valueOf(delFlag));
		}

		try {
			bsTitleService.addNewBsTitle(bsTitle);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
		}
		return gson.toJson(jsonResult);
	}
}