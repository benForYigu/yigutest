<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推荐位管理</title>
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
		<li class="active"><a href="${ctx}/interaction/recommend/interactionRecommend/">推荐位列表</a></li>
		<shiro:hasPermission name="interaction:recommend:interactionRecommend:edit"><li><a href="${ctx}/interaction/recommend/interactionRecommend/form">推荐位添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="interactionRecommend" action="${ctx}/interaction/recommend/interactionRecommend/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">

			<li><label>推荐位类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('recommend_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<%--<li><label>链接类型：</label>
				<form:select path="linkType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('recommend_link_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>--%>
			<li><label>支持企业：</label>
				<form:select path="suportCompany" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('recommend_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>标题</th>
				<th>副标题</th>
				<%--<th>适用学校</th>--%>
				<th>排序</th>
				<th>图片</th>
				<th>推荐位类型</th>
				<%--<th>链接</th>
				<th>链接类型</th>--%>
				<th>支持企业</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="interaction:recommend:interactionRecommend:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="interactionRecommend">
			<tr>
				<td><a href="${ctx}/interaction/recommend/interactionRecommend/form?id=${interactionRecommend.id}">
					${interactionRecommend.title}
				</a></td>
				<td>
					${interactionRecommend.subTitle}
				</td>
			<%--	<td>
					${interactionRecommend.applyTo}
				</td>--%>
				<td>
					${interactionRecommend.sort}
				</td>
				<td>
					<img style="width: 50px;height: 50px" src="${interactionRecommend.img}"/>

				</td>
				<td>
					${fns:getDictLabel(interactionRecommend.type, 'recommend_type', '')}
				</td>
					<%--<td>
                        ${interactionRecommend.link}
                    </td>
                    <td>
                        ${fns:getDictLabel(interactionRecommend.linkType, 'recommend_link_type', '')}
                    </td>--%>
				<td>
					${fns:getDictLabel(interactionRecommend.suportCompany, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(interactionRecommend.status, 'recommend_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${interactionRecommend.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${interactionRecommend.remarks}
				</td>
				<shiro:hasPermission name="interaction:recommend:interactionRecommend:edit"><td>
    				<a href="${ctx}/interaction/recommend/interactionRecommend/form?id=${interactionRecommend.id}">修改</a>
					<a href="${ctx}/interaction/recommend/interactionRecommend/delete?id=${interactionRecommend.id}" onclick="return confirmx('确认要删除该推荐位吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>