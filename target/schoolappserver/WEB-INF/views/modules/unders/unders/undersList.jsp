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
		<li class="active"><a href="${ctx}/unders/unders/unders?undersId=${orderUnders.undersId}">线下活动列表</a></li>
		<shiro:hasPermission name="unders:unders:unders:edit"><li><a href="${ctx}/unders/unders/unders/form">线下活动添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="unders" action="${ctx}/unders/unders/unders/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%--<li><label>类型1线下宣讲 2线下双选会：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>--%>
			<%--<li><label>学校id：</label>
				<form:input path="schoolId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>--%>
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
				<th>名称</th>
				<th>学校</th>
				<%--<th>图片</th>--%>
				<th>标题</th>
				<th>副标题</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>报名开始</th>
				<th>报名结束</th>
				<th>规模数量</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="unders:unders:unders:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="unders">
			<tr>
				<td><a href="${ctx}/unders/unders/unders/form?id=${unders.id}">
					${fns:getDictLabel(unders.type, 'recommend_under', '')}
				</a></td>
				<td>
					${unders.schoolName}
				</td>
				<%--<td>
					<img src="${unders.img}" style="width: 50px;height: 50px">
				</td>--%>
				<td>
					${unders.title}
				</td>
				<td>
					${unders.subTitle}
				</td>
				<td>
					<fmt:formatDate value="${unders.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${unders.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${unders.signStart}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${unders.signEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${unders.number}
				</td>
				<td>
					<fmt:formatDate value="${unders.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${unders.remarks}
				</td>
				<shiro:hasPermission name="unders:unders:unders:edit"><td>
					<a href="${ctx}/unders/sign/undersSign/?undersId=${unders.id}">报名列表</a>
    				<a href="${ctx}/unders/unders/unders/form?id=${unders.id}">修改</a>
					<a href="${ctx}/unders/unders/unders/delete?id=${unders.id}" onclick="return confirmx('确认要删除该线下活动吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>