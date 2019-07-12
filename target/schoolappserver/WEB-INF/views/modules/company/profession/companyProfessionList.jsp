<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>企业职位管理</title>
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
    <li class="active"><a href="${ctx}/company/profession/companyProfession">职位列表</a></li>
    <c:if test="${not empty companyStatus}">
        <shiro:hasPermission name="company:profession:companyProfession:edit">
            <li>
                <a href="${ctx}/company/profession/companyProfession/form?source=${empty companyUsers?'2':'1'}">${empty companyUsers?'校外':''}职位添加</a>
            </li>
        </shiro:hasPermission>
    </c:if>
</ul>
<form:form id="searchForm" modelAttribute="companyProfession" action="${ctx}/company/profession/companyProfession/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">

        <li><label>名称：</label>
            <form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
        </li>
        <c:if test="${empty companyUsers}">
            <li><label>来源：</label>
                <form:select path="source" class="input-medium ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('profession_source')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </li>
        </c:if>

        <li><label>热门：</label>
            <form:select path="hot" class="input-medium ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
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
        <th>名称</th>

        <th>城市</th>
       <%-- <th>工作经验</th>--%>
        <th>学历</th>
        <%--<th>标签,逗号隔开</th>--%>

        <th>更新时间</th>
        <c:if test="${empty companyUsers}">
            <th>热门</th>
            <th>排序值</th>
        </c:if>
        <th>备注信息</th>
        <shiro:hasPermission name="company:profession:companyProfession:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="companyProfession">
        <tr>
            <td><a href="${ctx}/company/profession/companyProfession/form?id=${companyProfession.id}">
                    ${companyProfession.name}
            </a></td>

            <td>
                    ${fns:getAreaLabel(companyProfession.city)}
            </td>
          <%--  <td>
                    ${fns:getDictLabel(companyProfession.experience, 'work_experience', '')}
            </td>--%>
            <td>
                    ${fns:getDictLabel(companyProfession.educationalBackground, 'student_edu_back', '')}
            </td>
                <%--<td>
                    ${companyProfession.tag}
                </td>--%>

            <td>
                <fmt:formatDate value="${companyProfession.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <c:if test="${empty companyUsers}">
                <td>
                        ${fns:getDictLabel(companyProfession.hot, 'yes_no', '')}
                </td>
                <td>
                        ${companyProfession.sort}
                </td>

            </c:if>
            <td>
                    ${companyProfession.remarks}
            </td>
            <shiro:hasPermission name="company:profession:companyProfession:edit">
                <td>
                    <a href="${ctx}/company/profession/companyProfession/form?id=${companyProfession.id}">修改</a>
                    <a href="${ctx}/company/profession/companyProfession/delete?id=${companyProfession.id}"
                       onclick="return confirmx('确认要删除该企业职位吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>