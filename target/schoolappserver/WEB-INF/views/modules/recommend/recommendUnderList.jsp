<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>线下活动管理</title>
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
		<li class="active"><a href="${ctx}/recommend/recommendUnder/">线下活动列表</a></li>
		<%--<shiro:hasPermission name="recommend:recommendUnder:edit"><li><a href="${ctx}/recommend/recommendUnder/form">线下活动添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="recommendUnder" action="${ctx}/recommend/recommendUnder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('recommend_under')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>类型</th>
				<th>标题</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="recommend:recommendUnder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="recommendUnder">
			<tr>
				<td><a href="${ctx}/recommend/recommendUnder/form?id=${recommendUnder.id}">
					${fns:getDictLabel(recommendUnder.type, 'recommend_under', '')}
				</a></td>
				<td>
					${recommendUnder.title}
				</td>
				<td>
					<fmt:formatDate value="${recommendUnder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${recommendUnder.remarks}
				</td>
				<shiro:hasPermission name="recommend:recommendUnder:edit"><td>
    				<a href="${ctx}/recommend/recommendUnder/form?id=${recommendUnder.id}">修改</a>
					<a href="${ctx}/recommend/recommendUnder/delete?id=${recommendUnder.id}" onclick="return confirmx('确认要删除该线下活动吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>