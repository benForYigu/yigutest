<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>宣讲聊天管理</title>
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
		<li class="active"><a href="${ctx}/interaction.teachin/chat/interactionTeachinChat/">宣讲聊天列表</a></li>
		<shiro:hasPermission name="interaction.teachin:chat:interactionTeachinChat:edit"><li><a href="${ctx}/interaction.teachin/chat/interactionTeachinChat/form">宣讲聊天添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="interactionTeachinChat" action="${ctx}/interaction.teachin/chat/interactionTeachinChat/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>宣讲id：</label>
				<form:input path="teachinId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>用户id：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>宣讲id</th>
				<th>头像</th>
				<th>姓名</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="interaction.teachin:chat:interactionTeachinChat:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="interactionTeachinChat">
			<tr>
				<td><a href="${ctx}/interaction.teachin/chat/interactionTeachinChat/form?id=${interactionTeachinChat.id}">
					${interactionTeachinChat.teachinId}
				</a></td>
				<td>
					${interactionTeachinChat.avatar}
				</td>
				<td>
					${interactionTeachinChat.realname}
				</td>
				<td>
					<fmt:formatDate value="${interactionTeachinChat.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${interactionTeachinChat.remarks}
				</td>
				<shiro:hasPermission name="interaction.teachin:chat:interactionTeachinChat:edit"><td>
    				<a href="${ctx}/interaction.teachin/chat/interactionTeachinChat/form?id=${interactionTeachinChat.id}">修改</a>
					<a href="${ctx}/interaction.teachin/chat/interactionTeachinChat/delete?id=${interactionTeachinChat.id}" onclick="return confirmx('确认要删除该宣讲聊天吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>