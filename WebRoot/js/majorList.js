$(function() {
	commodityListComp.init()
});
var commodityListComp = {
	basePath : null,
	onlineCommodImgPath : null,
	pageNo : 1,
	pageSize : 5,
	pageCount : 0,
	isTdAdded : false,
	isTdEventBinded : false,
	init : function() {
		this.basePath = basePath;
		this.onlineCommodImgPath = onlineCommodImgPath;
		//this.loadCommodityTypes();
		//this.loadCommodityFlags();
		this.loadShopList();
		this.bindEvent();
	},
	loadCommodityTypes : function() {
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/training/getDirListAjax.action",
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
					+ "/training/getFlagListAjax.action",
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
		$('#endTimeOrderBy').on("change", this.endTimeOrderByChange);
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
	endTimeOrderByChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	searchBtnClicked : function() {
		commodityListComp.pageNo = 1;
		$('#statusSelect').val("0");
		$('#typeSelect').val("-2");
		$('#flagSelect').val("-2");
		$('#timeOrderBy').val("0");
		$('#endTimeOrderBy').val("0");
		commodityListComp.loadShopList();
	},

	addModalSubmitBtnClicked : function() {
		var formData = new FormData($("#addModalForm")[0]);
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/bs/addTitleAjax.action",
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
					alert('添加选题成功.');
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
					+ "/bs/updateTitleAjax.action",
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
					alert('更新选题成功');
					window.location.reload();
				}
			},
		});
	},

	editCommodityClicked : function(id,name,bsDirId) {
		var modal = $('#editModal');
		$('#commodityId_editModal').val(id);
		$('#commodityName_editModal').val(name);
		/*$('#commoditySeq_editModal').val(beginTime);
		$('#commodityUrl_editModal').val(endTime);
		$('#commodityStatus_editModal').val(status);*/
		$('#commodityType_editModal').val(bsDirId);
	},
	delCommodityClicked : function(commodityId) {
		if (confirm("确定删除吗？") == false)
			return;
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/bs/deleteTitleAjax.action",
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

	loadShopList : function() {

		$
				.ajax({
					type : "POST",
					url : commodityListComp.basePath
							+ "/major/getMajorListAjax.action",
					data : {
						pageNo : commodityListComp.pageNo,
						pageSize : commodityListComp.pageSize,
						searchKey : $('#searchKey').val(),
						searchStartDate : $('#searchStartDate').val(),
						searchEndDate : $('#searchEndDate').val(),
						statusSelect : $('#statusSelect').val(),
						typeSelect : $('#typeSelect').val(),
						flagSelect : $('#flagSelect').val(),
						timeOrderBy : $('#timeOrderBy').val(),
						endTimeOrderBy : $('#endTimeOrderBy').val()
					},
					success : function(e) {
						var a = $.parseJSON(e);
						var c = a.resultObj;
						if (c && c.objList && c.objList.length > 0) {
							var b = "";
							var status = "";
							var answerOrUrl = "";
							$(c.objList)
									.each(
											function(i, obj) {
												b += '<tr class="orderTr"><td>'
														+ obj.name
														+ '</td>'
														+ '<td>'
														+ obj.parent.name
														+ '</td>'
														+ '<td>'
														+ obj.parent.parent.name
														+ '</td>'
														/*+ '<td>'
														+ obj.endTime
														+ '</td>'
														+ '<td id="status_'
														+ obj.id
														+ '">'
														+ status
														+ '</td>'*/
														/*+ '<td><a onclick="commodityListComp.editCommodityClicked(\''
														+ obj.id
														+ '\',\''
														+ obj.name
														+ '\''
														+ ',\''
														+ obj.bsDirId
														+ '\''
														+ ',\''
														+ obj.beginTime
														+ '\''
														+ ',\''
														+ obj.endTime
														+ '\''
														+ ',\''
														+ obj.status
														+ '\''
														+ ');" href="#editModal" data-toggle="modal">编辑</a>&nbsp;/&nbsp;'
														+ '<a onclick="commodityListComp.delCommodityClicked(\''
														+ obj.id
														+ '\');" href="javascript:void(0);">删除</a>'
														+ '</td>'*/
														+ '</tr>';
											});

							$("#commodityList").html(b);
							commodityListComp.buildPager(c.totalCount,
									commodityListComp.pageNo,
									commodityListComp.pageSize);
							gotoTop()

						} else {
							$("#commodityList")
									.html(
											"<tr><td colspan='4' style='text-align:center'>没有找到相关记录!</td></tr>");
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