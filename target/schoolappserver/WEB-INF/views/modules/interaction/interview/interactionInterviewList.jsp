<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>面试管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/interaction/interview/interactionInterview/">面试列表</a></li>
   <%-- <shiro:hasPermission name="interaction:interview:interactionInterview:edit">
        <li><a href="${ctx}/interaction/interview/interactionInterview/form">面试添加</a></li>
    </shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="interactionInterview"
           action="${ctx}/interaction/interview/interactionInterview/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">

        <li><label>面试方式：</label>
            <form:select path="interviewType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('interview_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>面试状态：</label>
            <form:select path="interviewStatus" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('interview_status')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>学生</th>
        <th>企业</th>
        <th>投递职位</th>
        <th>面试方式</th>
        <th>面试状态</th>
        <th>更新时间</th>
        <th>备注信息</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="interactionInterview">
        <tr>

            <td>
                    ${interactionInterview.student.realname}
            </td>
            <td>
                    ${interactionInterview.companyName}
            </td>
            <td>
                    ${interactionInterview.professionName}
            </td>

            <td>
                    ${fns:getDictLabel(interactionInterview.interviewType, 'interview_type', '')}
            </td>
            <td>
                    ${fns:getDictLabel(interactionInterview.interviewStatus, 'interview_status', '')}
            </td>
            <td>
                <fmt:formatDate value="${interactionInterview.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${interactionInterview.remarks}
            </td>
            <td>
      <%--          <a href="${ctx}/interaction/interview/interactionInterview/form?id=${interactionInterview.id}">查看</a>--%>
         <%-- <c:if test="${not empty companyStatus or  empty companyUsers}">--%>
          <a href="${ctx}/interaction/interview/interactionInterview/resume?studentId=${interactionInterview.studentId}">查看简历</a>
          <%--</c:if>--%>

          <%--<shiro:hasPermission name="interaction:interview:interactionInterview:edit">
                    <a href="${ctx}/interaction/interview/interactionInterview/delete?id=${interactionInterview.id}"
                       onclick="return confirmx('确认要删除该面试吗？', this.href)">
                        删除
                    </a>
                </shiro:hasPermission>--%>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>