<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户权益管理</title>
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
		<li class="active"><a href="${ctx}/vip/accountvip/vipAccount/">用户权益列表</a></li>
		<shiro:hasPermission name="vip:accountvip:vipAccount:edit"><li><a href="${ctx}/vip/accountvip/vipAccount/form">用户权益添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="vipAccount" action="${ctx}/vip/accountvip/vipAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户id：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户id</th>
				<th>校外信息发布</th>
				<th>招聘岗位</th>
				<th>线下宣讲查看</th>
				<th>线下宣讲报名</th>
				<th>双选会查看</th>
				<th>双选会报名</th>
				<th>宣讲</th>
				<th>企业宣传视频</th>
				<th>校企交流会</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="vip:accountvip:vipAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="vipAccount">
			<tr>
				<td><a href="${ctx}/vip/accountvip/vipAccount/form?id=${vipAccount.id}">
					${vipAccount.accountId}
				</a></td>
				<td>
					${fns:getDictLabel(vipAccount.offCampusInformation, 'vip_equity', '')}
				</td>
				<td>
					${fns:getDictLabel(vipAccount.profession, 'vip_equity', '')}
				</td>
				<td>
					${fns:getDictLabel(vipAccount.underTeachinRead, 'vip_equity', '')}
				</td>
				<td>
					${fns:getDictLabel(vipAccount.underTeachinSign, 'vip_equity', '')}
				</td>
				<td>
					${fns:getDictLabel(vipAccount.doubleRead, 'vip_equity', '')}
				</td>
				<td>
					${fns:getDictLabel(vipAccount.doubleSign, 'vip_equity', '')}
				</td>
				<td>
					${vipAccount.teachin}
				</td>
				<td>
					${vipAccount.video}
				</td>
				<td>
					${fns:getDictLabel(vipAccount.exchangeMeeting, 'vip_equity', '')}
				</td>
				<td>
					<fmt:formatDate value="${vipAccount.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${vipAccount.remarks}
				</td>
				<shiro:hasPermission name="vip:accountvip:vipAccount:edit"><td>
    				<a href="${ctx}/vip/accountvip/vipAccount/form?id=${vipAccount.id}">修改</a>
					<a href="${ctx}/vip/accountvip/vipAccount/delete?id=${vipAccount.id}" onclick="return confirmx('确认要删除该用户权益吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>