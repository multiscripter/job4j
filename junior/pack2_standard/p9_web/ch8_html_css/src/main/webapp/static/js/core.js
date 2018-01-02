(function ($) {
    $(function () {
        var chosenParams = {
            no_results_text: '\u043D\u0435\u0442 \u0441\u043E\u0432\u043F\u0430\u0434\u0435\u043D\u0438\u0439:'
        };
        var jfc = $('.js-filter-countries');
        
        $(".chosen-select").not(jfc).chosen(chosenParams);

        function changeCities(ctx) {
            /**
             * Задержка для того, чтобы плагин chosen успел 
             * произвести манипуляции с атрибутом selected.
             */
            setTimeout(function () {
                ctx.trigger('countryChanged', [ctx.children(':selected').val()]);
            }, 100);
        };
        jfc.chosen(chosenParams).change(function () {
            changeCities($(this));
        });
        
        /**
         * Фильтр городов по странам.
         * Версия 2017-12-23.
         */
        var filterCities = {
            countries: null,
            cities: null,
            display: null,

            change: function (val) {
                var val = this.cities.children().removeAttr('selected')
                    .addClass('nonexists').filter(function (index) {
                        return $(this).attr('data-countries').split(',')
                            .some(function (cur) {
                                return cur == val;
                            });
                    }).removeClass('nonexists').eq(0)
                    .attr('selected', 'selected').text();
                this.cities.trigger('chosen:updated');
                this.display.text(val);
            },

            init: function () {
                this.countries = jfc;
                this.cities = $('.js-filter-cities');
                if (!this.countries.length || !this.cities.length) return;
                this.display = this.cities.next().find('.chosen-single > span');
                var self = this;
                this.countries.on('countryChanged', function (event, val) {
                    self.change.call(self, val);
                });
            }
        };
        filterCities.init();
        changeCities(jfc);

        /**
         * Обработчик форм.
         * Версия 2017-12-25.
         */
        function handleForm(form) {
            var self = this;
            this.form = form;
            this.btn = this.form.find('.form-submit');
            this.errors = {};
            this.hasErrors = false;

            this.isDecimal = function (item, val) {
                var f = parseFloat(val);
                this.errors[item.attr('name')] = isNaN(f) || f - parseInt(val) != 0
                    ? errmsgs['isDecimal'] : '';
            };

            this.isEmail = function (item, val) {
                this.errors[item.attr('name')]
                    = !/^[a-z0-9._%+-]+@[a-z0-9-]+\.[a-z]{2,6}$/i.test(val)
                    ? errmsgs['isEmail'] : false;
            };

            this.isFilled = function (item, val) {
                this.errors[item.attr('name')] = !val.trim()
                    ? errmsgs['isFilled'] : false;
            };

            this.isName = function (item, val) {
                this.errors[item.attr('name')]
                    = !/([^\u0000-\u007F]|[a-z])+([^\u0000-\u007F]|[a-z]|[\d -])+([^\u0000-\u007F]|[a-z]|[ \d])+$/i.test(val)
                    ? errmsgs['isName'] : false;
            };

            this.validate = function () {
                this.hasErrors = false;
                this.form.find('.form-textfield, .form-select')
                    .each(function () {
                        var item = $(this);
                        var name = item.attr('name');
                        for (var a = 0; a < filters[name].length; a++) {
                            var val = item.hasClass('form-select')
                                ? item.children(':selected').val() : item.val();
                            self[filters[name][a]](item, val);
                            if (self.errors[name]) {
                                item.closest('.form-item').addClass('error')
                                    .children('.form-label-msg')
                                    .text(self.errors[name]);
                                break;
                            } else {
                                item.closest('.form-item').removeClass('error')
                                    .children('.form-label-msg')
                                    .text('');
                            }
                        }
                    });

                var keys = Object.keys(this.errors);
                for (var a = 0; a < keys.length; a++)
                    if (this.errors[keys[a]]) {
                        this.hasErrors = true;
                        break;
                    }
                return this.hasErrors;
            };

            this.form.on('submit', function (event) {
                if (self.validate.call(self))
                    event.preventDefault();
            });
        }
        
        $('.js-form').each(function () {
            var obj = new handleForm($(this));
        });
    });
})(jQuery);
