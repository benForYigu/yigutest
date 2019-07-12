<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业认证信息管理</title>
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
		<c:if test="${empty companyUsers}">
			<li><a href="${ctx}/company/company/">企业列表</a></li>
		</c:if>

		<li ><a href="${ctx}/company/company/form?id=${companyAuth.id}">企业<shiro:hasPermission name="company:company:edit">${not empty company.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="company:company:edit">查看</shiro:lacksPermission></a></li>

	<%--<li><a href="${ctx}/company/auth/companyAuth/">企业认证信息列表</a></li>--%>
		<li class="active"><a href="${ctx}/company/auth/companyAuth/form?id=${companyAuth.id}">认证信息</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="companyAuth" action="${ctx}/company/auth/companyAuth/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="companyId"/>
		<sys:message content="${message}"/>
		<%--<div class="control-group">
			<label class="control-label">company_id：</label>
			<div class="controls">
				<form:input path="companyId" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">logo：</label>
			<div class="controls">
				<form:hidden id="logo" path="logo" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="logo" type="images" uploadPath="/company/auth/companyAuth" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行业：</label>
			<div class="controls">

				<%--<form:select path="industry" items="${fns:getDictList('industry')}" itemLabel="name" itemValue="id" class="input-xlarge "/>--%>
					<sys:treeselect id="industry" name="industry" value="${companyAuth.industry}" labelName="industry.name"
									labelValue="${fns:getindustryLabel(companyAuth.industry)}" title="行业" url="/dict/industry/dictIndustry/treeData" cssClass="input-small"
									allowClear="true" notAllowSelectParent="true"/>


			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业网址：</label>
			<div class="controls">
				<form:input path="webSite" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		${companyHr.vip}
		<c:if test="${empty companyUsers or ( companyHr.vip!=null and companyHr.vip!='')}">
		<div class="control-group">
			<label class="control-label">公司视频：</label>
			<div class="controls">
				<form:hidden id="video" path="video" htmlEscape="false" class="input-xlarge"/>

				<sys:ckfinder input="video" type="flash" uploadPath="/company/auth/companyAuth/video" selectMultiple="false"/>
			</div>
		</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">公司照片：</label>
			<div class="controls">
				<form:hidden id="img" path="img" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="img" type="images" uploadPath="/company/auth/companyAuth" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业规模：</label>
			<div class="controls">

				<form:select path="scale" items="${fns:getDictList('company_scale')}" itemLabel="label" itemValue="value"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照：</label>
			<div class="controls">
				<form:hidden id="businessLicence" path="businessLicence" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="businessLicence" type="images" uploadPath="/company/auth/businessLicence" selectMultiple="false"/>

			</div>
		</div>

		<c:if test="${not empty companyUsers}" >
			<div class="control-group">
				<label class="control-label">认证状态：</label>
				<div class="controls">
					<form:select path="status" disabled="true"  class="input-xlarge ">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('authentication')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
			</div>

		</c:if>
		<c:if test="${ empty companyUsers}" >
			<div class="control-group">
				<label class="control-label">认证状态：</label>
				<div class="controls">
					<form:select path="status"   class="input-xlarge ">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('authentication')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
			</div>

		</c:if>

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