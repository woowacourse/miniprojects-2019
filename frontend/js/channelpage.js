const channelCtx = {
    flags: {
        isInUpdate: false
    }
}

if (!wootubeCtx.util.getUrlParams('id')) {
    api.retrieveLoginInfo()
        .then(res => {
            if (res.status !== 200) {
                alert('로그인이 필요합니다.')
                window.location.href = '/login.html'
            }
        })
}

document.querySelector('#btn-update').addEventListener('click', () => {
    channelCtx.flags.isInUpdate = changeUpdateState(channelCtx.flags.isInUpdate)
})

document.querySelector('#btn-submit').addEventListener('click', () => {
    const nameInput = document.querySelector('#update-name')
    const emailInput = document.querySelector('#update-email')

    api.retrieveLoginInfo()
        .then(res => res.json())
        .then(json => api.updateUser(json.id, JSON.stringify({
            name: nameInput.value,
            email: emailInput.value,
        })))
        .then(res => {
            if (res.status === 200) {
                window.location.reload()
                return
            }
            res.json()
                .then(json => {
                    const alertElm = document.querySelector('.alert.alert-danger')
                    alertElm.innerText = json.message
                    alertElm.classList.remove('d-none')
                })
        })
})

document.querySelector('#btn-leave').addEventListener('click', () => {
    const id = wootubeCtx.util.getUrlParams('id')

    if (id) {
        api.deleteUser(id)
        .then(res => {
            if (res.status === 204) {
                alert('탈퇴되었습니다')
                window.location.href = '/'
                return
            }
            return res.json()
        })
        .then(json => {
            const alertElm = document.querySelector('.alert.alert-danger')
            alertElm.innerText = json.message
            alertElm.classList.remove('d-none')
        })
        return
    }
    
    api.retrieveLoginInfo()
        .then(res => res.json())
        .then(json => api.deleteUser(json.id))
        .then(res => {
            if (res.status === 204) {
                alert('탈퇴되었습니다')
                window.location.href = '/'
                return
            }
            return res.json()
        })
        .then(json => {
            const alertElm = document.querySelector('.alert.alert-danger')
            alertElm.innerText = json.message
            alertElm.classList.remove('d-none')
        })
})

const changeUpdateState = function (flags) {
    const nameElm = document.querySelector('#user-name')
    const emailElm = document.querySelector('#user-email')
    const nameInput = document.querySelector('#update-name')
    const emailInput = document.querySelector('#update-email')
    const alertElm = document.querySelector('.alert.alert-danger')

    if (!flags) {
        nameInput.value = nameElm.innerText
        emailInput.value = emailElm.innerText
        
        nameElm.classList.add('d-none')
        emailElm.classList.add('d-none')
        nameInput.classList.remove('d-none')
        emailInput.classList.remove('d-none')
        alertElm.classList.add('d-none')

        document.querySelector('#btn-submit').classList.remove('d-none')
        document.querySelector('#btn-update').innerHTML = '<i class="ti-close"></i>'
    } else {
        nameElm.classList.remove('d-none')
        emailElm.classList.remove('d-none')
        nameInput.classList.add('d-none')
        emailInput.classList.add('d-none')
        alertElm.classList.add('d-none')

        document.querySelector('#btn-submit').classList.add('d-none')
        document.querySelector('#btn-update').innerHTML = '<i class="ti-pencil"></i>'
    }
    return !flags
}

const channelService = (function () {
    const id = wootubeCtx.util.getUrlParams('id')

    const loadVideoCards = function (userId) {
        api.requestMyChannelVideos(userId)
            .then(response => response.json())
            .then(json => addChannelVideoCardTemplates(json, 'dateVideoCard'))
    }

    const subscribe = function() {
        if (!id) {
            alert('자기 자신은 구독할 수 없습니다.')
            return
        }

        api.subscribe(id)
            .then(res => {
                if (res.status == 400) {
                    res.json().then(err => alert(err.message))
                } else if (res.status !== 201) {
                    alert('실패했습니다. 다시 시도해주세요.')
                } else {
                    alert('구독되었습니다.')
                    api.retrieveLoginInfo()
                        .then(res => res.json())
                        .then(json => addSubscribe(json.id))
                }
            })
    }
    
    const cancelSubscribe = function() {
        if (!id) {
            alert('자기 자신은 구독을 취소할 수 없습니다.')
            return
        }

        api.cancelSubscribe(id)
            .then(res => {
                if (res.status !== 204) {
                    alert('실패했습니다. 다시 시도해주세요.')
                } else {
                    alert('구독을 취소하였습니다.')
                    api.retrieveLoginInfo()
                        .then(res => res.json())
                        .then(json => addSubscribe(json.id))
                }
            })
    }
    
    const setSubscribeBtns = function() {
        const subscribeBtn = document.getElementById('subscribe-btn')
        const cancelSubscribeBtn = document.getElementById('cancel-subscribe-btn')
    
        subscribeBtn.addEventListener('click', subscribe)
        cancelSubscribeBtn.addEventListener('click', cancelSubscribe)
    }

    const setUserInfo = (name, email) => {
        const nameElm = document.querySelector('#user-name')
        const emailElm = document.querySelector('#user-email')

        nameElm.innerText = name
        emailElm.innerText = email

        const topNameElm = document.querySelector('#top-name')
        const topEmailElm = document.querySelector('#top-email')

        topNameElm.innerText = name
        topEmailElm.innerText = email

    }

    const init = () => {
        setSubscribeBtns()
        if (id) {
            api.requestUser(id)
            .then(res => {
                if (res.status === 404) {
                    alert('존재하지 않는 사용자입니다')
                    window.location.href = '/';
                    return
                }
                return res.json()
            })
            .then(json => {
                setUserInfo(json.name, json.email)

                api.retrieveLoginInfo()
                    .then(res => {
                        if (res.status === 200) {
                            return res.json()
                        }
                    })
                    .then(json => {
                        if (json && id === json.id) {
                            document.querySelector('#btn-update')
                                .classList.remove('d-none')
                            document.querySelector('#btn-leave')
                                .classList.remove('d-none')
                        }
                    })

                loadVideoCards(json.id)
            })
            return
        }
    
        api.retrieveLoginInfo()
            .then(res => res.json())
            .then(json => api.requestUser(json.id))
            .then(res => res.json())
            .then(json => {
                setUserInfo(json.name, json.email)
                document.querySelector('#btn-update').classList.remove('d-none')
                document.querySelector('#btn-leave').classList.remove('d-none')
                loadVideoCards(json.id)
            });
    }
    return {
        init,
    }
})();

channelService.init();