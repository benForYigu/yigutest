<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息记录管理</title>
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
		<li class="active"><a href="${ctx}/messagelog/messageLog/">消息记录列表</a></li>
		<shiro:hasPermission name="messagelog:messageLog:edit"><li><a href="${ctx}/messagelog/messageLog/form">消息记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="messageLog" action="${ctx}/messagelog/messageLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学生id：</label>
				<form:input path="studentId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>企业id：</label>
				<form:input path="companyId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('message_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>面试方式：</label>
				<form:select path="interviewType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('interview_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否已读 1是 0否：</label>
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
				<th>类型</th>
				<th>面试方式</th>
				<th>面试时间</th>
				<th>时间更改次数</th>
				<th>是否已读 1是 0否</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="messagelog:messageLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="messageLog">
			<tr>
				<td><a href="${ctx}/messagelog/messageLog/form?id=${messageLog.id}">
					${fns:getDictLabel(messageLog.type, 'message_type', '')}
				</a></td>
				<td>
					${fns:getDictLabel(messageLog.interviewType, 'interview_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${messageLog.interviewTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${messageLog.changeTime}
				</td>
				<td>
					${fns:getDictLabel(messageLog.isRead, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${messageLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${messageLog.remarks}
				</td>
				<shiro:hasPermission name="messagelog:messageLog:edit"><td>
    				<a href="${ctx}/messagelog/messageLog/form?id=${messageLog.id}">修改</a>
					<a href="${ctx}/messagelog/messageLog/delete?id=${messageLog.id}" onclick="return confirmx('确认要删除该消息记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>