$(function () {
  // add a click handler to the button
  $(".getMessageButton").click(function (event) {
    // make an ajax get request to get the message
    var id = $(this).parent().attr("id");
    console.log("id=" + id);
    var message = jsRoutes.controllers.MessageController.getMessage();
    message.ajax({
      success: function (data) {
        var d = $.parseJSON(data);
        console.log(d);
        $.each(d, function (idx, item) {
          console.log(item);
          $(".well").append($("<h2>").text(item.error))
        })
      },
      data : {
        id: id
      },
      dataType: 'json',
      contentType: "application/json"
    })
  })
});
