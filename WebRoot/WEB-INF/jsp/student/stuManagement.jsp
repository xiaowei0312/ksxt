<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String mallPicImgPath = "/QAContentPic/";
	String onlineCommodImgPath = "/QASubjectPic/";
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

.myTable td {
	word-wrap: break-word;
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
				<h3>学员管理</h3>
				<ul class="breadcrumb">
					<li><a
						href="${pageContext.request.contextPath }/user/main.action">Home</a></li>
					<li><a href="#">学员管理</a></li>
					<li class="active">学员管理</li>
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
								<div class="clearfix">
									<div class="btn-group pull-right">
										<button class="btn btn-default dropdown-toggle"
											data-toggle="dropdown">
											Tools <i class="fa fa-angle-down"></i>
										</button>
										<ul class="dropdown-menu pull-right">
											<!-- <li><a href="#">Print</a></li>
												<li><a href="#">Save as PDF</a></li> -->
											<li><a href="<%=basePath%>/offlineOrder/submitOrderExportExcel.action">Export
													to Excel</a></li>
											<li><a href="#importExcelModal" data-toggle="modal">Import Students</a></li>
											<li><a href="#importExcelModal1" data-toggle="modal">Import Titles</a></li>
										</ul>
									</div>
								</div>
								<!-- <br /> -->
								<div id="search-condition" class="search-condition row" style="margin-bottom: 5px;">
									<div class="col-md-5">
										<form class="form-inline" role="form">
											<div class="form-group col-md-5" style="padding-left: 0">
												<input type="text" class="form-control" id="searchKey"
													placeholder="姓名/身份证号/学号/指导老师">
											</div>
											<div class="form-group col-md-3">
												<button type="button" id="searchBtn" class="btn btn-primary">Search</button>
											</div>
											<div class="col-md-2" style="padding-left: 0; margin-left: 0">
												<a href="#addModal" data-toggle="modal"><button
												type="button" id="addNewCommodityBtn"
												class="btn btn-primary">添加新学员</button></a>
											</div>
										</form>
									</div>
									<!-- <div class="col-md-5">
									</div> -->
									
									<!-- <div class="col-md-2" style="padding-left: 0; margin-left: 0">
										<a href="#addModal" data-toggle="modal"><button
												type="button" id="addNewCommodityBtn"
												class="btn btn-primary">导入Excel文件</button></a>
									</div> -->
								</div>
									<!-- <div class="col-md-1">
									</div> -->
								<div id="search-condition" class="search-condition row" style="margin-bottom: 5px;">
									<div class="col-md-6" style="padding-left: 0">
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
										<!-- <div class="col-md-1" style="padding-left: 0">
											<select class="form-control" name="classSelect"
												id="classSelect">
												<option value="-2" checked="checked">所有班级</option>
												<option value="1" checked="checked">1班</option>
												<option value="2" checked="checked">2班</option>
												<option value="3" checked="checked">3班</option>
												<option value="4" checked="checked">4班</option>
											</select>
										</div> -->
									</div>
									<div class="col-md-6" style="padding-left: 0">
										<div class="col-md-3">
											<select class="form-control" name="trainingClassSelect"
												id="trainingClassSelect">
												<option value="-2" checked="checked">实训班级</option>
											</select>
										</div>
										<div class="col-md-3">
											<select class="form-control" name="trainingDirSelect"
												id="trainingDirSelect">
												<option value="-2" checked="checked">实训方向</option>
											</select>
										</div>
										<div class="col-md-3">
											<select class="form-control" name="bsDirSelect"
												id="bsDirSelect">
												<option value="-2" checked="checked">毕设方向</option>
											</select>
										</div>
										<div class="col-md-3">
											<select class="form-control" name="addTimeOrderBy"
												id="addTimeOrderBy">
												<option value="0" checked="checked">添加时间降序</option>
												<option value="1">添加时间升序</option>
											</select>
										</div>
									</div>
								</div>
								<br />
								<div class="adv-table">
									<table class="display table table-bordered myTable"
										id="hidden-table-info" style="table-layout: fixed">
										<thead>
											<tr class="orderTr" id="theadTr" style="text-align: center;">
												<th width="6%">姓名</th>
												<!-- <th>性别</th>
												<th>身份证号</th> -->
												<th width="8%">学号</th>
												<th width="20%">院校 / 系别 / 专业 / 级别 / 班级</th>
												<!-- <th>实训方向</th> -->
												<th width="8%">实训班级</th>
												<th width="10%">毕设方向</th>
												<th width="20%">毕设选题</th>
												<th width="10%">指导老师</th>
												<th width="8%">操作</th>
											</tr>
										</thead>
										<tbody id="commodityList">
										</tbody>
									</table>
								</div>
								<div id="pager_div" class="pager_div">
									<div id="pager"></div>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; To <input type="text"
										class="text-box" id="numberOfPages"> Page <input
										type="button" value="GO" class="sub-btn"
										onclick="javascript:orderListComp.goToPage();">
								</div>
							</div>
						</section>
						<section class="model">
							<!-- 导入Excel文件 模态框 -->
							<div aria-hidden="true" aria-labelledby="myModalLabel"
								role="dialog" tabindex="-1" id="importExcelModal" class="modal fade">
								<div class="modal-dialog">
									<div class="modal-content">
										<!-- <div class="modal-header">
											<button aria-hidden="true" data-dismiss="modal"
												class="close" type="button">×</button>
											<h4 class="modal-title">Update the Commodity Type</h4>
										</div> -->
										<div class="modal-body">
											<section class="panel">
												<header class="panel-heading"> 导入学员信息 </header>
												<div class="panel-body">
													<form class="form-horizontal" role="form"
														id="modalForm">
														<div class="form-group">
															<label for="commodityName_editModal"
																class="col-lg-2 col-sm-2 control-label">选择文件</label>
															<div class="col-lg-10">
																<input type="file" 
																	accept=".csv,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" class="form-control" 
																	name="uploadFile"
																	id="uploadFile">
																<p class="help-block">请选择要导入的Excel文件，支持.xls .xlsx文件后缀.</p>
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
							<!-- 导入Excel文件 模态框 -->
							<div aria-hidden="true" aria-labelledby="myModalLabel"
								role="dialog" tabindex="-1" id="importExcelModal1" class="modal fade">
								<div class="modal-dialog">
									<div class="modal-content">
										<!-- <div class="modal-header">
											<button aria-hidden="true" data-dismiss="modal"
												class="close" type="button">×</button>
											<h4 class="modal-title">Update the Commodity Type</h4>
										</div> -->
										<div class="modal-body">
											<section class="panel">
												<header class="panel-heading"> 导入题目信息 </header>
												<div class="panel-body">
													<form class="form-horizontal" role="form"
														id="modalForm">
														<div class="form-group">
															<label for="commodityName_editModal"
																class="col-lg-2 col-sm-2 control-label">选择文件</label>
															<div class="col-lg-10">
																<input type="file" 
																	accept=".csv,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" class="form-control" 
																	name="uploadFile"
																	id="uploadFile">
																<p class="help-block">请选择要导入的Excel文件，支持.xls .xlsx文件后缀.</p>
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
							<!-- 修改商品类别 模态框 -->
							<div aria-hidden="true" aria-labelledby="myModalLabel"
								role="dialog" tabindex="-1" id="editModal" class="modal fade">
								<div class="modal-dialog">
									<div class="modal-content">
										<!-- <div class="modal-header">
											<button aria-hidden="true" data-dismiss="modal"
												class="close" type="button">×</button>
											<h4 class="modal-title">Update the Commodity Type</h4>
										</div> -->
										<div class="modal-body">
											<section class="panel">
												<header class="panel-heading"> 修改学员信息 </header>
												<div class="panel-body">
													<form class="form-horizontal" role="form"
														id="editModalForm">
														<div class="form-group">
															<label for="commodityName_editModal"
																class="col-lg-2 col-sm-2 control-label">姓名</label>
															<div class="col-lg-10">
																<input type="hidden" class="form-control" name="id"
																	id="id" placeholder="商品ID">
																<input type="text" class="form-control" name="name"
																	id="name" placeholder="姓名">
																<!-- <p class="help-block">The Question will be display on the front web site.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">性别</label>
															<div class="col-lg-10">
																<div class="col-lg-3">
																	男：<input type="radio" name="gender" value="0" checked="checked"
																		id="gender_m">
																</div>
																<div class="col-lg-3">
																	女：<input type="radio" name="gender" value="1"
																		id="gender_f">
																</div>
																<!-- <p class="help-block">The Answer will be display on the front web site.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">身份证号</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="idCard"
																	id="idcard" placeholder="输入完整的身份证号，最后一位X大写输入">
																<!-- <p class="help-block">The Url will be display on the front web site.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">学号</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="stuNo"
																	id="stuNo" placeholder="输入学生学号">
																<!-- <p class="help-block">The Url will be display on the front web site.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commodityStatus_editModal"
																class="col-lg-2 col-sm-2 control-label">院校信息</label>
															<div class="col-lg-4">
																<select name="major.parent.parent.id" class="form-control" id="colSelect">
																	<option value="-2">-- 院校 --</option>
																</select>
																<!-- <p class="help-block">Please select the Status.</p> -->
															</div>
															<div class="col-lg-3">
																<select name="major.parent.id" class="form-control" id="depSelect">
																	<option value="-2">-- 系别 --</option>
																</select>
																<!-- <p class="help-block">Please select the Status.</p> -->
															</div>
															<div class="col-lg-3">
																<select name="major.id" class="form-control" id="majorSelect">
																	<option value="-2">-- 专业 --</option>
																</select>
																<!-- <p class="help-block">Please select the Status.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">级别</label>
															<div class="col-lg-10">
																<select name="gradeNo" id="gradeSelect" class="form-control">
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
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">班级</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="classNo"
																	id="classNo" placeholder="输入班级名称，eg: 0401班请填写 0401">
																<!-- <p class="help-block">The Url will be display on the front web site.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commodityType_editModal"
																class="col-lg-2 col-sm-2 control-label">实训班级</label>
															<div class="col-lg-3">
																<select name="trainingDirId" id="trainingDirSelect" class="form-control">
																	<option value="-2">- 实训方向 -</option>
																</select>
															</div>
															<div class="col-lg-7">
																<select name="trainingClassId" id="trainingClassSelect" class="form-control">
																	<option value="-2">- 选择实训班级 -</option>
																</select>
																<!-- <p class="help-block">Please select the question type.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commodityType_editModal"
																class="col-lg-2 col-sm-2 control-label">毕设选题</label>
															<div class="col-lg-3">
																<select name="bsDirId" id="bsDirSelect" class="form-control">
																	<option value="-2">- 毕设方向 -</option>
																</select>
																<!-- <p class="help-block">Please select the question type.</p> -->
															</div>
															<div class="col-lg-7">
																<select name="bsTitleId" id="bsTitleSelect" class="form-control">
																	<option value="-2">- 毕设选题  -</option>
																</select>
																<!-- <p class="help-block">Please select the question type.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">校内指导老师</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="bsInnerTeacher"
																	id="bsInnerTeacher" placeholder="输入校内指导老师名称">
																<!-- <p class="help-block">The Url will be display on the front web site.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">校外指导老师</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="bsOuterTeacher"
																	id="bsOuterTeacher" placeholder="输入校外指导老师名称">
																<!-- <p class="help-block">The Url will be display on the front web site.</p> -->
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
										<!-- <div class="modal-header">
											<button aria-hidden="true" data-dismiss="modal"
												class="close" type="button">×</button>
											<h4 class="modal-title">Update the Commodity Type</h4>
										</div> -->
										<div class="modal-body">
											<section class="panel">
												<header class="panel-heading"> 修改学员信息 </header>
												<div class="panel-body">
													<form class="form-horizontal" role="form"
														id="addModalForm">
														<div class="form-group">
															<label for="commodityName_editModal"
																class="col-lg-2 col-sm-2 control-label">姓名</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="name"
																	id="name" placeholder="姓名">
																<!-- <p class="help-block">The Question will be display on the front web site.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">性别</label>
															<div class="col-lg-10">
																<div class="col-lg-3">
																	男：<input type="radio" name="gender" value="0" checked="checked"
																		id="gender_m">
																</div>
																<div class="col-lg-3">
																	女：<input type="radio" name="gender" value="1"
																		id="gender_f">
																</div>
																<!-- <p class="help-block">The Answer will be display on the front web site.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">身份证号</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="idCard"
																	id="idcard" placeholder="输入完整的身份证号，最后一位X大写输入">
																<!-- <p class="help-block">The Url will be display on the front web site.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">学号</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="stuNo"
																	id="stuNo" placeholder="输入学生学号">
																<!-- <p class="help-block">The Url will be display on the front web site.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commodityStatus_editModal"
																class="col-lg-2 col-sm-2 control-label">院校信息</label>
															<div class="col-lg-4">
																<select name="major.parent.parent.id" class="form-control" id="colSelect">
																	<option value="-2">-- 院校 --</option>
																</select>
																<!-- <p class="help-block">Please select the Status.</p> -->
															</div>
															<div class="col-lg-3">
																<select name="major.parent.id" class="form-control" id="depSelect">
																	<option value="-2">-- 系别 --</option>
																</select>
																<!-- <p class="help-block">Please select the Status.</p> -->
															</div>
															<div class="col-lg-3">
																<select name="major.id" class="form-control" id="majorSelect">
																	<option value="-2">-- 专业 --</option>
																</select>
																<!-- <p class="help-block">Please select the Status.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">级别</label>
															<div class="col-lg-10">
																<select name="gradeNo" id="gradeSelect" class="form-control">
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
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">班级</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="classNo"
																	id="classNo" placeholder="输入班级名称，eg: 0401班请填写 0401">
																<!-- <p class="help-block">The Url will be display on the front web site.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commodityType_editModal"
																class="col-lg-2 col-sm-2 control-label">实训班级</label>
															<div class="col-lg-3">
																<select name="trainingDirId" id="trainingDirSelect" class="form-control">
																	<option value="-2">- 实训方向 -</option>
																</select>
															</div>
															<div class="col-lg-7">
																<select name="trainingClassId" id="trainingClassSelect" class="form-control">
																	<option value="-2">- 选择实训班级 -</option>
																</select>
																<!-- <p class="help-block">Please select the question type.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commodityType_editModal"
																class="col-lg-2 col-sm-2 control-label">毕设选题</label>
															<div class="col-lg-3">
																<select name="bsDirId" id="bsDirSelect" class="form-control">
																	<option value="-2">- 毕设方向 -</option>
																</select>
																<!-- <p class="help-block">Please select the question type.</p> -->
															</div>
															<div class="col-lg-7">
																<select name="bsTitleId" id="bsTitleSelect" class="form-control">
																	<option value="-2">- 毕设选题  -</option>
																</select>
																<!-- <p class="help-block">Please select the question type.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">校内指导老师</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="bsInnerTeacher"
																	id="bsInnerTeacher" placeholder="输入校内指导老师名称">
																<!-- <p class="help-block">The Url will be display on the front web site.</p> -->
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">校外指导老师</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="bsOuterTeacher"
																	id="bsOuterTeacher" placeholder="输入校外指导老师名称">
																<!-- <p class="help-block">The Url will be display on the front web site.</p> -->
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
							<!-- 商品详情 模态框 -->
							<div aria-hidden="true" aria-labelledby="myModalLabel"
								role="dialog" tabindex="-1" id="commodityDetailModal"
								class="modal fade">
								<div class="modal-dialog">
									<div class="modal-content">
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
	<script src="js/studentList.js"></script>
	<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var onlineCommodImgPath = '<%=onlineCommodImgPath%>';
	</script>
</body>
</html>