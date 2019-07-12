<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业双选会资料管理</title>
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
		<li class="active"><a href="${ctx}/unders/companyfile/undersCompanyFile/">企业双选会资料列表</a></li>
		<shiro:hasPermission name="unders:companyfile:undersCompanyFile:edit"><li><a href="${ctx}/unders/companyfile/undersCompanyFile/form">企业双选会资料添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="undersCompanyFile" action="${ctx}/unders/companyfile/undersCompanyFile/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>企业：</label>
				<form:input path="companyId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>企业</th>
				<th>名称</th>
				<th>联系人1</th>
				<th>联系方式1</th>
				<th>联系人2</th>
				<th>联系方式2</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="unders:companyfile:undersCompanyFile:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="undersCompanyFile">
			<tr>
				<td><a href="${ctx}/unders/companyfile/undersCompanyFile/form?id=${undersCompanyFile.id}">
					${undersCompanyFile.companyId}
				</a></td>
				<td>
					${undersCompanyFile.email}
				</td>
				<td>
					${undersCompanyFile.contact1}
				</td>
				<td>
					${undersCompanyFile.phone1}
				</td>
				<td>
					${undersCompanyFile.contact2}
				</td>
				<td>
					${undersCompanyFile.phone2}
				</td>
				<td>
					<fmt:formatDate value="${undersCompanyFile.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${undersCompanyFile.remarks}
				</td>
				<shiro:hasPermission name="unders:companyfile:undersCompanyFile:edit"><td>
    				<a href="${ctx}/unders/companyfile/undersCompanyFile/form?id=${undersCompanyFile.id}">修改</a>
					<a href="${ctx}/unders/companyfile/undersCompanyFile/delete?id=${undersCompanyFile.id}" onclick="return confirmx('确认要删除该企业双选会资料吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>