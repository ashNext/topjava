$(function () {
    makeEditable({
            ajaxUrl: "profile/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            })
        }
    );

    jQuery('#startDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d'
    });

    jQuery('#endDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d'
    });

    jQuery('#startTime').datetimepicker({
        datepicker: false,
        format: 'H:i'
    });

    jQuery('#endTime').datetimepicker({
        datepicker: false,
        format: 'H:i'
    });

    jQuery('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
});