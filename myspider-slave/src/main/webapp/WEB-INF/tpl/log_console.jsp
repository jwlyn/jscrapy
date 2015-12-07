<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.js
"></script>
    <style type="text/css">
        #errMsg{
            display: none;
        }
    </style>
    <script type="text/javascript" charset="utf-8">
        var offset = 0;//文件偏移量
        var update_log = function(taskId){
            $.get("/log/task/"+taskId+"/"+offset, function (data) {
                if(data.success){
                    $("#log_view").append(data.logContent);
                    $("#errMsg").hide();
                    offset = data.curOffset;
                }
                else{
                    $("#errMsg").text(data.errorReason).show();
                }
            }).error(function() { $("#errMsg").text("取不到日志，等待重试").show(); });
        };

        var log_updator = setInterval("update_log(${taskId})", 3000);
        update_log(${taskId});
    </script>
</head>
<body>
<div id="errMsg"></div>
<pre id="log_view"></pre>
</body>
</html>
