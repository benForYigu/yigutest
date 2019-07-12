<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>基础信息</title>
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
<c:if test="${companyUsers==null}">
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/company/company/">企业列表</a></li>
	<%--	<shiro:hasPermission name="company:company:edit"><li><a href="${ctx}/company/company/form">企业添加</a></li></shiro:hasPermission>--%>
	</ul>

	<form:form id="searchForm" modelAttribute="company" action="${ctx}/company/company/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>全称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	</c:if>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>认证状态</th>
				<th>全称</th>
				<th>简称</th>
				<th>hr姓名</th>
				<th>hr手机</th>
				<th>邮箱</th>
				<th>创建时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="company:company:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="company">
			<tr>
				<td>
						${fns:getDictLabel(company.auth.status,'authentication' ,'未认证' )}
				</td>
				<td>
					${company.name}
				</td>
				<td>
					${company.shortName}
				</td>
				<td>
						${company.companyhr.realname}

				</td>
				<td>
					${company.contactPhone}
				</td>
				<td>
					${company.email}
				</td>
				<td>
					<fmt:formatDate value="${company.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${company.remarks}
				</td>
				<shiro:hasPermission name="company:company:edit"><td>
    				<a href="${ctx}/company/company/form?id=${company.id}">修改&nbsp;</a>

					<a href="${ctx}/company/auth/companyAuth/form?id=${company.id}">认证&nbsp;</a>

					<c:if test="${company.account.status=='1'}">
						<a href="${ctx}/company/company/forbidden?id=${company.id}" onclick="return confirmx('确认禁止操作', this.href)">禁止登陆&nbsp;</a>
					</c:if>
					<c:if test="${company.account.status=='2'}">
						<a href="${ctx}/company/company/forbidden?id=${company.id}" onclick="return confirmx('确认激活操作', this.href)">激活&nbsp;</a>
					</c:if>


					<a href="${ctx}/company/company/delete?id=${company.id}" onclick="return confirmx('确认要删除该企业基本信息吗？', this.href)">删除&nbsp;</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>