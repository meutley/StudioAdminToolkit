var Client = Client || {};
Client.Invoice = Client.Invoice || {};
Client.Invoice.View = Client.Invoice.View || (function ($) {
    var _initForm = ($modal, $target, invoiceId) => {
        var $form = $target.find('form#edit-payment-form');
        $form.attr('action', `/invoice/${invoiceId}/payment`);
        $form.find('#date-collected').datetimepicker({ format: 'L', allowInputToggle: true });
        $form.on('submit', function (e) {
            e.preventDefault();
            $form.find('button.ok-button').attr('disabled', 'disabled');
            $.ajax({
                url: $form.attr('action'),
                method: $form.attr('method'),
                data: $form.serialize()
            }).then((res, _, xhr) => {
                if (xhr.status !== 204) {
                    $target.html($(res));
                    _initForm($modal, $target, invoiceId);
                } else {
                    $modal.modal('hide');
                    location.reload();
                }
            }).catch(() => {
                alert('An unexpected error occurred. Please try again later. If the problem persists, contact support.');
            }).always(() => {
                $form.find('button.ok-button').removeAttr('disabled');
            });
        });
    };
    
    var _init = () => {
        var $btnCollectPayment = $('button#collect-payment-button');
        $btnCollectPayment.on('click', () => {
            var invoiceId = $('input[type=hidden]#invoice-id').val();
            var url = `/invoice/${invoiceId}/payment/edit-payment-modal`;
            var $modal = $('<div class="modal" role="dialog">');
            $modal.load(`${url} div.modal-dialog`, function (responseText, textStatus) {
                if (textStatus === 'error') {
                    alert('An unknown error occurred. Please try again later. If the problem persists, contact support.');
                    return;
                }

                var $target = $(this);
                _initForm($modal, $target, invoiceId);
                $modal.on('hidden.bs.modal', () => $modal.remove());
                $modal.modal();
            });
        });
    };

    return {
        init: _init
    };
})(jQuery);