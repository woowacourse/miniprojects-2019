const friendAskTemplate = (friendAsk) =>
`
<div class="dropdown-item friend-ask-item" data-id="${friendAsk.friendAskId}">
    <p>${friendAsk.senderName}</p>
    <button class="friend-ask-accept-btn">수락</button>
    <button class="friend-ask-reject-btn">거절</button>
</div>
`