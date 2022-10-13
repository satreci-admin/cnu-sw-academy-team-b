let isRepeat = $('#isRepeat').is(':checked');
if(!isRepeat) {
    $('#executedDateTime').attr("disabled", true);
}

let main = {
    init : function () {
        let _this = this;
        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
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
    update : function () {
        const robotId = $('#robotId').val();
        const jobDescriptorId = Number($('#jobDescriptorId').val());
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
                type: 'PUT',
                url: '/jobdescriptor/' + jobDescriptorId,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('작업명세서가 수정되었습니다.');
                location.href = "/jobdescriptors";
            }).fail(function (error) {
                console.log(JSON.stringify(error));
            });
        }
    },
    delete : function () {
        const jobDescriptorId = Number($('#jobDescriptorId').val());

        $.ajax({
            type: 'DELETE',
            url: '/jobdescriptor/'+ jobDescriptorId,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('작업명세서가 삭제되었습니다.');
            location.href = "/jobdescriptors";
        }).fail(function (error) {
            console.log(JSON.stringify(error));
        });
    }
};

main.init();
