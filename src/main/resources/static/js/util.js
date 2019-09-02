class Request {
    constructor(baseUrl) {
        let request = axios.create({
            headers: {csrf: 'token'}
        });
        request.interceptors.response.use(this.handleSuccess, this.handleError);
        this.request = request;
        this.baseUrl = baseUrl;
    }

    handleSuccess(response) {
        return response;
    }

    handleError = (error) => {
        return Promise.reject(console.log(error));
    };

    get = (attachedUrl, callback = this.defaultCallback) => {
        return this.request.get(this.baseUrl + attachedUrl)
            .then(response => callback(response.status, response.data))
    };

    delete = (attachedUrl, callback = this.defaultCallback) => {
        return this.request.delete(this.baseUrl + attachedUrl)
            .then(response => callback(response.status, response.data))
    };

    post = (attachedUrl, data, callback = this.defaultCallback) => {
        return this.request.post(this.baseUrl + attachedUrl, data)
            .then(response => callback(response.status, response.data))
    };

    put = (attachedUrl, data, callback = this.defaultCallback) => {
        return this.request.put(this.baseUrl + attachedUrl, data)
            .then(response => callback(response.status, response.data))
    };

    defaultCallback = (status, data) => {
        console.log(status + data)
    }
}

const DomUtil = {
    active(domName) {
        const element = document.querySelector(domName);
        if (element.classList.contains("inactive")) {
            element.classList.remove("inactive");
        }
    },
    inactive(domName) {
        const element = document.querySelector(domName);
        element.classList.add("inactive")
    }
};

const htmlToStringParse = (html)=>{
    return html.replace("<","&lt;")
        .replace(">","&gt;")
}