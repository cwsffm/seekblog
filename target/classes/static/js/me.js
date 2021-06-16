function setFooterFixedBottom(){
    var height =  $("main").innerHeight()+$("nav").height()+$("footer").height();
    console.log(height+":" +$(window).height())
    if(height>$(window).height()){
        $("footer").removeClass("m-stick-bottom");
    }else {
        $("footer").addClass("m-stick-bottom");
    };
}

// $(window).resize(function() {
//     setFooterFixedBottom()
// });

