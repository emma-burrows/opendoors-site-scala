$(function () {
  var img = $('<img src="/assets/images/ajax-loader.gif">');

  // Import all
  $(".author-import-button").click(function (event) {
    var id = $(this).attr("data-author-id"),
      el = $('#author' + id),
      message = el.find('.message');
    message.text('');
    message.prepend(img); //http://ajaxload.info/

    var result = jsRoutes.controllers.AuthorsController.importAll();
    result.ajax({
      success: function (data) {
        var d = $.parseJSON(data);
        $.each(d.body, function (idx, item) {
          console.log('item: ' + item.error);
          message.text(item.error)
        })
      },
      error: function(data, status, err) {
        message.text('An error has occurred: "' + err + '". Status: ' + status)
      },
      complete: function() {
        message.remove(img);
      },
      data : {
        authorId: id
      },
      dataType: 'json',
      contentType: "application/json"
    })
  })
})
