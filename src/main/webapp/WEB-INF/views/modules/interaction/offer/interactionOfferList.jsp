<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>offer管理</title>
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
		<li class="active"><a href="${ctx}/interaction/offer/interactionOffer/">offer列表</a></li>
		<shiro:hasPermission name="interaction:offer:interactionOffer:edit"><li><a href="${ctx}/interaction/offer/interactionOffer/form">offer添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="interactionOffer" action="${ctx}/interaction/offer/interactionOffer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">

			<li><label>学生：</label>
				<form:input path="studentId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>企业：</label>
				<form:input path="companyId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>确认状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('offer_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>职位</th>
				<th>入职时间</th>
				<th>省份</th>
				<th>确认状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="interaction:offer:interactionOffer:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="interactionOffer">
			<tr>
				<td><a href="${ctx}/interaction/offer/interactionOffer/form?id=${interactionOffer.id}">
					${interactionOffer.position}
				</a></td>
				<td>
					<fmt:formatDate value="${interactionOffer.entryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${interactionOffer.province}
				</td>
				<td>
					${fns:getDictLabel(interactionOffer.status, 'offer_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${interactionOffer.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${interactionOffer.remarks}
				</td>
				<shiro:hasPermission name="interaction:offer:interactionOffer:edit"><td>
    				<a href="${ctx}/interaction/offer/interactionOffer/form?id=${interactionOffer.id}">修改</a>
					<a href="${ctx}/interaction/offer/interactionOffer/delete?id=${interactionOffer.id}" onclick="return confirmx('确认要删除该offer吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>