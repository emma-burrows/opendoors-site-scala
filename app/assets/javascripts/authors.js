var opts = {
  lines: 13 // The number of lines to draw
  , length: 17 // The length of each line
  , width: 7 // The line thickness
  , radius: 6 // The radius of the inner circle
  , scale: 0.25 // Scales overall size of the spinner
  , corners: 1 // Corner roundness (0..1)
  , color: ['#f00', '#FFA500', '#0ff', '#0f0', '#00f', '#f0f', '#4B0082'] // #rgb or #rrggbb or array of colors
  , opacity: 0.1 // Opacity of the lines
  , rotate: 0 // The rotation offset
  , direction: 1 // 1: clockwise, -1: counterclockwise
  , speed: 1 // Rounds per second
  , trail: 25 // Afterglow percentage
  , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
  , zIndex: 2e9 // The z-index (defaults to 2000000000)
  , className: 'spinner' // The CSS class to assign to the spinner
  , top: '0px' // Top position relative to parent
  , left: '0px' // Left position relative to parent
  , shadow: false // Whether to render a shadow
  , hwaccel: false // Whether to use hardware acceleration
  , position: 'absolute' // Element positioning
}

$(function () {
  // Import all
  $(".author-import-button").click(function (event) {
    var id = $(this).attr("data-author-id"),
        el = $('#author' + id),
        message = el.find('.message');
    message.text('');
    message.spin(opts);

    var result = jsRoutes.controllers.AuthorsController.importAll();
    result.ajax({
      success: function (data) {
        var d = $.parseJSON(data);
        $.each(d, function (idx, item) {
          message.text(item.error)
        })
      },
      error: function(data, status, err) {
        message.text('An error has occurred: "' + err + '". Status: ' + status)
      },
      complete: function() {
        message.spin(false);
      },
      data : {
        authorId: id
      },
      dataType: 'json',
      contentType: "application/json"
    })
  })
});
