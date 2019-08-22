$(document).ready(function () {
    friendAskModule.init()
})

let friendAskModule = (function () {
    function loadFriendsAsks() {
        let res = $.ajax({
            url: "/api/friends/asks",
            type: "GET",
            async: false,
            dataType: 'json'
        })

        let data = res.responseJSON
        for (let i = 0; i < data.length; i++) {
            console.log(data[i])
            createFriendAskDOM(data[i])
        }

        addFriendAskEventListener()
        preventDropdownHide()
    }

    function createFriendAskDOM(friendAsk) {
        document.getElementById('friend-ask-list').insertAdjacentHTML('beforeend', friendAskTemplate(friendAsk))
    }

    function addFriendAskEventListener() {
        let btns = document.getElementsByClassName('friend-ask-accept-btn')
        for (let i = 0; i < btns.length; i++) {
            btns[i].addEventListener('click', function () {
                let data = {"friendAskId": btns[i].parentNode.getAttribute("data-id")}

                let res = $.ajax({
                    url: "/api/friends",
                    type: "POST",
                    async: false,
                    headers: header,
                    dataType: 'json',
                    data: JSON.stringify(data)
                })

                if (res.status == 201) {
                    alert('success')
                    let parent = btns[i].parentNode
                    parent.parentNode.removeChild(parent)
                } else if (res.status == 400) {
                    alert(res.responseJSON.message)
                }
            })
        }
    }

    function preventDropdownHide() {
        $('div.btn-group.dropleft').on('click', function (event) {
            let events = $._data(document, 'events') || {}
            events = events.click || []
            for (let i = 0; i < events.length; i++) {
                if (events[i].selector) {
                    if ($(event.target).is(events[i].selector)) {
                        events[i].handler.call(event.target, event)
                    }

                    $(event.target).parents(events[i].selector).each(function () {
                        events[i].handler.call(this, event)
                    })
                }
            }
            event.stopPropagation()
        })
    }

    return {
        init: function () {
            loadFriendsAsks()
        }
    }
})()