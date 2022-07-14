var main = {
    init : function () {
        var _this = this;
        var uid = Cookies.get('userId');

        function receiveMessage(event)
        {
          if(event.origin !== "http://localhost:8080"){
            return;
          }

          $.ajax({
            type: 'DELETE',
            url: '/users/' + uid,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
          }).done(function(){
            alert('탈퇴 되었습니다.');
            window.location.href = '/';
          }).fail(function(err){
            console.log(JSON.stringify(err));
          })
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