<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>线下活动管理</title>
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
		<li><a href="${ctx}/unders/unders/unders/">线下活动列表</a></li>
		<li class="active"><a href="${ctx}/unders/unders/unders/form?id=${unders.id}">线下活动<shiro:hasPermission name="unders:unders:unders:edit">${not empty unders.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="unders:unders:unders:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="unders" action="${ctx}/unders/unders/unders/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="type" value="2"/>
		<sys:message content="${message}"/>
		<%--<div class="control-group">
			<label class="control-label">类型1线下宣讲 2线下双选会：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">学校：</label>
			<div class="controls">

				<c:if test="${unders.id==null}">
					<form:select path="schoolId" itemLabel="name" itemValue="id" items="${schools}" class="input-xlarge "/>
				</c:if>
				<c:if test="${unders.id!=null}">
					<form:select path="schoolId" disabled="true" itemLabel="name" itemValue="id" items="${schools}" class="input-xlarge "/>
				</c:if>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<form:hidden id="img" path="img" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="img" type="images" uploadPath="/unders/unders/unders" selectMultiple="false"/>

			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">副标题：</label>
			<div class="controls">
				<form:input path="subTitle" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${unders.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${unders.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报名开始：</label>
			<div class="controls">
				<input name="signStart" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${unders.signStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报名结束：</label>
			<div class="controls">
				<input name="signEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${unders.signEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规模数量：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" maxlength="11" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false"  class="input-xxlarge"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>--%>

		<div class="control-group">
				<label class="control-label">展位：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>图片</th>
								<th>名称</th>
								<th>数量</th>
								<th>价格</th>
								<th>尺寸</th>
								<th>备注信息</th>
								<shiro:hasPermission name="unders:unders:unders:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="undersBoothList">
						</tbody>
						<shiro:hasPermission name="unders:unders:unders:edit"><tfoot>
							<tr><td colspan="7"><a href="javascript:" onclick="addRow('#undersBoothList', undersBoothRowIdx, undersBoothTpl);undersBoothRowIdx = undersBoothRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="undersBoothTpl">//<!--
						<tr id="undersBoothList{{idx}}">
							<td class="hide">
								<input id="undersBoothList{{idx}}_id" name="undersBoothList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="undersBoothList{{idx}}_delFlag" name="undersBoothList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>

							<input type="hidden" id="undersBoothList{{idx}}_img" name="undersBoothList[{{idx}}].img" value="{{row.img}}"/>
							<sys:ckfinder input="undersBoothList{{idx}}_img" type="images" maxWidth="100" maxHeight="100" uploadPath="/unders/unders/undersBooth" selectMultiple="false"/>

							</td>
							<td>
								<input id="undersBoothList{{idx}}_name" name="undersBoothList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="100" class="input-small "/>
							</td>
							<td>
								<input id="undersBoothList{{idx}}_number" name="undersBoothList[{{idx}}].number" type="text" value="{{row.number}}" maxlength="10" class="input-small "/>
							</td>
							<td>
								<input id="undersBoothList{{idx}}_price" name="undersBoothList[{{idx}}].price" type="text" value="{{row.price}}" class="input-small  number"/>
							</td>
							<td>
							<input id="undersBoothList{{idx}}_size" name="undersBoothList[{{idx}}].size" type="text" value="{{row.size}}" class="input-small  "/>
							</td>
							<td>
								<textarea id="undersBoothList{{idx}}_remarks" name="undersBoothList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="unders:unders:unders:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#undersBoothList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var undersBoothRowIdx = 0, undersBoothTpl = $("#undersBoothTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(unders.undersBoothList)};
							for (var i=0; i<data.length; i++){
								addRow('#undersBoothList', undersBoothRowIdx, undersBoothTpl, data[i]);
								undersBoothRowIdx = undersBoothRowIdx + 1;
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
			<shiro:hasPermission name="unders:unders:unders:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>