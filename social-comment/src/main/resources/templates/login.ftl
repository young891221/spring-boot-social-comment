<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>

<div style="text-align: center;">
<br>
<#if name??>
    <span style="font-size: 15px; font-weight: bold; color: #1f326a">Welcom, ${name}!</span><br><br>
    <a href="/logout" class="btn-layerClose">logout</a>
<#else>
    <span style="font-size: 15px; font-weight: bold; color: #1f326a">Select your social for login</span><br><br>
    <a href="javascript:;" class="btn_social" data-social="facebook"><img src="/img/facebook.png" width="40px" height="40px"></a>
    <a href="javascript:;" class="btn_social" data-social="twitter"><img src="/img/twitter.png" width="40px" height="40px"></a>
    <a href="javascript:;" class="btn_social" data-social="google"><img src="/img/google.png" width="40px" height="40px"></a>
    <a href="javascript:;" class="btn_social" data-social="kakao"><img src="/img/kakao.png" width="40px" height="40px"></a>
</#if>
    <br><br>
    <a href="/comment/test/1" class="btn-layerClose" style="color">댓글 보러가기</a>
</div>


<script src="/lib/js/jquery.min.js"></script>
<script>
    $('.btn_social').click(function () {
        var socialType = $(this).data('social');
        window.open('/login/'+socialType, '', 'scrollbars=yes, resizable=yes, status=yes, location=yes, width=500, height=350, left=0, top=0');
        window['onSuccess'] = function() {
            location.reload();
            window['onSuccess'] = null;
        }
    });
</script>
</body>
</html>