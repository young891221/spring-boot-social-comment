<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<#if commentPage?? && (commentPage.totalElements > 0) >
<div id="layer1" class="pop-layer" data-user="none">
    <div class="pop-container">
        <div class="pop-conts">
            <span style="font-size: 15px; font-weight: bold; color: #122b40">댓글 리스트</span>
            <div id="comment-placeholder"></div>
                <#list commentPage.content as data>
                <div class="personal-comment-box">
                    <span style="font-size: 15px; font-weight: bold; color: #1f326a">{{userName}} {{dateformat date}}</span><br>
                    <span style="font-size: 15px; font-weight: bold;">${data.content}</span><br>
                </div>
                </#list>
            <div class="btn-r">
                <a href="javascript:;" class="btn-layerClose">닫기</a>
            </div>
        </div>
    </div>
</div>
<#else>
<h3>댓글이 없습니다.</h3>
</#if>

<div id="write_comment" style="display: block;">
    <div id="write_comment_body" style="float: left;">
        <input type="text" id="content" value="" placeholder="댓글을 작성해주세요. (140자까지 입력 가능합니다)" style="border: none; outline: none;">
        <input type="hidden" id="articleIdx" value="${articleIdx}" readonly>
        <input type="hidden" id="parentIdx" value="0">
        <input type="checkbox" id="isShare">
        <button id="postBtn" class="btn btn-default btn-sm" type="submit">등록</button>
    </div>
</div>

<script src="/lib/jquery.min.js"></script>
<script src="/lib/handlebars.min.js"></script>
<script>
    $('#postBtn').click(function () {
        var content = $('#content').val();
        var articleIdx = $('#articleIdx').val();
        var parentIdx = $('#parentIdx').val();
        var isShare = $('#isShare').val();
        var jsonData = JSON.stringify({
            content: content,
            articleIdx: articleIdx,
            parentIdx: parentIdx,
            isShare: isShare
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
                       data.content;

                   },
                   error: function (data) {
                       alert('잠시만 기다려주세요.');
                   }
               })
    });
</script>
</body>
</html>