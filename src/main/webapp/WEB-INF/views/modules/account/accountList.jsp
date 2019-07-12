<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
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
		<li class="active"><a href="${ctx}/account/account/">用户列表</a></li>
		<shiro:hasPermission name="account:account:edit"><li><a href="${ctx}/account/account/form">用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="account" action="${ctx}/account/account/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户权限：</label>
				<form:select path="role" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('account_role')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>账户：</label>
				<form:input path="username" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>密码：</label>
				<form:input path="password" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>网易云信id：</label>
				<form:input path="accid" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>网易云信token：</label>
				<form:input path="token" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('account_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户权限</th>
				<th>账户</th>
				<th>密码</th>
				<th>网易云信id</th>
				<th>网易云信token</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="account:account:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="account">
			<tr>
				<td><a href="${ctx}/account/account/form?id=${account.id}">
					${fns:getDictLabel(account.role, 'account_role', '')}
				</a></td>
				<td>
					${account.username}
				</td>
				<td>
					${account.password}
				</td>
				<td>
					${account.accid}
				</td>
				<td>
					${account.token}
				</td>
				<td>
					${fns:getDictLabel(account.status, 'account_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${account.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${account.remarks}
				</td>
				<shiro:hasPermission name="account:account:edit"><td>
    				<a href="${ctx}/account/account/form?id=${account.id}">修改</a>
					<a href="${ctx}/account/account/delete?id=${account.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>