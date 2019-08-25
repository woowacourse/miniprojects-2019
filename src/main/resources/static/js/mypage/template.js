const feedTemplatesCreator = () => {
    const templates = {
        introduce: (user) => `
        <div class="card-contents">
            <i class="ti-email"></i><span> ${user.email}</span>
        </div>
        `,

        // pictures: (user) => `
        //     <div class="card-images">
        //         ${multipleImageParser(user.images)}
        //     </div>
        // `,
        pictures: (user) => `
            <div class="card-images">
            </div>
        `,

        // friends: (user) => {
        //     const images = user.friends.map(friend => friend.profile)
        //     return `
        //         <div class="card-images">
        //             ${multipleImageParser(images)}
        //         </div>
        //     `
        // }
        friends: (user) => {
            return `
                <div class="card-images">
                </div>
            `
        }
    }

    const imageTemplate = (imageSrc) => `
        <img src=${imageSrc} class="image">
    `
    const multipleImageParser = (images) => images.map(image => imageTemplate(image.path)).join('')

    return templates
}

const wrapperTemplate = (template) => {
    const div = document.createElement('div');
    div.innerHTML = template
    return div.firstElementChild
}

const feedTemplates = feedTemplatesCreator()