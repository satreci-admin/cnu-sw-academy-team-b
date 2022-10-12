let main = {
    init : function () {
        let _this = this
        $('#btn-update').on('click', function () {
            _this.update()
        })
        $('#btn-delete').on('click', function () {
            _this.delete()
        })
    },
    update : function () {
        let data = {
            address: $("#address").val(),
            user: $("#user").val(),
            password: $("#password").val()
        }
        console.log(data)

        let id = $("#id").val()

        $.ajax({
            type: "PUT",
            url: '/robot/' + id,
            contentType: 'application/json; charset=utf-8',
            dataType: 'html',
            data: JSON.stringify(data)
        }).done(() => {
            alert("전송되었습니다.")
        }).fail((e) => {
            alert("실패하였습니다.")
            console.log(e)
        })
    },

    delete: function () {
        console.log("clicked")
    }
}

main.init()