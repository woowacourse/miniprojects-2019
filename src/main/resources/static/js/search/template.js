const searchboxResultTemplate = (user) =>
    `${user.name}`

const searchboxAllTemplate = (keyword) =>
    `<p style="color: blue"><strong>"${keyword}"</strong>에 대한 전체 검색</p>`


const resultUserTemplate = (user) =>
    `
<div class="media-left">
    <a href="/users/${user.id}">
        <img class="thumb-img img-circle" style="width: 74px; height: 74px;" src="https://www.facebook.com/search/async/profile_picture/?fbid=100004092244167&width=72&height=72" alt="...">
    </a>
</div>
<div class="media-body" style="margin-left: 20px">
    <h3 class="media-heading">
        <a href="/users/${user.id}">${user.name}</a>
    </h3>
    블라블라브라
</div>
`