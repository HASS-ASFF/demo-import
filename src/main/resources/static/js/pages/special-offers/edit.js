$(function() {

    $('#product').on('change', function () {
        let price = $("#product option:selected").attr('data-price');
        let reduction = $('#reduction').val();

        let priceAfterReduction = getPriceAfterReduction(price, reduction);

        $('#priceOfProduct').val(price);
        $('#priceAfterReduction').val(priceAfterReduction);

    });

    $('#reduction').on('keyup', function () {
        let price = $("#product option:selected").attr('data-price');
        let reduction = $('#reduction').val();

        let priceAfterReduction = getPriceAfterReduction(price, reduction,);
        $('#priceAfterReduction').val(priceAfterReduction);

    });

    $('#reduction_type').on('change', function () {
        let price = $("#product option:selected").attr('data-price');
        let reduction = $('#reduction').val();

        let priceAfterReduction = getPriceAfterReduction(price, reduction);
        $('#priceAfterReduction').val(priceAfterReduction);

    });


    $('#check_start_date').on('click', function () {

        if(this.checked) {

            $('#startDate').val($('#to_day').val()) ;

            $('#startDate').attr('readonly', true);
            $('#startDate').addClass('readonly');
        }else{

            $('#startDate').attr('readonly', false);
            $('#startDate').removeClass('readonly');
        }

    });

    $('#check_end_date').on('click', function () {

        if(this.checked) {

            $('#endDate').val('');

            $('#endDate').attr('readonly', true);
            $('#endDate').addClass('readonly');
        }else{

            $('#endDate').attr('readonly', false);
            $('#endDate').removeClass('readonly');
        }

    });


    function getPriceAfterReduction(price, reduction){

        if(isNaN(price) || isNaN(reduction))
            return 0;

        return (price * (1 - (reduction / 100) )).toFixed(2);

    }

});