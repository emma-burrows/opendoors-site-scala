$(function () {
  var img = $('<img src="' + jsRoutes.controllers.Assets.at("images/ajax-loader.gif").url + '">');
  var checkResult = jsRoutes.controllers.AuthorsController.findAll();
  var importResult = jsRoutes.controllers.AuthorsController.importAll();
  var donotimportResult = jsRoutes.controllers.AuthorsController.doNotImportAll();

  // HELPERS
  var authorSetup = function (id) {
    var el = $('#author' + id),
      message = el.find('.message'),
      storyMessages = el.find('.Story .message-info');
    console.log("author-import " + id);
    message.removeClass().addClass('message');
    $(message).text();
    message.prepend(img); //http://ajaxload.info/
    $(storyMessages).text('');
    return { el: el, message: message, storyMessages: storyMessages };
  };

  var toggleDoNotImport = function (el) {
    var button =$(el).find('.author-not-import-button');
    var importButton = $(el).find('.author-import-button');
    var doNotImport = !(button.attr("data-do-not-import")  === 'true');

    button.attr("data-do-not-import", doNotImport);
    if (doNotImport == true) {
      el.addClass("do-not-import");
      button.text("set all to allow import");
      importButton.hide();
    } else {
      el.removeClass("do-not-import");
      button.text("set all to do NOT import");
      importButton.show();
    }
  };


  // HANDLERS
  // Import all
  $(".author-import-button").click(function (event) {

    var id = $(this).attr("data-author-id");
    var els = authorSetup(id);
    els.message.text('');

    importResult.ajax({
      success: function (data) {
        if (data.status = 'error') {
          els.message.addClass('text-warning bg-warning');
        }
        if (data.messages != undefined) {
          $.each(data.messages, function (idx, item) {
            els.message.append(item + '<br/>')
          })
        } else if (data.error != undefined) {
          els.message.addClass('text-warning bg-warning');
          els.message.text("Error: " + data.error);
        }
      },
      error: function (data, status, err) {
        els.message.addClass('text-warning bg-warning');
        els.message.text('An error has occurred: "' + err + '". Status: ' + status);
        if (data.messages != 'undefined') {
          els.message.append('MESSAGES: ' + data.messages)
        }
        else {
          els.message.append(data)
        }

      },
      complete: function () {
        els.message.remove(img);
      },
      data: {
        authorId: id
      },
      dataType: 'json',
      contentType: "application/json"
    });
    console.log("author-import click handler done " + id);
  });

  // Check all
  $(".author-check-button").click(function (event) {
    var id = $(this).attr("data-author-id");
    var els = authorSetup(id);

    checkResult.ajax({
      success: function (data) {
        if (data.body != undefined) {
          $.each(data.body, function (idx, item) {
            switch (item.status) {
              case "ok":
                $(els.storyMessages.get(idx)).text("Found at " + item.work_url);
                break;
              case "not_found":
                $(els.storyMessages.get(idx)).text(item.error);
                break;
              case undefined:
                els.message.text("Empty response from remote server")
                break;
            }
            els.message.text("Please check stories below for results")
          })
        } else if (data.error != undefined) {
          els.message.addClass('text-warning bg-warning');
          els.message.text("Error: " + data.error);
        }
      },
      error: function (data, status, err) {
        console.log("failure");
        els.message.text('An error has occurred: "' + err + '". Status: ' + status)
      },
      complete: function () {
        els.message.remove(img);
      },
      data: {
        authorId: id
      },
      dataType: 'json',
      contentType: "application/json"
    });
  })

  // Do not import all
  $(".author-not-import-button").click(function (event) {
    var id = $(this).attr("data-author-id");
    var els = authorSetup(id);
    var doNotImport = $(this).attr("data-do-not-import") !== 'true';

    donotimportResult.ajax({
      success: function (data) {
        toggleDoNotImport(els.el);
        if (data.body != undefined) {
          els.message.text(data.body);
        } else if (data.error != undefined) {
          els.message.addClass('text-warning bg-warning');
          els.message.text("Error: " + data.error);
        }
      },
      error: function (data, status, err) {
        els.message.text('An error has occurred: "' + err + '". Status: ' + status)
      },
      complete: function () {
        els.message.remove(img);
      },
      data: {
        authorId: id,
        doNotImport: doNotImport
      },
      dataType: 'json',
      contentType: "application/json"
    });
  })
});
