<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
  <head>
  	<%@ include file="/common/global.jsp"%>
  	<title>Files Uploaded</title>
  </head>
  <body>
    <h3>Files uploaded</h3>
    <c:forEach items="${uploaded}" var="f">
      ${f}<br/>
     </c:forEach>
  </body>
</html>
