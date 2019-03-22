var Application = Application || (function ($) {
    const _init = () => {
        $('.date-time-picker.dt-default').datetimepicker({ format: 'L', allowInputToggle: true });
    };

    return {
        init: _init
    };
})(jQuery);
