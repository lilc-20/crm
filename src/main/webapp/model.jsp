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

        $(".time").datetimepicker({
            minView: "month",
            language:  'zh-CN',
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            pickerPosition: "bottom-left"
        });

        $("#activityPage").bs_pagination({
            currentPage: pageNo, // 页码
            rowsPerPage: pageSize, // 每页显示的记录条数
            maxRowsPerPage: 20, // 每页最多显示的记录条数
            totalPages: data.totalPages, // 总页数
            totalRows: data.total, // 总记录条数

            visiblePageLinks: 3, // 显示几个卡片

            showGoToPage: true,
            showRowsPerPage: true,
            showRowsInfo: true,
            showRowsDefaultInfo: true,

            onChangePage : function(event, data){
                pageList(data.currentPage , data.rowsPerPage);
            }
        });
        pageList($("#activityPage").bs_pagination('getOption', 'currentPage'),
            $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

    </script>
</head>
<body>

</body>
</html>
