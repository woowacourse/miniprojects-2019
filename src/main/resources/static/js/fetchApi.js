const FETCH_APP = (() => {
    'use strict';

    const FetchApi = function () {
        const GET = 'GET';
        const POST = 'POST';
        const PUT = 'PUT';
        const DELETE = 'DELETE';

        const fetchTemplate = function (requestUrl, method, header, body, ifSucceed) {
            fetch(requestUrl, {
                method: method,
                headers: header,
                body: body
            }).then(response => {
                if (response.status === 200) {
                    return ifSucceed(response);
                }
                if (response.status === 400) {
                    errorHandler(response);
                }
            });
        };

        const fetchTemplateWithoutBody = function (requestUrl, method, ifSucceed) {
            fetch(requestUrl, {
                method: method,
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                    'Accept': 'application/json'
                }
            }).then(response => {
                if (response.status === 200) {
                    return ifSucceed(response);
                }
                if (response.status === 400) {
                    errorHandler(response);
                }
            });
        };

        const errorHandler = function (error) {
            error.json()
                .then(exception => {
                    alert(exception.message)
                });
        };

        return {
            GET: GET,
            POST: POST,
            PUT: PUT,
            DELETE: DELETE,
            fetchTemplate: fetchTemplate,
            fetchTemplateWithoutBody: fetchTemplateWithoutBody,
        }
    };

    return {
        FetchApi: FetchApi
    }
})();
