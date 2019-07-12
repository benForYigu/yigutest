<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
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
		<li class="active"><a href="${ctx}/account/accountstudent/accountStudentinfo/">学生列表</a></li>
		<%--<shiro:hasPermission name="account:accountstudent:accountStudentinfo:edit"><li><a href="${ctx}/account/accountstudent/accountStudentinfo/form">用户_学生添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="accountStudentinfo" action="${ctx}/account/accountstudent/accountStudentinfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">

			<li><label>学校：</label>
				<form:select path="schoolId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${schools}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>

			<li><label>姓名：</label>
				<form:input path="realname" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>电话：</label>
				<form:input path="phone" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>





			<li><label>认证状态：</label>
				<form:select path="studentIdStatus" class="input-medium">
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
				<%--<th>序号</th>--%>

				<th>头像</th>
				<th>学校</th>
				<th>学生认证</th>
				<th>姓名</th>
				<th>电话</th>



				<th>毕业时间</th>
				<th>更新时间</th>

				<shiro:hasPermission name="account:accountstudent:accountStudentinfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accountStudentinfo" varStatus="status">
			<tr>
				<%--<td><a href="${ctx}/account/accountstudent/accountStudentinfo/form?id=${accountStudentinfo.id}">
					${status.count}
				</a></td>--%>
				<td>
					<img style="width: 50px;height: 50px" src="${accountStudentinfo.avatar}"/>
				</td>
				<td>
						${accountStudentinfo.schoolName}


				</td>
				<td>
						${fns:getDictLabel(accountStudentinfo.studentIdStatus,"yes_no" ,'' )}

				</td>


				<td>
					${accountStudentinfo.realname}
				</td>
				<td>
					${accountStudentinfo.phone}
				</td>
				<td>
					<fmt:formatDate value="${accountStudentinfo.graduationTime}" pattern="yyyy-MM-dd"/>

				</td>


				<td>
					<fmt:formatDate value="${accountStudentinfo.graduationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>

				<shiro:hasPermission name="account:accountstudent:accountStudentinfo:edit"><td>
    				<a href="${ctx}/account/accountstudent/accountStudentinfo/form?id=${accountStudentinfo.id}">修改</a>
					<a href="${ctx}/account/accountstudent/accountStudentinfo/delete?id=${accountStudentinfo.id}" onclick="return confirmx('确认要删除该用户_学生吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>