const friendTagService = (function() {
	const createFriendsList = () => {
        api.GET("/api/friends")
            .then(res => {
            	if (res.status == 200) {
            		return res.json()
            	}
            	throw res.json()
            })
            .then(friends => {
                for (let i = 0; i < friends.length; i++) {
                    createFriendDOM(friends[i])
                }
            })
            .catch(errorMessage => {
            	alert(errorMessage.message)
            })
	}

	const deleteFriendsList = () => {
		const friendsTagList = document.getElementsByClassName('friends-tag-list')[0]
		while (friendsTagList.hasChildNodes()) { 
			friendsTagList.removeChild(friendsTagList.firstChild);
		}
	}

	const createFriendDOM = friend => {
        document.getElementsByClassName('friends-tag-list')[0].insertAdjacentHTML('beforeend', friendTagTemplate(friend))
	}

	const tag = () => {
		const friends_tag_list_items = document.getElementsByClassName('friends-tag-list')[0].getElementsByTagName('li')
		let tagged_id = []
		for(let i = 0; i < friends_tag_list_items.length; i++) {
			if (friends_tag_list_items[i].getElementsByTagName('input')[0].checked == true) {
				tagged_id.push(friends_tag_list_items[i].getAttribute('data-id'))
			}
		}
		return tagged_id
	}

	const init = () => {
		deleteFriendsList()
		createFriendsList()
	}

	return {
		init : () => init(),
		tag : () => tag()
	}
})()

$('#friend-tag-modal').on('show.bs.modal', function(e) {
	friendTagService.init()
})
