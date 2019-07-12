<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>宣讲管理</title>
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
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/interaction/teachin/interactionTeachin/">宣讲列表</a></li>
		<li class="active"><a href="${ctx}/interaction/teachin/interactionTeachin/form?id=${interactionTeachin.id}">宣讲<shiro:hasPermission name="interaction:teachin:interactionTeachin:edit">${not empty interactionTeachin.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="interaction:teachin:interactionTeachin:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="interactionTeachin" action="${ctx}/interaction/teachin/interactionTeachin/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">企业：</label>
			<div class="controls">
				<form:input path="company.name" disabled="true" htmlEscape="false" maxlength="255"  class="input-xlarge " />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">学校：</label>
			<div class="controls">
				<form:input path="school.name" disabled="true" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关职位：</label>
			<div class="controls">

				<form:select   class="input-xlarge" path="professionArray" items="${professions}" itemValue="id" itemLabel="name" multiple="true" />



			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<form:hidden id="img" path="img" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="img" type="images" uploadPath="/interaction/teachin/interactionTeachin" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">宣讲名称：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">宣讲日期：</label>
			<div class="controls">
				<input name="date" disabled="true" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${interactionTeachin.date}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">宣讲时间段：</label>
			<div class="controls">
				<form:radiobuttons disabled="true" path="time" items="${fns:getDictList('teachin_time')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">宣讲内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否上架：</label>
			<div class="controls">

				<form:select path="shelf" class="input-xlarge ">
					<form:options items="${fns:getDictList('teachin_shelf')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进行状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">

					<form:options items="${fns:getDictList('teachin_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			</div>
		</div>

			<div class="control-group">
				<label class="control-label">首页宣讲材料：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>内容</th>
								<th>备注信息</th>
								<shiro:hasPermission name="interaction:teachin:interactionTeachin:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="interactionTeachinDataList">
						</tbody>
						<shiro:hasPermission name="interaction:teachin:interactionTeachin:edit"><tfoot>
							<tr><td colspan="4"><a href="javascript:" onclick="addRow('#interactionTeachinDataList', interactionTeachinDataRowIdx, interactionTeachinDataTpl);interactionTeachinDataRowIdx = interactionTeachinDataRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="interactionTeachinDataTpl">//<!--
						<tr id="interactionTeachinDataList{{idx}}">
							<td class="hide">
								<input id="interactionTeachinDataList{{idx}}_id" name="interactionTeachinDataList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="interactionTeachinDataList{{idx}}_delFlag" name="interactionTeachinDataList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="interactionTeachinDataList{{idx}}_url" name="interactionTeachinDataList[{{idx}}].url" type="hidden" value="{{row.url}}" maxlength="255"/>
								<sys:ckfinder input="interactionTeachinDataList{{idx}}_url" type="images" uploadPath="/interaction/teachin/interactionTeachin" selectMultiple="true"/>
							</td>
							<td>
								<textarea id="interactionTeachinDataList{{idx}}_remarks" name="interactionTeachinDataList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="interaction:teachin:interactionTeachin:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#interactionTeachinDataList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var interactionTeachinDataRowIdx = 0, interactionTeachinDataTpl = $("#interactionTeachinDataTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(interactionTeachin.interactionTeachinDataList)};

							for (var i=0; i<data.length; i++){
								addRow('#interactionTeachinDataList', interactionTeachinDataRowIdx, interactionTeachinDataTpl, data[i]);
								interactionTeachinDataRowIdx = interactionTeachinDataRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>

		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="interaction:teachin:interactionTeachin:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>