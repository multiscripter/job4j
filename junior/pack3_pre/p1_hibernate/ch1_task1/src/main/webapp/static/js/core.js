(function ($) {
    $(function () {
        /*
         * Показать/скрыть выполненные задачи.
         * Версия: 2018-03-30.
         */
        var showAll = {
            chbox: null,
            tbody: null,
            items: null,

            toggle: function () {
                this.items = this.tbody.find('tr:not(:first)');
                this.items.removeClass('d-none');
                if (this.chbox.filter(':checked').val() != 'on')
                    this.items.children('td[data-done=true]')
                        .parent().addClass('d-none');
            },

            init: function () {
                this.chbox = $('.js-show-all');
                if (!this.chbox.length) return;
                this.tbody = $('.js-items tbody');
                var self = this;
                this.chbox.on('click', function (event) {
                    self.toggle.call(self);
                });
                this.toggle();
            }
        };
        showAll.init();

        /*
         * Обработчик формы.
         * Версия: 2018-04-03.
         */
        var form = {
            action: null,
            form: null,
            fieldId: null,
            fieldName: null,
            fieldDescr: null,
            fieldCreated: null,
            fieldDone: null,
            btn: null,
            curTr: null,

            replace: function (tar) {
                this.action = tar.attr('data-action');
                this.form.attr('action', document.location.href + this.action + '/');
                this.curTr.removeClass('js-curTr');
                this.tarTr = tar.closest('tr').addClass('js-curTr');
                var tarTds = this.tarTr.children('th, td');
                // Заменить id.
                this.fieldId.val(tarTds.eq(0).text());
                // Заменить name.
                this.fieldName.parent().text(this.fieldName.val());
                var tmp = this.fieldName.detach();
                this.fieldName.val(tarTds.eq(1).text());
                tarTds.eq(1).empty().append(tmp);
                // Заменить descr.
                this.fieldDescr.parent().text(this.fieldDescr.val());
                tmp = this.fieldDescr.detach();
                this.fieldDescr.val(tarTds.eq(2).text());
                tarTds.eq(2).empty().append(tmp);
                // Заменить created.
                this.fieldCreated.parent().text(this.fieldCreated.val());
                tmp = this.fieldCreated.detach();
                this.fieldCreated.val(tarTds.eq(3).text());
                tarTds.eq(3).empty().append(tmp);
                // Заменить done.
                this.fieldDone.parent().text(this.fieldDone.find(':checked').next().text());
                tmp = this.fieldDone.detach();
                this.fieldDone.find('input').removeAttr('checked')
                    .filter('input[value=' + tarTds.eq(4).attr('data-done') + ']')
                    .prop('checked', true);
                tarTds.eq(4).empty().append(tmp);
                // Заменить кнопки.
                this.btn.parent().find('.js-form-action').removeClass('d-none');
                tarTds.eq(5).append(this.btn.val(tar.text()).detach())
                    .find('.js-form-action').addClass('d-none');
                this.curTr = this.tarTr;
            },

            setDataDone: function (tar) {
                tar.closest('td').attr('data-done', tar.parents('.js-field-done')
                    .find(':checked').val());
            },

            click: function (event) {
                var tar = $(event.target);
                if (tar.hasClass('js-form-action')) {
                    event.preventDefault();
                    this.replace(tar);
                } else if (tar.parents('.js-field-done').length)
                    this.setDataDone(tar);
            },

            createdSuccess: function (obj) {
                this.fieldId.val('');
                this.fieldName.val('');
                this.fieldDescr.val('');
                this.fieldCreated.val('');
                this.fieldDone.find(':checked').removeAttr('checked')
                    .filter('input[value=false]').prop('checked', true);
                var tr = $('<tr>');
                var col1 = $('<th>');
                col1.attr('scope', 'row');
                col1.text(obj.id);
                tr.append(col1);
                tr.append($('<td>' + obj.name + '</td>'));
                tr.append($('<td>' + obj.descr + '</td>'));
                tr.append($('<td>' + obj.created + '</td>'));
                var col5 = $('<td>');
                col5.attr('data-done', obj.done);
                col5.text(obj.done == 'true' ? messages.yes : messages.no);
                if (showAll.chbox.filter(':checked').val() != 'on' && obj.done == 'true')
                    tr.addClass('d-none');
                tr.append(col5);
                var col6 = $('<td>');
                col6.addClass('col-btns');
                var btnUpdate = $('<a>');
                btnUpdate.addClass('btn btn-primary btn-update js-form-action');
                btnUpdate.attr('data-action', 'update');
                btnUpdate.attr('role', 'button');
                btnUpdate.attr('href', '#');
                btnUpdate.text(messages.update);
                col6.append(btnUpdate);
                var btnDel = $('<a>');
                btnDel.addClass('btn btn-primary btn-del js-form-action');
                btnDel.attr('data-action', 'delete');
                btnDel.attr('role', 'button');
                btnDel.attr('href', '#');
                btnDel.text(messages.delete);
                col6.append(btnDel);
                tr.append(col6);
                this.form.find('tbody').append(tr);
            },

            deletedSuccess: function () {
                var deleted = this.curTr;
                this.replace(this.form.find('tbody tr:first-child .js-form-action'));
                deleted.remove();
            },

            updatedSuccess: function() {
                this.replace(this.form.find('tbody tr:first-child .js-form-action'));
            },

            handleErrors: function (errors) {
                for (var prop in errors) {
                    if (errors.hasOwnProperty(prop)) {
                        var cur = this.form.find('.js-field-' + prop);
                        if (cur.length)
                            cur.addClass('is-invalid').parent()
                                .append('<div class="invalid-feedback">'
                                + messages[errors[prop]] + '</div>');
                    }
                }
            },

            submit: function () {
                var obj = {
                    type: 'ajax',
                    id: this.fieldId.val(),
                    name: this.fieldName.val(),
                    descr: this.fieldDescr.val(),
                    created: this.fieldCreated.val(),
                    done: this.fieldDone.find(':checked').val()
                };
                var self = this;
                $.post(this.form.attr('action'), obj, function (data) {
                    self.form.find('.is-invalid').removeClass('is-invalid')
                        .parent().children('.invalid-feedback').remove();
                    console.log(data);
                    if (data.status == 'ok') {
                        switch (self.action) {
                            case 'create':
                                obj.id = data.id;
                                self.createdSuccess(obj);
                                break;
                            case 'delete':
                                self.deletedSuccess();
                                break;
                            case 'update':
                                self.updatedSuccess();
                                break;
                        }
                    } else if (data.status == 'error') {
                        self.handleErrors(data.errors);
                    }
                });
            },

            init: function () {
                this.form = $('.js-form');
                if (!this.form.length) return;
                this.action = 'create';
                this.fieldId = this.form.find('.js-form-item-id');
                this.fieldName = this.form.find('.js-field-name');
                this.fieldDescr = this.form.find('.js-field-descr');
                this.fieldCreated = this.form.find('.js-field-created');
                this.fieldDone = this.form.find('.js-field-done');
                this.btn = this.form.find('.js-form-submit');
                this.curTr = this.form.find('.js-curTr');
                var self = this;
                this.form.on('click', function (event) {
                    self.click.call(self, event);
                });
                this.form.on('submit', function (event) {
                    event.preventDefault();
                    self.submit.call(self);
                });
            }
        };
        form.init();
    });
})(jQuery);