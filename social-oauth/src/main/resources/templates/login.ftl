<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div class="dim-layer">
	    <div class="dimBg"></div>
	    <div id="layer2" class="pop-layer">
	        <div class="pop-container">
	            <div class="pop-conts" style="text-align: center;">
                    <input id="login_name" type="text" value="<#if name??>${name}<#else>로그인이 필요합니다.</#if>">
		            <br>
	                <span style="font-size: 15px; font-weight: bold; color: #1f326a">로그인할 소셜 계정을 선택하세요.</span>
		            <br>
	                <a href="javascript:;" id="facebook"><img src="/img/facebook.png" width="40px" height="40px" data-parent="-999"></a>
	                <a href="javascript:;" id="twitter"><img src="/img/twitter.png" width="40px" height="40px" data-parent="-999"></a>
                    <a href="javascript:;" id="google"><img src="/img/google.png" width="40px" height="40px" data-parent="-999"></a>
                    <a href="javascript:;" id="kakao"><img src="/img/kakao.png" width="40px" height="40px" data-parent="-999"></a>
	                <div class="btn-r">
	                    <a href="javascript:;" class="btn-layerClose">닫기</a>
	                </div>
                    <div class="btn-r">
                        <a href="/logout" class="btn-layerClose">로그아웃</a>
                    </div>
	            </div>
	        </div>
	    </div>
	</div>

<script src="/lib/jquery.min.js"></script>
<script>
    $('#facebook').click(function () {
        var $parent = $(this).data('parent');
        window.open('/login/facebook', 'facebook', 'scrollbars=yes, resizable=yes, status=yes, location=yes, width=500, height=350, left=0, top=0');
        window['onSuccess'] = function () {
            $.ajax("/authentication", {
                success: function (data) {
                    if (data.result) {
                        alert('인증 성공!');
                        $('#login_name').val(data.name);
                    }
                    else {
                        alert('인증 실패!');
                    }
                }
            });
            window['onSuccess'] = null;
        };
        $(this).data('parent', -999);
    });

    $('#twitter').click(function () {
        $('.btn-layerClose').click();
        var $parent = $(this).data('parent');
        window.open('/login/twitter', 'twitter', 'scrollbars=yes, resizable=yes, status=yes, location=yes, width=500, height=350, left=0, top=0');
        window['onSuccess'] = function () {
            $.ajax("/authentication", {
                success: function (result) {
                    if (result) {
                        alert('인증 성공!');
                        $('#login_name').val(data.name);
                    }
                    else {
                        alert('인증 실패!');
                    }
                }
            });
            window['onSuccess'] = null;
        };
        $(this).data('parent', -999);
    });

    $('#google').click(function () {
        $('.btn-layerClose').click();
        var $parent = $(this).data('parent');
        window.open('/login/google', 'google', 'scrollbars=yes, resizable=yes, status=yes, location=yes, width=500, height=350, left=0, top=0');
        window['onSuccess'] = function () {
            $.ajax("/authentication", {
                success: function (result) {
                    if (result) {
                        alert('인증 성공!');
                        $('#login_name').val(data.name);
                    }
                    else {
                        alert('인증 실패!');
                    }
                }
            });
            window['onSuccess'] = null;
        };
        $(this).data('parent', -999);
    });

    $('#kakao').click(function () {
        $('.btn-layerClose').click();
        var $parent = $(this).data('parent');
        window.open('/login/kakao', 'kakao', 'scrollbars=yes, resizable=yes, status=yes, location=yes, width=500, height=350, left=0, top=0');
        window['onSuccess'] = function () {
            $.ajax("/authentication", {
                success: function (result) {
                    if (result) {
                        alert('인증 성공!');
                        $('#login_name').val(data.name);
                    }
                    else {
                        alert('인증 실패!');
                    }
                }
            });
            window['onSuccess'] = null;
        };
        $(this).data('parent', -999);
    });
</script>
</body>
</html>