<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>简历其他管理</title>
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
		<li class="active"><a href="${ctx}/resume/other/studentResumeOther/">简历其他列表</a></li>
		<shiro:hasPermission name="resume:other:studentResumeOther:edit"><li><a href="${ctx}/resume/other/studentResumeOther/form">简历其他添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="studentResumeOther" action="${ctx}/resume/other/studentResumeOther/" method="post" class="breadcrumb form-search">
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
				<th>技能</th>
				<th>自评</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="resume:other:studentResumeOther:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studentResumeOther">
			<tr>
				<td><a href="${ctx}/resume/other/studentResumeOther/form?id=${studentResumeOther.id}">
					${studentResumeOther.skill}
				</a></td>
				<td>
					${studentResumeOther.selfEvaluation}
				</td>
				<td>
					<fmt:formatDate value="${studentResumeOther.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${studentResumeOther.remarks}
				</td>
				<shiro:hasPermission name="resume:other:studentResumeOther:edit"><td>
    				<a href="${ctx}/resume/other/studentResumeOther/form?id=${studentResumeOther.id}">修改</a>
					<a href="${ctx}/resume/other/studentResumeOther/delete?id=${studentResumeOther.id}" onclick="return confirmx('确认要删除该简历其他吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>