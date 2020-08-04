function updateTable() {
    $.get(context.ajaxUrl, refreshTable);
}

function switchEnable(obj) {
    let id = obj.closest("tr").attr("id");
    let checked = obj.prop('checked');
    $.ajax({
        url: context.ajaxUrl + id,
        type: "POST",
        data: "enabled=" + checked
    }).done(function () {
        obj.closest("tr").attr("data-userEnabled", checked);
        successNoty("User is " + (checked ? "activated" : "deactivated"));
    }).fail(function () {
        obj.prop('checked', !checked);
    });
}

// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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
                        "asc"
                    ]
                ],
            })
        }
    );
    $(".enable").click(function () {
        switchEnable($(this));
    });
});