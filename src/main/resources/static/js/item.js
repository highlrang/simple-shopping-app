const item = {

    list: function(page = 0){
        $.ajax({
            type: "GET",
            url: `/api/item?page=${page}`,
            dataType: "json",
            success: function(result){
                if(result.status !== "success"){
                    alert("상품을 불러오지 못하였습니다.");
                    return;
                }

                const itemObj = result.object;
                if(itemObj.content.length === 0){
                    $("#morePage").hide();
                    return;
                }

                $("#morePage").show();
                let html = "";

                $.each(itemObj.content, function(idx, item){

                    html += "<div class='row mb-3'>"
                         + `<div class="col-sm-8">${item.name} - ${item.price}</div>`
                         + "<div class='col-sm-4'>"
                         + `<input type='hidden' name="itemName" value='${item.name}'>`
                         + `<input type='hidden' name="itemId" value='${item.id}'>`
                         + '<button type="button" class="btn btn-outline-secondary" onclick="item.clickModal(this);">'
                         + '주문하기</button>'
                         + "</div>"
                         + "</div>";
                });

                $("#content").append(html).slideDown();
                nowPage = itemObj.page;
                nowContent = "item";
            },
            error: function(err){
                alert("상품 목록을 불러오지 못했습니다.");
                console.log(err);
            }
        });
    },

    clickModal: function(e){
        let id = $(e).prev("input[name='itemId']").val();
        let name = $(e).prev().prev("input[name='itemName']").val();

        let html =
        "<div class='row m-3'>"
            + "<div class='col'>"
                + `<span>${name}</span>`
            + "</div>"
            + "<div class='col-md-auto'>"
                + `<input type="number" id="quantity" value="1" placeholder="수량" class="form-control">`
            + "</div>"

            + `<div class="col col-lg-2"><input type="button" value="담기" onclick="orderItem.add(${id}, '${name}');" class="btn btn-outline-primary"></div>`
        + "</div>";

        $(".selectItemModalBody").html(html);
        $("#selectItem").modal('show');
    }

}