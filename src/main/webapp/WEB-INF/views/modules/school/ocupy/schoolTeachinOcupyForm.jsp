<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校宣讲时间管理</title>
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
        function isUsed(){
            $("#time1").removeProp("disabled");
            $("#time2").removeProp("disabled");
            $("#time3").removeProp("disabled");
            $("#time4").removeProp("disabled");
            $("#time5").removeProp("disabled");
            $("#time6").removeProp("disabled");
            var schoolId=$("#schoolId").val(),date=$("#date").val();

            if(schoolId!=null && date!=null){
				$(".timeLabel").remove();
                $.get("${ctx}/school/ocupy/schoolTeachinOcupy/isUsed?schoolId="+schoolId+"&date="+date,function (ret) {
                    $.each(ret,function (i,v) {

                       if(v.id!=$("#id").val() || $("#id").val()==""){

                           var dom=$("#time"+v.time);
                           dom.prop("checked",false);
                           if(v.flag==1){
                               $("#time"+v.time).next().after("<label class='timeLabel' style='color: red;'>&nbsp&nbsp已购买&nbsp</label>");
                           }else{
                               $("#time"+v.time).next().after("<label class='timeLabel' style='color: red;'>&nbsp&nbsp已占用&nbsp</label>");
                           }
                           dom.prop("disabled","disabled");
					   }

                    })
                   /* $.each(ret,function (i,v) {

                       if(v.flag==1){
							$.each(v.time,function (i2,v2) {
                                var dom=$("#time"+v.time);
                                dom.prop("checked",false);
                                if(v.flag==1){
                                    $("#time"+v.time).next().after("<label class='timeLabel' style='color: red;'>&nbsp&nbsp已购买&nbsp</label>");
                                }else{
                                    $("#time"+v.time).next().after("<label class='timeLabel' style='color: red;'>&nbsp&nbsp已占用&nbsp</label>");
                                }
                                dom.prop("disabled","disabled");
                            })

					   }else{
                           var dom=$("#time"+v.time);
                           dom.prop("checked",false);
                           if(v.flag==1){
                               $("#time"+v.time).next().after("<label class='timeLabel' style='color: red;'>&nbsp&nbsp已购买&nbsp</label>");
                           }else{
                               $("#time"+v.time).next().after("<label class='timeLabel' style='color: red;'>&nbsp&nbsp已占用&nbsp</label>");
                           }
                           dom.prop("disabled","disabled");
					   }

                    })*/
                })
            }

        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/school/ocupy/schoolTeachinOcupy/">学校宣讲占用列表</a></li>
		<li class="active"><a href="${ctx}/school/ocupy/schoolTeachinOcupy/form?id=${schoolTeachinOcupy.id}">学校宣讲占用<shiro:hasPermission name="school:ocupy:schoolTeachinOcupy:edit">${not empty schoolTeachinOcupy.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="school:ocupy:schoolTeachinOcupy:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="schoolTeachinOcupy" action="${ctx}/school/ocupy/schoolTeachinOcupy/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>

		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">学校名称：</label>
			<div class="controls">
				<form:select path="schoolId"   class="input-medium required">

					<form:options items="${schools}" itemLabel="name" itemValue="id"></form:options>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日期：</label>
			<div class="controls">
				<%--<input id="date" name="date" type="text" onchange="isUsed()" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value='${schoolTeachinOcupy.date}' pattern='yyyy-MM-dd'/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
--%>
				<input id="date"  name="date" type="text"   readonly="readonly" maxlength="40" class="Wdate required"
					   value="<fmt:formatDate value="${schoolTeachinOcupy.date}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
				<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input id="endDate"  name="endDate" type="text"  readonly="readonly"  maxlength="20" class="required Wdate"
					    onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'date\')}',maxDate:'2120-10-01'})"/>
		</div>
		<div class="control-group">
			<label class="control-label">宣讲时间段：</label>
			<div class="controls">

				<form:checkboxes  path="time"  items="${fns:getDictList('teachin_time')}" itemLabel="label" itemValue="value"  class="input-xlarge required"/>

				<%--<script>
                    $(document).ready(function() {
                        isUsed();
                    })
				</script>--%>
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