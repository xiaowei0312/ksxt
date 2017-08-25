$(function() {
	commodityListComp.init()
});


function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

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
		// this.listHtml = '{for shop in objList}<li><a target="_blank" href="'
		// + this.basePath + '/mall/item/${shop.id}"><div
		// class="product-img"><img class="lazy" data-original="' +
		// this.mallPicImgPath + '${shop.mallMainPicUrl}" alt="${shop.mallName
		// }"/><span class="shopName">${shop.mallName
		// }</span></div></a><p>电话：${shop.mallPhone }</p><p
		// class="address">地址：${shop.mallAddress }</p></li>{/for}';
		this.listHtml = '{for obj in objList}<tr><td>${obj.orderNo}</td>'
				+ '<td>${obj.user.phone}</td><td>${obj.totalAmount}</td>'
				+ '<td>${obj.journalTime}</td>'
				+ '<td>${obj.recvCommodityAddress.contacts}</td><td>${obj.recvCommodityAddress.phone}</td>'
				+ '<td>${obj.recvCommodityAddress.province} ${obj.recvCommodityAddress.city} '
				+ '${obj.recvCommodityAddress.area} ${obj.recvCommodityAddress.extra}</td>'
				+ '<td>${obj.status}</td>' + '</tr>{/for}';
		this.loadCommodityTypes();
		this.loadCollage(); // 选择院校信息
		this.loadDbType(); // 选择院校信息
		this.loadShopList();
		this.bindEvent();
	},
	

	bindEvent : function() {
		$('#colSelect').on("change",{srcObj:'#colSelect',dstObj:'#depSelect',dstObj1:'#majorSelect'},this.loadDep);
		$('#depSelect').on("change",{srcObj:'#depSelect',dstObj:'#majorSelect'},this.loadMajor);
		
		$('#editModal #colSelect').on("change",{srcObj:'#editModal #colSelect',dstObj:'#editModal #depSelect',dstObj1:'#editModal #majorSelect'},this.loadDep);
		$('#editModal #depSelect').on("change",{srcObj:'#editModal #depSelect',dstObj:'#editModal #majorSelect'},this.loadMajor);
		
		$('#addModal #colSelect').on("change",{srcObj:'#addModal #colSelect',dstObj:'#addModal #depSelect',dstObj1:'#addModal #majorSelect'},this.loadDep);
		$('#addModal #depSelect').on("change",{srcObj:'#addModal #depSelect',dstObj:'#addModal #majorSelect'},this.loadMajor);
		
		$('#editModal #typeSelect').on("change",{srcObj:'#editModal #typeSelect',dstObj:'#editModal #bsCommitFormatDiv',dstObj1:'#editModal #dbTypeDiv'},this.taskTypeSelectChange);
		$('#addModal #typeSelect').on("change",{srcObj:'#addModal #typeSelect',dstObj:'#addModal #bsCommitFormatDiv',dstObj1:'#addModal #dbTypeDiv'},this.taskTypeSelectChange);
		
		$('#searchEndDate').on("blur", this.searchEndDateBlur);
		$('#statusSelect').on("change", this.statusSelectChange);
		$('#typeSelect').on("change", this.typeSelectChange);
		$('#timeOrderBy').on("change", this.timeOrderByChange);
		$('#searchBtn').on("click", this.searchBtnClicked);
		$('#colSelect').on("change",this.colSelectChange);
		$('#depSelect').on("change",this.depSelectChange);
		$('#majorSelect').on("change", this.majorSelectChange);
		$('#gradeSelect').on("change", this.gradeSelectChange);
		
		$('#addNewTaskBtn').on("click", this.addNewTaskBtnClicked);
		$('#editModal #submitBtn').on("click", this.editModalSubmitBtnClicked);
		$('#addModal #submitBtn').on("click", this.addModalSubmitBtnClicked);
		
		$('#editModal_model #editModalSubmitBtn').on("click",
				this.editModalSubmitBtnClicked_model);
		$('#addModal_model #addModalSubmitBtn').on("click",
				this.addModalSubmitBtnClicked_model);
		$('#commodityDetailModalSubmitBtn').on("click",this.commodityDetailModalSubmitBtnClicked);
	},
	
	downloadBtnClicked: function(stuTaskId){
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/task/downloadCommitFile.action",
			data : {
				stuTaskId: stuTaskId
				},
			success : function(e) {
				var a = $.parseJSON(e);
				var c = a.resultObj;
				if(c.logicCode != "0"){
					alert(c.resultMsg);
				}else{
					alert("下载成功");
				}
			}
		})
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
	
	loadDbType : function(){
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/dbType/getTypeListAjax.action",
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
					$('#editModal #bsDbTypeSelect').html("<option value='-2'>选择答辩类型</option>")
					$("#editModal #bsDbTypeSelect").append(b);
					
					$('#addModal #bsDbTypeSelect').html("<option value='-2'>选择答辩类型</option>")
					$("#addModal #bsDbTypeSelect").append(b);
				} else {
					alert(e.resultMsg);
				}
			}
		})
	},
	
	taskTypeSelectChange : function(event){
		var srcObj = event.data.srcObj;
		var dstObj = event.data.dstObj;
		var dstObj1 = event.data.dstObj1;
		
		var typeId = $(srcObj).val();
		if(typeId=="0"){
			$(dstObj).css('display','block');
			$(dstObj1).css('display','none');
		}
		else{
			$(dstObj).css('display','none');
			$(dstObj1).css('display','block');
		}
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
	loadCommodityTypes : function() {
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/task/getTaskListAjax.action",
			data : {},
			success : function(e) {
				var a = $.parseJSON(e);
				var c = a.resultObj;
				if (c && c.objList && c.objList.length > 0) {
					var b = "";
					$(c.objList).each(
							function(i, obj) {
								b += '<option value=' + obj.id + '>'
										+ obj.typeName + '</option>';
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

	commodityDetailModalSubmitBtnClicked : function(){
		// var formData = new FormData($("#updateDetailForm")[0]);
		for ( instance in CKEDITOR.instances ) 
			CKEDITOR.instances[instance].updateElement(); 
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/onlineCommodity/updateCommodityDetailAjax.action",
			data :{
				id:$('#commodityId_detail_modal').val(),
				editor1:escape($('#ckeditorTxArea').val()),
			},
// data:$('#updateDetailForm').serialize(),
			success : function(e) {
				var a = $.parseJSON(e);
				if (a.logicCode != '0') {
					alert(a.resultMsg);
				} else {
					alert('商品详情修改成功');
					window.location.reload();
				}
			},
		});
	},
	editCommodityDetailPageClicked : function(commodityId,detailContent) {
		// alert("editCommodityDetailPageClicked" + commodityId );
		// alert(commodityId);
		// alert(detailContent);
		$('#commodityId_detail_modal').val(commodityId);
		// $('#ckeditorTxArea')[0].innerHTML = detailContent;
		// CKEDITOR.replace('content',{width:600});
// alert(CKEDITOR.instance);
// CKEDITOR.instances.editor1.setData("test");
		var _editor = $("iframe")[0];
		if(_editor != undefined)
		{
			$(_editor).contents().find("body").html(unescape(detailContent));// 访问iframe中的body，并插入html
		}
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
	timeOrderByChange : function() {
		commodityListComp.pageNo = 1;
		commodityListComp.loadShopList();
	},
	searchBtnClicked : function() {
		commodityListComp.pageNo = 1;
		$('#statusSelect').val("0");
		$('#typeSelect').val("-2");
		$('#timeOrderBy').val("0");
		commodityListComp.loadShopList();
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

	addModalSubmitBtnClicked : function() {
		var formData = new FormData($("#addModal #modalForm")[0]);
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/task/addTaskAjax.action",
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
					alert('添加成功');
					window.location.reload();
				}
			},
		});
	},
	editModalSubmitBtnClicked : function() {
		if (confirm("确定要修改吗？") == false)
			return;
		var formData = new FormData($("#editModal #modalForm")[0]);

		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+ "/task/updateTaskAjax.action",
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
					alert('修改成功');
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


	editCommodityClicked : function(str) {
		var obj = $.parseJSON(unescape(str));

		$('#editModal #id').val(obj.id);
		$('#editModal #extra').val(obj.extra);
		$('#editModal #beginTime').val(obj.beginTime);
		$('#editModal #endTime').val(obj.endTime);
		$('#editModal #colSelect').val(obj.major.parent.parent.id);
		$('#editModal #colSelect').change();
		$('#editModal #depSelect').val(obj.major.parent.id);
		$('#editModal #depSelect').change();
		$('#editModal #majorSelect').val(obj.major.id);
		$('#editModal #gradeSelect').val(obj.gradeNo);
		$('#editModal #typeSelect').val(obj.type);
		$('#editModal #typeSelect').change();
		if($('#editModal #typeSelect').val() == 0){
			$('#editModal #bsCommitFormatId').val(obj.bsCommitFormat.id);
			$('#editModal #bsCommitFormat').val(obj.bsCommitFormat.format);
		}
		else
			$('#editModal #bsDbTypeSelect').val(obj.bsDbType.id);
		$('#editModal #statusSelect').val(obj.status);
	},
	delCommodityClicked : function(commodityId) {
		if (confirm("确定删除该任务吗？") == false)
			return;
		$.ajax({
			type : "POST",
			url : commodityListComp.basePath
					+"/task/deleteTaskAjax.action",
			data : {
				reqType: 2,
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
	addNewTaskBtnClicked : function() {
		$('#addModal #typeSelect').change();
		$('#addModal #beginTime').val(getNowFormatDate());
		$('#addModal #endTime').val(getNowFormatDate());
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
		if (this.isTdAdded == true) {
			var theadTr = $('#theadTr');
			var theadTrTd = $('#newAddedTh');
			theadTr[0].removeChild(theadTrTd[0]);
		}

		$
				.ajax({
					type : "POST",
					url : commodityListComp.basePath
							+ "/task/getTaskListAjax.action",
					data : {
						pageNo : commodityListComp.pageNo,
						pageSize : commodityListComp.pageSize,
						searchKey : $('#searchKey').val(),
						searchStartDate : $('#searchStartDate').val(),
						searchEndDate : $('#searchEndDate').val(),
						statusSelect : $('#statusSelect').val(),
						typeSelect : $('#typeSelect').val(),
						addTimeOrderBy : $('#timeOrderBy').val(),
						colSelect : $('#colSelect').val(),
						depSelect : $('#depSelect').val(),
						majorSelect : $('#majorSelect').val(),
						gradeSelect : $('#gradeSelect').val(),
					},
					success : function(e) {
						var a = $.parseJSON(e);
						var c = a.resultObj;
						if (c && c.objList && c.objList.length > 0) {
							// var b = commodityListComp.listHtml.process(c);
							var b = "";
							var status = "";
							$(c.objList)
									.each(
											function(i, obj) {
												b += '<tr class="orderTr">'
														+ '<td>'
														+ obj.id
														+ '</td>'
														/*+ '<td>'
														+ obj.addTime
														+ '</td>'*/
														+ '<td>'
														+ ((obj.type == 0)?"文件提交":"参加答辩")
														+ '</td>'
														/*+ '<td>'
														+ obj.extra
														+ '</td>'*/
														+ '<td>'
														+ '起：'+(obj.beginTime.substring(obj.beginTime.indexOf("-")+1)) + "<br/>止：" 
														+ (obj.endTime.substring(obj.endTime.indexOf("-")+1))
														+ '</td>'
														/*+ '<td>'
														+ obj.endTime
														+ '</td>'*/
														+ '<td>'
														+ obj.major.parent.parent.name + "&nbsp;/&nbsp;" 
														+ obj.major.parent.name + "&nbsp;/&nbsp;" 
														+ obj.major.name + "&nbsp;/&nbsp;"
														+ obj.gradeNo
														+ '</td>'
														+ '<td>'
														+ ((obj.type == 0)?(obj.bsCommitFormat.format):(obj.bsDbType.name))
														+ '</td>'
														+ '<td>'
														+ obj.statusPhase
														+ '</td>'
														+ '<td><a onclick="commodityListComp.editCommodityClicked(\''
														+ escape(JSON.stringify(obj))
														+ '\');" href="#editModal" data-toggle="modal">编辑</a>/'
														+ '<a onclick="commodityListComp.delCommodityClicked('
														+ obj.id
														+ ');" href="javascript:void(0);">删除</a>'
														+'</td>'
														+ '</tr>';
												//						
												b += '<tr class="commodityTr" style="display:none"><td class="details" colspan="10"><table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;width:100%">';

												var modelStatus = "";
												$(obj.stuTasks)
														.each(
																function(i,
																		item) {
																	b += '<tr><td style="width:15%">学生姓名：'
																			+ item.student.name
																			+ '</td>'
																			+ '<td style="width:30%">执行状态：'
																			+ item.statusPhase
																			+ '</td>';
																	
																	if(obj.type==0 && item.status>=0){
//																		b +='<td style="width:40%">'
//																			+ '<a onclick="commodityListComp.downloadBtnClicked('
//																			+ item.id
//																			+ ');" href="javascript:void(0);">下载</a></td>'
																		b +='<td style="width:40%">'
																			+ '<a href="' 
																			+ commodityListComp.basePath
																			+ '/task/downloadCommitFile.action?stuTaskId='
																			+ item.id
																			+ '">下载</a></td>'
																	}
																	
																	b += 	'</tr>';
																});
												b += '</table></td></tr>';
											});
							$("#commodityList").html(b);
							commodityListComp.buildPager(c.totalCount,
									commodityListComp.pageNo,
									commodityListComp.pageSize);
							gotoTop()

							var nCloneTh = document.createElement('th');
							var nCloneTd = document.createElement('td');
							$(nCloneTh).attr('width', '3%');
							$(nCloneTh).attr("id", "newAddedTh");
							nCloneTd.innerHTML = '<img class="imgClosed" src="images/details_open.png">';
							nCloneTd.className = "center newAddedTd";

							$('#hidden-table-info thead .orderTr').each(
									function() {
										this.insertBefore(nCloneTh,
												this.childNodes[0]);
									});

							$('#hidden-table-info tbody .orderTr').each(
									function() {
										this.insertBefore(nCloneTd
												.cloneNode(true),
												this.childNodes[0]);
									});
							commodityListComp.isTdAdded = true;

							if (commodityListComp.isTdEventBinded == true)
								return;
							$(document)
									.on(
											'click',
											'#hidden-table-info tbody .newAddedTd img',
											function() {
												var nTr = $(this).parents('tr')[0];
												if ($(this).attr('class') == 'imgClosed') {
													/*
													 * This row is already open -
													 * close it
													 */
													$(this).attr('class',
															'imgOpened');
													this.src = "images/details_close.png";
													$(nTr).next()[0].style.display = '';
													// alert($(nTr).siblings('tr')[0]);
												} else {
													/* Open this row */
													$(this).attr('class',
															'imgClosed');
													this.src = "images/details_open.png";
													$(nTr).next().css(
															"display", "none");
												}
											});
							commodityListComp.isTdEventBinded = true;
						} else {
							$("#commodityList")
									.html(
											"<tr><td colspan='10' style='text-align:center'>没有找到相关记录!</td></tr>");
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