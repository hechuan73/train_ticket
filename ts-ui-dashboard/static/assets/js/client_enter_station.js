
var enterStation = new Vue({
    el: '#enterStation',
    data: {
        enter_order_id: ''
    },
    methods: {
        initPage() {
            this.checkLogin();
        },
        checkLogin(){
            var username = sessionStorage.getItem("client_name");
            if (username == null) {
                // alert("Please login first!");
            }
            else {
                document.getElementById("client_name").innerHTML = username;
            }
        },
        enterStation() {
            if (this.enter_order_id != '' && this.enter_order_id != "") {
                $("#enter_reserve_execute_order_button").attr("disabled", true);
                var executeInfo = new Object();
                executeInfo.orderId = this.enter_order_id;
                var data = JSON.stringify(executeInfo);
                $.ajax({
                    type: "get",
                    url: "/api/v1/executeservice/execute/execute/" + executeInfo.orderId,
                    contentType: "application/json",
                    dataType: "json",
                    xhrFields: {
                        withCredentials: true
                    },
                    success: function (result) {

                        if (result.status == 1) {
                            alert("server send message: " + result.msg);
                        } else {
                            alert(result.msg);
                        }
                    },
                    complete: function () {
                        $("#enter_reserve_execute_order_button").attr("disabled", false);
                    }
                });
            } else {
                alert("please input your order id first !")
            }
        }
    },
    mounted() {
        this.initPage();
    }
});