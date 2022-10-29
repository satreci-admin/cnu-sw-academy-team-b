let isRepeat = false;

let main = {
        init : function () {
                let _this = this;
                $('#btn-save').on('click', function () {
                        _this.save();
                });

                $('#isRepeat').on({
                        'click': function() {
                                if($('#isRepeat').is(':checked')){
                                        isRepeat = true;
                                        $('#executedDateTime').attr("disabled", false);
                                } else{
                                        isRepeat = false;
                                        $('#executedDateTime').val("");
                                        $('#executedDateTime').attr("disabled", true);
                                }
                        }
                });
        },
        save : function () {
                const robotId = $('#robotId').val();
                if(isNaN(robotId)) {
                        alert("로봇을 선택해 주세요.")
                }else {
                        let data = {
                                name: $('#name').val(),
                                robotId: Number(robotId),
                                isRepeat: isRepeat,
                                executedDatetime: $('#executedDateTime').val()
                        };

                        $.ajax({
                                type: 'POST',
                                url: '/api/v1/jobdescriptor',
                                dataType: 'json',
                                contentType:'application/json; charset=utf-8',
                                data: JSON.stringify(data)
                        }).done(function() {
                                alert('작업명세서가 추가되었습니다.');
                                location.href = "/jobdescriptors";
                        }).fail(function (error) {
                                console.log(JSON.stringify(error));
                        });
                }
        },
};
main.init();
