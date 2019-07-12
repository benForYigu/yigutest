<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>入职秀管理</title>
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
		<li class="active"><a href="${ctx}/interaction/entry/interactionEntry/">入职秀列表</a></li>
		<shiro:hasPermission name="interaction:entry:interactionEntry:edit"><li><a href="${ctx}/interaction/entry/interactionEntry/form">入职秀添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="interactionEntry" action="${ctx}/interaction/entry/interactionEntry/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学生姓名：</label>
				<form:input path="studentId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>学校名称：</label>
				<form:input path="companyId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>学生姓名</th>
				<th>学校名称</th>
				<th>投递企业</th>
				<th>入职部门</th>
				<th>入职时间</th>
				<th>地点</th>
				<th>签到时间</th>
				<th>签到内容</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="interaction:entry:interactionEntry:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="interactionEntry">
			<tr>
				<td><a href="${ctx}/interaction/entry/interactionEntry/form?id=${interactionEntry.id}">
					${interactionEntry.studentId}
				</a></td>
				<td>
					${interactionEntry.companyId}
				</td>
				<td>
					${interactionEntry.conpanyName}
				</td>
				<td>
					${interactionEntry.entryBranch}
				</td>
				<td>
					<fmt:formatDate value="${interactionEntry.entryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${interactionEntry.area}
				</td>
				<td>
					<fmt:formatDate value="${interactionEntry.signTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${interactionEntry.signContent}
				</td>
				<td>
					<fmt:formatDate value="${interactionEntry.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${interactionEntry.remarks}
				</td>
				<shiro:hasPermission name="interaction:entry:interactionEntry:edit"><td>
    				<a href="${ctx}/interaction/entry/interactionEntry/form?id=${interactionEntry.id}">修改</a>
					<a href="${ctx}/interaction/entry/interactionEntry/delete?id=${interactionEntry.id}" onclick="return confirmx('确认要删除该入职秀吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>