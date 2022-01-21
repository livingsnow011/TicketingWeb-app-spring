var main = {
    init : function () {
        var _this = this;
        var time_idx = 0;
        var seat_idx = 0;

        $('#btn-save').on('click', function(e){
            e.preventDefault();
            _this.save();
        });
        $('#addTimeInput').on('click', function(e){
            e.preventDefault();
            time_idx += 1;
            var newDiv = document.createElement("div");
            newDiv.setAttribute("id", "times" + time_idx);
            var newTimeInput = document.createElement("input");
            newTimeInput.setAttribute("class", "form-control");
            newTimeInput.setAttribute("type", "datetime-local");
            newTimeInput.setAttribute("id", "time" + time_idx);
            newTimeInput.setAttribute("name", "startDate");

            newDiv.appendChild(newTimeInput);
            document.getElementById("times").appendChild(newDiv);
        });
        $('#addSeatInput').on('click', function(e){
                    e.preventDefault();
                    seat_idx += 1;
                    var newDiv = document.createElement("div");
                    newDiv.setAttribute("class", "form-group row");
                    newDiv.setAttribute("id", "seat" + seat_idx);

                    var newSeatGradeInput = document.createElement("input");
                    newSeatGradeInput.setAttribute("class", "form-control col");
                    newSeatGradeInput.setAttribute("type", "text");
                    newSeatGradeInput.setAttribute("id", "grade" + seat_idx);
                    newSeatGradeInput.setAttribute("name", "grade");
                    newSeatGradeInput.setAttribute("placeholder", "좌석 등급");

                    var newSeatPriceInput = document.createElement("input");
                    newSeatPriceInput.setAttribute("class", "form-control col");
                    newSeatPriceInput.setAttribute("type", "number");
                    newSeatPriceInput.setAttribute("id", "price" + seat_idx);
                    newSeatPriceInput.setAttribute("name", "price");
                    newSeatPriceInput.setAttribute("placeholder", "좌석 가격");

                    var newSeatCountInput = document.createElement("input");
                    newSeatCountInput.setAttribute("class", "form-control col");
                    newSeatCountInput.setAttribute("type", "number");
                    newSeatCountInput.setAttribute("id", "count" + seat_idx);
                    newSeatCountInput.setAttribute("name", "count");
                    newSeatCountInput.setAttribute("placeholder", "좌석 수");

                    newDiv.appendChild(newSeatGradeInput);
                    newDiv.appendChild(newSeatPriceInput);
                    newDiv.appendChild(newSeatCountInput);
                    document.getElementById("seats").appendChild(newDiv);
                })
        // 입력 칸 중에 필수입력 항목에 값이 있는지 체크해야하는데 시간이 없어서 추후 구현
    },
    save : function () {
        var myForm = document.getElementById('showRegisterForm');
        var formData = new FormData(myForm);
        for (let key of formData.keys()) {
            console.log(key);
        }

        for (let value of formData.values()) {
            console.log(value);
        }

        var gradeList = formData.getAll('grade');
        var priceList = formData.getAll('price');
        var countList = formData.getAll('count');

        var seatSaveRequestDtoList = [];

        $.each(gradeList, function (idx, item) {
            if(typeof item == null || item == '' || item == 'undefined'){
                return false;
            }
            var seat = {
                grade: gradeList[idx],
                price: priceList[idx],
                totalSeat: countList[idx]
            }

            seatSaveRequestDtoList.push(seat);
        })

        var requestDto = {
            name: $('#name').val(),
            classification: $('#classification').val(),
            description: $('#description').val(),
            startDate: formData.getAll('startDate'),
            seatSaveRequestDtoList: seatSaveRequestDtoList
        }
        formData.append("requestDto", JSON.stringify(requestDto));
        formData.delete('startDate');

        for (let key of formData.keys()) {
            console.log(key);
        }

        for (let value of formData.values()) {
            console.log(value);
        }
        $.ajax({
            type: 'POST',
            method: 'POST',
            url: '/api/v1/show',
            processData: false,
            contentType: false,
            data: formData
        }).done(function (response){
            alert(response);
            window.location.href = '/show/register';
        }).fail(function (err){
            alert(JSON.stringify(err));
        })
    },

};
main.init();