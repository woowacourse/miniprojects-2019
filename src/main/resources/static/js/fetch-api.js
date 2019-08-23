const AjaxApi = function() {
    this.post = (url, data) => {
        return fetch(url, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
    };

    this.put = (url, data) => {
        return fetch(url, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
    };

    this.delete = (url) => {
        return fetch(url, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json'
            }
        });
    };

    this.get = (url) => {
        return fetch(url, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json'
            }
        });
    };
}
const Api = new AjaxApi();