<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/lib/css/bootstrap.min.css"/>
</head>
<body>

<div style="display: block;">
    <div style="float: left;">
        <input type="text" id="content" value="" placeholder="댓글을 작성해주세요." style="border: none; outline: none;">
        <input type="hidden" id="articleIdx" value="${articleIdx}" readonly>
        <input type="hidden" id="parentIdx" value="0">
        <button id="postBtn" class="btn btn-default" type="submit">등록</button>
        <#if isAuthenticated>
        <a class="btn btn-default" href="/logout" role="button">logout</a>
        </#if>
    </div>
</div><br>

<div id="layer1" class="pop-layer" data-user="none">
    <div class="pop-container">
        <div class="pop-conts">
            <h2>댓글 리스트</h2>
            <div id="comment-placeholder"></div>
            <#if commentPage?? && (commentPage.totalElements > 0) >
                <#list commentPage.content as data>
                <div class="personal-comment-box">
                    <span style="font-size: 15px; font-weight: bold; color: #1f326a">${data.userName}</span> ${data.convertModifiedAt}<br>
                    <span style="font-size: 15px; font-weight: bold;">${data.content}</span><br>
                </div>
                </#list>
            </#if>
        </div>
    </div>
</div>


<script src="/lib/js/jquery.min.js"></script>
<script src="/lib/js/handlebars.min.js"></script>

<script id="commentTemplate" type="text/x-handlebars-template">
    {{#commentData}}
    <div class="personal-comment-box">
        <span style="font-size: 15px; font-weight: bold; color: #1f326a">{{userName}}</span> {{convertModifiedAt}}<br>
        <span style="font-size: 15px; font-weight: bold;">{{content}}</span><br>
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
                       $('#comment-placeholder').append(replyHtml);
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