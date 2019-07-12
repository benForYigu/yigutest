<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
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
		<li class="active"><a href="${ctx}/order/order/order/">订单列表</a></li>
		<%--<shiro:hasPermission name="order:order:order:edit"><li><a href="${ctx}/order/order/order/form">订单添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="order" action="${ctx}/order/order/order/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%--<li><label>宣讲id：</label>
				<form:input path="teachinId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>企业id：</label>
				<form:input path="companyId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>用户id：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>--%>
				<li><label>支付状态：</label>
					<form:select path="status" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
			<li><label>购买方式：</label>
				<form:select path="payType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pay_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>企业</th>
				<th>用户</th>
				<th>宣讲名称</th>
				<th>支付时间</th>
				<th>购买方式</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="order:order:order:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="order">
			<tr>
				<td>
						${order.companyhr.companyName}
				</td>
				<td>
						${order.companyhr.realname}
				</td>
				<td>
						${order.teachin.title}
				<td>
					<fmt:formatDate value="${order.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(order.payType, 'pay_type', '')}
				</td>
				<td>
					${fns:getDictLabel(order.status, 'pay_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${order.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${order.remarks}
				</td>
				<shiro:hasPermission name="order:order:order:edit"><td>
    				<a href="${ctx}/order/order/order/form?id=${order.id}">${order.payType=='4'?"审核":"查看"}</a>
					<c:if test="${not empty order.invoicesId}">
    				<a href="${ctx}/orderinvoices/orderInvoices/form?orderId=${order.id}&flag=1">
							${order.orderInvoices.status=='2'?"发票寄送":""}
							${order.orderInvoices.status=='3'?"查看发票":""}
					</a>
					</c:if>
						<%--<a href="${ctx}/order/order/order/delete?id=${order.id}" onclick="return confirmx('确认要删除该订单吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>