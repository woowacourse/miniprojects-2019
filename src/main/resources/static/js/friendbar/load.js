$(document).ready(function() {
	friendBarModule.init()
})

let friendBarModule = (function() {
	function loadAllUser() {
	    let res = $.ajax({
	        url:"/api/users",
	        type:"GET",
	        async:false,
	        dataType:'json'
	    })

	    let data = res.responseJSON
	    for(let i=0; i<data.length; i++) {
	    	createUserDOM(data[i])
	    }

	    addFriendsAskButtonClickListener()
	}

	function loadFriends() {
	    let res = $.ajax({
            url:"/api/friends",
            type:"GET",
            async:false,
            dataType:'json'
        })

        let data = res.responseJSON
        for(let i=0; i<data.length; i++) {
            createFriendDOM(data[i])
        }
    }

	function createUserDOM(user) {
		document.getElementById('all-user-list').insertAdjacentHTML('beforeend', allUserTemplate(user))
	}

	function createFriendDOM(friend) {
        document.getElementById('friends-list').insertAdjacentHTML('beforeend', friendTemplate(friend))
    }

	function addFriendsAskButtonClickListener() {
		let btns = document.getElementsByClassName('FriendAsk')
		for(let i = 0; i < btns.length; i++) {
			btns[i].addEventListener('click', function() {
			    let data = {"receiverId" : btns[i].parentNode.parentNode.getAttribute("data-id")}

	            let res = $.ajax({
	                url:"/api/friends/asks",
	                type:"POST",
	                async:false,
	                headers: header,
	                dataType:'json',
	                data: JSON.stringify(data)
	            })

	            if(res.status == 201) {
	                alert('success')
	            } else if(res.status == 400) {
    	            alert(res.responseJSON.message)
	            }
			})
		}
	}

	function preventDropdownHide() {
		$('div.btn-group.dropleft').on('click', function(event) {
			let events = $._data(document, 'events') || {}
			events = events.click || []
			for(let i = 0; i < events.length; i++) {
				if(events[i].selector) {
					if($(event.target).is(events[i].selector)) {
						events[i].handler.call(event.target, event)
					}

					$(event.target).parents(events[i].selector).each(function() {
						events[i].handler.call(this, event)
					})
				}
			}
			event.stopPropagation()
		})
	}

	return {
		init : function() {
		    loadAllUser()
		    loadFriends()
		    preventDropdownHide()
		}
	}
})()