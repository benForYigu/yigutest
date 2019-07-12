<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>测试表管理</title>
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
		function exportData(url){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action",url);
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			/*$.ajax({
				url:url,
				type:"get",
				data: $("#searchForm").serialize(),
				dataType:'json',
				success:function(data) {
					window.open(data.responseText);
				},
				error:function (data) {
					window.open(data.responseText);
				}
			});*/
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/testsex/aBenTest/">测试列表</a></li>
		<shiro:hasPermission name="testsex:aBenTest:edit"><li><a href="${ctx}/testsex/aBenTest/form">测试添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ABenTest" action="${ctx}/testsex/aBenTest/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form" >
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="36" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns" style="float:right"><button type="button" class="btn btn-info" onclick="exportData('${ctx}/testsex/aBenTest/export')">导出</button></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>性别</th>
				<th>手机号</th>
				<th>备注</th>
				<th>备注信息</th>
				<shiro:hasPermission name="testsex:aBenTest:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="aBenTest">
			<tr>
				<td><a href="${ctx}/testsex/aBenTest/form?id=${aBenTest.id}">
					${aBenTest.name}
			</a></td>
				<td>
						${aBenTest.sex}
				</td>
				<td>
						${aBenTest.mobile}
				</td>
				<td>
					<fmt:formatDate value="${aBenTest.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${aBenTest.remarks}
				</td>
				<shiro:hasPermission name="testsex:aBenTest:edit"><td>
    				<a href="${ctx}/testsex/aBenTest/form?id=${aBenTest.id}">修改</a>
					<a href="${ctx}/testsex/aBenTest/delete?id=${aBenTest.id}" onclick="return confirmx('确认要删除该测试表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>