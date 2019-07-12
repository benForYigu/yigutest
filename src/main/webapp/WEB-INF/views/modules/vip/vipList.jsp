<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>vip管理</title>
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
		<li class="active"><a href="${ctx}/vip/vip/">vip列表</a></li>
		<shiro:hasPermission name="vip:vip:edit"><li><a href="${ctx}/vip/vip/form">vip添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="vip" action="${ctx}/vip/vip/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
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
				<th>价格</th>
				<th>校外信息发布</th>
				<th>招聘岗位</th>
				<th>线下宣讲查看</th>
				<th>线下宣讲报名</th>
				<th>双选会查看</th>
				<th>双选会报名</th>
				<th>宣讲</th>
				<th>面试时长</th>
				<th>企业宣传视频</th>
				<th>校企交流会</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="vip:vip:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="vip">
			<tr>
				<td><a href="${ctx}/vip/vip/form?id=${vip.id}">
					${vip.name}
				</a></td>
				<td>
					${vip.price}
				</td>
				<td>
					${fns:getDictLabel(vip.offCampusInformation, 'vip_equity', '')}
				</td>
				<td>
					${fns:getDictLabel(vip.profession, 'vip_equity', '')}
				</td>
				<td>
					${fns:getDictLabel(vip.underTeachinRead, 'vip_equity', '')}
				</td>
				<td>
					${fns:getDictLabel(vip.underTeachinSign, 'vip_equity', '')}
				</td>
				<td>
					${fns:getDictLabel(vip.doubleRead, 'vip_equity', '')}
				</td>
				<td>
					${fns:getDictLabel(vip.doubleSign, 'vip_equity', '')}
				</td>
				<td>
					${vip.teachin}
				</td>
				<td>
					${vip.interviewTime}
				</td>
				<td>
					${vip.video}
				</td>
				<td>
					${fns:getDictLabel(vip.exchangeMeeting, 'vip_equity', '')}
				</td>
				<td>
					<fmt:formatDate value="${vip.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${vip.remarks}
				</td>
				<shiro:hasPermission name="vip:vip:edit"><td>
    				<a href="${ctx}/vip/vip/form?id=${vip.id}">修改</a>
					<a href="${ctx}/vip/vip/delete?id=${vip.id}" onclick="return confirmx('确认要删除该vip吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>