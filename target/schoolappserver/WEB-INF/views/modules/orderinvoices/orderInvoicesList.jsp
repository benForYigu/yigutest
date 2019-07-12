<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业发票管理</title>
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
		<li class="active"><a href="${ctx}/orderinvoices/orderInvoices/">企业发票列表</a></li>
		<shiro:hasPermission name="orderinvoices:orderInvoices:edit"><li><a href="${ctx}/orderinvoices/orderInvoices/form">企业发票添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderInvoices" action="${ctx}/orderinvoices/orderInvoices/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户id：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>企业id：</label>
				<form:input path="companyId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>订单id：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>联系人：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户id</th>
				<th>企业id</th>
				<th>订单id</th>
				<th>联系人</th>
				<th>联系人电话</th>
				<th>联系人地址</th>
				<th>税号</th>
				<th>单位名称</th>
				<th>单位地址</th>
				<th>单位电话</th>
				<th>开户银行</th>
				<th>银行卡号</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="orderinvoices:orderInvoices:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderInvoices">
			<tr>
				<td><a href="${ctx}/orderinvoices/orderInvoices/form?id=${orderInvoices.id}">
					${orderInvoices.accountId}
				</a></td>
				<td>
					${orderInvoices.companyId}
				</td>
				<td>
					${orderInvoices.orderId}
				</td>
				<td>
					${orderInvoices.name}
				</td>
				<td>
					${orderInvoices.phone}
				</td>
				<td>
					${orderInvoices.address}
				</td>
				<td>
					${orderInvoices.taxcode}
				</td>
				<td>
					${orderInvoices.companyName}
				</td>
				<td>
					${orderInvoices.companyAddress}
				</td>
				<td>
					${orderInvoices.companyPhone}
				</td>
				<td>
					${orderInvoices.bank}
				</td>
				<td>
					${orderInvoices.bankCard}
				</td>
				<td>
					<fmt:formatDate value="${orderInvoices.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${orderInvoices.remarks}
				</td>
				<shiro:hasPermission name="orderinvoices:orderInvoices:edit"><td>
    				<a href="${ctx}/orderinvoices/orderInvoices/form?id=${orderInvoices.id}">修改</a>
					<a href="${ctx}/orderinvoices/orderInvoices/delete?id=${orderInvoices.id}" onclick="return confirmx('确认要删除该企业发票吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>