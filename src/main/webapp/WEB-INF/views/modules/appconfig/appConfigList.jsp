<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配置数据管理</title>
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
		<li class="active"><a href="${ctx}/appconfig/appConfig/">配置数据列表</a></li>
		<shiro:hasPermission name="appconfig:appConfig:edit"><li><a href="${ctx}/appconfig/appConfig/form">配置数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appConfig" action="${ctx}/appconfig/appConfig/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>适配类型：</label>
				<form:select path="configType"  class="input-medium">
					<form:option value=""></form:option>
					<form:options items="${fns:getDictList('app_config_type')}" itemValue="value" itemLabel="label"></form:options>
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
				<th>版本名称</th>
				<th>版本号</th>
				<th>适配类型</th>
				<th>标题</th>
				<th>下载地址</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="appconfig:appConfig:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="appConfig">
			<tr>
				<td><a href="${ctx}/appconfig/appConfig/form?id=${appConfig.id}">
					${appConfig.version}
				</a></td>
				<td>
					${appConfig.name}
				</td>
				<td>
					${fns:getDictLabel(appConfig.configType,'app_config_type','' )}
				</td>
				<td>
					${appConfig.title}
				</td>
				<td>
					${appConfig.url}
				</td>
				<td>
					<fmt:formatDate value="${appConfig.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${appConfig.remarks}
				</td>
				<shiro:hasPermission name="appconfig:appConfig:edit"><td>
    				<a href="${ctx}/appconfig/appConfig/form?id=${appConfig.id}">修改</a>
					<%--<a href="${ctx}/appconfig/appConfig/delete?id=${appConfig.id}" onclick="return confirmx('确认要删除该配置数据吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>