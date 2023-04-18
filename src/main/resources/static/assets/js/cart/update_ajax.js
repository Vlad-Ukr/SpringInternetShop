function update_ajax(productId, amount) {
    console.log("Updating PRODUCT")
    $.post('/cart', {action: 'update', productID: productId, amount: amount},
        function (data) {
            var amount = document.getElementById("sst".concat(productId));
            amount.innerHTML = data;
        }
    );
}

function addOne(productId) {
    $.ajax({
            url: '/cart',
            method: 'post',
            data: {action: 'add', productID: productId, productQnt: 1},
            success: function (data) {
                window.location.reload();
            }
        }
    );
}
function removeOne(productId,amount){
    $.ajax({
            url: '/cart',
            method: 'post',
            data: {action: 'update', productID: productId, productQnt: amount-1},
            success: function (data) {
                window.location.reload();
            }
        }
    );
}
