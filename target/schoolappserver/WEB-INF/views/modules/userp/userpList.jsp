<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公众号用户管理</title>
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
		<li class="active"><a href="${ctx}/userp/userp/">公众号用户列表</a></li>
		<shiro:hasPermission name="userp:userp:edit"><li><a href="${ctx}/userp/userp/form">公众号用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userp" action="${ctx}/userp/userp/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。：</label>
				<form:input path="subscribe" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>openid：</label>
				<form:input path="openid" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>unionid：</label>
				<form:input path="unionid" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>昵称：</label>
				<form:input path="nickname" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>用户的性别，值为1时是男性，值为2时是女性，值为0时是未知：</label>
				<form:input path="sex" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>订阅时间：</label>
				<form:input path="subscribeTime" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。</th>
				<th>openid</th>
				<th>unionid</th>
				<th>昵称</th>
				<th>用户的性别，值为1时是男性，值为2时是女性，值为0时是未知</th>
				<th>城市</th>
				<th>省份</th>
				<th>国家</th>
				<th>头像</th>
				<th>订阅时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="userp:userp:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userp">
			<tr>
				<td><a href="${ctx}/userp/userp/form?id=${userp.id}">
					${userp.subscribe}
				</a></td>
				<td>
					${userp.openid}
				</td>
				<td>
					${userp.unionid}
				</td>
				<td>
					${userp.nickname}
				</td>
				<td>
					${userp.sex}
				</td>
				<td>
					${userp.city}
				</td>
				<td>
					${userp.province}
				</td>
				<td>
					${userp.country}
				</td>
				<td>
					${userp.headimgurl}
				</td>
				<td>
					${userp.subscribeTime}
				</td>
				<td>
					<fmt:formatDate value="${userp.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${userp.remarks}
				</td>
				<shiro:hasPermission name="userp:userp:edit"><td>
    				<a href="${ctx}/userp/userp/form?id=${userp.id}">修改</a>
					<a href="${ctx}/userp/userp/delete?id=${userp.id}" onclick="return confirmx('确认要删除该公众号用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>