<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>企业职位管理</title>
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
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/company/profession/companyProfession?">职位列表</a></li>
    <li class="active"><a
            href="${ctx}/company/profession/companyProfession/form?id=${companyProfession.id}">职位<shiro:hasPermission
            name="company:profession:companyProfession:edit">${not empty companyProfession.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="company:profession:companyProfession:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="companyProfession" action="${ctx}/company/profession/companyProfession/save"
           method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="source"/>

    <c:if test="${not empty companyUsers}">
        <form:hidden path="companyId"/>
    </c:if>

    <sys:message content="${message}"/>

    <c:if test="${  empty companyProfession.company.name and empty companyUsers}">
        <div class="control-group">
            <label class="control-label">企业名称：</label>
            <div class="controls">
                <form:input path="companyId" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </c:if>
    <c:if test="${ not empty companyProfession.company.name and empty companyUsers}">
        <form:hidden path="companyId"/>
        <div class="control-group">
            <label class="control-label">企业名称：</label>
            <div class="controls">
                <form:input path="company.name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </c:if>

    <div class="control-group">
        <label class="control-label">名称：</label>
        <div class="controls">

            <c:if test="${ empty  companyUsers or empty companyProfession.id }">
            <form:input path="name"  htmlEscape="false" maxlength="255" class="input-xlarge "/>
            </c:if>
            <c:if test="${ !(empty  companyUsers or empty companyProfession.id) }">
            <form:input path="name" disabled="true" htmlEscape="false" maxlength="255" class="input-xlarge "/>
            </c:if>
        </div>
    </div>
    <c:if test="${empty companyUsers}">
        <div class="control-group">
            <label class="control-label">热门：</label>
            <div class="controls">
                <form:select path="hot" class="input-xlarge ">

                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>

                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">排序值(从小到大排序)：</label>
            <div class="controls">
                <form:input path="sort" htmlEscape="false" maxlength="255" class="input-xlarge number"/>

            </div>
        </div>
    </c:if>
    <div class="control-group">
        <label class="control-label">岗位性质：</label>
        <div class="controls">
            <form:select path="nature" class="input-xlarge ">
                <form:options items="${fns:getDictList('profession_nature')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">职位类型：</label>
        <div class="controls">
            <c:if test="${ empty  companyUsers or empty companyProfession.id }">
                <sys:treeselect id="type" name="type" value="${companyProfession.type}" labelName=""
                                labelValue="${fns:getpositionLabel(companyProfession.type)}"
                                title="职位类型" url="/dict/position/dictPosition/treeData" cssClass="" allowClear="true"
                                notAllowSelectParent="true"/>
            </c:if>
            <c:if test="${ !(empty  companyUsers or empty companyProfession.id) }">
                <sys:treeselect id="type" disabled="disabled" name="type" value="${companyProfession.type}" labelName=""
                                labelValue="${fns:getpositionLabel(companyProfession.type)}"
                                title="职位类型" url="/dict/position/dictPosition/treeData" cssClass="" allowClear="true"
                                notAllowSelectParent="true"/>
            </c:if>



        </div>
    </div>
    <div class="control-group">
        <label class="control-label">城市：</label>
        <div class="controls">
            <c:if test="${ empty  companyUsers or empty companyProfession.id }">
                <sys:treeselect id="city" name="city" value="${companyProfession.city}" labelName=""
                                labelValue="${fns:getAreaLabel(companyProfession.city)}"
                                title="区域" url="/sys/area/treeData" cssClass="" allowClear="true"/>
            </c:if>
            <c:if test="${ !(empty  companyUsers or empty companyProfession.id) }">
                <sys:treeselect id="city" disabled="disabled" name="city"  value="${companyProfession.city}" labelName=""
                                labelValue="${fns:getAreaLabel(companyProfession.city)}"
                                title="区域" url="/sys/area/treeData" cssClass="" allowClear="true"/>
            </c:if>

        </div>
    </div>
   <%-- <div class="control-group">
        <label class="control-label">工作经验：</label>
        <div class="controls">
            <form:select path="experience" class="input-xlarge ">

                <form:options items="${fns:getDictList('work_experience')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>--%>
    <%--<div class="control-group">
        <label class="control-label">专业：</label>
        <div class="controls">
            <sys:treeselect id="major" name="major" value="${companyProfession.major}" labelName="" labelValue="${fns:getMajorLabel(companyProfession.major)}"
                            title="专业" url="/dict/major/dictMajor/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
        </div>
    </div>--%>
    <div class="control-group">
        <label class="control-label">学历要求：</label>
        <div class="controls">
            <form:select path="educationalBackground" class="input-xlarge ">


                <form:options items="${fns:getDictList('student_edu_back')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">福利待遇,逗号隔开：</label>
        <div class="controls">
            <form:input path="tag" htmlEscape="false" maxlength="255" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">薪资区间：</label>
        <div class="controls">
            <form:select path="salary" class="input-xlarge ">


                <form:options items="${fns:getDictList('salary')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>

        </div>
    </div>
    <%--<div class="control-group">
        <label class="control-label">招聘人数：</label>
        <div class="controls">
            <form:input path="number" htmlEscape="false" maxlength="10" class="input-xlarge "/>
        </div>
    </div>--%>
    <div class="control-group">
        <label class="control-label">食/宿：</label>
        <div class="controls">
            <form:select path="board" class="input-xlarge ">

                <form:options items="${fns:getDictList('board')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>

        </div>
    </div>
   <%-- <div class="control-group">
        <label class="control-label">交通补贴：</label>
        <div class="controls">
            <form:select path="trafficSubsidy" class="input-xlarge ">

                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>

        </div>
    </div>--%>

    <div class="control-group">
        <label class="control-label">职位描述：</label>
        <div class="controls">
            <form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注信息：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="company:profession:companyProfession:edit"><input id="btnSubmit"
                                                                                     class="btn btn-primary"
                                                                                     type="submit"
                                                                                     value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>