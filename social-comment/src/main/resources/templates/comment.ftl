<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/lib/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/lib/css/main.css"/>
</head>
<body>

<div style="float: left;">
    <input type="text" id="content" value="" placeholder="댓글을 작성해주세요." style="border: none; outline: none;">
    <input type="hidden" id="articleIdx" value="${articleIdx}" readonly>
    <input type="hidden" id="parentIdx" value="0">
    <button id="postBtn" class="btn btn-default" type="submit">등록</button>
    <#if isAuthenticated>
    <a class="btn btn-default" href="/logout" role="button">logout</a>
    </#if>
</div><br>

<h2>댓글 리스트</h2>
<div id="comment-placeholder">
<#if commentPage?? && (commentPage.totalElements > 0) >
    <#list commentPage.content as data>
        <div class="comment">
            <div style="float: left; margin-right: 10px">
                <a href="${data.user.userImage}" target="_blank"><img src="${data.user.userImage}"></a>
            </div>
            <div style="float: left; text-align: left;">
                <span style="color: darkblue; font-weight: bold;">${data.user.userName}</span> ${data.convertModifiedAt}
                <br><span style="font-size: 15px; font-weight: bold;"> ${data.content}</span><br>
                <a href="javascript:;" class="warning" data-idx="${data.commentIdx}" style="text-decoration: none; color: red;">신고</a>
            </div>
            <br>
        </div>
    </#list>
</#if>
</div>

<script src="/lib/js/jquery.min.js"></script>
<script src="/lib/js/handlebars.min.js"></script>

<script id="commentTemplate" type="text/x-handlebars-template">
    {{#commentData}}
    <div class="comment">
        <div style="float: left; margin-right: 10px">
            <a href="{{user.userImage}}" target="_blank"><img src="{{user.userImage}}"></a>
        </div>
        <div style="float: left; text-align: left;">
            <span style="color: darkblue; font-weight: bold;">{{user.userName}}</span> {{convertModifiedAt}}
            <br><span style="font-size: 15px; font-weight: bold;"> {{content}}</span><br>
            <a href="javascript:;" class="warning" data-idx="{{data.commentIdx}}" style="text-decoration: none; color: red;">신고</a>
        </div>
        <br>
    </div>
    {{/commentData}}
</script>

<script>
    var isAuthenticated = ${isAuthenticated?default(false)?string};

    $('#postBtn').click(function () {
        if(!isAuthenticated) {
            window.open('/login/alert', '', 'scrollbars=yes, resizable=yes, status=yes, location=yes, width=500, height=350, left=400, top=300');
            return;
        }

        var content = $('#content').val();
        var articleIdx = $('#articleIdx').val();
        var parentIdx = $('#parentIdx').val();
        var jsonData = JSON.stringify({
            content: content,
            articleIdx: articleIdx,
            parentIdx: parentIdx
        });

        $.ajax({
                   url: "/api/comment/post",
                   type: "POST",
                   data: jsonData,
                   headers: {
                       "Accept": "application/json",
                       "Content-Type": "application/json"
                   },
                   dataType: "json",
                   success: function (data) {
                       var replyHtml = $('<h3>댓글이 없습니다.</h3>');
                       if(data !== null) {
                           var template = Handlebars.compile($("#commentTemplate").html()),
                                   result = {};
                           result.commentData = data;
                           replyHtml = template(result);
                       }
                       $('#comment-placeholder').prepend(replyHtml);
                       $('#content').val('');
                   },
                   error: function () {
                       alert('잠시만 기다려주세요.');
                   }
               })
    });
</script>
</body>
</html>