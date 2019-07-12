<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>双选会报名管理</title>
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
		<li class="active"><a href="${ctx}/unders/sign/undersSign/">双选会报名列表</a></li>
		<shiro:hasPermission name="unders:sign:undersSign:edit"><li><a href="${ctx}/unders/sign/undersSign/form">双选会报名添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="undersSign" action="${ctx}/unders/sign/undersSign/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%--<li><label>双选会id：</label>
				<form:input path="undersId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>企业id：</label>
				<form:input path="companyId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>邮箱：</label>
				<form:input path="email" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>联系人1：</label>
				<form:input path="contact1" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>联系电话1：</label>
				<form:input path="phone1" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>联系人2：</label>
				<form:input path="contact2" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>联系电话2：</label>
				<form:input path="phone2" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>数量：</label>
				<form:input path="content" htmlEscape="false" maxlength="1000" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>--%>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>双选会</th>

				<th>企业</th>
				<th>邮箱</th>
				<th>联系人1</th>
				<th>联系电话1</th>
				<th>联系人2</th>
				<th>联系电话2</th>
				<th>数量</th>
				<th>更新时间</th>

				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="undersSign">
			<tr>
				<td><a href="${ctx}/unders/sign/undersSign/form?id=${undersSign.id}">
					${undersSign.unders.title}
				</a></td>

				<td>
					${undersSign.companyhr.companyName}
				</td>
				<td>
					${undersSign.email}
				</td>
				<td>
					${undersSign.contact1}
				</td>
				<td>
					${undersSign.phone1}
				</td>
				<td>
					${undersSign.contact2}
				</td>
				<td>
					${undersSign.phone2}
				</td>
				<td>
					${undersSign.content}
				</td>
				<td>
					<fmt:formatDate value="${undersSign.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>

				<td>
    				<a href="${ctx}/unders/sign/undersSign/form?id=${undersSign.id}">查看</a>

				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>