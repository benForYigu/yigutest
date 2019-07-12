<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>宣讲管理</title>
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
		<li class="active"><a href="${ctx}/interaction/teachin/interactionTeachin/">宣讲列表</a></li>
		<c:if test="${companyUsers==null}"><shiro:hasPermission name="interaction:teachin:interactionTeachin:edit"><li><a href="${ctx}/interaction/teachin/interactionTeachin/form">宣讲添加</a></li></shiro:hasPermission></c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="interactionTeachin" action="${ctx}/interaction/teachin/interactionTeachin/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<c:if test="${companyUsers==null}">
			<li><label>企业：</label>
				<form:select path="companyId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${companys}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			</c:if>
			<li><label>学校：</label>
				<form:select path="schoolId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${schools}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>


			<li><label>进行状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('teachin_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否上架：</label>
				<form:select path="shelf" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('teachin_shelf')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>宣讲时间：</label>
				<input name="date"  type="text"  class="input-medium Wdate "
					   value="<fmt:formatDate value="${interactionTeachin.date}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

			</li>
			<li><label>宣讲名称：</label>
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
				<th>企业</th>

				<th>学校</th>
				<th>图片</th>
				<th>宣讲名称</th>
				<th>宣讲日期</th>
				<th>宣讲时间段</th>
				<th>进行状态</th>
				<th>是否上架</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="interaction:teachin:interactionTeachin:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="interactionTeachin">
			<tr>
				<td><a href="${ctx}/interaction/teachin/interactionTeachin/form?id=${interactionTeachin.id}">
					${interactionTeachin.company.name}
				</a></td>

				<td>
					${interactionTeachin.school.name}
				</td>
				<td>
					<img style="height: 50px;width: 50px" src="${interactionTeachin.img}">
				</td>
				<td>
					${interactionTeachin.title}
				</td>
				<td>
					<fmt:formatDate value="${interactionTeachin.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(interactionTeachin.time, 'teachin_time', '')}
				</td>
				<td>
					${fns:getDictLabel(interactionTeachin.status, 'teachin_status', '')}
				</td>
				<td>
					${fns:getDictLabel(interactionTeachin.shelf, 'teachin_shelf', '')}
				</td>
				<td>
					<fmt:formatDate value="${interactionTeachin.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${interactionTeachin.remarks}
				</td>
				<shiro:hasPermission name="interaction:teachin:interactionTeachin:edit"><td>
    				<a href="${ctx}/interaction/teachin/interactionTeachin/form?id=${interactionTeachin.id}">修改</a>
					<c:if test="${companyUsers==null}">	<a href="${ctx}/interaction/teachin/interactionTeachin/delete?id=${interactionTeachin.id}" onclick="return confirmx('确认要删除该宣讲吗？', this.href)">删除</a></c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>