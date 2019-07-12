<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>验证码管理</title>
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
		<li class="active"><a href="${ctx}/smsrecord/smsRecord/">验证码列表</a></li>
		<shiro:hasPermission name="smsrecord:smsRecord:edit"><li><a href="${ctx}/smsrecord/smsRecord/form">验证码添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="smsRecord" action="${ctx}/smsrecord/smsRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台记录号：</label>
				<form:input path="platformNo" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="phone" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>验证状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="smsrecord:smsRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsRecord">
			<tr>
				<td><a href="${ctx}/smsrecord/smsRecord/form?id=${smsRecord.id}">
					<fmt:formatDate value="${smsRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${smsRecord.remarks}
				</td>
				<shiro:hasPermission name="smsrecord:smsRecord:edit"><td>
    				<a href="${ctx}/smsrecord/smsRecord/form?id=${smsRecord.id}">修改</a>
					<a href="${ctx}/smsrecord/smsRecord/delete?id=${smsRecord.id}" onclick="return confirmx('确认要删除该验证码吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>