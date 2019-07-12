<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校宣讲时间管理</title>
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
		<li class="active"><a href="${ctx}/school/ocupy/schoolTeachinOcupy/">学校宣讲占用列表</a></li>
		<li><a href="${ctx}/school/ocupy/schoolTeachinOcupy/form">学校宣讲占用添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="schoolTeachinOcupy" action="${ctx}/school/ocupy/schoolTeachinOcupy/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学校：</label>
				<form:select path="schoolId"  class="input-medium">
					<form:option value="" label=""></form:option>
					<form:options items="${schools}" itemLabel="name" itemValue="id"></form:options>
				</form:select>
			</li>
			<li><label>日期：</label>
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate  value="${schoolTeachinOcupy.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>宣讲时间段：</label>
				<form:select path="time" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('teachin_time')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>日期</th>
				<th>宣讲时间段</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="schoolTeachinOcupy">
			<tr>
				<td>
					${schoolTeachinOcupy.school.name}
				</td>
				<td>
					<fmt:formatDate value="${schoolTeachinOcupy.date}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(schoolTeachinOcupy.time, 'teachin_time', '')}
				</td>
				<td>
					<fmt:formatDate value="${schoolTeachinOcupy.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${schoolTeachinOcupy.remarks}
				</td>
				<td>
    				<a href="${ctx}/school/ocupy/schoolTeachinOcupy/form?id=${schoolTeachinOcupy.id}">修改</a>
					<a href="${ctx}/school/ocupy/schoolTeachinOcupy/delete?id=${schoolTeachinOcupy.id}" onclick="return confirmx('确认要删除该学校宣讲时间吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>