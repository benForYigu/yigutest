<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>测试管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/testuser/testUserinfo/">测试列表</a></li>
		<shiro:hasPermission name="testuser:testUserinfo:edit"><li><a href="${ctx}/testuser/testUserinfo/form">测试添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="testUserinfo" action="${ctx}/testuser/testUserinfo/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input name="userName" type="text" value="${testUserinfo.userName }">
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>测试名称</th>
				<th>测试时间</th>
				<th>测试数据</th>
				<shiro:hasPermission name="testuser:testUserinfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="testUserinfo">
			<tr>
				<td>${testUserinfo.id }</td>
				<td>${testUserinfo.userName }</td>
				<td>${testUserinfo.userDste }</td>
				<td>${testUserinfo.userDete }</td>
				<td>
					<shiro:hasPermission name="testuser:testUserinfo:edit">
	    				<a href="${ctx}/testuser/testUserinfo/form?id=${testUserinfo.id}">修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="testuser:testUserinfo:delete">
						<a href="${ctx}/testuser/testUserinfo/delete?id=${testUserinfo.id}" onclick="return confirmx('确认要删除该测试吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>