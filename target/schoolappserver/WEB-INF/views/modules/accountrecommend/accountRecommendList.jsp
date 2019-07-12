<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推荐记录管理</title>
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
		<li class="active"><a href="${ctx}/accountrecommend/accountRecommend/">推荐记录列表</a></li>
		<shiro:hasPermission name="accountrecommend:accountRecommend:edit"><li><a href="${ctx}/accountrecommend/accountRecommend/form">推荐记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="accountRecommend" action="${ctx}/accountrecommend/accountRecommend/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<ul class="ul-form">
			<li><label>推荐人id：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>被推荐人id：</label>
				<form:input path="beAccountId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>推荐人</th>

				<th>被推荐人</th>
				<th>推荐权限</th>
				<th>更新时间</th>

				<shiro:hasPermission name="accountrecommend:accountRecommend:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accountRecommend">
			<tr>
				<td>
					${accountRecommend.inviteAccount.username}
				</td>
				<td>
					${accountRecommend.beInviteAccount.username}
				</td>
				<td>
					${fns:getDictLabel(accountRecommend.beInviteAccount.role,"account_role" ,'' )}
				</td>
				<td>
					<fmt:formatDate value="${accountRecommend.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>

				<shiro:hasPermission name="accountrecommend:accountRecommend:edit"><td>
    				<a href="${ctx}/accountrecommend/accountRecommend/form?id=${accountRecommend.id}">修改</a>
					<a href="${ctx}/accountrecommend/accountRecommend/delete?id=${accountRecommend.id}" onclick="return confirmx('确认要删除该推荐记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>