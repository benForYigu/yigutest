<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业发票管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();

			$("#inputForm").validate({
				submitHandler: function(form){
                   /* if($("#status").val()=="3" && $("#img").val()==''){
                        $.jBox.tip('请先上传发票', '');
                        return ;
                    }*/
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
		<%--<li><a href="${ctx}/orderinvoices/orderInvoices/">企业列表</a></li>--%>
		<li class="active"><a href="${ctx}/orderinvoices/orderInvoices/form?id=${orderInvoices.id}">发票<shiro:hasPermission name="orderinvoices:orderInvoices:edit">${not empty orderInvoices.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="orderinvoices:orderInvoices:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderInvoices" action="${ctx}/orderinvoices/orderInvoices/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="accountId"/>
		<form:hidden path="companyId"/>
		<form:hidden path="orderId"/>
		<input type="hidden" name="flag" value="${flag}">
		<sys:message content="${message}"/>
		<%--<div class="control-group">
			<label class="control-label">用户id：</label>
			<div class="controls">
				<form:input path="accountId" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业id：</label>
			<div class="controls">
				<form:input path="companyId" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单id：</label>
			<div class="controls">
				<form:input path="orderId" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">税号：</label>
			<div class="controls">
				<form:input path="taxcode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位名称：</label>
			<div class="controls">
				<form:input path="companyName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位地址：</label>
			<div class="controls">
				<form:input path="companyAddress" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位电话：</label>
			<div class="controls">
				<form:input path="companyPhone" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户银行：</label>
			<div class="controls">
				<form:input path="bank" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡号：</label>
			<div class="controls">
				<form:input path="bankCard" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">发票：</label>
			<div class="controls">
				<form:hidden id="img" path="img" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="img" type="images" uploadPath="/orderinvoices/orderInvoices" selectMultiple="false"/>

			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status"  items="${fns:getDictList('invoice_status')}" itemValue="value" itemLabel="label" class="input-xlarge "/>
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