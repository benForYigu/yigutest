<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校公告管理</title>
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
		<li ><a href="${ctx}/school/school/">学校列表</a></li>
		<li><a href="${ctx}/school/notice/schoolNotice?schoolId=${schoolNotice.schoolId}">学校公告列表</a></li>
		<li class="active"><a href="${ctx}/school/notice/schoolNotice/form?id=${schoolNotice.id}&schoolId=${schoolNotice.schoolId}">学校公告<shiro:hasPermission name="school:notice:schoolNotice:edit">${not empty schoolNotice.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="school:notice:schoolNotice:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="schoolNotice" action="${ctx}/school/notice/schoolNotice/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="schoolId"/>
		<sys:message content="${message}"/>
		<%--<div class="control-group">
			<label class="control-label">学校名称：</label>
			<div class="controls">
				<form:input path="schoolName" htmlEscape="false" maxlength="100" class="input-xlarge " disabled="true"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<form:hidden id="img" path="img" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="img" type="images" uploadPath="/school/notice/schoolNotice" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">子标题：</label>
			<div class="controls">
				<form:input path="subTitle" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('shelves_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>