<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>orm表单管理</title>
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
		<li class="active"><a href="${ctx}/formid/formId/">orm表单列表</a></li>
		<shiro:hasPermission name="formid:formId:edit"><li><a href="${ctx}/formid/formId/form">orm表单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="formId" action="${ctx}/formid/formId/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>form_id：</label>
				<form:input path="formId" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>用户open_id：</label>
				<form:input path="openId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>form_id</th>
				<th>用户open_id</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="formid:formId:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="formId">
			<tr>
				<td><a href="${ctx}/formid/formId/form?id=${formId.id}">
					${formId.formId}
				</a></td>
				<td>
					${formId.openId}
				</td>
				<td>
					<fmt:formatDate value="${formId.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${formId.remarks}
				</td>
				<shiro:hasPermission name="formid:formId:edit"><td>
    				<a href="${ctx}/formid/formId/form?id=${formId.id}">修改</a>
					<a href="${ctx}/formid/formId/delete?id=${formId.id}" onclick="return confirmx('确认要删除该orm表单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>