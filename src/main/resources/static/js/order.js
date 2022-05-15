const order = {

    save: function(){
        if(orderItemList.length == 0)
            return false;

        let data = [...orderItemList];

        $.ajax({
            type: "POST",
            url: "/api/order",
            data: JSON.stringify(data),
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            success: function(result){
                $("#selectItem").modal('hide');
                orderItem.init();

                if(result.status === "error"){
                    alert(result.message);
                    return;
                }

                alert("주문이 완료되었습니다.");

            },
            error: function(err){
                alert("주문에 실패하였습니다.");
                console.log(err);
            }

        });

    },

    list: function(page = 0){
        $.ajax({
            type: "GET",
            url: `/api/order?page=${page}`,
            dataType: "json",
            success: function(result){
                if(result.status !== "success"){
                    alert("주문 내역을 불러오지 못했습니다.");
                    return;
                }

                const orderObj = result.object;
                if(orderObj.content.length === 0){
                    $("#morePage").hide();
                    return;
                }

                $("#morePage").show();

                let html = "";

                $.each(orderObj.content, function(idx, item){
                    html += "<div class='mb-5 px-5' style='border-right: 2px solid gray'>";

                    $.each(item.orderItems, function(idx, orderItem){
                        html += "<div class='row mb-3'>"
                        + `<div class='col' style='font-weight: bold;'>${orderItem.itemName}</div>`
                        + `<div class='col'><span style='font-weight: bold'>${orderItem.totalPrice} </span>${orderItem.price}, ${orderItem.quantity}</div>`
                        + "</div>";
                    });


                    html += `<div class='my-3' style='text-align: right;'><span style='font-weight: bold'>총 상품가격 </span>${item.totalPrice} <span style='font-weight: bold'>배송비 </span>${item.deliveryFee}</div>`
                        + `<div class='mb-3' style='text-align: right;'><span style='font-weight: bold'>지불금액 </span>${item.paymentAmount}</div>`

                    + "</div>";

                });

                $("#content").append(html);
                nowPage = orderObj.page;
                nowContent = "order";
            },
            error: function(err){
                alert("주문 목록을 불러오지 못했습니다.");
                console.log(err);
            }
        });
    }

}