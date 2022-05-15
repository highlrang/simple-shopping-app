const orderItemList = [];

const orderItem = {
    add: function(itemId, itemName){

        let dto = {
            "itemId": itemId,
            "itemName": itemName,
            "quantity": $("#quantity").val()
        };

        orderItemList.push(dto);

        orderItem.changeModal();
    },

    init: function(){
        orderItemList.length = 0;
        orderItem.initModal();
    },

    changeModal: function(){
        let html = "<div class='m-3'><div class='p-3 mb-2 bg-secondary text-white font-weight-bold'>선택한 상품</div>";

        for(var i=0; i<orderItemList.length; i++){
            let { itemName : name, quantity} = orderItemList[i];
            html += `<div class="row m-3">${name} - ${quantity}개</div>`;
        }

        html += '<div><button type="button" onclick="order.save()" class="btn btn-outline-dark">주문하기</button></div>'

        + "</div>";

        $(".selectedItemModalBody").html(html);
    },

    initModal: function(){
        $(".selectedItemModalBody").empty();
    }

}

