
var collectTicket = new Vue({
        el: '#collectTicket',
        data: {
            collect_order_id: ''
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
            collectTicket() {
                if (this.collect_order_id != '' && this.collect_order_id != "") {
                    $("#reserve_collect_button").attr("disabled", true);
                    var executeInfo = new Object();
                    executeInfo.orderId = this.collect_order_id;
                    var data = JSON.stringify(executeInfo);
                    $.ajax({
                        type: "get",
                        url: "/api/v1/executeservice/execute/collected/" + executeInfo.orderId,
                        contentType: "application/json",
                        dataType: "json",
                        xhrFields: {
                            withCredentials: true
                        },
                        success: function (result) {

                            if (result.status == 1) {
                                alert(result.msg + " - you can enter station with your order id !");
                            } else {
                                alert(result.msg );
                            }
                        },
                        complete: function () {
                            $("#reserve_collect_button").attr("disabled", false);
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