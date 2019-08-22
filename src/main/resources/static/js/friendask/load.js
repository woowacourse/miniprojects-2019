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

	    addFriendAskAcceptEventListener()
	    addFriendAskRejectEventListener()
	    preventDropdownHide()
	}

    function createFriendAskDOM(friendAsk) {
        document.getElementById('friend-ask-list').insertAdjacentHTML('beforeend', friendAskTemplate(friendAsk))
    }

	function addFriendAskAcceptEventListener() {
		let btns = document.getElementsByClassName('friend-ask-accept-btn')
		for(let i = 0; i < btns.length; i++) {
			btns[i].addEventListener('click', function() {
			    let data = {"friendAskId" : btns[i].parentNode.getAttribute("data-id")}

                let res = $.ajax({
                    url: "/api/friends",
                    type: "POST",
                    async: false,
                    headers: header,
                    dataType: 'json',
                    data: JSON.stringify(data)
                })

	            if(res.status == 201) {
	                alert('친구가 되었습니다~')
	                let parent = btns[i].parentNode
	                parent.parentNode.removeChild(parent)
	            } else if(res.status == 400) {
    	            alert(res.responseJSON.message)
	            }
			})
		}
	}

	function addFriendAskRejectEventListener() {
    		let btns = document.getElementsByClassName('friend-ask-reject-btn')
    		for(let i = 0; i < btns.length; i++) {
    			btns[i].addEventListener('click', function() {
    			    let friendAskId = btns[i].parentNode.getAttribute("data-id")
    			    let deleteUrl = "/api/friends/asks/" + friendAskId

    	            let res = $.ajax({
    	                url: deleteUrl,
    	                type: "DELETE",
    	                async: false,
    	                headers: header
    	            })

    	            if(res.status == 204) {
    	                alert('친구 요청이 거절되었습니다.')
    	                let parent = btns[i].parentNode
    	                parent.parentNode.removeChild(parent)
    	            } else if(res.status == 400) {
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