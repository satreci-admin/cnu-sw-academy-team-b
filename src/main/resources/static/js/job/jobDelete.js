let Delete = {
    init : function () {
        let _this = this;

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },

    delete : function () {
        let id = Number($('#ID').val());    $('#ID').text();
        let jobDescriptorId = $('#jobDescriptorId').text();

        $.ajax({
            type: 'DELETE',
            url: '/job/'+ id,
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('작업이 삭제되었습니다.');
            location.href = "/jobs/" + jobDescriptorId;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

Delete.init();
