<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>简历工作经历管理</title>
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
		<li class="active"><a href="${ctx}/resume/experience/studentResumeExperience/">简历工作经历列表</a></li>
		<shiro:hasPermission name="resume:experience:studentResumeExperience:edit"><li><a href="${ctx}/resume/experience/studentResumeExperience/form">简历工作经历添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="studentResumeExperience" action="${ctx}/resume/experience/studentResumeExperience/" method="post" class="breadcrumb form-search">
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
				<th>start_date</th>
				<th>end_date</th>
				<th>企业名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="resume:experience:studentResumeExperience:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studentResumeExperience">
			<tr>
				<td><a href="${ctx}/resume/experience/studentResumeExperience/form?id=${studentResumeExperience.id}">
					<fmt:formatDate value="${studentResumeExperience.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					<fmt:formatDate value="${studentResumeExperience.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${studentResumeExperience.companyName}
				</td>
				<td>
					<fmt:formatDate value="${studentResumeExperience.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${studentResumeExperience.remarks}
				</td>
				<shiro:hasPermission name="resume:experience:studentResumeExperience:edit"><td>
    				<a href="${ctx}/resume/experience/studentResumeExperience/form?id=${studentResumeExperience.id}">修改</a>
					<a href="${ctx}/resume/experience/studentResumeExperience/delete?id=${studentResumeExperience.id}" onclick="return confirmx('确认要删除该简历工作经历吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>