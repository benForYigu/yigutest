<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/school/school/">学校列表</a></li>
		<li class="active"><a href="${ctx}/school/school/form?id=${school.id}">学校<shiro:hasPermission name="school:school:edit">${not empty school.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="school:school:edit">查看</shiro:lacksPermission></a></li>

		<c:if test="${not empty school.id}">
			<li><a href="${ctx}/school/notice/schoolNotice?schoolId=${school.id}">学校公告</a></li>
		</c:if>

	</ul><br/>
	<form:form id="inputForm" modelAttribute="school" action="${ctx}/school/school/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">全称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">LOGO：</label>
			<div class="controls">
				<form:hidden id="logo" path="logo" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="logo" type="images" uploadPath="/school/school" selectMultiple="false"/>


			</div>
		</div>
		<div class="control-group">
			<label class="control-label">热门：</label>
			<div class="controls">
				<form:select path="hot" class="input-small ">

					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" items="${fns:getDictList('school_type')}" itemLabel="label" itemValue="value" class="input-small "/>


			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">隶属：</label>
			<div class="controls">
				<form:input path="jurisdiction" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教育部统一编码：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>--%>

		<div class="control-group">
			<label class="control-label">双一流：</label>
			<div class="controls">
				<form:select path="fD11" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" class="input-small "/>


			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">双一流学科数量：</label>
			<div class="controls">
				<form:input path="slD11" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">211工程：</label>
			<div class="controls">
				<form:select path="f211" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" class="input-small "/>

			</div>
		</div>
	<%--	<div class="control-group">
			<label class="control-label">985平台：</label>
			<div class="controls">
				<form:input path="f985pt" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">985工程：</label>
			<div class="controls">
				<%--<form:input path="f985" htmlEscape="false" maxlength="2" class="input-xlarge "/>--%>
				<form:select path="f985" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" class="input-small "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性质：</label>
			<div class="controls">
				<form:select path="natrue" items="${fns:getDictList('school_nature')}" itemLabel="label" itemValue="value" class="input-small "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">类别：</label>
			<div class="controls">
				<form:input path="profession" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>--%>


		<div class="control-group">
			<label class="control-label">区域：</label>
			<div class="controls">
				<sys:treeselect id="city" name="city" value="${school.city}" labelName="" labelValue="${fns:getAreaLabel(school.city)}"
								title="区域" url="/sys/area/treeData" cssClass="" allowClear="true"/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序值：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="20" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简介：</label>
			<div class="controls">
				<form:textarea path="introduce" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="2" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="school:school:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>