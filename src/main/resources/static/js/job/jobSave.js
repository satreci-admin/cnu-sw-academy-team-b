let activation = false;
let Save = {
    init : function () {

        let _this = this;
        $('#btn-add').on('click', function () {
            _this.save();
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
    save : function () {
        let jobDescriptionId = $('#jobDescriptorId').text();
        let data = {
            command: $('#Command').val(),
            parameter: $('#Parameter').val(),
            activation: activation,
            jobDescriptionId: Number(jobDescriptionId)
        };

        $.ajax({
            type: 'POST',
            url: '/job',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('작업이 등록되었습니다.');
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

};

Save.init();
