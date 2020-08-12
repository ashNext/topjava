var mealAjaxUrl="profile/meals/";

$.ajaxSetup({
    converters: {
        "text json": function (date) {
            var json = JSON.parse(date);
            $(json).each(function () {
                this.dateTime = this.dateTime.replace('T', ' ').substr(0, 16);
            });
            return json;
        }
    }
});

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl+"filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                    $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    });

    jQuery('#startDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                maxDate: jQuery('#endDate').val() ? jQuery('#endDate').val() : false
            })
        }
    });

    jQuery('#endDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                minDate: jQuery('#startDate').val() ? jQuery('#startDate').val() : false
            })
        }
    });

    jQuery('#startTime').datetimepicker({
        datepicker: false,
        format: 'H:i',
        onShow:function( ct ){
            this.setOptions({
                maxTime: jQuery('#endTime').val() ? jQuery('#endTime').val() : false
            })
        }
    });

    jQuery('#endTime').datetimepicker({
        datepicker: false,
        format: 'H:i',
        onShow:function( ct ){
            this.setOptions({
                minTime: jQuery('#startTime').val() ? jQuery('#startTime').val() : false
            })
        }
    });

    jQuery('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
});