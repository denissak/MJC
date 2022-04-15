AJS.toInit(function () {
    AJS.$("#select2-example").auiSelect2();
    AJS.$('.project-dashboard-save-button').click(function(e) {
        e.preventDefault();
        var options = AJS.$(this).closest('form');
        var dashboardNames = AJS.$('#select2-example').val();
        var filterJQLId = parseInt(options.find('.select option:selected').val());
        var isDark = AJS.$('#checkbox-isDark').prop('checked');
        var token = AJS.$('meta[name=token]').attr("content");
        console.log(token);
        if (dashboardNames == null) {
            dashboardNames = [""];
        }
        var data = {
            id: $('.project-dashboard-save-button').attr('rowId'),
            dashboardNames: dashboardNames,
            filterJQLId: filterJQLId,
            dark: isDark
        };
        AJS.$.ajax({
            url: /*AJS.contextPath() + */'/rest/dashboard/1.0/projectdashboards/save',
            type: 'PUT',
            headers: {
            Authorization: "JWT " + token
            },
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(data),
            success: function(resp) {
                console.log(resp);
                $('.project-dashboard-save-button').attr('rowId', resp.id);
                AJS.messages.confirmation({
                    title: 'Success!',
                    delay: 500,
                    body: '<p>Settings saved successfully.</p>'
                });
            },
            error: function(err) {
                console.log(err);
            }
        });
    });

    AJS.$('.view-setting-button').click(function(e) {
        $(".setting-div").show();
        $(".project-dashboard-view-div").hide();
    });
    AJS.$('.hide-setting-button').click(function(e) {
        $(".project-dashboard-view-div").show();
        $(".setting-div").hide();
    });
});
