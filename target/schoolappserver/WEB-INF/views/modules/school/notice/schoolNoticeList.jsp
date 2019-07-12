<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校公告管理</title>
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
		<li ><a href="${ctx}/school/school/">学校列表</a></li>
		<li class="active"><a href="${ctx}/school/notice/schoolNotice?schoolId=${schoolNotice.schoolId}">学校公告列表</a></li>
		<li><a href="${ctx}/school/notice/schoolNotice/form?schoolId=${schoolNotice.schoolId}">学校公告添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="schoolNotice" action="${ctx}/school/notice/schoolNotice?schoolId=${schoolNotice.schoolId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('shelves_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>学校名称</th>
				<th>图片</th>
				<th>标题</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="schoolNotice">
			<tr>
				<td><a href="${ctx}/school/notice/schoolNotice/form?id=${schoolNotice.id}">
					${schoolNotice.schoolName}
				</a></td>
				<td>
					${schoolNotice.img}
				</td>
				<td>
					${schoolNotice.title}
				</td>
				<td>
					${fns:getDictLabel(schoolNotice.status, 'shelves_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${schoolNotice.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${schoolNotice.remarks}
				</td>
				<td>
    				<a href="${ctx}/school/notice/schoolNotice/form?id=${schoolNotice.id}">修改</a>
					<a href="${ctx}/school/notice/schoolNotice/delete?id=${schoolNotice.id}" onclick="return confirmx('确认要删除该学校公告吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>