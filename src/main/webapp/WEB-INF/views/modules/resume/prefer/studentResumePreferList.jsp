<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>简历意向管理</title>
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
		<li class="active"><a href="${ctx}/resume/prefer/studentResumePrefer/">简历意向列表</a></li>
		<shiro:hasPermission name="resume:prefer:studentResumePrefer:edit"><li><a href="${ctx}/resume/prefer/studentResumePrefer/form">简历意向添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="studentResumePrefer" action="${ctx}/resume/prefer/studentResumePrefer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>salary：</label>
				<form:select path="salary" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('salary')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>salary</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="resume:prefer:studentResumePrefer:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studentResumePrefer">
			<tr>
				<td><a href="${ctx}/resume/prefer/studentResumePrefer/form?id=${studentResumePrefer.id}">
					${fns:getDictLabel(studentResumePrefer.salary, 'salary', '')}
				</a></td>
				<td>
					<fmt:formatDate value="${studentResumePrefer.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${studentResumePrefer.remarks}
				</td>
				<shiro:hasPermission name="resume:prefer:studentResumePrefer:edit"><td>
    				<a href="${ctx}/resume/prefer/studentResumePrefer/form?id=${studentResumePrefer.id}">修改</a>
					<a href="${ctx}/resume/prefer/studentResumePrefer/delete?id=${studentResumePrefer.id}" onclick="return confirmx('确认要删除该简历意向吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>