<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校人员管理</title>
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
		<li class="active"><a href="${ctx}/account/accountschool/accountTeacherinfo/">学校人员列表</a></li>
		<%--<shiro:hasPermission name="account:accountschool:accountTeacherinfo:edit"><li><a href="${ctx}/account/accountschool/accountTeacherinfo/form">学校人员添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="accountTeacherinfo" action="${ctx}/account/accountschool/accountTeacherinfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学校：</label>
				<form:select path="schoolId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${schools}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>电话：</label>
				<form:input path="phone" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>认证状态：</label>
				<form:select path="idCardStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('authentication')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>头像</th>
				<th>真实姓名</th>
				<th>电话</th>
				<th>性别</th>
				<th>身份证认证状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="account:accountschool:accountTeacherinfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accountTeacherinfo">
			<tr>
				<td>
					<img style="width: 50px;height: 50px" src="${accountTeacherinfo.avatar}"/>
				</td>
				<td>
					${accountTeacherinfo.realname}
				</td>
				<td>
					${accountTeacherinfo.phone}
				</td>
				<td>
					${fns:getDictLabel(accountTeacherinfo.sex, 'sex', '')}
				</td>
				<td>

					${fns:getDictLabel(accountTeacherinfo.idCardStatus, 'authentication', '')}
				</td>
				<td>
					<fmt:formatDate value="${accountTeacherinfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${accountTeacherinfo.remarks}
				</td>
				<shiro:hasPermission name="account:accountschool:accountTeacherinfo:edit"><td>
    				<a href="${ctx}/account/accountschool/accountTeacherinfo/form?id=${accountTeacherinfo.id}">修改</a>
					<a href="${ctx}/account/accountschool/accountTeacherinfo/delete?id=${accountTeacherinfo.id}" onclick="return confirmx('确认要删除该学校人员吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>