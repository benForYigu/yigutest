<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配置数据管理</title>
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
		<li><a href="${ctx}/appconfig/appConfig/">配置数据列表</a></li>
		<li class="active"><a href="${ctx}/appconfig/appConfig/form?id=${appConfig.id}">配置数据<shiro:hasPermission name="appconfig:appConfig:edit">${not empty appConfig.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="appconfig:appConfig:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="appConfig" action="${ctx}/appconfig/appConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">版本名称：</label>
			<div class="controls">
				<form:input path="version" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本号：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">适配类型：</label>
			<div class="controls">
				<form:select path="configType" items="${fns:getDictList('app_config_type')}" itemLabel="label" itemValue="value"  class="input-xlarge required"/>


			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件地址：</label>
			<div class="controls">
				<form:input id="url" path="url" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<input type="file"  id="urls">
			<%--<sys:ckfinder input="url" type="files" uploadPath="/appconfig/appConfig" selectMultiple="false"/>--%>
			</div>
			<script type="text/javascript">
                //③创建fileLoad方法用来上传文件
                function fileLoad(ele){
                    //④创建一个formData对象
                    var formData = new FormData();
                    //⑤获取传入元素的val
                    var name = $(ele).val();
                    //⑥获取files
                    var files = $(ele)[0].files[0];
                    //⑦将name 和 files 添加到formData中，键值对形式
                    formData.append("file", files);
                    formData.append("name", name);
                    $.ajax({
                        url: "${ctx}/appconfig/appConfig/uploads",
                        type: 'POST',
                        data: formData,
                        processData: false,// ⑧告诉jQuery不要去处理发送的数据
                        contentType: false, // ⑨告诉jQuery不要去设置Content-Type请求头

                        success: function (responseStr) {
                            $("#url").val(responseStr);
                            $.jBox.tip('上传成功', '');

                        }
                        ,
                        error : function (responseStr) {
                            //12出错后的动作vb
                            $.jBox.tip('上传失败', '');
                        }
                    });
                }
                $(function () {
                    var $input =  $("#urls");
                    // ①为input设定change事件
                    $input.change(function () {
                        //    ②如果value不为空，调用文件加载方法
                        if($(this).val() != ""){
                            fileLoad(this);
                        }
                    })
                })
			</script>

		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="appconfig:appConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>