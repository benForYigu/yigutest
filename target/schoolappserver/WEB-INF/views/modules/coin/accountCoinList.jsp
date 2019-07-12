<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>岗币记录管理</title>
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
		<li class="active"><a href="${ctx}/coin/accountCoin/">岗币记录列表</a></li>
		<shiro:hasPermission name="coin:accountCoin:edit"><li><a href="${ctx}/coin/accountCoin/form">岗币记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="accountCoin" action="${ctx}/coin/accountCoin/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>来源类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('coin_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>港币数量：</label>
				<form:input path="coin" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户</th>
				<th>来源类型</th>
				<th>港币数量</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="coin:accountCoin:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accountCoin">
			<tr>
				<td><a href="${ctx}/coin/accountCoin/form?id=${accountCoin.id}">
					${accountCoin.accountId}
				</a></td>
				<td>
					${fns:getDictLabel(accountCoin.type, 'coin_type', '')}
				</td>
				<td>
					${accountCoin.coin}
				</td>
				<td>
					<fmt:formatDate value="${accountCoin.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${accountCoin.remarks}
				</td>
				<shiro:hasPermission name="coin:accountCoin:edit"><td>
    				<a href="${ctx}/coin/accountCoin/form?id=${accountCoin.id}">修改</a>
					<a href="${ctx}/coin/accountCoin/delete?id=${accountCoin.id}" onclick="return confirmx('确认要删除该岗币记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>