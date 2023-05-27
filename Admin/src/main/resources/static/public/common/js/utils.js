// noinspection JSUnresolvedVariable,JSUnresolvedFunction
// noinspection JSUnresolvedFunction

/**
 * @param {String} selector
 * @param {String} into
 * @param {String} rootPath
 * @param {Function} callback
 * @param {String} action
 */
function prepareMouseAction(selector, into = null, rootPath = null, callback = null, action = "click") {
    $(selector).off(action).on(action, function (e) {
        e.preventDefault();
        let url;
        if ($(this).get(0).tagName.toUpperCase() === "A") {
            url = $(this).attr("href");
        } else {
            url = $(this).data("url");
        }
        load(url, into, rootPath, callback);
    });
}

/**
 *
 * @param {String} selector
 * @param {String} into
 * @param {String} rootPath
 * @param {Function} callback
 * @param {String} type
 * @param {String} action
 */
function prepareKeyboardAction(selector, into = null, rootPath = null, callback = null, type = "GET", action = "keyup") {
    $(selector).off(action).on(action, function () {
        let url = $(this).data("url") + removeUrlParam(location.search, "p")
        let val = $(this).val()
        let paramKey = $(this).data("param-key");

        if (val !== undefined && val.length > 0) {
            url = replaceUrlParam(url, paramKey, val)
        } else {
            url = removeUrlParam(url, paramKey)
        }

        load(url, into, rootPath, callback, type);
    });
}

function refreshTableItemSort(tableId) {
    let tbl = $(tableId);
    let currentSort = tbl.data("sort");
    let currentOrder = tbl.data("order");

    tbl.find("thead th.sortable").each(function () {
        $(this).removeClass("asc desc");
        if ($(this).data("sort") === currentSort) {
            $(this).addClass(currentOrder);
            return false;
        }
    })
}

/**
 *
 * @param {String} searchId
 */
function refreshSearchBar(searchId) {
    let paramKey = $(searchId).data("param-key");
    $(searchId).val(getUrlParam(paramKey));
}

function pendingFocus(modal, ele) {
    modal.on('shown.bs.modal', function () {
        ele.focus();
    });
}

function clearFormElements(ele) {
    ele.find(":input").removeClass("valid error");
    ele[0].reset();
}

/**
 *
 * @param {String} $msg
 * @param {String} $type
 * @param {String} $title
 * @param {Number} $duration
 */
function showToast($msg, $type, $title = "", $duration = 3000) {
    let $toast = {
        "title": $title !== "" ? $title : ucfirst($type) + "!",
        "message": $msg,
        "type": $type,
        "duration": $duration
    };
    toast($toast);
}

/**
 *
 * @param {String} $title
 * @param {String} $msg
 * @param {String} $btnType
 * @param {String} $icon
 * @param {Function} callback
 * @param {Boolean} autoHide
 */
function showConfirm($title, $msg, $btnType = "danger", $icon = null, callback = null, autoHide = true) {
    let mModal = $("#confirmDialog");
    let mIcon = mModal.find(".modal-header i")
    let mSubmit = mModal.find("button[submit]")
    mModal.find(".modal-title").text($title)
    mModal.find(".modal-body").text($msg)

    mSubmit.removeClass();
    mSubmit.addClass("btn btn-" + $btnType)

    mIcon.removeClass();
    if ($icon != null) {
        mIcon.addClass("mdi mdi-" + $icon)
    }

    mSubmit.off("click").on("click", () => {
        if (autoHide) {
            mModal.modal("hide")
        }
        if (callback != null) {
            callback()
        }
    })
    mModal.modal("show")
}

function hideConfirm() {
    $("#confirmDialog").modal("hide");
}
