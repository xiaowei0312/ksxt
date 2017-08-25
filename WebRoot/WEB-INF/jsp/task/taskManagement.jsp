<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String mallPicImgPath = "/mallImgs/";
	String onlineCommodImgPath = "/onlineCommodImgs/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="keywords"
	content="admin, dashboard, bootstrap, template, flat, modern, theme, responsive, fluid, retina, backend, html5, css, css3">
<meta name="description" content="">
<meta name="author" content="ThemeBucket">
<base href="<%=basePath%>"></base>

<link rel="shortcut icon" href="#" type="image/png">
<title>AdminX</title>

<!--icheck-->
<link href="js/iCheck/skins/minimal/minimal.css" rel="stylesheet">
<link href="js/iCheck/skins/square/square.css" rel="stylesheet">
<link href="js/iCheck/skins/square/red.css" rel="stylesheet">
<link href="js/iCheck/skins/square/blue.css" rel="stylesheet">

<!--pickers css-->
<link rel="stylesheet" type="text/css"
	href="js/bootstrap-datepicker/css/datepicker-custom.css" />
<link rel="stylesheet" type="text/css"
	href="js/bootstrap-timepicker/css/timepicker.css" />
<link rel="stylesheet" type="text/css"
	href="js/bootstrap-colorpicker/css/colorpicker.css" />
<link rel="stylesheet" type="text/css"
	href="js/bootstrap-daterangepicker/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css"
	href="js/bootstrap-datetimepicker/css/datetimepicker-custom.css" />


<!--dashboard calendar-->
<link href="css/clndr.css" rel="stylesheet">

<!--Morris Chart CSS -->
<link rel="stylesheet" href="js/morris-chart/morris.css">

<!--common-->
<link href="css/style.css" rel="stylesheet">
<link href="css/style-responsive.css" rel="stylesheet">
<link href="css/Pager.css" rel="stylesheet" type="text/css" />
<link href="css/doc2.css" rel="stylesheet">


<link href="js/advanced-datatable/css/demo_page.css" rel="stylesheet" />
<link href="js/advanced-datatable/css/demo_table.css" rel="stylesheet" />
<link rel="stylesheet" href="js/data-tables/DT_bootstrap.css" />
<link href="css/style-responsive.css" rel="stylesheet">

<style type="text/css">
.uploadFile_div {
	position: relative;
}

.uploadFile_div input {
	opacity: 0;
	filter: alpha(opacity = 0);
	height: 95px;
	width: 100px;
	position: absolute;
	top: 0;
	left: 0;
	z-index: 9;
}
</style>

</head>

<body class="sticky-header">
	<section>
		<!-- 左栏信息 -->
		<jsp:include page="/user/getUserOperations.action"></jsp:include>

		<!-- main content start-->
		<div class="main-content">
			<!-- 头部信息 -->
			<jsp:include page="/user/getLoginInfo.action"></jsp:include>

			<!-- page heading start-->
			<div class="page-heading">
				<h3>任务管理</h3>
				<ul class="breadcrumb">
					<li><a
						href="${pageContext.request.contextPath }/user/main.action">首页</a></li>
					<li><a href="#">任务管理</a></li>
					<li class="active">任务管理</li>
				</ul>
			</div>
			<!-- page heading end-->

			<!--body wrapper start-->
			<div class="wrapper">
				<div class="row">
					<div class="col-sm-12">
						<section class="panel">
							<header class="panel-heading">
								Dynamic Table <span class="tools pull-right"> <a
									href="javascript:;" class="fa fa-chevron-down"></a> <a
									href="javascript:;" class="fa fa-times"></a>
								</span>
							</header>
							<div class="panel-body">
								<div id="search-condition" class="search-condition row"
									style="margin-bottom: 5px;">
									<div class="col-md-6">
										<form class="form-inline" role="form">
											<div class="form-group col-md-4" style="padding-left: 0">
												<input type="text" class="form-control" id="searchKey"
													placeholder="编号/详情">
											</div>
											<div class="form-group col-md-6">
												<div class="input-group input-large custom-date-range"
													data-date="" data-date-format="yyyy/mm/dd">
													<input type="text" class="form-control dpd1"
														name="startDate" id="searchStartDate" placeholder="起始日期">
													<span class="input-group-addon">To</span> <input
														type="text" id="searchEndDate" class="form-control dpd2"
														name="to" placeholder="结束日期">
												</div>
											</div>
											<div class="form-group col-md-1">
												<button type="button" id="searchBtn" class="btn btn-primary">&nbsp;&nbsp;搜索&nbsp;&nbsp;</button>
											</div>
										</form>
									</div>
									<!-- <div class="col-md-1">
									</div> -->
									<div class="col-md-5" style="padding-left: 0">
										<div class="col-md-4" style="padding-left: 0">
											<select class="form-control" name="statusSelect"
												id="statusSelect">
												<option value="-2" checked="checked">全部状态</option>
												<option value="0">进行中</option>
												<option value="-1">已失效</option>
												<option value="-3">未激活</option>
											</select>
										</div>
										<div class="col-md-4">
											<select class="form-control" name="typeSelect"
												id="typeSelect">
												<option value="-2" checked="checked">全部类型</option>
												<option value="0">提交文件</option>
												<option value="1">参加答辩</option>
											</select>
										</div>
										<div class="col-md-4">
											<select class="form-control" name="addTimeOrderBy"
												id="timeOrderBy">
												<option value="0" checked="checked">开始时间降序</option>
												<option value="1">开始时间升序</option>
											</select>
										</div>
									</div>
									<div class="col-md-1" style="padding-left: 0; margin-left: 0">
										<a href="#addModal" data-toggle="modal"><button
												type="button" id="addNewTaskBtn" class="btn btn-primary">添加任务</button></a>
									</div>
								</div>
								<div id="search-condition" class="search-condition row"
									style="margin-bottom: 5px;">
									<div class="col-md-12" style="padding-left: 0">
										<div class="col-md-3">
											<select class="form-control" name="colSelect" id="colSelect">
												<option value="-2" checked="checked">所有院校</option>
											</select>
										</div>
										<div class="col-md-3">
											<select class="form-control" name="depSelect" id="depSelect">
												<option value="-2" checked="checked">所有系别</option>
											</select>
										</div>
										<div class="col-md-3">
											<select class="form-control" name="majorSelect"
												id="majorSelect">
												<option value="-2" checked="checked">所有专业</option>
											</select>
										</div>
										<div class="col-md-3">
											<select class="form-control" name="gradeSelect"
												id="gradeSelect">
												<option value="-2" checked="checked">所有级别</option>
												<option value="2013">2013级</option>
												<option value="2014">2014级</option>
												<option value="2015">2015级</option>
												<option value="2016">2016级</option>
												<option value="2017">2017级</option>
												<option value="2018">2018级</option>
												<option value="2019">2019级</option>
												<option value="2020">2020级</option>
												<option value="2021">2021级</option>
											</select>
										</div>
									</div>
									<br />
									<br />
									<div class="adv-table">
										<table class="display table table-bordered"
											id="hidden-table-info">
											<thead>
												<tr class="orderTr" id="theadTr">
													<!-- <th style="width: 5%">编号</th>
												<th style="width: 8%">类型</th>
												<th style="width: 10%">任务概述</th>
												<th style="width: 10%">开始时间</th>
												<th style="width: 10%">结束时间</th>
												<th style="width:5%">发布人</th>
												<th style="width: 30%">对象</th>
												<th style="width: 20%">详情</th>
												<th style="width: 10%">状态</th>
												<th style="width: 15%">操作</th> -->
													<th>编号</th>
													<!-- <th>添加时间</th> -->
													<th>任务类型</th>
													<!-- <th>任务概述</th> -->
													<th>起止时间</th>
													<!-- <th>结束时间</th> -->
													<!-- <th>发布人</th> -->
													<th>任务对象</th>
													<th>任务详情</th>
													<th>任务状态</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody id="commodityList">
											</tbody>
										</table>
									</div>
									<div id="pager_div" class="pager_div">
										<div id="pager"></div>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 到第<input type="text"
											class="text-box" id="numberOfPages"> 页 <input
											type="button" value="GO" class="sub-btn"
											onclick="javascript:orderListComp.goToPage();">
									</div>
								</div>
						</section>
						<section class="model">
							<!-- 修改商品类别 模态框 -->
							<div aria-hidden="true" aria-labelledby="myModalLabel"
								role="dialog" tabindex="-1" id="editModal" class="modal fade">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-body">
											<section class="panel">
												<header class="panel-heading"> 修改任务信息 </header>
												<div class="panel-body">
													<form class="form-horizontal" role="form" id="modalForm">
														<div class="form-group">
															<label for="commodityName_editModal"
																class="col-lg-2 col-sm-2 control-label">任务摘要</label>
															<div class="col-lg-10">
																<input type="hidden" class="form-control" name="id"
																	id="id" placeholder="商品ID"> <input type="text"
																	class="form-control" name="extra" id="extra"
																	placeholder="10个字以内描述任务信息">
																<p class="help-block">10个字以内，描述该任务的基本信息，任务摘要会显示在学生界面</p>
															</div>
														</div>
														<div class="form-group">
															<label class="control-label col-md-2">任务起止时间</label>
															<div class="col-md-10">
																<div class="input-group input-large custom-date-range"
																	data-date="13/07/2013" data-date-format="mm/dd/yyyy">
																	<input size="14" type="text" value="2012-06-15 14:45"
																		readonly class="form_datetime form-control"
																		name="beginTimeStr" id="beginTime"> <span
																		class="input-group-addon">To</span> <input size="14"
																		type="text" value="2012-06-15 14:45" readonly
																		class="form_datetime form-control" name="endTimeStr"
																		id="endTime">
																</div>
																<span class="help-block">请选择任务起止时间</span>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityStatus_editModal"
																class="col-lg-2 col-sm-2 control-label">任务对象</label>
															<div class="col-lg-10 col-sm-10">
																<div class="col-lg-4" style="padding-left: 0px">
																	<select name="major.parent.parent.id"
																		class="form-control" id="colSelect"
																		disabled="disabled">
																		<option value="-2">院校</option>
																	</select>
																</div>
																<div class="col-lg-3" style="padding-left: 0px">
																	<select name="major.parent.id" class="form-control"
																		id="depSelect" disabled="disabled">
																		<option value="-2">系别</option>
																	</select>
																</div>
																<div class="col-lg-3" style="padding-left: 0px">
																	<select name="major.id" class="form-control"
																		id="majorSelect" disabled="disabled">
																		<option value="-2">专业</option>
																	</select>
																</div>
																<div class="col-lg-2" style="padding-left: 0px">
																	<select name="gradeNo" id="gradeSelect"
																		class="form-control" disabled="disabled">
																		<option value="2013">2013级</option>
																		<option value="2014">2014级</option>
																		<option value="2015">2015级</option>
																		<option value="2016">2016级</option>
																		<option value="2017">2017级</option>
																		<option value="2018">2018级</option>
																		<option value="2019">2019级</option>
																		<option value="2020">2020级</option>
																		<option value="2021">2021级</option>
																	</select>
																</div>
																<p class="help-block">任务对象不能修改，如需修改，建议将该任务删除，重新添加任务</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityStatus_editModal"
																class="col-lg-2 col-sm-2 control-label">任务类型</label>
															<div class="col-lg-10">
																<select name="type" id="typeSelect" class="form-control">
																	<option value="0" checked="checked">【提交文件】任务</option>
																	<option value="1">【参加答辩】任务</option>
																</select>
																<p class="help-block">请选择任务类型</p>
															</div>
														</div>
														<div class="form-group" style="display: none"
															id="bsCommitFormatDiv">
															<label for="commodityType_editModal"
																class="col-lg-2 col-sm-2 control-label">文件提交格式</label>
															<div class="col-lg-10">
																<input type="hidden" class="form-control"
																	name="bsCommitFormat.id" id="bsCommitFormatId">
																<input type="text" class="form-control"
																	name="bsCommitFormat.format" id="bsCommitFormat"
																	placeholder="文件提交格式">
																<p class="help-block">支持的格式如下:
																	key1_key2_key3.suffix1|suffix2|suffix3
																	姓名,学号，身份证号，年级，院校，系别，专业，实训班级，实训方向，毕设方向，毕设选题，校内指导老师，校外指导老师
																</p>
															</div>
														</div>
														<div class="form-group" style="display: none"
															id="dbTypeDiv">
															<label for="commodityType_editModal"
																class="col-lg-2 col-sm-2 control-label">答辩类型</label>
															<div class="col-lg-10">
																<select name="bsDbType.id" id="bsDbTypeSelect"
																	class="form-control">
																	<option value="-2" checked="checked">选择答辩类型</option>
																</select>
																<p class="help-block">请选择答辩类型</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityPic_editModal"
																class="col-lg-2 col-sm-2 control-label">任务状态</label>
															<div class="col-lg-10">
																<select name="status" id="statusSelect"
																	class="form-control">
																	<option value="-3" checked="checked">未激活</option>
																	<option value="0">进行中</option>
																	<option value="-1">已失效</option>
																</select>
																<p class="help-block">请选择任务状态</p>
															</div>
														</div>
														<div class="form-group">
															<div class="col-lg-offset-2 col-lg-2">
																<button type="button" id="submitBtn"
																	class="btn btn-primary">确定</button>
															</div>
															<div class="col-lg-8">
																<button type="button" class="btn btn-default"
																	data-dismiss="modal"">关闭</button>
															</div>
														</div>
													</form>
												</div>
											</section>
										</div>
									</div>
								</div>
							</div>
							<!-- 添加商品类别 模态框 -->
							<div aria-hidden="true" aria-labelledby="myModalLabel"
								role="dialog" tabindex="-1" id="addModal" class="modal fade">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-body">
											<section class="panel">
												<header class="panel-heading"> 新增任务信息 </header>
												<div class="panel-body">
													<form class="form-horizontal" role="form" id="modalForm">
														<div class="form-group">
															<label for="commodityName_editModal"
																class="col-lg-2 col-sm-2 control-label">任务摘要</label>
															<div class="col-lg-10">
																<input type="hidden" class="form-control" name="id"
																	id="id" placeholder="商品ID"> <input type="text"
																	class="form-control" name="extra" id="extra"
																	placeholder="10个字以内描述任务信息">
																<p class="help-block">10个字以内，描述该任务的基本信息，任务摘要会显示在学生界面</p>
															</div>
														</div>
														<div class="form-group">
															<label class="control-label col-md-2">任务起止时间</label>
															<div class="col-md-10">
																<div class="input-group input-large custom-date-range"
																	data-date="13/07/2013" data-date-format="mm/dd/yyyy">
																	<input size="14" type="text" value="2012-06-15 14:45"
																		readonly class="form_datetime form-control"
																		name="beginTimeStr" id="beginTime"> <span
																		class="input-group-addon">To</span> <input size="14"
																		type="text" value="2012-06-15 14:45" readonly
																		class="form_datetime form-control" name="endTimeStr"
																		id="endTime">
																</div>
																<span class="help-block">请选择任务起止时间</span>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityStatus_editModal"
																class="col-lg-2 col-sm-2 control-label">任务对象</label>
															<div class="col-lg-10 col-sm-10">
																<div class="col-lg-4" style="padding-left: 0px">
																	<select name="major.parent.parent.id"
																		class="form-control" id="colSelect">
																		<option value="-2">院校</option>
																	</select>
																</div>
																<div class="col-lg-3" style="padding-left: 0px">
																	<select name="major.parent.id" class="form-control"
																		id="depSelect">
																		<option value="-2">系别</option>
																	</select>
																</div>
																<div class="col-lg-3" style="padding-left: 0px">
																	<select name="major.id" class="form-control"
																		id="majorSelect">
																		<option value="-2">专业</option>
																	</select>
																</div>
																<div class="col-lg-2" style="padding-left: 0px">
																	<select name="gradeNo" id="gradeSelect"
																		class="form-control">
																		<option value="2013">2013级</option>
																		<option value="2014">2014级</option>
																		<option value="2015">2015级</option>
																		<option value="2016">2016级</option>
																		<option value="2017">2017级</option>
																		<option value="2018">2018级</option>
																		<option value="2019">2019级</option>
																		<option value="2020">2020级</option>
																		<option value="2021">2021级</option>
																	</select>
																</div>
																<p class="help-block">请选择任务对象</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityStatus_editModal"
																class="col-lg-2 col-sm-2 control-label">任务类型</label>
															<div class="col-lg-10">
																<select name="type" id="typeSelect" class="form-control">
																	<option value="0" checked="checked">【提交文件】任务</option>
																	<option value="1">【参加答辩】任务</option>
																</select>
																<p class="help-block">请选择任务类型</p>
															</div>
														</div>
														<div class="form-group" style="display: none"
															id="bsCommitFormatDiv">
															<label for="commodityType_editModal"
																class="col-lg-2 col-sm-2 control-label">文件提交格式</label>
															<div class="col-lg-10">
																<input type="text" class="form-control"
																	name="bsCommitFormat.format" id="bsCommitFormat"
																	placeholder="文件提交格式">
																<p class="help-block">支持的格式如下:
																	key1_key2_key3.suffix1|suffix2|suffix3
																	姓名,学号，身份证号，年级，院校，系别，专业，实训班级，实训方向，毕设方向，毕设选题，校内指导老师，校外指导老师
																</p>
															</div>
														</div>
														<div class="form-group" style="display: none"
															id="dbTypeDiv">
															<label for="commodityType_editModal"
																class="col-lg-2 col-sm-2 control-label">答辩类型</label>
															<div class="col-lg-10">
																<select name="bsDbType.id" id="bsDbTypeSelect"
																	class="form-control">
																	<option value="-2" checked="checked">选择答辩类型</option>
																</select>
																<p class="help-block">请选择答辩类型</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityPic_editModal"
																class="col-lg-2 col-sm-2 control-label">任务状态</label>
															<div class="col-lg-10">
																<select name="status" id="statusSelect"
																	class="form-control">
																	<option value="-3" checked="checked">未激活</option>
																	<option value="0">进行中</option>
																	<option value="-1">已失效</option>
																</select>
																<p class="help-block">请选择任务状态</p>
															</div>
														</div>
														<div class="form-group">
															<div class="col-lg-offset-2 col-lg-2">
																<button type="button" id="submitBtn"
																	class="btn btn-primary">确定</button>
															</div>
															<div class="col-lg-8">
																<button type="button" class="btn btn-default"
																	data-dismiss="modal"">关闭</button>
															</div>
														</div>
													</form>
												</div>
											</section>
										</div>
									</div>
								</div>
							</div>
							<!-- 修改商品模型类别 模态框 -->
							<div aria-hidden="true" aria-labelledby="myModalLabel"
								role="dialog" tabindex="-1" id="editModal_model"
								class="modal fade">
								<div class="modal-dialog">
									<div class="modal-content">
										<!-- <div class="modal-header">
											<button aria-hidden="true" data-dismiss="modal"
												class="close" type="button">×</button>
											<h4 class="modal-title">Update the Commodity Type</h4>
										</div> -->
										<div class="modal-body">
											<section class="panel">
												<header class="panel-heading"> 修改商品型号信息 </header>
												<div class="panel-body">
													<form class="form-horizontal" role="form"
														id="editModalForm">
														<!-- <div class="form-group">
															<label for="commodityId_editModal"
																class="col-lg-2 col-sm-2 control-label">商品ID</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="commodityId_editModal"
																	id="commodityId_editModal" placeholder="商品ID" readonly="readonly">
																<p class="help-block">商品ID是商品唯一标识，有数据库自动生成，用户无法修改.</p>
															</div>
														</div> -->
														<div class="form-group">
															<label for="commodityModel_editModal"
																class="col-lg-2 col-sm-2 control-label">商品名称</label>
															<div class="col-lg-10">
																<input type="hidden" class="form-control" name="id"
																	id="hiddenId_editModal" placeholder="型号ID"> <input
																	type="text" class="form-control" name="commodityModel"
																	id="commodityModel_editModal" placeholder="商品型号">
																<p class="help-block">商品型号会显示在前台网站上.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityPrice_editModal"
																class="col-lg-2 col-sm-2 control-label">价格</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="price"
																	id="commodityPrice_editModal" placeholder="价格">
																<p class="help-block">该型号对应的价格（￥）.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityRepertory_editModal"
																class="col-lg-2 col-sm-2 control-label">库存量</label>
															<div class="col-lg-10">
																<input type="text" class="form-control"
																	name="commodityRepertory"
																	id="commodityRepertory_editModal" placeholder="库存量">
																<p class="help-block">现有库存数量.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityStatus_editModal"
																class="col-lg-2 col-sm-2 control-label">商品状态</label>
															<div class="col-lg-10">
																<select name="commodityFlag"
																	id="commodityStatus_editModal">
																	<option value="-2">--- 修改状态 --</option>
																	<option value="0">已上架</option>
																	<option value="1">已下架</option>
																	<!-- <option value="2">已删除</option> -->
																</select>
																<p class="help-block">请选择商品状态.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityType_editModal"
																class="col-lg-2 col-sm-2 control-label">缺省显示</label>
															<div class="col-lg-10">
																<select name="isDefaultModel"
																	id="commodityIsDefault_editModal">
																	<option value="1" checked="checked">缺省显示</option>
																	<option value="0">不缺省显示</option>
																</select>
																<p class="help-block">是否缺省显示该商品.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityPic_editModal"
																class="col-lg-2 col-sm-2 control-label">商品图片</label>
															<div class="col-lg-10 uploadFile_div">
																<img alt="" src="" style="width: 50px; height: 50px"
																	id="modelPic_img1_editModal"
																	onclick="imgClicked_edit('modelPic1_editModal')" /> <img
																	alt="" src="" style="width: 50px; height: 50px"
																	id="modelPic_img2_editModal"
																	onclick="imgClicked_edit('modelPic2_editModal')" /> <img
																	alt="" src="" style="width: 50px; height: 50px"
																	id="modelPic_img3_editModal"
																	onclick="imgClicked_edit('modelPic3_editModal')" /> <img
																	alt="" src="" style="width: 50px; height: 50px"
																	id="modelPic_img4_editModal"
																	onclick="imgClicked_edit('modelPic4_editModal')" /> <img
																	alt="" src="" style="width: 50px; height: 50px"
																	id="modelPic_img5_editModal"
																	onclick="imgClicked_edit('modelPic5_editModal')" />
																<script type="text/javascript">
																	function imgClicked_edit(id)
																	{
																		$('#editModal_model' + ' ' + '#'+id).click();
																	}
																	function fileSelected_edit(o,id){
																		var val = o.value;
																		$('#editModal_model' + ' ' + '#'+id).attr('src',"");
																		$('#editModal_model' + ' ' + '#'+id).attr('alt',val.split("\\")[val.split("\\").length-1]);
																	}
																</script>
															</div>
															<div class="col-lg-10">
																<input type="file" style="display: none"
																	id="modelPic1_editModal" name="img0"
																	onchange="fileSelected_edit(this,'modelPic_img1_editModal')">
																<input type="file" style="display: none"
																	id="modelPic2_editModal" name="img1"
																	onchange="fileSelected_edit(this,'modelPic_img2_editModal')">
																<input type="file" style="display: none"
																	id="modelPic3_editModal" name="img2"
																	onchange="fileSelected_edit(this,'modelPic_img3_editModal')">
																<input type="file" style="display: none"
																	id="modelPic4_editModal" name="img3"
																	onchange="fileSelected_edit(this,'modelPic_img4_editModal')">
																<input type="file" style="display: none"
																	id="modelPic5_editModal" name="img4"
																	onchange="fileSelected_edit(this,'modelPic_img5_editModal')">
															</div>
														</div>
														<!-- <div class="form-group">
															<div class="col-lg-offset-2 col-lg-10">
																<div class="checkbox">
																	<label> <input type="checkbox">
																		Remember me
																	</label>
																</div>
															</div>
														</div>-->
														<div class="form-group">
															<div class="col-lg-offset-2 col-lg-2">
																<button type="button" id="editModalSubmitBtn"
																	class="btn btn-primary">确定</button>
															</div>
															<div class="col-lg-8">
																<button type="button" class="btn btn-default"
																	data-dismiss="modal"">关闭</button>
															</div>
														</div>
													</form>
												</div>
											</section>
										</div>
									</div>
								</div>
							</div>
							<!-- 添加商品模型类别 模态框 -->
							<div aria-hidden="true" aria-labelledby="myModalLabel"
								role="dialog" tabindex="-1" id="addModal_model"
								class="modal fade">
								<div class="modal-dialog">
									<div class="modal-content">
										<!-- <div class="modal-header">
											<button aria-hidden="true" data-dismiss="modal"
												class="close" type="button">×</button>
											<h4 class="modal-title">Update the Commodity Type</h4>
										</div> -->
										<div class="modal-body">
											<section class="panel">
												<header class="panel-heading"> 修改商品型号信息 </header>
												<div class="panel-body">
													<form class="form-horizontal" role="form" id="addModalForm">
														<!-- <div class="form-group">
															<label for="commodityId_editModal"
																class="col-lg-2 col-sm-2 control-label">商品ID</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="commodityId_editModal"
																	id="commodityId_editModal" placeholder="商品ID" readonly="readonly">
																<p class="help-block">商品ID是商品唯一标识，有数据库自动生成，用户无法修改.</p>
															</div>
														</div> -->
														<div class="form-group">
															<label for="commodityModel_editModal"
																class="col-lg-2 col-sm-2 control-label">商品名称</label>
															<div class="col-lg-10">
																<input type="hidden" class="form-control"
																	name="commodityId" id="hiddenId_editModal"
																	placeholder="商品ID"> <input type="text"
																	class="form-control" name="commodityModel"
																	id="commodityModel_editModal" placeholder="商品型号">
																<p class="help-block">商品型号会显示在前台网站上.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityPrice_editModal"
																class="col-lg-2 col-sm-2 control-label">价格</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="price"
																	id="commodityPrice_editModal" placeholder="价格">
																<p class="help-block">该型号对应的价格（￥）.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityRepertory_editModal"
																class="col-lg-2 col-sm-2 control-label">库存量</label>
															<div class="col-lg-10">
																<input type="text" class="form-control"
																	name="commodityRepertory"
																	id="commodityRepertory_editModal" placeholder="库存量">
																<p class="help-block">现有库存数量.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityStatus_editModal"
																class="col-lg-2 col-sm-2 control-label">商品状态</label>
															<div class="col-lg-10">
																<select name="commodityFlag"
																	id="commodityStatus_editModal">
																	<option value="-2">--- 修改状态 --</option>
																	<option value="0">已上架</option>
																	<option value="1">已下架</option>
																	<!-- <option value="2">已删除</option> -->
																</select>
																<p class="help-block">请选择商品状态.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityType_editModal"
																class="col-lg-2 col-sm-2 control-label">缺省显示</label>
															<div class="col-lg-10">
																<select name="isDefaultModel"
																	id="commodityIsDefault_editModal">
																	<option value="1" checked="checked">缺省显示</option>
																	<option value="0">不缺省显示</option>
																</select>
																<p class="help-block">是否缺省显示该商品.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityPic_editModal"
																class="col-lg-2 col-sm-2 control-label">商品图片</label>
															<div class="col-lg-10 uploadFile_div">
																<img alt="" src="" style="width: 50px; height: 50px"
																	id="modelPic_img1_editModal"
																	onclick="imgClicked('modelPic1_editModal')" /> <img
																	alt="" src="" style="width: 50px; height: 50px"
																	id="modelPic_img2_editModal"
																	onclick="imgClicked('modelPic2_editModal')" /> <img
																	alt="" src="" style="width: 50px; height: 50px"
																	id="modelPic_img3_editModal"
																	onclick="imgClicked('modelPic3_editModal')" /> <img
																	alt="" src="" style="width: 50px; height: 50px"
																	id="modelPic_img4_editModal"
																	onclick="imgClicked('modelPic4_editModal')" /> <img
																	alt="" src="" style="width: 50px; height: 50px"
																	id="modelPic_img5_editModal"
																	onclick="imgClicked('modelPic5_editModal')" />
																<script type="text/javascript">
																	function imgClicked(id)
																	{
																		$('#addModal_model' + ' ' + '#'+id).click();
																	}
																	function fileSelected(o,id){
																		var val = o.value;
																		$('#addModal_model' + ' ' + '#'+id).attr('alt',val.split("\\")[val.split("\\").length-1]);
																	}
																</script>
															</div>
															<div class="col-lg-10">
																<input type="file" style="display: none"
																	id="modelPic1_editModal" name="img0"
																	onchange="fileSelected(this,'modelPic_img1_editModal')">
																<input type="file" style="display: none"
																	id="modelPic2_editModal" name="img1"
																	onchange="fileSelected(this,'modelPic_img2_editModal')">
																<input type="file" style="display: none"
																	id="modelPic3_editModal" name="img2"
																	onchange="fileSelected(this,'modelPic_img3_editModal')">
																<input type="file" style="display: none"
																	id="modelPic4_editModal" name="img3"
																	onchange="fileSelected(this,'modelPic_img4_editModal')">
																<input type="file" style="display: none"
																	id="modelPic5_editModal" name="img4"
																	onchange="fileSelected(this,'modelPic_img5_editModal')">
															</div>
														</div>
														<!-- <div class="form-group">
															<div class="col-lg-offset-2 col-lg-10">
																<div class="checkbox">
																	<label> <input type="checkbox">
																		Remember me
																	</label>
																</div>
															</div>
														</div>-->
														<div class="form-group">
															<div class="col-lg-offset-2 col-lg-2">
																<button type="button" id="addModalSubmitBtn"
																	class="btn btn-primary">确定</button>
															</div>
															<div class="col-lg-8">
																<button type="button" class="btn btn-default"
																	data-dismiss="modal"">关闭</button>
															</div>
														</div>
													</form>
												</div>
											</section>
										</div>
									</div>
								</div>
							</div>

							<!-- 商品详情 模态框 -->
							<div aria-hidden="true" aria-labelledby="myModalLabel"
								role="dialog" tabindex="-1" id="commodityDetailModal"
								class="modal fade">
								<div class="modal-dialog">
									<div class="modal-content">
										<!-- <div class="modal-header">
											<button aria-hidden="true" data-dismiss="modal"
												class="close" type="button">×</button>
											<h4 class="modal-title">Update the Commodity Type</h4>
										</div> -->
										<div class="modal-body">
											<section class="panel">
												<header class="panel-heading"> 更新商品详情 </header>
												<!-- 	<header class="panel-heading">
													CKEditor <span class="tools pull-right"> <a
														class="fa fa-chevron-down" href="javascript:;"></a> <a
														class="fa fa-times" href="javascript:;"></a>
													</span>
													</header> -->

												<div class="panel-body">
													<div class="form-group">
														<form action="#" class="form-horizontal"
															id="updateDetailForm">
															<div class="form-group">
																<div class="col-sm-12">
																	<input type="hidden" name="id"
																		id="commodityId_detail_modal" rows="6">
																	</textarea>
																	<textarea class="form-control ckeditor" name="editor1"
																		id="ckeditorTxArea" rows="6"></textarea>
																</div>
															</div>
														</form>
													</div>
													<div class="form-group">
														<div class="col-lg-offset-2 col-lg-2">
															<button type="button" id="commodityDetailModalSubmitBtn"
																class="btn btn-primary">确定</button>
														</div>
														<div class="col-lg-8">
															<button type="button" class="btn btn-default"
																data-dismiss="modal"">关闭</button>
														</div>
													</div>
												</div>
											</section>
										</div>
									</div>
								</div>
							</div>
						</section>
					</div>
				</div>
			</div>
			<!--body wrapper end-->
			<jsp:include page="/user1/footer.action"></jsp:include>
		</div>
	</section>



	<!-- Placed js at the end of the document so the pages load faster -->
	<script src="js/jquery-1.10.2.min.js"></script>
	<script src="js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="js/jquery-migrate-1.2.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/modernizr.min.js"></script>
	<script src="js/jquery.nicescroll.js"></script>
	<script src="js/jquery.pager1.js"></script>
	<!-- <script src="js/jquery_002.js"></script> -->
	<!--common scripts for all pages-->
	<script src="js/scripts.js"></script>


	<!--pickers plugins-->
	<script type="text/javascript"
		src="js/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript"
		src="js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript"
		src="js/bootstrap-daterangepicker/moment.min.js"></script>
	<script type="text/javascript"
		src="js/bootstrap-daterangepicker/daterangepicker.js"></script>
	<script type="text/javascript"
		src="js/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
	<script type="text/javascript"
		src="js/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>

	<!--pickers initialization-->
	<script src="js/pickers-init.js"></script>

	<!--data table-->
	<script type="text/javascript"
		src="js/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="js/data-tables/DT_bootstrap.js"></script>

	<!--script for editable table-->
	<script src="js/editable-table.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/common/layer.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/common/template.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/common/base.js"></script>
	<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>
	<script src="js/taskList.js"></script>
	<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var onlineCommodImgPath = '<%=onlineCommodImgPath%>';
	</script>
</body>
</html>