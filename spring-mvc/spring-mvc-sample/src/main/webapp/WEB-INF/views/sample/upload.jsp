<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
  <head>
    <%@ include file="/common/global.jsp"%>
    <title>File Upload Sample</title>
  </head>
  <body>
    <h3>Upload File</h3>
    <form enctype="multipart/form-data" value="/sample/upload" method="post">
      <input type="file" name="file" /><br/>
      <input type="submit" value="Upload"/>
    </form>
  </body>
</html>
