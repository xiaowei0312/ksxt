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
				<h3>选题管理</h3>
				<ul class="breadcrumb">
					<li><a
						href="${pageContext.request.contextPath }/user/main.action">Home</a></li>
					<li><a href="#">毕设管理</a></li>
					<li class="active">选题管理</li>
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
								<div id="search-condition" class="search-condition row">
									<div class="col-md-4">
										<form class="form-inline" role="form">
											<div class="form-group col-md-7" style="padding-left: 0">
												<input type="text" class="form-control" id="searchKey"
													placeholder="选题名称">
											</div>
											<!-- <div class="form-group col-md-6">
												<div class="input-group input-large custom-date-range"
													data-date="" data-date-format="yyyy/mm/dd">
													<input type="text" class="form-control dpd1"
														name="startDate" id="searchStartDate" placeholder="开班时间">
													<span class="input-group-addon">To</span> <input
														type="text" id="searchEndDate" class="form-control dpd2"
														name="to" placeholder="开班时间">
												</div>
											</div> -->
											<div class="form-group col-md-5">
												<button type="button" id="searchBtn" class="btn btn-primary">Search</button>
											</div>
										</form>
									</div>
									<!-- <div class="col-md-1">
									</div> -->
									<div class="col-md-7" style="padding-left: 0">
										<!-- <div class="col-md-3" style="padding-left: 0">
											<select class="form-control" name="statusSelect"
												id="statusSelect">
												<option value="-2" checked="checked">All Status</option>
												<option value="0">实训中</option>
												<option value="1">已结训</option>
											</select>
										</div> -->
										<div class="col-md-6">
											<select class="form-control" name="typeSelect" id="typeSelect">
												<option value="-2" checked="checked">所有方向</option>
											</select>
										</div>
										<div class="col-md-6">
											<select class="form-control" name="timeOrderBy"
												id="timeOrderBy">
												<option value="0" checked="checked">时间降序</option>
												<option value="1">时间升序</option>
											</select>
										</div>
										<!-- <div class="col-md-3">
											<select class="form-control" name="endTimeOrderBy"
												id="endTimeOrderBy">
												<option value="0" checked="checked">结训时间降序</option>
												<option value="1">结训时间升序</option>
											</select>
										</div> -->
									</div>
									<div class="col-md-1" style="padding-left: 0; margin-left: 0">
										<a href="#addModal" data-toggle="modal"><button
												type="button" id="addNewCommodityBtn"
												class="btn btn-primary">Add New</button></a>
									</div>
								</div>
								<br />
								<div class="adv-table">
									<table class="display table table-bordered myTable"
										id="hidden-table-info" style="table-layout: fixed">
										<thead>
											<tr class="orderTr" id="theadTr">
												<th>选题名称</th>
												<th>方向</th>
												<th>最后修改时间</th>
												<!-- <th>结训时间</th>
												<th>实训状态</th> -->
												<th>操作</th>
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
												<header class="panel-heading"> 修改选题信息 </header>
												<div class="panel-body">
													<form class="form-horizontal" role="form"
														id="editModalForm">
														<div class="form-group">
															<label for="commodityName_editModal"
																class="col-lg-2 col-sm-2 control-label">选题名称</label>
															<div class="col-lg-10">
																<input type="hidden" class="form-control" name="id"
																	id="commodityId_editModal" placeholder="商品ID">
																<input type="text" class="form-control" name="name"
																	id="commodityName_editModal" placeholder="选题名称">
																<!-- <p class="help-block">The Question will be display on the front web site.</p> -->
															</div>
														</div>
														<!-- <div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">开班时间</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="beginTime"
																	id="commoditySeq_editModal" placeholder="The Answer">
																<p class="help-block">The Answer will be display on the front web site.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commoditySeq_editModal"
																class="col-lg-2 col-sm-2 control-label">结训时间</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="endTime"
																	id="commodityUrl_editModal" placeholder="The Url">
																<p class="help-block">The Url will be display on the front web site.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityStatus_editModal"
																class="col-lg-2 col-sm-2 control-label">班级状态</label>
															<div class="col-lg-10">
																<select name="status" id="commodityStatus_editModal">
																	<option value="-2">--- 班级状态 --</option>
																	<option value="0" selected="selected">实训中</option>
																	<option value="1">已结训</option>
																</select>
																<p class="help-block">Please select the Status.</p>
															</div>
														</div> -->
														<div class="form-group">
															<label for="commodityType_editModal"
																class="col-lg-2 col-sm-2 control-label">选题方向</label>
															<div class="col-lg-10">
																<select name="bsDirId" id="commodityType_editModal">
																	<option value="-2">--- 选择选题方向 --</option>
																</select>
																<!-- <p class="help-block">Please select the question type.</p> -->
															</div>
														</div>
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
												<header class="panel-heading"> Add New QA
													Information </header>
												<div class="panel-body">
													<form class="form-horizontal" role="form" id="addModalForm">
														<div class="form-group">
															<label for="commodityName_addModal"
																class="col-lg-2 col-sm-2 control-label">选题名称</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="name"
																	id="commodityName_addModal" placeholder="选题名称">
																<!-- <p class="help-block">The Question will be display on the front web site.</p> -->
															</div>
														</div>
														<!-- <div class="form-group">
															<label for="commoditySeq_addModal"
																class="col-lg-2 col-sm-2 control-label">开班时间</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="beginTime"
																	id="commoditySeq_addModal" placeholder="The Answer">
																<p class="help-block">The Answer will be display on the front web site.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityUrl_addModal"
																class="col-lg-2 col-sm-2 control-label">结训时间</label>
															<div class="col-lg-10">
																<input type="text" class="form-control" name="endTime"
																	id="commodityUrl_addModal" placeholder="The Url">
																<p class="help-block">The Url will be display on the front web site.</p>
															</div>
														</div>
														<div class="form-group">
															<label for="commodityStatus_addModal"
																class="col-lg-2 col-sm-2 control-label">班级状态</label>
															<div class="col-lg-10">
																<select name="status" id="commodityStatus_addModal">
																	<option value="-2">--- 选择班级状态 --</option>
																	<option value="0" selected="selected">实训中</option>
																	<option value="1">已结训</option>
																</select>
																<p class="help-block">Please select the Status.</p>
															</div>
														</div> -->
														<div class="form-group">
															<label for="commodityType_addModal"
																class="col-lg-2 col-sm-2 control-label">选题方向</label>
															<div class="col-lg-10">
																<select name="bsDirId" id="commodityType_addModal">
																	<option value="-2">--- 选择选题方向 --</option>
																</select>
																<!-- <p class="help-block">Please select the question type.</p> -->
															</div>
														</div>
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
	<script src="js/bsTitleList.js"></script>
	<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var onlineCommodImgPath = '<%=onlineCommodImgPath%>';
	</script>
</body>
</html>