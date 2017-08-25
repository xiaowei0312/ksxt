$(function() {
	commodityListComp.init()
});
var commodityListComp = {
	basePath : null,
	onlineCommodImgPath : null,
	listHtml : null,
	pageNo : 1,
	pageSize : 5,
	pageCount : 0,
	isTdAdded : false,
	isTdEventBinded : false,
	init : function() {
		this.basePath = basePath;
		this.onlineCommodImgPath = onlineCommodImgPath;
		this.listHtml = '{for obj in objList}<tr><td>${obj.orderNo}</td>'
				+ '<td>${obj.user.phone}</td><td>${obj.totalAmount}</td>'
				+ '<td>${obj.journalTime}</td>'
				+ '<td>${obj.recvCommodityAddress.contacts}</td><td>${obj.recvCommodityAddress.phone}</td>'
				+ '<td>${obj.recvCommodityAddress.province} ${obj.recvCommodityAddress.city} '
				+ '${obj.recvCommodityAddress.area} ${obj.recvCommodityAddress.extra}</td>'
				+ '<td>${obj.status}</td>' + '</tr>{/for}';
		this.loadCommodityTypes();
		this.loadCommodityFlags();
		this.loadShopList();
		this.bindEvent();
	},
	loadCommodityTypes : function() {
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/qa/getTypeListAjax.action",
			data : {},
			success : function(e) {
				var a = $.parseJSON(e);
				var c = a.resultObj;
				if (c && c.objList && c.objList.length > 0) {
					var b = "";
					$(c.objList).each(
							function(i, obj) {
								b += '<option value=' + obj.id + '>'
										+ obj.name + '</option>';
							});
					$("#typeSelect").append(b);
					$("#commodityType_editModal").append(b);
					$("#commodityType_addModal").append(b);
				} else {
					alert(e.resultMsg);
				}
			}
		})
	},

	loadCommodityFlags : function() {
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/qa/getFlagListAjax.action",
			data : {},
			success : function(e) {
				var a = $.parseJSON(e);
				var c = a.resultObj;
				if (c && c.objList && c.objList.length > 0) {
					var b = "";
					$(c.objList).each(
							function(i, obj) {
								b += '<option value=' + obj.id + '>'
										+ obj.name + '</option>';
							});
					$("#flagSelect").append(b);
					$("#commodityFlag_editModal").append(b);
					$("#commodityFlag_addModal").append(b);
				} else {
					alert(e.resultMsg);
				}
			}
		})
	},
	
	bindEvent : function() {
		$('#searchEndDate').on("blur", this.searchEndDateBlur);
		$('#statusSelect').on("change", this.statusSelectChange);
		$('#typeSelect').on("change", this.typeSelectChange);
		$('#flagSelect').on("change", this.flagSelectChange);
		$('#timeOrderBy').on("change", this.timeOrderByChange);
		$('#searchBtn').on("click", this.searchBtnClicked);
		$('#addNewCommodityBtn').on("click", this.addNewCommodityBtnClicked);
		$('#editModalSubmitBtn').on("click", this.editModalSubmitBtnClicked);
		$('#addModalSubmitBtn').on("click", this.addModalSubmitBtnClicked);
		$('#editModal_model #editModalSubmitBtn').on("click",
				this.editModalSubmitBtnClicked_model);
		$('#addModal_model #addModalSubmitBtn').on("click",
				this.addModalSubmitBtnClicked_model);
		$('#commodityDetailModalSubmitBtn').on("click",this.commodityDetailModalSubmitBtnClicked);
	},

	commodityDetailModalSubmitBtnClicked : function(){
		//var formData = new FormData($("#updateDetailForm")[0]);
		for ( instance in CKEDITOR.instances ) 
			CKEDITOR.instances[instance].updateElement(); 
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/qa/updateQAEntityDetailAjax.action",
			data :{
				id:$('#commodityId_detail_modal').val(),
				editor1:$('#ckeditorTxArea').val(),
			},
//			data:$('#updateDetailForm').serialize(),
			success : function(e) {
				var a = $.parseJSON(e);
				if (a.logicCode != '0') {
					alert(a.resultMsg);
				} else {
					alert('QA Detail Update Successfully.');
					window.location.reload();
				}
			},
		});
	},
	editCommodityDetailPageClicked : function(entityId,url) {
		// alert("editCommodityDetailPageClicked" + commodityId );
		//alert(commodityId);
		//alert(detailContent);
		$('#commodityId_detail_modal').val(entityId);
		//$('#ckeditorTxArea')[0].innerHTML = detailContent;
		//CKEDITOR.replace('content',{width:600});
//		alert(CKEDITOR.instance);
//		CKEDITOR.instances.editor1.setData("test");
		
		var _editor = $("iframe")[0];
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/qa/loadQAEntityUrlContent.action",
			data :{
				url:url
			},
			success : function(e) {
				var a = $.parseJSON(e);
				if (a.logicCode != '0') {
					alert(a.resultMsg);
					if(_editor != undefined){
						$(_editor).contents().find("body").html('');//访问iframe中的body，并插入html
					}
				} else {
					if(_editor != undefined){
						$(_editor).contents().find("body").html(a.resultMsg);//访问iframe中的body，并插入html
					}
					//window.location.reload();
				}
			},
		});
	},
	searchEndDateBlur : function() {
		var startDate = $('#searchStartDate').val();
		var endDate = $('#searchEndDate').val();
		if (startDate == "" || endDate == "") {
			return;
		}
		var arr = startDate.split('/');
		var startDateObj = new Date(arr[2], arr[0], arr[1]);
		arr = endDate.split('/');
		var endDateObj = new Date(arr[2], arr[0], arr[1]);
		if (startDateObj.getTime() > endDateObj.getTime()) {
			alert('结束日期不能早于起始日期');
			$('#searchEndDate').val("");
		}
	},
	statusSelectChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	typeSelectChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	flagSelectChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	timeOrderByChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	searchBtnClicked : function() {
		commodityListComp.pageNo = 1;
		$('#statusSelect').val("0");
		$('#typeSelect').val("-2");
		$('#flagSelect').val("-2");
		$('#timeOrderBy').val("0");
		commodityListComp.loadShopList();
	},

	addModalSubmitBtnClicked : function() {
		var formData = new FormData($("#addModalForm")[0]);
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/qa/addQAEntityAjax.action",
			data : formData,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			success : function(e) {
				var a = $.parseJSON(e);
				if (a.logicCode != '0') {
					alert(a.resultMsg);
				} else {
					alert('Add New QA Successful');
					window.location.reload();
				}
			},
		});
	},
	editModalSubmitBtnClicked : function() {
		if (confirm("确定要修改吗？") == false)
			return;
		var formData = new FormData($("#editModalForm")[0]);

		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/qa/updateQAEntityAjax.action",
			data : formData,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			success : function(e) {
				var a = $.parseJSON(e);
				if (a.logicCode != '0') {
					alert(a.resultMsg);
				} else {
					alert('QA Update Successful');
					window.location.reload();
				}
			},
		});
	},
	addModalSubmitBtnClicked_model : function() {
		var formData = new FormData($("#addModal_model #addModalForm")[0]);
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/onlineCommodity/addModelAjax.action",
			data : formData,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			success : function(e) {
				var a = $.parseJSON(e);
				if (a.logicCode != '0') {
					alert(a.resultMsg);
				} else {
					alert('商品添加成功');
					window.location.reload();
				}
			},
		});
	},
	editModalSubmitBtnClicked_model : function() {
		if (confirm("确定要修改吗？") == false)
			return;
		var formData = new FormData($("#editModal_model #editModalForm")[0]);

		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/onlineCommodity/updateModelAjax.action",
			data : formData,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			success : function(e) {
				var a = $.parseJSON(e);
				if (a.logicCode != '0') {
					alert(a.resultMsg);
				} else {
					alert('型号修改成功');
					window.location.reload();
				}
			},
		});
	},

	editCommodityClicked : function(id, question, answer,url,picUrl,typeId,flagId,
			status,extra) {
		var modal = $('#editModal');
		$('#commodityId_editModal').val(id);
		$('#commodityName_editModal').val(question);
		$('#commoditySeq_editModal').val(answer);
		$('#commodityUrl_editModal').val(url);
		//$('#commodityPicUrl_editModal').val(picUrl);
		$('#commodityStatus_editModal').val(status);
		$('#commodityExtra_editModal').val(extra);
		$('#commodityType_editModal').val(typeId);
		$('#commodityFlag_editModal').val(flagId);
		if (picUrl == "undefined" || picUrl == "")
			$('#picUrl_img_editModal').attr('src', "");
		else
			$('#picUrl_img_editModal').attr('src',
					commodityListComp.onlineCommodImgPath + picUrl);
	},
	delCommodityClicked : function(commodityId) {
		if (confirm("确定删除该商品吗？") == false)
			return;
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/qa/deleteQAEntityAjax.action",
			data : {
				id : commodityId,
			},
			success : function(e) {
				var a = $.parseJSON(e);
				if (a.logicCode == '-1') {
					alert(a.resultMsg);
				} else {
					alert("删除成功!");
					window.location.reload();
				}
			}
		});
	},

	addNewModelClicked : function(commodityId) {
		$('#addModal_model #hiddenId_editModal').val(commodityId);
	},
	addNewCommodityBtnClicked : function() {
		// alert("addNewCommodityBtnClicked");
	},

	editModelClicked : function(id, commodityModel, commodityPrice,
			commodityRepertory, commodityFlag, isDefaultModel,
			commoditySmallPic1, commoditySmallPic2, commoditySmallPic3,
			commoditySmallPic4, commoditySmallPic5) {
		$('#editModal_model #hiddenId_editModal').val(id);
		$('#editModal_model #commodityModel_editModal').val(commodityModel);
		$('#editModal_model #commodityPrice_editModal').val(commodityPrice);
		$('#editModal_model #commodityRepertory_editModal').val(
				commodityRepertory);
		$('#editModal_model #commodityStatus_editModal').val(commodityFlag);
		$('#editModal_model #commodityIsDefault_editModal').val(isDefaultModel);

		if ((commoditySmallPic1) == "undefined")
			$('#commodityPic_img_editModal').attr('src', "");
		else
			$('#editModal_model #modelPic_img1_editModal').attr('src',
					commodityListComp.onlineCommodImgPath + commoditySmallPic1);
		if ((commoditySmallPic2) == "undefined")
			$('#commodityPic_img_editModal').attr('src', "");
		else
			$('#editModal_model #modelPic_img2_editModal').attr('src',
					commodityListComp.onlineCommodImgPath + commoditySmallPic2);
		if ((commoditySmallPic3) == "undefined")
			$('#commodityPic_img_editModal').attr('src', "");
		else
			$('#editModal_model #modelPic_img3_editModal').attr('src',
					commodityListComp.onlineCommodImgPath + commoditySmallPic3);
		if ((commoditySmallPic4) == "undefined")
			$('#commodityPic_img_editModal').attr('src', "");
		else
			$('#editModal_model #modelPic_img4_editModal').attr('src',
					commodityListComp.onlineCommodImgPath + commoditySmallPic4);
		if ((commoditySmallPic5) == "undefined")
			$('#commodityPic_img_editModal').attr('src', "");
		else
			$('#editModal_model #modelPic_img5_editModal').attr('src',
					commodityListComp.onlineCommodImgPath + commoditySmallPic5);
	},
	delModelClicked : function(modelId) {
		// alert("delModelClicked" + modelId );
		if (confirm("确定删除该型号吗？") == false)
			return;
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/onlineCommodity/deleteModelAjax.action",
			data : {
				id : modelId,
			},
			success : function(e) {
				var a = $.parseJSON(e);
				if (a.logicCode == '-1') {
					alert(a.resultMsg);
				} else {
					alert("删除成功!");
					window.location.reload();
				}
			}
		});
	},

	loadShopList : function() {
//		if (this.isTdAdded == true) {
//			var theadTr = $('#theadTr');
//			var theadTrTd = $('#newAddedTh');
//			theadTr[0].removeChild(theadTrTd[0]);
//		}

		$
				.ajax({
					type : "POST",
					url : commodityListComp.basePath
							+ "/qa/getQAEntityListAjax.action",
					data : {
						pageNo : commodityListComp.pageNo,
						pageSize : commodityListComp.pageSize,
						searchKey : $('#searchKey').val(),
						searchStartDate : $('#searchStartDate').val(),
						searchEndDate : $('#searchEndDate').val(),
						statusSelect : $('#statusSelect').val(),
						typeSelect : $('#typeSelect').val(),
						flagSelect : $('#flagSelect').val(),
						timeOrderBy : $('#timeOrderBy').val()
					},
					success : function(e) {
						var a = $.parseJSON(e);
						var c = a.resultObj;
						if (c && c.objList && c.objList.length > 0) {
							// var b = commodityListComp.listHtml.process(c);
							var b = "";
							var status = "";
							var answerOrUrl = "";
							$(c.objList)
									.each(
											function(i, obj) {
												switch (obj.status) {
												case 0:
													status = 'Normal';
													break;
												case 1:
													status = 'Diabled';
													break;
												case 2:
													status = 'Deleted';
													break;
												}
												
												switch (obj.qaFlag.flagNo) {
												case 1:
													answerOrUrl = obj.answer;
													break;
												case 2:
													answerOrUrl = obj.url;
													break;
												}
												
												b += '<tr class="orderTr"><td>'
														+ obj.question
														+ '</td>'
														+ '<td>'
														+ answerOrUrl
														+ '</td>'
														+ '<td>'
														//+ (typeof obj.picUrl =='undefined'?'':obj.picUrl)
														+ ((typeof obj.picUrl == "undefined" || obj.picUrl=="") ? '暂无'
																: ('<img style="width:40px;height:40px" src="'
																		+ commodityListComp.onlineCommodImgPath
																		+ obj.picUrl + '"/>'))
														+ '</td>'
														+ '<td>'
														+ obj.qaType.name
														+ '</td>'
														+ '<td>'
														+ obj.qaFlag.name
														+ '</td>'
														/*+ '<td>'
														+ ((typeof obj.commodityMainPic == "undefined") ? '暂无图片'
																: ('<img style="width:40px;height:40px" src="'
																		+ commodityListComp.onlineCommodImgPath
																		+ obj.commodityMainPic + '"/>'))
														+ '</td>'*/
														/*	+ '<td>'
														+ obj.commodityDetailFileName
														+ '</td>'*/
														+ '<td id="status_'
														+ obj.id
														+ '">'
														+ status
														+ '</td>'
														/*+ '<td>'
														+ (typeof(obj.extra)?'':obj.extra)
														+ '</td>'*/
														+ '<td>'
														+ obj.lastModifyTime
														+ '</td>'
														+ '<td><a onclick="commodityListComp.editCommodityClicked(\''
														+ obj.id
														+ '\',\''
														+ obj.question
														+ '\''
														+ ',\''
														+ obj.answer
														+ '\''
														+ ',\''
														+ obj.url
														+ '\''
														+ ',\''
														+ obj.picUrl
														+ '\''
														+ ',\''
														+ obj.qaType.id
														+ '\''
														+ ',\''
														+ obj.qaFlag.id
														+ '\''
														+ ',\''
														+ obj.status
														+ '\''
														+ ',\''
														+ obj.extra
														+ '\''
														+ ');" href="#editModal" data-toggle="modal">Edit</a>&nbsp;/&nbsp;'
														+ '<a onclick="commodityListComp.delCommodityClicked(\''
														+ obj.id
														+ '\');" href="javascript:void(0);">Delete</a>&nbsp;/&nbsp;'
														+ '<a onclick="commodityListComp.editCommodityDetailPageClicked(\''
														+ obj.id + '\',\'' + obj.url + '\''
														+ ');" href="#commodityDetailModal" data-toggle="modal">Detail</a>'
														/*+ '<a onclick="commodityListComp.addNewModelClicked('
														+ obj.id
														+ ');" href="#addModal_model" data-toggle="modal">添加型号</a>' */
														+ '</td>'
														+ '</tr>';
												//						
												/*b += '<tr class="commodityTr" style="display:none"><td class="details" colspan="10"><table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;width:100%">';

												var modelStatus = "";
												$(obj.commodityModels)
														.each(
																function(i,
																		item) {
																	switch (item.commodityFlag) {
																	case 0:
																		modelStatus = '已上架';
																		break;
																	case 1:
																		modelStatus = '已下架';
																		break;
																	case 2:
																		modelStatus = '已删除';
																		break;
																	}
																	b += '<tr><td style="width:15%">型号：'
																			+ item.commodityModel
																			+ '</td>'
																			+ '<td style="width:10%">价格：'
																			+ item.commodityPrice
																			+ '</td>'
																			+ '<td style="width:10%">库存：'
																			+ item.commodityRepertory
																			+ '</td>'
																			+ '<td style="width:10%">缺省：'
																			+ (item.isDefaultModel ? "是"
																					: "否")
																			+ '</td>'
																			+
																			 '<td style="width:10%">数量：'+item.num+'</td>' + 
																			'<td style="width:10%">状态：'
																			+ modelStatus
																			+ '</td>'
																			+ '<td style="width:40%"><a onclick="commodityListComp.editModelClicked('
																			+ item.id
																			+ ',\''
																			+ item.commodityModel
																			+ '\''
																			+ ',\''
																			+ item.commodityPrice
																			+ '\''
																			+ ',\''
																			+ item.commodityRepertory
																			+ '\''
																			+ ',\''
																			+ item.commodityFlag
																			+ '\''
																			+ ',\''
																			+ item.isDefaultModel
																			+ '\''
																			+ ',\''
																			+ item.commoditySmallPic1
																			+ '\''
																			+ ',\''
																			+ item.commoditySmallPic2
																			+ '\''
																			+ ',\''
																			+ item.commoditySmallPic3
																			+ '\''
																			+ ',\''
																			+ item.commoditySmallPic4
																			+ '\''
																			+ ',\''
																			+ item.commoditySmallPic5
																			+ '\''
																			+ ');" href="#editModal_model" data-toggle="modal">编辑</a>&nbsp;&nbsp;'
																			+ '<a onclick="commodityListComp.delModelClicked('
																			+ item.id
																			+ ');" href="javascript:void(0);">删除</a></td>'
																			+ '</tr>';
																});
												b += '</table></td></tr>';*/
											});

							$("#commodityList").html(b);
							commodityListComp.buildPager(c.totalCount,
									commodityListComp.pageNo,
									commodityListComp.pageSize);
							gotoTop()

//							var nCloneTh = document.createElement('th');
//							var nCloneTd = document.createElement('td');
//							$(nCloneTh).attr('width', '3%');
//							$(nCloneTh).attr("id", "newAddedTh");
//							nCloneTd.innerHTML = '<img class="imgClosed" src="images/details_open.png">';
//							nCloneTd.className = "center newAddedTd";
//
//							$('#hidden-table-info thead .orderTr').each(
//									function() {
//										this.insertBefore(nCloneTh,
//												this.childNodes[0]);
//									});
//
//							$('#hidden-table-info tbody .orderTr').each(
//									function() {
//										this.insertBefore(nCloneTd
//												.cloneNode(true),
//												this.childNodes[0]);
//									});
//							commodityListComp.isTdAdded = true;
//
//							if (commodityListComp.isTdEventBinded == true)
//								return;
//							$(document)
//									.on(
//											'click',
//											'#hidden-table-info tbody .newAddedTd img',
//											function() {
//												var nTr = $(this).parents('tr')[0];
//												if ($(this).attr('class') == 'imgClosed') {
//													/*
//													 * This row is already open -
//													 * close it
//													 */
//													$(this).attr('class',
//															'imgOpened');
//													this.src = "images/details_close.png";
//													$(nTr).next()[0].style.display = '';
//													// alert($(nTr).siblings('tr')[0]);
//												} else {
//													/* Open this row */
//													$(this).attr('class',
//															'imgClosed');
//													this.src = "images/details_open.png";
//													$(nTr).next().css(
//															"display", "none");
//												}
//											});
//							commodityListComp.isTdEventBinded = true;
						} else {
							$("#commodityList")
									.html(
											"<tr><td colspan='9' style='text-align:center'>没有找到相关记录!</td></tr>");
							$(".pages").hide();
							$(".pager_div").hide();
							commodityListComp.isTdAdded = false;
						}
					}
				})
	},
	buildPager : function(c, b, a) {
		var d = c / a;
		var e = (c % a == 0) ? (d) : (parseInt(d) + 1);
		commodityListComp.pageCount = e;
		$("#pager").pager({
			pagenumber : b,
			pagecount : e,
			totalRecords : c,
			buttonClickCallback : commodityListComp.pageClick
		});
		$(".pages").show()
		$(".pager_div").show();
	},
	pageClick : function(a) {
		commodityListComp.pageNo = a;
		commodityListComp.loadShopList()
	},
	goToPage : function() {
		var a = "";
		var c = "^[0-9]*$";
		var b = new RegExp(c);
		a = $.trim($("#numberOfPages").val());
		if (a == "" || !a.match(b)) {
			alert("页数不能为空，且必须是数字，请重新输入。")
		} else {
			if (parseInt(a) <= 0) {
				alert("页数不能小于零，请重新输入。")
			} else {
				if (parseInt(a) > commodityListComp.pageCount) {
					alert("页数不能大于总页数，请重新输入。")
				} else {
					commodityListComp.pageNo = a;
					commodityListComp.loadShopList()
				}
			}
		}
	}
};