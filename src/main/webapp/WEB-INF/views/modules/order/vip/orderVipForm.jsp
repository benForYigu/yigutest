<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>vip订单管理</title>
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
		<li><a href="${ctx}/order/vip/orderVip/">vip订单列表</a></li>
		<li class="active"><a href="${ctx}/order/vip/orderVip/form?id=${orderVip.id}">vip订单<shiro:hasPermission name="order:vip:orderVip:edit">${not empty orderVip.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:vip:orderVip:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderVip" action="${ctx}/order/vip/orderVip/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">企业：</label>
			<div class="controls">
				<form:input  disabled="true" path="companyhr.companyName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户：</label>
			<div class="controls">
				<form:input disabled="true" path="companyhr.realname" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">vip名称：</label>
			<div class="controls">
				<form:input  disabled="true" path="vip.name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单金额：</label>
			<div class="controls">
				<form:input  disabled="true" path="payment" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付时间：</label>
			<div class="controls">
				<input name="payTime"  disabled="true" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${orderVip.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">完成时间：</label>
			<div class="controls">
				<input name="endTime"  disabled="true" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${orderVip.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易号：</label>
			<div class="controls">
				<form:input  disabled="true" path="transactionId" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">购买方式：</label>
			<div class="controls">
				<form:select  disabled="true" path="payType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('vip_pay_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">支付状态：</label>
			<div class="controls">
				<c:if test="${orderVip.payType!='4'  or orderVip.status!='1'}">
				<form:select  disabled="true" path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				</c:if>
				<c:if test="${orderVip.payType=='4' and orderVip.status=='1'}">
				<form:select  path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				</c:if>
			</div>
		</div>

		<c:if test="${orderVip.payType=='4'}">
			<div class="control-group">
				<label class="control-label">支付凭证：</label>
				<div class="controls">
					<a href="${orderVip.voucher}" target="_blank>">
						<img src="${orderVip.voucher}" />
					</a>

				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${orderVip.status=='1' and orderVip.payType=='4'}">
				<shiro:hasPermission name="order:vip:orderVip:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>

			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>