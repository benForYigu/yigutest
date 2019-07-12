<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>简历证书管理</title>
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
		<li class="active"><a href="${ctx}/resume/certificate/studentResumeSchoolCertificate/">简历证书列表</a></li>
		<shiro:hasPermission name="resume:certificate:studentResumeSchoolCertificate:edit"><li><a href="${ctx}/resume/certificate/studentResumeSchoolCertificate/form">简历证书添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="studentResumeSchoolCertificate" action="${ctx}/resume/certificate/studentResumeSchoolCertificate/" method="post" class="breadcrumb form-search">
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
				<th>证书名称</th>
				<th>证书类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="resume:certificate:studentResumeSchoolCertificate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studentResumeSchoolCertificate">
			<tr>
				<td><a href="${ctx}/resume/certificate/studentResumeSchoolCertificate/form?id=${studentResumeSchoolCertificate.id}">
					${studentResumeSchoolCertificate.studentId}
				</a></td>
				<td>
					<fmt:formatDate value="${studentResumeSchoolCertificate.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${studentResumeSchoolCertificate.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${studentResumeSchoolCertificate.certificateName}
				</td>
				<td>
					${studentResumeSchoolCertificate.certificateType}
				</td>
				<td>
					<fmt:formatDate value="${studentResumeSchoolCertificate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${studentResumeSchoolCertificate.remarks}
				</td>
				<shiro:hasPermission name="resume:certificate:studentResumeSchoolCertificate:edit"><td>
    				<a href="${ctx}/resume/certificate/studentResumeSchoolCertificate/form?id=${studentResumeSchoolCertificate.id}">修改</a>
					<a href="${ctx}/resume/certificate/studentResumeSchoolCertificate/delete?id=${studentResumeSchoolCertificate.id}" onclick="return confirmx('确认要删除该简历证书吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>