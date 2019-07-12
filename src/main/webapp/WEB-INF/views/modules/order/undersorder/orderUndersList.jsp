<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>双选会订单管理</title>
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
		<li class="active"><a href="${ctx}/order/undersorder/orderUnders/">双选会订单列表</a></li>
		<%--<shiro:hasPermission name="order:undersorder:orderUnders:edit"><li><a href="${ctx}/order/undersorder/orderUnders/form">双选会订单添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="orderUnders" action="${ctx}/order/undersorder/orderUnders/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">

				<li><label>支付状态：</label>
					<form:select path="status" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
				<li><label>购买方式：</label>
					<form:select path="payType" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('normal_pay_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>活动</th>
				<th>企业</th>
				<th>用户</th>
				<th>订单金额</th>
				<th>支付时间</th>
				<th>完成时间</th>
				<th>交易号</th>
				<th>购买方式</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="order:undersorder:orderUnders:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderUnders">
			<tr>
				<td><a href="${ctx}/order/undersorder/orderUnders/form?id=${orderUnders.id}">
					${orderUnders.unders.title}
				</a></td>
				<td>
					${orderUnders.companyhr.companyName}
				</td>
				<td>
					${orderUnders.companyhr.realname}
				</td>
				<td>
					${orderUnders.payment}
				</td>
				<td>
					<fmt:formatDate value="${orderUnders.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${orderUnders.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${orderUnders.transactionId}
				</td>
				<td>
					${fns:getDictLabel(orderUnders.payType, 'normal_pay_type', '')}
				</td>
				<td>
					${fns:getDictLabel(orderUnders.status, 'pay_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${orderUnders.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${orderUnders.remarks}
				</td>
				<shiro:hasPermission name="order:undersorder:orderUnders:edit"><td>


    				<a href="${ctx}/order/undersorder/orderUnders/form?id=${orderUnders.id}">${orderUnders.payType=='4'?"审核":"查看"}</a>
					<c:if test="${not empty orderUnders.invoicesId}">
						<a href="${ctx}/orderinvoices/orderInvoices/form?orderId=${orderVip.id}&flag=2">
								${orderUnders.orderInvoices.status=='2'?"发票寄送":""}
								${orderUnders.orderInvoices.status=='3'?"查看发票":""}
						</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>