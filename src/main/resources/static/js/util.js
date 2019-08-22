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
        return Promise.reject(error)
    }

    get = async (attatchedUrl, callback = this.defaultCallback) => {
        return await this.request.get(this.baseUrl + attatchedUrl)
            .then(response => callback(response.status, response.data))
    }

    delete = async (attatchedUrl, callback = this.defaultCallback) => {
        return await this.request.delete(this.baseUrl + attatchedUrl)
            .then(response => callback(response.status, response.data))
    }

    post = async (attatchedUrl, data, callback = this.defaultCallback) => {
        return await this.request.post(this.baseUrl + attatchedUrl, data)
            .then(response => callback(response.status, response.data))
    }

    put = async (attatchedUrl, data, callback = this.defaultCallback) => {
        return await this.request.put(this.baseUrl + attatchedUrl, data)
            .then(response => callback(response.status, response.data))
    }

    defaultCallback = (status,data)=>{
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
}