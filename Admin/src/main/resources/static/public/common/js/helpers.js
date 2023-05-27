const CommonHelper = {

    documentReady(callback) {
        if (document.readyState === 'complete') {
            callback()
        } else {
            document.addEventListener("DOMContentLoaded", function onLoaded() {
                callback()
                document.removeEventListener('DOMContentLoaded', onLoaded)
            });
        }
    },

    loadScripts(src, raw = false, head = false, selector = ".LoadedScript") {
        this.documentReady(function () {
            const container = head ? "head" : "body";
            const contentScriptSelector = container + " " + selector;
            let contentScript = $(contentScriptSelector);
            if (contentScript.length === 0) {
                let attrName = selector.startsWith(".") ? "class" : "id";
                let attrValue = selector.substring(1);
                let div = $("<div></div>").attr(attrName, attrValue);
                $(container).append(div);
                contentScript = $(contentScriptSelector);
            }
            let content = raw ? "<script>" + src + "</script>" : "<script src='" + src + "'></script>";
            contentScript.html(content);
        })
    },

}

const PdfHelper = {

    savePdf(ele, fileName = this._randomFileName()) {
        if (typeof ele == 'string') {
            ele = $(ele)[0]
        }
        let opt = {
            margin: 0.5,
            filename: fileName,
            image: {type: 'jpeg', quality: 0.98},
            html2canvas: {scale: 2},
            jsPDF: {unit: 'in', format: 'letter', orientation: 'portrait'}
        };
        if (!window.html2pdf) {
            loadScripts("https://raw.githack.com/eKoopmans/html2pdf/master/dist/html2pdf.bundle.js", false, true);
        }
        let count = 0;
        let timeOut = 250;
        let run = () => {
            if (count++ > 20) return;
            window.html2pdf ? html2pdf(ele, opt) : setTimeout(run, timeOut);
        };
        run();
    },

    previewPdf(ele) {
        if (typeof ele == 'string') {
            ele = $(ele)
        }
        const width = 800, height = 450;
        let left = (screen.width - width) / 2;
        let top = screen.height / 6;
        let params = 'width=' + width + ',height=' + height + ',left=' + left + ',top=' + top;
        let printWindow = window.open('', '', params);
        printWindow.document.write('<html><head>' + $("head").html() + '</head>');
        printWindow.document.write('<body class="container-fluid p-0" onafterprint="close()">');
        printWindow.document.write(ele.parent().html());
        printWindow.document.write('<script>window.print();</script>');
        printWindow.document.write('</body></html>');
        printWindow.document.close();
    },

    _randomFileName(length = 16) {
        let charset = "abcdefghijklmnopqrstuvwxyz0123456789",
            retVal = "";
        for (let i = 0, n = charset.length; i < length; ++i) {
            retVal += charset.charAt(Math.floor(Math.random() * n));
        }
        return retVal;
    }

}

const AjaxHelper = {

    // default param and value when load ajax
    PARAMETER_AJAX: "ajax",
    VALUE_AJAX: true,

    _adapter_ajax($param) {
        return $.ajax({
            url: $param.url,
            type: $param.type,
            data: $param.data,
            success: $param.success,
            error: $param.error,
            async: $param.async ? $param.async : true,
        });
    },

    _adapter_ajax_with_file($param) {
        return $.ajax({
            url: $param.url,
            type: $param.type,
            data: $param.data,
            encType: "multipart/form-data",
            cache: false,
            processData: false,
            contentType: false,
            success: $param.success,
            error: $param.error
        });
    },

    _getTailUrl(url) {
        let lastIndex = url.lastIndexOf("?");
        return lastIndex !== -1 ? url.substring(lastIndex) : "";
    },

    _getTailUrlWithoutQuestionMark(url) {
        let lastIndex = url.lastIndexOf("?");
        return lastIndex !== -1 ? url.substring(lastIndex + 1) : "";
    },

    _getUrlParam(param) {
        let searchParams = new URLSearchParams(window.location.search)
        if (searchParams.has(param)) {
            return searchParams.get(param);
        }
        return null;
    },

    _replaceUrlParam(url, paramName, paramValue) {
        if (paramValue == null) {
            paramValue = '';
        }
        const pattern = new RegExp('(\\b' + paramName + '=)[^&]*');
        if (url.search(pattern) >= 0) {
            return url.replace(pattern, '$1' + encodeURIComponent(paramValue));
        }
        if (url.indexOf('?') === -1) {
            url += '?';
        } else {
            url += '&';
        }
        return url + paramName + '=' + encodeURIComponent(paramValue);
    },

    _removeUrlParam(url, paramName) {
        let rtn = url.split("?")[0],
            param,
            params_arr = [],
            queryString = (url.indexOf("?") !== -1) ? url.split("?")[1] : "";
        if (queryString !== "") {
            params_arr = queryString.split("&");
            for (let i = params_arr.length - 1; i >= 0; i -= 1) {
                param = params_arr[i].split("=")[0];
                if (param === paramName) {
                    params_arr.splice(i, 1);
                }
            }
            if (params_arr.length) rtn = rtn + "?" + params_arr.join("&");
        }
        return rtn;
    },

    _removeUrlParamAndReplace(param) {
        let url = new URL(window.location.href);
        url.searchParams.delete(param);
        window.history.replaceState({}, '', url);
    },

    /**
     *
     * @param {String} into
     * @param {String} rootPath
     * @param {String} url
     * @param {String} type GET | POST
     * @param {Object} data
     * @param {Function, String} success
     * @param {Function} error
     * @return {XMLHttpRequest}
     * @private
     */
    _load(into, rootPath, url, type, data = null, success = null, error = null) {
        return this._loadUrl(url, type, data, function (response) {
            if (rootPath != null) {
                let realUrl = rootPath + AjaxHelper._getTailUrl(url)
                let pushData = {
                    into: into,
                    loadUrl: url,
                    realUrl: realUrl,
                    type: type,
                    data: data,
                }
                if (typeof success == "string") {
                    pushData.success = success;
                }
                if (typeof success == "function" && success.name !== "") {
                    pushData.success = success.name;
                }
                if (history.state == null || history.state.realUrl !== realUrl) {
                    history.pushState(pushData, null, realUrl);
                }
            }
            if (typeof success == "function") {
                success(response);
            }
        }, error);
    },

    _loadUrl(url, type, data = null, success = null, error = null) {
        const $param = {
            url: url,
            type: type,
            data: data,
            success: success,
            error: error,
        };
        return this._adapter_ajax($param);
    },

    load(into, rootPath, url, type = "GET", data = null, success = null, error = null) {
        return this._load(into, rootPath, url, type, data, success, error)
    },

    loadUrl(url, type, data = null, success = null, error = null) {
        return this._loadUrl(url, type, data, success, error)
    }
}
