<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":"
            + request.getServerPort()
            + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>" />
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
