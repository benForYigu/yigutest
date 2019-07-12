<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>简历教育经历管理</title>
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
		<li class="active"><a href="${ctx}/resume/education/studentResumeEducation/">简历教育经历列表</a></li>
		<shiro:hasPermission name="resume:education:studentResumeEducation:edit"><li><a href="${ctx}/resume/education/studentResumeEducation/form">简历教育经历添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="studentResumeEducation" action="${ctx}/resume/education/studentResumeEducation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>student_id：</label>
				<form:input path="studentId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="resume:education:studentResumeEducation:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studentResumeEducation">
			<tr>
				<td><a href="${ctx}/resume/education/studentResumeEducation/form?id=${studentResumeEducation.id}">
					<fmt:formatDate value="${studentResumeEducation.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${studentResumeEducation.remarks}
				</td>
				<shiro:hasPermission name="resume:education:studentResumeEducation:edit"><td>
    				<a href="${ctx}/resume/education/studentResumeEducation/form?id=${studentResumeEducation.id}">修改</a>
					<a href="${ctx}/resume/education/studentResumeEducation/delete?id=${studentResumeEducation.id}" onclick="return confirmx('确认要删除该简历教育经历吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>