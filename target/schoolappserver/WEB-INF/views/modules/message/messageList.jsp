<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息管理</title>
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
		<li class="active"><a href="${ctx}/message/message/">消息列表</a></li>
		<shiro:hasPermission name="message:message:edit"><li><a href="${ctx}/message/message/form">消息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="message" action="${ctx}/message/message/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>是否已读：</label>
				<form:select path="isRead" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>用户</th>
				<th>内容</th>
				<th>是否已读</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="message:message:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="message">
			<tr>
				<td><a href="${ctx}/message/message/form?id=${message.id}">
					${message.accountId}
				</a></td>
				<td>
					${message.content}
				</td>
				<td>
					${fns:getDictLabel(message.isRead, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${message.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${message.remarks}
				</td>
				<shiro:hasPermission name="message:message:edit"><td>
    				<a href="${ctx}/message/message/form?id=${message.id}">修改</a>
					<a href="${ctx}/message/message/delete?id=${message.id}" onclick="return confirmx('确认要删除该消息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>