const DEFAULT_CONTENT_TYPE = 'application/json;charset=UTF-8';

const api = {
    GET: url => fetch(url, { method: 'GET' }),
    POST: (url, data) => fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': DEFAULT_CONTENT_TYPE
        },
        body: JSON.stringify(data)
    })
}