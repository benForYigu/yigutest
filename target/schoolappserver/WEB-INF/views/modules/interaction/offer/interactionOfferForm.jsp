<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>offer管理</title>
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
		<li><a href="${ctx}/interaction/offer/interactionOffer/">offer列表</a></li>
		<li class="active"><a href="${ctx}/interaction/offer/interactionOffer/form?id=${interactionOffer.id}">offer<shiro:hasPermission name="interaction:offer:interactionOffer:edit">${not empty interactionOffer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="interaction:offer:interactionOffer:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="interactionOffer" action="${ctx}/interaction/offer/interactionOffer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="interviewId"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">学生：</label>
			<div class="controls">
				<form:input path="student.realname" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业：</label>
			<div class="controls">
				<form:input path="company.name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职位：</label>
			<div class="controls">
				<form:input path="position" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入职时间：</label>
			<div class="controls">
				<input name="entryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${interactionOffer.entryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省份：</label>
			<div class="controls">
				<sys:treeselect id="province" name="province" value="${interactionOffer.city}" labelName="province.name"
								labelValue="${fns:getAreaLabel(interactionOffer.province)}" title="省份" url="/sys/area/treeData" cssClass="input-small"
								allowClear="true" notAllowSelectParent="true"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">城市：</label>
			<div class="controls">

				<sys:treeselect id="city" name="city" value="${interactionOffer.city}" labelName="city.name"
								labelValue="${fns:getAreaLabel(interactionOffer.city)}" title="城市" url="/sys/area/treeData" cssClass="input-small"
								allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区域：</label>
			<div class="controls">
				<sys:treeselect id="area" name="area" value="${interactionOffer.area}" labelName="area.name"
								labelValue="${fns:getAreaLabel(interactionOffer.area)}" title="区域" url="/sys/area/treeData" cssClass="input-small"
								allowClear="true" notAllowSelectParent="true"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">薪水：</label>
			<div class="controls">
				<form:input path="salary" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注意事项：</label>
			<div class="controls">
				<form:input path="notice" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('offer_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">协议：</label>
			<div class="controls">

				<form:hidden id="agreement" path="agreement" class="input-xlarge"/>
				<sys:ckfinder input="agreement" type="images" uploadPath="/interaction/offer/interactionOffer" selectMultiple="false"/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="interaction:offer:interactionOffer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>