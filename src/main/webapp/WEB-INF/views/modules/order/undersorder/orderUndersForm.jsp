<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>双选会订单管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
//$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>

    <script src="https://cdn.bootcss.com/layer/2.3/layer.js"></script>
</head>


<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/order/undersorder/orderUnders/">双选会订单列表</a></li>
    <li class="active"><a
            href="${ctx}/order/undersorder/orderUnders/form?id=${orderUnders.id}">双选会订单<shiro:hasPermission
            name="order:undersorder:orderUnders:edit">${not empty orderUnders.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="order:undersorder:orderUnders:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="orderUnders" action="${ctx}/order/undersorder/orderUnders/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="undersId"/>
    <sys:message content="${message}"/>


    <div class="control-group">
        <label class="control-label">双选会：</label>
        <div class="controls">
            <form:input disabled="true" path="unders.title" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">企业：</label>
        <div class="controls">
            <form:input disabled="true" path="companyhr.companyName" htmlEscape="false" maxlength="100"
                        class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户：</label>
        <div class="controls">
            <form:input disabled="true" path="companyhr.realname" htmlEscape="false" maxlength="100"
                        class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单金额：</label>
        <div class="controls">
            <form:input disabled="true" path="payment" htmlEscape="false" class="input-xlarge  number"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">支付时间：</label>
        <div class="controls">
            <input disabled="true" name="payTime" type="text" readonly="readonly" maxlength="20"
                   class="input-medium Wdate "
                   value="<fmt:formatDate value="${orderUnders.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">完成时间：</label>
        <div class="controls">
            <input disabled="true" name="endTime" type="text" readonly="readonly" maxlength="20"
                   class="input-medium Wdate "
                   value="<fmt:formatDate value="${orderUnders.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">交易号：</label>
        <div class="controls">
            <form:input disabled="true" path="transactionId" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">购买方式：</label>
        <div class="controls">
            <form:select disabled="true" path="payType" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('normal_pay_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">状态：</label>
        <div class="controls">

            <c:if test="${orderUnders.payType!='4' or orderUnders.status!='1'}">
                <form:select disabled="true" path="status" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </c:if>
            <c:if test="${orderUnders.payType=='4' and orderUnders.status=='1'}">
                <form:select path="status" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </c:if>
        </div>
    </div>
    <c:if test="${orderUnders.payType=='4'}">
        <div class="control-group">
            <label class="control-label">凭证：</label>
            <div class="controls">
                <a href="${orderUnders.voucher}" target="_blank>">
                    <img src="${orderUnders.voucher}"/>
                </a>
            </div>
        </div>
    </c:if>
    <script>
        function files(id) {
            html = ' <table class="table table-bordered"><tr><td>企业</td><td id="company">  company </td></tr><tr><td>邮箱</td><td id="email">email</td></tr><tr><td>联系人1</td><td id="link1">link1</td></tr><tr><td>联系电话1</td><td id="linkphone1">linkphone1</td></tr><tr><td>联系人2</td><td id="link2">link2</td></tr><tr><td>联系电话2</td><td id="linkphone2">linkphone2</td></tr><tr><td>招聘简章</td><td id="content">content</td></tr><tr><td>执照</td><td id="license"><img style="width: 150px;height: 150px" src="license"></img></td></tr></tr></table>';
            $.ajaxSettings.async = false;
            $.get("${ctx}/order/undersorder/orderUnders/signFile?orderId="+id,function (ret) {
                html=html.replace(/company/g,ret.companyName);
                html=html.replace(/email/g,ret.email);
                html=html.replace(/link1/g,ret.contact1);
                html=html.replace(/linkphone1/g,ret.phone1);
                html=html.replace(/link2/g,ret.contact2);
                html=html.replace(/linkphone2/g,ret.phone2);
                html=html.replace(/content/g,ret.content);
                html=html.replace(/license/g,ret.license);
            })

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['700px', '500px'], //宽高
                content: html
            });
        }
    </script>
    <div class="control-group">
        <label class="control-label"></label>
        <div class="controls">
            <a class="btn btn-success" onclick="files('${orderUnders.id}')">查看资料</a>
        </div>


    </div>
    <div class="control-group">
        <label class="control-label">备注信息：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <c:if test="${orderUnders.status=='1' and orderUnders.payType=='4'}">
            <shiro:hasPermission name="order:undersorder:orderUnders:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                                  type="submit"
                                                                                  value="保 存"/>&nbsp;</shiro:hasPermission>
        </c:if>

        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>