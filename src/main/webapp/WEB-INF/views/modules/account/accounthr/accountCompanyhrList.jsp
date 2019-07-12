<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业人员管理</title>
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
		<li class="active"><a href="${ctx}/account/accounthr/accountCompanyhr/">企业人员列表</a></li>
		<%--<shiro:hasPermission name="account:accounthr:accountCompanyhr:edit"><li><a href="${ctx}/account/accounthr/accountCompanyhr/form">企业人员添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="accountCompanyhr" action="${ctx}/account/accounthr/accountCompanyhr/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">

			<li><label>电话：</label>
				<form:input path="phone" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>认证状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('authentication')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<%--<th>用户id</th>--%>
				<th>企业名称</th>
				<th>头像</th>
				<th>真实姓名</th>
				<th>电话</th>
				<th>职业</th>
				<th>性别</th>
				<th>邮箱</th>
				<th>认证状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="account:accounthr:accountCompanyhr:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accountCompanyhr">
			<tr>
				<%--<td><a href="${ctx}/account/accounthr/accountCompanyhr/form?id=${accountCompanyhr.id}">
					${accountCompanyhr.accountId}
				</a></td>--%>
				<td>
					${accountCompanyhr.companyName}
				</td>
				<td>
					<img style="width: 50px;height: 50px" src="${accountCompanyhr.avatar}"/>

				</td>
				<td>
					${accountCompanyhr.realname}
				</td>
				<td>
					${accountCompanyhr.phone}
				</td>
				<td>
					${accountCompanyhr.major}
				</td>
				<td>
					${fns:getDictLabel(accountCompanyhr.sex, 'sex', '')}
				</td>
				<td>
					${accountCompanyhr.email}
				</td>
				<td>
						${fns:getDictLabel(accountCompanyhr.status,"authentication" ,'' )}

				</td>
				<td>
					<fmt:formatDate value="${accountCompanyhr.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${accountCompanyhr.remarks}
				</td>
				<shiro:hasPermission name="account:accounthr:accountCompanyhr:edit"><td>
    				<a href="${ctx}/account/accounthr/accountCompanyhr/form?id=${accountCompanyhr.id}">修改</a>
					<a href="${ctx}/account/accounthr/accountCompanyhr/delete?id=${accountCompanyhr.id}" onclick="return confirmx('确认要删除该hr吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>