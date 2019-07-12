<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业地址管理</title>
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
		<li class="active"><a href="${ctx}/company/address/companyAddress/">企业地址列表</a></li>
		<shiro:hasPermission name="company:address:companyAddress:edit"><li><a href="${ctx}/company/address/companyAddress/form">企业地址添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="companyAddress" action="${ctx}/company/address/companyAddress/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>company_id：</label>
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
				<th>名字</th>
				<th>联系电话</th>
				<th>省份</th>
				<th>市</th>
				<th>省市区域</th>
				<th>详细地址</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="company:address:companyAddress:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="companyAddress">
			<tr>
				<td><a href="${ctx}/company/address/companyAddress/form?id=${companyAddress.id}">
					${companyAddress.name}
				</a></td>
				<td>
					${companyAddress.phone}
				</td>
				<td>
					${companyAddress.}
				</td>
				<td>
					${companyAddress.}
				</td>
				<td>
					${companyAddress.}
				</td>
				<td>
					${companyAddress.address}
				</td>
				<td>
					<fmt:formatDate value="${companyAddress.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${companyAddress.remarks}
				</td>
				<shiro:hasPermission name="company:address:companyAddress:edit"><td>
    				<a href="${ctx}/company/address/companyAddress/form?id=${companyAddress.id}">修改</a>
					<a href="${ctx}/company/address/companyAddress/delete?id=${companyAddress.id}" onclick="return confirmx('确认要删除该企业地址吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>