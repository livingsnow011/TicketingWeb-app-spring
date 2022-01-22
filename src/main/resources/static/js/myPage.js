var main = {
    init : function () {
        var _this = this;

        function receiveMessage(event)
        {
          var uid = $('#uid').val();
          if(event.origin !== "http://localhost:8080"){
            return;
          }
            //TODO ajax로 delete request 보내기
          alert(uid);
        }
        window.addEventListener("message", receiveMessage, false);

        $('#delete').on('click', function(e){
            e.preventDefault();
            _this.popUp();
        });
    },
    popUp : function () {
        var popUp = window.open("deleteAccount.html", '회원 탈퇴',
         'width=400px,height=200px,scrollbars=no,resizable=no,top=200px,left=400px,status=no,titlebar=no');
    }
};
main.init();