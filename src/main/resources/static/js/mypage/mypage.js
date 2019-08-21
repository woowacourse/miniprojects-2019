const USER_URI = `/api${location.pathname}`

const profile = document.getElementById('profile')
const details = document.getElementById('details')
const blockTypes = ['introduce', 'pictures', 'friends']

const feedInitLoad = () =
>
{
    api.GET(USER_URI)
        .then(res = > res.json()
)
.
    then(user = > user.contents
)
.
    then(user = > {
        profile.stylze.backgroundImage = `url(${user.profile})`
        return user
    }
)
.
    then(user = > {
        blockTypes.forEach(type = > {
            const block = details.querySelector(`.${type} .card-block`)
            const template = feedTemplates[type](user)
            block.appendChild(wrapperTemplate(template))
        }
)
})
.
    catch(error = > console.error(error)
)
}

feedInitLoad()
