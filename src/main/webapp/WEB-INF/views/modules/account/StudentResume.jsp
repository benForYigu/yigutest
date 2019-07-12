<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
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
		<li><a href="${ctx}${ctx}/interaction/interview/interactionInterview/">面试列表</a></li>
		<li class="active"><a href="${ctx}/interaction/interview/interactionInterview/resume?studentId=${accountStudentinfo.accountId}">学生<shiro:hasPermission name="account:accountstudent:accountStudentinfo:edit">${not empty accountStudentinfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="account:accountstudent:accountStudentinfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="accountStudentinfo" action="${ctx}/account/accountstudent/accountStudentinfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="accountId"/>
		<form:hidden path="schoolId"/>
		<form:hidden path="nickname"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">头像：</label>
			<div class="controls">
				<form:hidden path="avatar" htmlEscape="false" maxlength="200" class="input-xlarge "/>
				<sys:ckfinder input="avatar" type="images" uploadPath="/accountstudent/avatar"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="realname" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学校：</label>
			<div class="controls">
				<form:input path="schoolName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>

		<%--<div class="control-group">
			<label class="control-label">院系：</label>
			<div class="controls">
				<form:input path="department" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">学历：</label>
			<div class="controls">
				<form:select path="educationBackground" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('student_edu_back')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业：</label>
			<div class="controls">
				<sys:treeselect id="major" name="major" value="accountStudentinfo.major" labelName="parent.name" labelValue="${fns:getMajorLabel(accountStudentinfo.major)}"
								title="父级编号" url="/dict/major/dictMajor/treeData"  cssClass="" allowClear="true"/>
				<%--<form:input path="major" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select path="sex" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">毕业时间：</label>
			<div class="controls">
				<input name="graduationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${accountStudentinfo.graduationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">学号：</label>
			<div class="controls">
				<form:input path="studentId" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">在职状态：</label>
			<div class="controls">
				<form:select path="incumbency" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>


		<label style="color:#73b573;font-size: 20px">工作意向</label>
		<table class="table table-hover">
			<tr>
				<td>期望行业</td>
				<td>期望职位</td>
				<td>城市</td>
				<td>期望薪资</td>

			</tr>
			<c:forEach items="${prefers}" var="prefer">
				<tr>
					<td>${fns:getindustryLabel(prefer.positionId)}</td>

					<td>${fns:getpositionLabel(prefer.professionId)}</td>
					<td>${fns:getAreaLabel(prefer.city)}</td>
					<td>${fns:getDictLabel(prefer.salary,"salary","" )}</td>
				</tr>
			</c:forEach>
		</table>

		</br>
		<label style="color:#73b573;font-size: 20px">实习/工作经历</label>
		<table class="table table-hover">
			<tr>
				<td>企业名称</td>
				<td>开始时间</td>
				<td>结束时间</td>
				<td>内容</td>
			</tr>
			<c:forEach items="${experiences}" var="experience">
				<tr>
					<td>${experience.companyName}</td>
					<td><fmt:formatDate value="${experience.startDate}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${experience.endDate}" pattern="yyyy-MM-dd"/></td>
					<td>${experience.content}</td>
				</tr>
			</c:forEach>
		</table>




		</br>

		<label style="color:#73b573;font-size: 20px">教育经历</label>
		<table class="table table-hover">
			<tr>
				<td>学校名称</td>
				<td>开始时间</td>
				<td>结束时间</td>
				<td>内容</td>
			</tr>
		<c:forEach items="${educations}" var="education">
			<tr>
				<td>${education.schoolName}</td>
				<td><fmt:formatDate value="${education.startDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${education.endDate}" pattern="yyyy-MM-dd"/></td>
				<td>${education.content}</td>
			</tr>
		</c:forEach>
		</table>
		</br>

		<label style="color:#73b573;font-size: 20px">校内职务</label>
		<table class="table table-hover">
			<tr>
				<td>开始时间</td>
				<td>结束时间</td>
				<td>职务名称</td>
				<td>职务描述</td>
			</tr>
			<c:forEach items="${schoolExperiences}" var="schoolExperience">
				<tr>
					<td><fmt:formatDate value="${schoolExperience.startDate}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${schoolExperience.endDate}" pattern="yyyy-MM-dd"/></td>
					<td>${schoolExperience.job}</td>
					<td>${schoolExperience.jobDescribe}</td>
				</tr>
			</c:forEach>
		</table>
		</br>

		<label style="color:#73b573;font-size: 20px">实践活动</label>
		<table class="table table-hover">
			<tr>
				<td>开始时间</td>
				<td>结束时间</td>
				<td>活动名称</td>
				<td>活动描述</td>
			</tr>
			<c:forEach items="${actives}" var="schoolExperience">
				<tr>
					<td><fmt:formatDate value="${schoolExperience.startDate}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${schoolExperience.endDate}" pattern="yyyy-MM-dd"/></td>
					<td>${schoolExperience.name}</td>
					<td>${schoolExperience.content}</td>
				</tr>
			</c:forEach>
		</table>
		</br>
		<label style="color:#73b573;font-size: 20px">荣誉证书</label>
		<table class="table table-hover">
			<tr>
				<td>开始时间</td>
				<td>结束时间</td>
				<td>名称</td>
				<td>证书等级</td>
			</tr>
			<c:forEach items="${certificates}" var="certificate">
				<tr>
					<td><fmt:formatDate value="${certificate.startDate}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${certificate.endDate}" pattern="yyyy-MM-dd"/></td>
					<td>${certificate.certificateName}</td>
					<td>${certificate.certificateType}</td>
				</tr>
			</c:forEach>
		</table>
		</br>

		<label style="color:#73b573;font-size: 20px">自我评价</label>
		<table class="table table-hover">
			<tr>
					<%--<td>技能</td>--%>
				<td>自我评价</td>
					<%--<td>相关证书</td>--%>

			</tr>
			<c:forEach items="${others}" var="other">
				<tr>
						<%--<td>${other.skill}</td>--%>
					<td>${other.selfEvaluation}</td>
						<%--	<td>${other.certificate}</td>--%>
				</tr>
			</c:forEach>
		</table>






		<div class="form-actions">
			<%--<shiro:hasPermission name="account:accountstudent:accountStudentinfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>