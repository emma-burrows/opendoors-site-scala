$(function () {
  var img = $('<img src="' + jsRoutes.controllers.Assets.at("images/ajax-loader.gif").url+ '">');
  var checkResult = jsRoutes.controllers.AuthorsController.findAll();
  var importResult = jsRoutes.controllers.AuthorsController.importAll();


  // Import all
  $(".author-import-button").click(function (event) {

    var id = $(this).attr("data-author-id"),
      el = $('#author' + id),
      message = el.find('.message'),
      storyMessages = el.find('.Story .message-info');
    console.log("author-import " + id);
    message.removeClass('text-warning bg-warning');
    $(message).text('gg');
    message.prepend(img); //http://ajaxload.info/
    $(storyMessages).text('');

    importResult.ajax({
      success: function (data) {
        console.log("author-import success " + id);
        if (data.body != undefined) {
          console.log("author-import data body is defined " + id);
          $.each(data.body, function (idx, item) {
            switch (item.status) {
              case "ok":
                $(storyMessages.get(idx)).text("Found at " + item.work_url);
                break;
              case "not_found":
                $(storyMessages.get(idx)).text(item.error);
                break;
            }
            message.text("Please check stories below for results")
          })
        } else if (data.error != undefined) {
          console.log("author-import data ERROR is defined " + message.text());
          message.addClass('text-warning bg-warning');
          message.text("Error: " + data.error);
        }
      },
      error: function (data, status, err) {
        console.log("author-import failure " + id);
        message.text('An error has occurred: "' + err + '". Status: ' + status)
      },
      complete: function () {
        message.remove(img);
      },
      data: {
        authorId: id
      },
      dataType: 'json',
      contentType: "application/json"
    });
    console.log("author-import click handler done " + id);
    return;
  });

  $(".author-check-button").click(function (event) {
    var id = $(this).attr("data-author-id"),
      el = $('#author' + id),
      message = el.find('.message'),
      storyMessages = el.find('.Story .message-info');
    message.text('');
    message.removeClass('text-warning bg-warning');
    message.prepend(img); //http://ajaxload.info/
    $(storyMessages).text('');

    checkResult.ajax({
      success: function (data) {
        if (data.body != undefined) {
          $.each(data.body, function (idx, item) {
            switch (item.status) {
              case "ok":
                $(storyMessages.get(idx)).text("Found at " + item.work_url);
                break;
              case "not_found":
                $(storyMessages.get(idx)).text(item.error);
                break;
              case undefined:
                message.text("Empty response from remote server")
                break;
            }
            message.text("Please check stories below for results")
          })
        } else if (data.error != undefined) {
          message.addClass('text-warning bg-warning');
          message.text("Error: " + data.error);
        }
      },
      error: function (data, status, err) {
        console.log("failure");
        message.text('An error has occurred: "' + err + '". Status: ' + status)
      },
      complete: function () {
        message.remove(img);
      },
      data: {
        authorId: id
      },
      dataType: 'json',
      contentType: "application/json"
    });
    return;
  })
});
