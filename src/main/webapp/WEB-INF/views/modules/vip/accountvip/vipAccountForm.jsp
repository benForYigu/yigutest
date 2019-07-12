<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户权益管理</title>
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
		<li><a href="${ctx}/vip/accountvip/vipAccount/">用户权益列表</a></li>
		<li class="active"><a href="${ctx}/vip/accountvip/vipAccount/form?id=${vipAccount.id}">用户权益<shiro:hasPermission name="vip:accountvip:vipAccount:edit">${not empty vipAccount.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="vip:accountvip:vipAccount:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="vipAccount" action="${ctx}/vip/accountvip/vipAccount/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户id：</label>
			<div class="controls">
				<form:input path="accountId" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">校外信息发布：</label>
			<div class="controls">
				<form:select path="offCampusInformation" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('vip_equity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">招聘岗位：</label>
			<div class="controls">
				<form:select path="profession" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('vip_equity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">线下宣讲查看：</label>
			<div class="controls">
				<form:select path="underTeachinRead" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('vip_equity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">线下宣讲报名：</label>
			<div class="controls">
				<form:select path="underTeachinSign" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('vip_equity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">双选会查看：</label>
			<div class="controls">
				<form:select path="doubleRead" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('vip_equity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">双选会报名：</label>
			<div class="controls">
				<form:select path="doubleSign" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('vip_equity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">宣讲：</label>
			<div class="controls">
				<form:input path="teachin" htmlEscape="false" maxlength="10" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业宣传视频：</label>
			<div class="controls">
				<form:input path="video" htmlEscape="false" maxlength="10" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">校企交流会：</label>
			<div class="controls">
				<form:select path="exchangeMeeting" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('vip_equity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="vip:accountvip:vipAccount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>