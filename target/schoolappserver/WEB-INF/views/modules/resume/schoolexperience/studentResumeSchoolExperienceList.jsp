<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>简历校园经历管理</title>
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
		<li class="active"><a href="${ctx}/resume/schoolexperience/studentResumeSchoolExperience/">简历校园经历列表</a></li>
		<shiro:hasPermission name="resume:schoolexperience:studentResumeSchoolExperience:edit"><li><a href="${ctx}/resume/schoolexperience/studentResumeSchoolExperience/form">简历校园经历添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="studentResumeSchoolExperience" action="${ctx}/resume/schoolexperience/studentResumeSchoolExperience/" method="post" class="breadcrumb form-search">
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
				<th>student_id</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>职位</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="resume:schoolexperience:studentResumeSchoolExperience:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studentResumeSchoolExperience">
			<tr>
				<td><a href="${ctx}/resume/schoolexperience/studentResumeSchoolExperience/form?id=${studentResumeSchoolExperience.id}">
					${studentResumeSchoolExperience.studentId}
				</a></td>
				<td>
					<fmt:formatDate value="${studentResumeSchoolExperience.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${studentResumeSchoolExperience.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${studentResumeSchoolExperience.job}
				</td>
				<td>
					<fmt:formatDate value="${studentResumeSchoolExperience.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${studentResumeSchoolExperience.remarks}
				</td>
				<shiro:hasPermission name="resume:schoolexperience:studentResumeSchoolExperience:edit"><td>
    				<a href="${ctx}/resume/schoolexperience/studentResumeSchoolExperience/form?id=${studentResumeSchoolExperience.id}">修改</a>
					<a href="${ctx}/resume/schoolexperience/studentResumeSchoolExperience/delete?id=${studentResumeSchoolExperience.id}" onclick="return confirmx('确认要删除该简历校园经历吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>