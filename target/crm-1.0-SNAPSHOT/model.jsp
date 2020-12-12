<%--
  Created by IntelliJ IDEA.
  User: 15067
  Date: 2020/12/12
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://"
            + request.getServerName() + "/"
            + request.getServerPort()
            + request.getContextPath() + "/";
%>
<html>
<head>
    <base <%=basePath%>>
    <title>Title</title>

    <script>

        $.ajax({
            url : "",
            type : "",
            data : {

            },
            dataType : "",
            success : function (resp){

            }
        });

    </script>
</head>
<body>

</body>
</html>
