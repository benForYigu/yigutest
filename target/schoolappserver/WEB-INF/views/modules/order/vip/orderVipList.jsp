<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>vip订单管理</title>
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
		<li class="active"><a href="${ctx}/order/vip/orderVip/">vip订单列表</a></li>
		<%--<shiro:hasPermission name="order:vip:orderVip:edit"><li><a href="${ctx}/order/vip/orderVip/form">vip订单添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="orderVip" action="${ctx}/order/vip/orderVip/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%--<li><label>企业id：</label>
				<form:input path="companyId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>用户id：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>--%>
			<%--<li><label>购买方式：</label>
				<form:select path="payType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('vip_pay_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
						<form:options items="${fns:getDictList('vip_pay_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>vip名称</th>
				<th>订单金额</th>
				<th>支付时间</th>
				<th>购买方式</th>
				<th>支付状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="order:vip:orderVip:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderVip">
			<tr>
				<td><a href="${ctx}/order/vip/orderVip/form?id=${orderVip.id}">
					${orderVip.companyhr.companyName}
				</a></td>
				<td>
					${orderVip.companyhr.realname}
				</td>
				<td>
					${orderVip.vip.name}
				</td>
				<td>
					${orderVip.payment}
				</td>
				<td>
					<fmt:formatDate value="${orderVip.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(orderVip.payType, 'vip_pay_type', '')}
				</td>
				<td>
					${fns:getDictLabel(orderVip.status, 'pay_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${orderVip.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${orderVip.remarks}
				</td>
				<shiro:hasPermission name="order:vip:orderVip:edit"><td>
					<a href="${ctx}/order/vip/orderVip/form?id=${orderVip.id}">${orderVip.payType=='4'?"审核":"查看"}</a>

					<c:if test="${not empty orderVip.invoicesId}">
						<a href="${ctx}/orderinvoices/orderInvoices/form?orderId=${orderVip.id}&flag=2">
								${orderVip.orderInvoices.status=='2'?"发票寄送":""}
								${orderVip.orderInvoices.status=='3'?"查看发票":""}
						</a>
					</c:if>
					<%--<a href="${ctx}/order/vip/orderVip/delete?id=${orderVip.id}" onclick="return confirmx('确认要删除该vip订单吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>