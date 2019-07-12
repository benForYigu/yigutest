<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>面试评论管理</title>
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
		<li class="active"><a href="${ctx}/interaction.interview/comment/interactionInterviewComment/">面试评论列表</a></li>
		<shiro:hasPermission name="interaction.interview:comment:interactionInterviewComment:edit"><li><a href="${ctx}/interaction.interview/comment/interactionInterviewComment/form">面试评论添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="interactionInterviewComment" action="${ctx}/interaction.interview/comment/interactionInterviewComment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学生id：</label>
				<form:input path="interviewId" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>学生id</th>
				<th>魅力度</th>
				<th>亲和度</th>
				<th>素质度</th>
				<th>积极度</th>
				<th>文化度</th>
				<th>意识度</th>
				<th>兴趣度</th>
				<th>信赖度</th>
				<th>融入度</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="interaction.interview:comment:interactionInterviewComment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="interactionInterviewComment">
			<tr>
				<td><a href="${ctx}/interaction.interview/comment/interactionInterviewComment/form?id=${interactionInterviewComment.id}">
					${interactionInterviewComment.interviewId}
				</a></td>
				<td>
					${interactionInterviewComment.charm}
				</td>
				<td>
					${interactionInterviewComment.affinity}
				</td>
				<td>
					${interactionInterviewComment.quality}
				</td>
				<td>
					${interactionInterviewComment.positive}
				</td>
				<td>
					${interactionInterviewComment.culture}
				</td>
				<td>
					${interactionInterviewComment.consciousness}
				</td>
				<td>
					${interactionInterviewComment.interest}
				</td>
				<td>
					${interactionInterviewComment.trust}
				</td>
				<td>
					${interactionInterviewComment.integrate}
				</td>
				<td>
					<fmt:formatDate value="${interactionInterviewComment.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${interactionInterviewComment.remarks}
				</td>
				<shiro:hasPermission name="interaction.interview:comment:interactionInterviewComment:edit"><td>
    				<a href="${ctx}/interaction.interview/comment/interactionInterviewComment/form?id=${interactionInterviewComment.id}">修改</a>
					<a href="${ctx}/interaction.interview/comment/interactionInterviewComment/delete?id=${interactionInterviewComment.id}" onclick="return confirmx('确认要删除该面试评论吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>