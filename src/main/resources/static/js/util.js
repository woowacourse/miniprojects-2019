const request = {
    get(url) {
        return axios.get(url)
            .catch(
                (response) => {
                    console.log(response)
                }
            )
    },
    post(url, data) {
        return axios.post(url, data)
    },
    put(url, data) {
        return axios.put(url, data)
    },
    delete(url) {
        return axios.delete(url)
    }
}