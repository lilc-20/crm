<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":"
	+ request.getServerPort()
	+ request.getContextPath() + "/";
%>
<html>
<head>
	<base href="<%=basePath%>">

	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

	<script type="text/javascript">

	$(function(){

		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});

		addActivity();

		save();

		pageList(1,2);

		search();

		clean();

		check();

		deleteActivity();

		edit();

		update();

	});

	function addActivity(){
		$("#create-btn").click(function (){
			$.ajax({
				url : "workbench/activity/selectUsers.do",
				type : "get",
				dataType : "json",
				success : function (resp){
					var html = "";
					$.each(resp, function (i, item){
						html += "<option value=" + item.id + ">" + item.name + "</option>";
					});
					$("#create-owner").html(html);

					var id = "${user.id}";
					$("#create-owner").val(id);

					$("#createActivityModal").modal("show");
				}
			});
		});
	}

	function save(){
		$("#save-btn").click(function (){
			$.ajax({
				url : "workbench/activity/save.do",
				type : "post",
				data : {
					"owner" : $.trim($("#create-owner").val()),
					"name" : $.trim($("#create-name").val()),
					"startDate" : $.trim($("#create-startDate").val()),
					"endDate" : $.trim($("#create-endDate").val()),
					"cost" : $.trim($("#create-cost").val()),
					"description" : $.trim($("#create-description").val())
				},
				dataType : "json",
				success : function (resp){
					if (resp.success){
						$("#createActivityModal").modal("hide");
						$("#add-activity")[0].reset();
					}
					pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
				}
			});
		});
	}

	function pageList(pageNo, pageSize){
		$("#checkAll").prop("checked", false);

		$("#search-name").val($.trim($("#hidden-name").val()));
		$("#search-owner").val($.trim($("#hidden-owner").val()));
		$("#search-startDate").val($.trim($("#hidden-startDate").val()));
		$("#search-endDate").val($.trim($("#hidden-endDate").val()));

		$.ajax({
			url : "workbench/activity/pageList.do",
			type : "get",
			data : {
				"pageNo" : pageNo,
				"pageSize" : pageSize,
				"name" : $.trim($("#search-name").val()),
				"owner" : $.trim($("#search-owner").val()),
				"startDate" : $.trim($("#search-startDate").val()),
				"endDate" : $.trim($("#search-endDate").val())
			},
			dataType : "json",
			success : function (data){
				var html = "";
				$.each(data.dataList, function (i, item){
					html += '<tr class="active">';
					html += '<td><input type="checkbox" name="xz" id="' + item.id + '" /></td>'
					html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id=' + item.id + '\';">' + item.name + '</a></td>';
					html += '<td>' + item.owner + '</td>';
					html += '<td>' + item.startDate + '</td>';
					html += '<td>' + item.endDate + '</td>';
					html += '</tr>';
				});

				$("#activityBody").html(html);

				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: data.totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});

			}
		});
	}

	function search(){
		$("#search-btn").click(function (){
			$("#hidden-name").val($.trim($("#search-name").val()));
			$("#hidden-owner").val($.trim($("#search-owner").val()));
			$("#hidden-startDate").val($.trim($("#search-startDate").val()));
			$("#hidden-endDate").val($.trim($("#search-endDate").val()));
			pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
		});
	}

	function check(){
		$("#checkAll").click(function (){
			$("input[name=xz]").prop("checked", this.checked);
		});

		$("#activityBody").on("click", $("input[name=xz]"), function (){
			$("#checkAll").prop("checked", $("input[name=xz]").length == $("input[name=xz]:checked").length);
		});
	}

	function clean(){
		$("#clean-btn").click(function (){
			$("#hidden-name").val("");
			$("#hidden-owner").val("");
			$("#hidden-startDate").val("");
			$("#hidden-endDate").val("");
			pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
		});
	}

	function deleteActivity(){
		$("#del-btn").click(function (){
			var $checked = $("input[name=xz]:checked");
			if ($checked.length == 0){
				alert("请选择要删除的活动");
			}else {
				if (confirm("确定要删除选中的记录吗？")){
					var param = "";
					$.each($checked, function (i, item){
						param += "id=" + item.id + "&";
					});
					param = param.substring(0, param.lastIndexOf("&"));

					$.ajax({
						url : "workbench/activity/delete.do",
						type : "post",
						data : param,
						dataType : "json",
						success : function (resp){
							//"success" : true/false
							if (resp.success){
								pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							}
						}
					});
				}
			}
		});


	}

	function edit(){
		$("#edit-btn").click(function (){
			var $checked = $("input[name=xz]:checked");
			if ($checked.length == 0){
				alert("请选择要修改的活动");
			}else if ($checked.length > 1){
				alert("一次只能查询一项活动")
			}else {
				var param = "id=" + $checked[0].id;

				$.ajax({
					url : "workbench/activity/edit.do",
					type : "post",
					data : param,
					dataType : "json",
					success : function (resp){
						//"users" : "user" , "activity" : "activity"
						var html = "";
						$.each(resp.users, function (i, item){
							html += "<option value='" + item.id + "'>" + item.name + "</option>"
						});
						$("#edit-owner").html(html);
						$("#edit-owner").val(resp.activity.owner);

						$("#edit-id").val(resp.activity.id);
						$("#edit-name").val(resp.activity.name);
						$("#edit-startDate").val(resp.activity.startDate);
						$("#edit-endDate").val(resp.activity.endDate);
						$("#edit-cost").val(resp.activity.cost);
						$("#edit-description").val(resp.activity.description);
					}
				});

				$("#editActivityModal").modal("show");
			}
		});
	}

	function update(){
		$("#update-btn").click(function (){
			$.ajax({
				url : "workbench/activity/update.do",
				type : "post",
				data : {
					"id" : $("#edit-id").val(),
					"owner" : $.trim($("#edit-owner").val()),
					"name" : $.trim($("#edit-name").val()),
					"startDate" : $.trim($("#edit-startDate").val()),
					"endDate" : $.trim($("#edit-endDate").val()),
					"cost" : $.trim($("#edit-cost").val()),
					"description" : $.trim($("#edit-description").val())
				},
				dataType : "json",
				success : function (resp){
					if (resp.success){
						$("#createActivityModal").modal("hide");
					}
					pageList($("#activityPage").bs_pagination('getOption', 'currentPage'),
							$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
				}
			});

			$("#editActivityModal").modal("hide");
		});
	}
	
</script>
</head>
<body>

	<input type="hidden" id="hidden-name"/>
	<input type="hidden" id="hidden-owner"/>
	<input type="hidden" id="hidden-startDate"/>
	<input type="hidden" id="hidden-endDate"/>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="add-activity">
					
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">

								</select>
							</div>
                            <label for="create-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate" readonly>
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="save-btn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<input type="hidden" id="edit-id"/>
							<label for="edit-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">

								</select>
							</div>
                            <label for="edit-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startDate">
							</div>
							<label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endDate">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="update-btn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="search-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="search-endDate">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="search-btn">查询</button>
					<button type="button" class="btn btn-default" id="clean-btn">清空</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="create-btn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="edit-btn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="del-btn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">

					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>