    document.addEventListener("DOMContentLoaded", function() {
        // 모든 sub_price 요소를 가져옵니다.
        document.querySelectorAll('.sub-price').forEach(function(element) {
            var price = parseInt(element.innerText, 10);
            if (!isNaN(price)) {
                // 가격을 천 단위로 구분하여 포맷합니다.
                element.innerText = price.toLocaleString() + '원';
            }
        });
    });