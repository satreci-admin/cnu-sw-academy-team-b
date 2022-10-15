let activation = $('#activation1').is(':checked');

let update = {
    init : function () {
        let _this = this;

        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#activation1').on({
            'click': function() {
                if($('#activation1').is(':checked')){
                    activation = true;
                } else{
                    activation = false;
                }
            }
        });

    },

    update : function () {
        let jobDescriptorId = $('#jobDescriptorId').text();
        let data = {
            command: $('#Command').val(),
            parameter: $('#Parameter').val(),
            activation: activation,
            jobDescriptionId: Number(jobDescriptorId)
        };

        let id = $('#ID').val();

        $.ajax({
            type: 'PUT',
            url: '/job/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('작업이 수정되었습니다.');
            location.href = "/jobs/" + jobDescriptorId;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

};

update.init();
