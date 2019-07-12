<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校管理</title>
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
		<li class="active"><a href="${ctx}/school/school/">学校列表</a></li>
		<shiro:hasPermission name="school:school:edit"><li><a href="${ctx}/school/school/form">学校添加</a></li></shiro:hasPermission>

	</ul>
	<form:form id="searchForm" modelAttribute="school" action="${ctx}/school/school/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>全称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<%--<li><label>类型：</label>
				<form:input path="type" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>隶属：</label>
				<form:input path="jurisdiction" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>性质：</label>
				<form:input path="natrue" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>类别：</label>
				<form:input path="profession" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>--%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>全称</th>
				<th>logo</th>
				<%--<th>隶属</th>--%>

				<th>区域</th>
				<th>是否热门</th>
				<th>排序值</th>
				<th>更新时间</th>
				<%--<th>备注信息</th>--%>
				<shiro:hasPermission name="school:school:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="school">
			<tr>
				<td><a href="${ctx}/school/school/form?id=${school.id}">
					${school.name}
				</a></td>
				<td>
					<img style="width: 50px;height: 50px" src="${school.logo}">
				</td>
				<%--<td>
					${school.jurisdiction}
				</td>--%>
				<%--<td>
					${school.area}
				</td>--%>
				<td>
					${fns:getAreaLabel(school.city)}
				</td>
				<td>
						${fns:getDictLabel(school.hot, 'yes_no', '')}
				</td>
				<td>
					${school.sort}
				</td>
				<td>
					<fmt:formatDate value="${school.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>
					${school.remarks}
				</td>--%>
				<shiro:hasPermission name="school:school:edit"><td>
    				<a href="${ctx}/school/school/form?id=${school.id}">修改</a>

					<a href="${ctx}/school/school/delete?id=${school.id}" onclick="return confirmx('确认要删除该学校吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>