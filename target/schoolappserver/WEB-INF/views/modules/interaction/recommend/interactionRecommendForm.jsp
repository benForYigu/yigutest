<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推荐位管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
                    $("#content").val(UE.getEditor('editor').getContent());
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
		<li><a href="${ctx}/interaction/recommend/interactionRecommend/">推荐位列表</a></li>
		<li class="active"><a href="${ctx}/interaction/recommend/interactionRecommend/form?id=${interactionRecommend.id}">推荐位<shiro:hasPermission name="interaction:recommend:interactionRecommend:edit">${not empty interactionRecommend.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="interaction:recommend:interactionRecommend:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="interactionRecommend" action="${ctx}/interaction/recommend/interactionRecommend/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">副标题：</label>
			<div class="controls">
				<form:input path="subTitle" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">适用学校：</label>
			<div class="controls" id="schools">
				<a class="btn btn-default" onclick="checkSchools()">反选</a>
				</br>
					<%--<form:input path="applyTo" htmlEscape="false" maxlength="255" class="input-xlarge required"/>--%>
				<c:forEach items="${schools}" var="s" >
					<label class="checkbox-inline">
						<input  type="checkbox" name="applyTos" value="${s.id}" <c:if test="${fn:contains(interactionRecommend.applyTo,s.id )}">checked</c:if>>${s.name}
					</label>
					<%--<input type="checkbox" name="applyTo" value="${s.id}" <c:if test="${fn:contains(interactionRecommend.applyTo,s.id )}">checked</c:if>>${s.name}
				--%></c:forEach>
				<script>
					function checkSchools() {
                        $.each($("#schools :checkbox"),function (i,v) {

                            if(v.checked){
                                $(v).removeAttr("checked");
                            }else{
                                $(v).attr("checked", "true");
                            }
                        })

                    }
				</script>
			</div>
		</div>
	<div class="control-group">
		<label class="control-label">是否支持企业：</label>
		<div class="controls">
			<form:select path="suportCompany" class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<form:hidden id="img" path="img" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="img" type="images" uploadPath="/interaction/recommend/interactionRecommend" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐位类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('recommend_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">链接：</label>
			<div class="controls">
				<form:input path="link" htmlEscape="false" maxlength="1000" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接类型：</label>
			<div class="controls">
				<form:select path="linkType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('recommend_link_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>

		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
				<%--	<form:option value="" label=""/>--%>
					<form:options items="${fns:getDictList('recommend_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

	<div class="control-group">
		<label class="control-label">内容：</label>
		<div class="controls">

			<!-- 加载编辑器的容器 -->
			<script id="editor" type="text/plain" style="width:1000px;height:200px;">
			</script>
            <form:textarea path="content" cssStyle="display: none" htmlEscape="false" rows="4" class="input-xxlarge "/>
            </div>
            </div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="interaction:recommend:interactionRecommend:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
                <!-- 配置文件 -->
                <script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
			<!-- 编辑器源码文件 -->
			<script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.all.js"></script>
			<!-- 实例化编辑器 -->
			<script type="text/javascript">
                var ue = UE.getEditor('editor');
                //编辑器初始化完成再赋值
                ue.ready(function() {
                    //赋值给UEditor
                    ue.setContent($('#content').val());
                    /* UE.getEditor('editor').execCommand('insertHtml',  $('#hdms').val());*/

                });
			</script>
</body>
</html>