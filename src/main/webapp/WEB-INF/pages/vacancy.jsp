<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>Find job app</title>
</head>
<body>

<form:form method="post" action="add" commandName="vacancy">

    <table>
        <tr>
            <td><form:label path="vacancy">
                Vacancy
            </form:label></td>
            <td><form:input path="vacancy" /></td>
        </tr>
        <tr>
            <td><form:label path="salary">
                Salary
            </form:label></td>
            <td><form:input path="salary" /></td>
        </tr>
        <tr>
            <td><form:label path="experience">
                Experience
            </form:label></td>
            <td><form:input path="experience" /></td>
        </tr>
        <tr>
            <td><form:label path="education">
                Education
            </form:label></td>
            <td><form:input path="education" /></td>
        </tr>`
        <tr>
            <td><form:label path="employer">
               Employer
            </form:label></td>
            <td><form:input path="employer" /></td>
        </tr>
        <tr>
            <td><form:label path="details">
                Details
            </form:label></td>
            <td><form:input path="details" /></td>
        </tr>
        <tr>
            <td><form:label path="hr">
                HR
            </form:label></td>
            <td><form:input path="hr" /></td>
        </tr>
        <tr>
            <td><form:label path="url">
                Url
            </form:label></td>
            <td><form:input path="url" /></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit"
                                   value="Add Vacancy" /></td>
        </tr>
    </table>
</form:form>
<%--<form:form method="post" action="find" commandName="Query">
<table>
    <tr>
        <td><form:label path="FindVacancy">
            Query
        </form:label></td>
        <td><form:input path="FindVacancy" /></td>
    </tr>
    <tr>
        <td colspan="2"><input type="submit"
                               value="Find!" /></td>
    </tr>
</table>
</form:form>--%>

<h3>Vacancies</h3>
<c:if test="${!empty vacancyList}">
    <table class="data">
        <tr>
            <th>vacancy</th>
            <th>salary</th>
            <th>experience</th>
            <th>education</th>
            <th>employer</th>
            <th>details</th>
            <th>hr</th>
            <th>url</th>
            <th>&nbsp;</th>
        </tr>
        <c:forEach items="${vacancyList}" var="vacancy">
            <tr>
                <td>${vacancy.vacancy}</td>
                <td>${vacancy.salary}</td>
                <td>${vacancy.experience}</td>
                <td>${vacancy.education}</td>
                <td>${vacancy.employer}</td>
                <td>${vacancy.details}</td>
                <td>${vacancy.hr}</td>
                <td>${vacancy.url}</td>
                <td><a href="delete/${vacancy.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>