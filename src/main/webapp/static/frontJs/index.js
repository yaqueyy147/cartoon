/**
 * Created by suyx on 2017/1/5 0005.
 */
$(function () {

    $("#province").val("");
    $("#province").change();
    // $("p[name='familyDesc']").mouseover(function () {
    //    $(this).popover('show');
    // });
    //
    // $("p[name='familyDesc']").mouseout(function () {
    //     $(this).popover('hide');
    // });

    $(".panel-heading").click(function () {
        var contentDiv = $(this).next();

        if($(contentDiv).is(":hidden")){
            $(contentDiv).show(300);
            $(this).find("i").removeClass("fa-chevron-down");
            $(this).find("i").addClass("fa-chevron-up");
        } else{
            $(contentDiv).hide(300);
            $(this).find("i").removeClass("fa-chevron-up");
            $(this).find("i").addClass("fa-chevron-down");
        }
    });

    $("#searchBtn").click(function () {
        $(".loading").show();
        var params = {};
        params.cartoonname = $("#cartoonname").val();

        $.ajax({
            type:'post',
            url: projectUrl + '/fronts/querycartoon',
            dataType: 'json',
            data:params,
            // async:false,
            success:function (data) {
                var cartoonlist = data.cartoonlist;
                var cartoonContent = "";
                if(cartoonlist.length > 0){
                    for(var i=0;i<cartoonlist.length;i++){
                        var ii = cartoonlist[i];
                        cartoonContent += "<div class='col-sm-3 col-md-2 familyDiv'>";
                        cartoonContent += "<div class='thumbnail'>";
                        cartoonContent += "<a href='" + projectUrl + "/fronts/cartoondetail?cartoonid=" + ii.id + "' style=\"float: none;width: 100%;\">";
                        cartoonContent += "<img class=\"familyImgFF\" src='" + ii.cartoonpic + "' class='img-thumbnail'/></a>";
                        cartoonContent += "<div class='caption'>";
                        cartoonContent += "<h6>" + ii.cartoonname + "</h6>";
                        cartoonContent += "<p>" + ii.cartoontypedesc + "</p>";
                        cartoonContent += "<p name='familyDesc' onmouseover='pPopover(this,1)' onmouseout='pPopover(this,2)' style='text-overflow: ellipsis;white-space: nowrap;overflow: hidden' data-container='body' data-toggle='popover' data-placement='right' data-content='" + ii.cartooninfo +"'>";
                        cartoonContent += ii.cartooninfo;
                        cartoonContent += "</p></div></div></div>";
                    }
                }
                $("#cartoonContent").html(cartoonContent);
                $(".loading").hide();
            },
            error:function (data) {
                var responseText = data.responseText;
                if(responseText.indexOf("登出跳转页面") >= 0){
                    ajaxErrorToLogin();
                }else{
                    alert(JSON.stringify(data));
                }
            }
        });

    });

});

