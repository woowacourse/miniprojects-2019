const channelCtx = {
    flags: {
        isInUpdate: false
    }
}

loadVideoCards = function (page, size, sort) {
    api.requestVideos(page, size, sort)
        .then(response => response.json())
        .then(json => addVideoCardTemplates(json.content, 'dateVideoCard'))
}
loadVideoCards(0, wootubeCtx.constants.videoChannelPageSize, 'createDate')

document.querySelector('#btn-update').addEventListener('click', (event) => {
    channelCtx.flags.isInUpdate = changeUpdateState(channelCtx.flags.isInUpdate)
})

changeUpdateState = function (flags) {
    const nameElm = document.querySelector('#user-name')
    const emailElm = document.querySelector('#user-email')
    const nameInput = document.querySelector('#update-name')
    const emailInput = document.querySelector('#update-email')

    if (!flags) {
        nameInput.value = nameElm.innerText
        emailInput.value = emailElm.innerText
        
        nameElm.classList.add('d-none')
        emailElm.classList.add('d-none')
        nameInput.classList.remove('d-none')
        emailInput.classList.remove('d-none')

        document.querySelector('#btn-submit').classList.remove('d-none')
        document.querySelector('#btn-update').innerHTML = '<i class="ti-close"></i>'
    } else {
        nameElm.classList.remove('d-none')
        emailElm.classList.remove('d-none')
        nameInput.classList.add('d-none')
        emailInput.classList.add('d-none')

        document.querySelector('#btn-submit').classList.add('d-none')
        document.querySelector('#btn-update').innerHTML = '<i class="ti-pencil"></i>'
    }
    return !flags
}