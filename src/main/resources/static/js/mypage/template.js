const feedTemplatesCreator = () => {
    const templates = {
        introduce: (user) => `
        <div class="card-contents">
            <i class="ti-email"></i><span>${user.email}</span>
        </div>
        `,

        pictures: (user) => `
            <div class="card-images">
                ${multipleImageParser(user.images)}
            </div>
        `,

        friends: (user) => {
            const images = user.friends.map(friend => friend.profile)
            return `
                <div class="card-images">
                    ${multipleImageParser(images)}
                </div>
            `
        }
    }

    imageTemplate = (imageSrc) => `
        <img src=${imageSrc} class="image">
    `,
    multipleImageParser = (images) => images.map(image = > imageTemplate(image.path)).join('')

    return templates
}

const wrapperTemplate = (template) => {
    const div = document.createElement('div');
    div.innerHTML = template
    return div
}

const feedTemplates = feedTemplatesCreator()