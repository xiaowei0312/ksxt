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
		this.loadTrainingDir(); // 实训方向
		this.loadTrainingClass(); // 实训班级
		this.loadCollage(); // 选择院校信息
		this.loadShopList();
		this.bindEvent();
	},
	bindEvent : function() {
		$('#colSelect').on("change",{srcObj:'#colSelect',dstObj:'#depSelect',dstObj1:'#majorSelect'},this.loadDep);
		$('#depSelect').on("change",{srcObj:'#depSelect',dstObj:'#majorSelect'},this.loadMajor);
		
		$('#editModal #colSelect').on("change",{srcObj:'#editModal #colSelect',dstObj:'#editModal #depSelect',dstObj1:'#editModal #majorSelect'},this.loadDep);
		$('#editModal #depSelect').on("change",{srcObj:'#editModal #depSelect',dstObj:'#editModal #majorSelect'},this.loadMajor);
		
		$('#editModal #trainingDirSelect').on("change",{srcObj:'#editModal #trainingDirSelect',dstObj:'#editModal #trainingClassSelect'},this.loadTrainingClass1);
		$('#editModal #bsDirSelect').on("change",{srcObj:'#editModal #bsDirSelect',dstObj:'#editModal #bsTitleSelect'},this.loadBsTitle);
		
		$('#addModal #colSelect').on("change",{srcObj:'#addModal #colSelect',dstObj:'#addModal #depSelect',dstObj1:'#addModal #majorSelect'},this.loadDep);
		$('#addModal #depSelect').on("change",{srcObj:'#addModal #depSelect',dstObj:'#addModal #majorSelect'},this.loadMajor);
		
		$('#addModal #trainingDirSelect').on("change",{srcObj:'#addModal #trainingDirSelect',dstObj:'#addModal #trainingClassSelect'},this.loadTrainingClass1);
		$('#addModal #bsDirSelect').on("change",{srcObj:'#addModal #bsDirSelect',dstObj:'#addModal #bsTitleSelect'},this.loadBsTitle);
		
		
		$('#colSelect').on("change",this.colSelectChange);
		$('#depSelect').on("change",this.depSelectChange);
		$('#majorSelect').on("change", this.majorSelectChange);
		$('#gradeSelect').on("change", this.gradeSelectChange);
		$('#trainingClassSelect').on("change", this.trainingClassSelectChange);
		$('#trainingDirSelect').on("change", this.trainingDirSelectChange);
		$('#bsDirSelect').on("change", this.bsDirSelectChange);
		$('#addTimeOrderBy').on("change", this.addTimeOrderByChange);
		$('#searchBtn').on("click", this.searchBtnClicked);
		
		$('#addNewCommodityBtn').on("click", this.addNewCommodityBtnClicked);
		$('#editModal #submitBtn').on("click", this.editModalSubmitBtnClicked);
		$('#addModal #submitBtn').on("click", this.addModalSubmitBtnClicked);
		$('#importExcelModal #submitBtn').on("click", this.importExcelModalSubmitBtnClicked);
		$('#importExcelModal1 #submitBtn').on("click", this.importExcelModalSubmitBtnClicked1);
		$('#editModal_model #editModalSubmitBtn').on("click",
				this.editModalSubmitBtnClicked_model);
		$('#addModal_model #addModalSubmitBtn').on("click",
				this.addModalSubmitBtnClicked_model);
		$('#commodityDetailModalSubmitBtn').on("click",
				this.commodityDetailModalSubmitBtnClicked);
	},
	
	loadCollage : function(){
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/major/getMajorListAjax.action",
			data : {
				reqType:"loadCollage"
				},
			success : function(e) {
				var a = $.parseJSON(e);
				var c = a.resultObj;
				if (c && c.objList && c.objList.length > 0) {
					var b = "";
					$(c.objList).each(
							function(i, obj) {
								b += '<option value=' + obj.id + '>' + obj.name
										+ '</option>';
							});
					$('#colSelect').html("<option value='-2'>选择院校</option>")
					$("#colSelect").append(b);
					
					$('#editModal #colSelect').html("<option value='-2'>选择院校</option>")
					$("#editModal #colSelect").append(b);
					
					$('#addModal #colSelect').html("<option value='-2'>选择院校</option>")
					$("#addModal #colSelect").append(b);
					// $("#commodityType_addModal").append(b);
				} else {
					alert(e.resultMsg);
				}
			}
		})
	},
	loadDep : function(event){
		var srcObj = event.data.srcObj;
		var dstObj = event.data.dstObj;
		var dstObj1 = event.data.dstObj1;
		
		var colId = $(srcObj).val();
		if(colId == '-2'){
			$(dstObj).val("-2");
			$(dstObj).html("<option value='-2'>选择系别</option>")
			$(dstObj1).val("-2");
			$(dstObj1).html("<option value='-2'>选择系别</option>")
			return;
		}
		
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/major/getMajorListAjax.action",
			data : {
				reqType:"loadDep",
				parentId : colId
				},

			async: false,
			success : function(e) {
				var a = $.parseJSON(e);
				var c = a.resultObj;
				if (c && c.objList && c.objList.length > 0) {
					var b = "";
					$(c.objList).each(
							function(i, obj) {
								b += '<option value=' + obj.id + '>' + obj.name
										+ '</option>';
							});
					$(dstObj).html("<option value='-2'>选择系别</option>")
					$(dstObj).append(b);
					// $("#commodityType_addModal").append(b);
				} else {
					$(dstObj).val("-2");
					$(dstObj).html("<option value='-2'>选择系别</option>")
					$(dstObj1).val("-2");
					$(dstObj1).html("<option value='-2'>选择系别</option>")
				}
			}
		})
	},
	loadMajor : function(event){
		var srcObj = event.data.srcObj;
		var dstObj = event.data.dstObj;
		
		var depId = $(srcObj).val();
		if(depId == '-2'){
			$(dstObj).val("-2");
			$(dstObj).html("<option value='-2'>选择专业</option>")
			return;
		}
		var obj = event.data.obj;
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/major/getMajorListAjax.action",
			data : {
				reqType:"loadMajor",
				parentId : depId
				},

			async: false,
			success : function(e) {
				var a = $.parseJSON(e);
				var c = a.resultObj;
				if (c && c.objList && c.objList.length > 0) {
					var b = "";
					$(c.objList).each(
							function(i, obj) {
								b += '<option value=' + obj.id + '>' + obj.name
										+ '</option>';
							});
					$(dstObj).html("<option value='-2'>选择专业</option>")
					$(dstObj).append(b);
					// $("#commodityType_addModal").append(b);
				} else {
					$(dstObj).val("-2");
					$(dstObj).html("<option value='-2'>选择专业</option>")
				}
			}
		})
	},
	loadTrainingClass : function() {
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/training/getClassListAjax.action",
			data : {},

			async: false,
			success : function(e) {
				var a = $.parseJSON(e);
				var c = a.resultObj;
				if (c && c.objList && c.objList.length > 0) {
					var b = "";
					$(c.objList).each(
							function(i, obj) {
								b += '<option value=' + obj.id + '>' + obj.name
										+ '</option>';
							});
					$("#trainingClassSelect").append(b);
					//$("#bsDirSelect").append(b);
					// $("#commodityType_addModal").append(b);
				} else {
					alert(e.resultMsg);
				}
			}
		})
	},
	loadTrainingClass1 : function(event) {
		var srcObj = event.data.srcObj;
		var dstObj = event.data.dstObj;
		var trainingDirId = $(srcObj).val();
		if(trainingDirId == "-2"){
			$(dstObj).val("-2");
			$(dstObj).html("<option value='-2'>选择实训班级</option>")
			return;
		}
		
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/training/getClassListAjax.action",
			data : {
				typeSelect : trainingDirId
			},

			async: false,
			success : function(e) {
				var a = $.parseJSON(e);
				var c = a.resultObj;
				if (c && c.objList && c.objList.length > 0) {
					var b = "";
					$(c.objList).each(
							function(i, obj) {
								b += '<option value=' + obj.id + '>' + obj.name
										+ '</option>';
							});
					$(dstObj).html("<option value='-2'>选择实训班级</option>")
					$(dstObj).append(b);
					//$("#bsDirSelect").append(b);
					// $("#commodityType_addModal").append(b);
				} else {
					//alert(e.resultMsg);
					$(dstObj).val("-2");
					$(dstObj).html("<option value='-2'>选择实训班级</option>")
				}
			}
		})
	},
	loadBsTitle : function(event) {
		var srcObj = event.data.srcObj;
		var dstObj = event.data.dstObj;
		var trainingDirId = $(srcObj).val();
		if(trainingDirId == "-2"){
			$(dstObj).val("-2");
			$(dstObj).html("<option value='-2'>选择毕设选题</option>")
			return;
		}
		
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/bs/getTitleListAjax.action",
			data : {
				typeSelect : trainingDirId
			},
			async: false,
			success : function(e) {
				var a = $.parseJSON(e);
				var c = a.resultObj;
				if (c && c.objList && c.objList.length > 0) {
					var b = "";
					$(c.objList).each(
							function(i, obj) {
								b += '<option value=' + obj.id + '>' + obj.name
										+ '</option>';
							});
					$(dstObj).html("<option value='-2'>选择毕设选题</option>")
					$(dstObj).append(b);
					//$("#bsDirSelect").append(b);
					// $("#commodityType_addModal").append(b);
				} else {
					//alert(e.resultMsg);
					$(dstObj).val("-2");
					$(dstObj).html("<option value='-2'>选择毕设选题</option>")
				}
			}
		})
	},
	loadTrainingDir : function() {
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
								b += '<option value=' + obj.id + '>' + obj.name
										+ '</option>';
							});
					$("#trainingDirSelect").append(b);
					$("#bsDirSelect").append(b);
					$("#editModal #trainingDirSelect").append(b);
					$("#editModal #bsDirSelect").append(b);
					$("#addModal #trainingDirSelect").append(b);
					$("#addModal #bsDirSelect").append(b);
					// $("#commodityType_addModal").append(b);
				} else {
					alert(e.resultMsg);
				}
			}
		})
	},

	commodityDetailModalSubmitBtnClicked : function() {
		// var formData = new FormData($("#updateDetailForm")[0]);
		for (instance in CKEDITOR.instances)
			CKEDITOR.instances[instance].updateElement();
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/qa/updateQAEntityDetailAjax.action",
			data : {
				id : $('#commodityId_detail_modal').val(),
				editor1 : $('#ckeditorTxArea').val(),
			},
			// data:$('#updateDetailForm').serialize(),
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
	editCommodityDetailPageClicked : function(entityId, url) {
		// alert("editCommodityDetailPageClicked" + commodityId );
		// alert(commodityId);
		// alert(detailContent);
		$('#commodityId_detail_modal').val(entityId);
		// $('#ckeditorTxArea')[0].innerHTML = detailContent;
		// CKEDITOR.replace('content',{width:600});
		// alert(CKEDITOR.instance);
		// CKEDITOR.instances.editor1.setData("test");

		var _editor = $("iframe")[0];
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/qa/loadQAEntityUrlContent.action",
			data : {
				url : url
			},
			success : function(e) {
				var a = $.parseJSON(e);
				if (a.logicCode != '0') {
					alert(a.resultMsg);
					if (_editor != undefined) {
						$(_editor).contents().find("body").html('');// 访问iframe中的body，并插入html
					}
				} else {
					if (_editor != undefined) {
						$(_editor).contents().find("body").html(a.resultMsg);// 访问iframe中的body，并插入html
					}
					// window.location.reload();
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
	colSelectChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	depSelectChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	majorSelectChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	gradeSelectChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	trainingClassSelectChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	trainingDirSelectChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	bsDirSelectChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	addTimeOrderByChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	searchBtnClicked : function() {
		commodityListComp.pageNo = 1;
		$('#colSelect').val("-2");
		$('#depSelect').val("-2");
		$('#majorSelect').val("-2");
		$('#gradeSelect').val("-2");
		$('#trainingClassSelect').val("-2");
		$('#trainingDirSelect').val("-2");
		$('#bsDirSelect').val("-2");
		$('#addTimeOrderBy').val("0");
		commodityListComp.loadShopList();
	},

	importExcelModalSubmitBtnClicked : function() {
		var formData = new FormData($("#importExcelModal #modalForm")[0]);
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath + "/excel/importStusAjax.action",
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
					alert('导入成功.');
					window.location.reload();
				}
			},
		});
	},
	importExcelModalSubmitBtnClicked1 : function() {
		var formData = new FormData($("#importExcelModal1 #modalForm")[0]);
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath + "/excel/importTitlesAjax.action",
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
					alert('导入成功.');
					window.location.reload();
				}
			},
		});
	},
	addModalSubmitBtnClicked : function() {
		var formData = new FormData($("#addModalForm")[0]);
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath + "/student/addStuAjax.action",
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
					alert('添加学生成功.');
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
					+ "/student/updateStuAjax.action",
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
					alert('更新学生信息成功');
					window.location.reload();
				}
			},
		});
	},

//	editCommodityClicked : function(id, name, gender,idcard,stuNo,colId,depId,majorId,
//				gradeNo,classNo,trainingDirId,trainingClassId,bsDirId,bsTitleId,bsInnerTeacher,bsOuterTeacher) {
	editCommodityClicked : function(str) {
		var obj = $.parseJSON(unescape(str));
		//alert(strObj.name);
		//var modal = $('#editModal');
		$('#editModal #id').val(obj.id);
		$('#editModal #gender').val(obj.gender);
		$('#editModal #name').val(obj.name);
		$('#editModal #idcard').val(obj.idCard);
		$('#editModal #stuNo').val(obj.stuNo);
		$('#editModal #gradeSelect').val(obj.gradeNo);
		$('#editModal #classNo').val(obj.classNo);
		$('#editModal #colSelect').val(obj.major.parent.parent.id);
		$('#editModal #colSelect').change();
		$('#editModal #depSelect').val(obj.major.parent.id);
		$('#editModal #depSelect').change();
		$('#editModal #majorSelect').val(obj.major.id);
		$('#editModal #trainingDirSelect').val(obj.trainingClass.trainingDir.id);
		$('#editModal #trainingDirSelect').change();
		$('#editModal #trainingClassSelect').val(obj.trainingClass.id);
		$('#editModal #bsDirSelect').val(obj.bsTitle.bsDir.id);
		$('#editModal #bsDirSelect').change();
		$('#editModal #bsTitleSelect').val(obj.bsTitle.id);
		$('#editModal #bsInnerTeacher').val(obj.bsInnerTeacher);
		$('#editModal #bsOuterTeacher').val(obj.bsOuterTeacher);
	},
	delCommodityClicked : function(commodityId) {
		if (confirm("确定删除该学生吗？") == false)
			return;
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/student/deleteStuAjax.action",
			data : {
				id : commodityId,
				reqType: 2,
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
							+ "/student/getStuListAjax.action",
					data : {
						pageNo : commodityListComp.pageNo,
						pageSize : commodityListComp.pageSize,
						searchKey : $('#searchKey').val(),
						colSelect : $('#colSelect').val(),
						depSelect : $('#depSelect').val(),
						majorSelect : $('#majorSelect').val(),
						gradeSelect : $('#gradeSelect').val(),
						trainingClassSelect : $('#trainingClassSelect').val(),
						trainingDirSelect : $('#trainingDirSelect').val(),
						bsDirSelect : $('#bsDirSelect').val(),
						addTimeOrderBy : $('#addTimeOrderBy').val()
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
												//console.log(JSON.stringify(obj));
												b += '<tr class="orderTr"><td>'
														+ obj.name
														+ '</td>'
														+ '<td>'
														+ obj.stuNo
														+ '</td>'
														+ '<td>'
														+ obj.major.parent.parent.name	+ '&nbsp;/&nbsp;' + obj.major.parent.name + '&nbsp;/&nbsp;' + obj.major.name
														+ '&nbsp;/&nbsp;' + obj.gradeNo + '级&nbsp;/&nbsp;' + obj.classNo + '班'
														+ '</td>'
														+ '<td>'
														+ ((obj.trainingClass == null) ? '' : (obj.trainingClass.name))
														+ '</td>'
														+ '<td>'
														+ ((obj.bsTitle == null) ? '' : (obj.bsTitle.bsDir.name))
														+ '</td>'
														+ '<td>'
														+ ((obj.bsTitle == null) ? '' : (obj.bsTitle.name))
														+ '</td>'
														+ '<td>'
														+ obj.bsInnerTeacher + '&nbsp;/&nbsp;' + obj.bsOuterTeacher
														+ '</td>'
														+ '<td><a onclick="commodityListComp.editCommodityClicked(\''
														+ escape(JSON.stringify(obj))
														+ '\');" href="#editModal" data-toggle="modal">编辑</a>&nbsp;/&nbsp;'
														+ '<a onclick="commodityListComp.delCommodityClicked(\''
														+ obj.id
														+ '\');" href="javascript:void(0);">删除</a>'
														+ '</td>' + '</tr>';
											});

							$("#commodityList").html(b);
							commodityListComp.buildPager(c.totalCount,
									commodityListComp.pageNo,
									commodityListComp.pageSize);
							gotoTop()

						} else {
							$("#commodityList")
									.html(
											"<tr><td colspan='8' style='text-align:center'>没有找到相关记录!</td></tr>");
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