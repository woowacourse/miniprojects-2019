const USER_URI = `/api${location.pathname}`
const INTRODUCTION_URI = `/api${location.pathname}/introduction`
const DEFAULT_PERSON_IMAGE_URL = (number) => `/images/default/person${number}.jpg`

const profile = document.getElementById('profile')
const details = document.getElementById('details')
const blockTypes = ['introduce', 'pictures', 'friends']

const feedInitLoad = async () => {
    const introduction = await api.GET(INTRODUCTION_URI)
        .then(res => res.json())
        .catch(error => console.error(error))

    api.GET(USER_URI)
        .then(res => res.json())
        .then(user => {
            profile.style.backgroundImage = `url(${user.profile === undefined ?
                DEFAULT_PERSON_IMAGE_URL(generateRandomNumber(4)) : user.profile})`
            loadWriteForm(user)
            return user
        })
        .then(user => {
            blockTypes.forEach(type => {
                const block = details.querySelector(`.${type} .card-block`)
                let template
                if (type === 'introduce') {
                    template = feedTemplates[type](user, introduction)
                    block.appendChild(wrapperTemplate(template))
                    if (localStorage.loginUserId == introduction.userId) {
                        block.appendChild(wrapperTemplate(updateForm))
                        block.appendChild(wrapperTemplate(updateIntroductionBtn))
                        console.log($('#up'))
                        addUpdateListener()
                    }
                } else {
                    template = feedTemplates[type](user)
                }
            })
        })
        .catch(error => console.error(error))

    const updateIntroductionRequest = serializeObject($('#up'))
    console.log(updateIntroductionRequest)
}

const loadWriteForm = (receiver) => {
    const writeContainer = document.getElementById("write-post")
    writeContainer.innerHTML = writeFormTemplate(receiver)
}

const generateRandomNumber = (bound) => Math.floor(Math.random() * bound) + 1

const addUpdateListener = () => {
    const item_map =
        {'company' : '회사',
        'currentCity' : '거주지',
        'education' : '학교',
        'hometown' : '출신지'}

    const update_btn = document.getElementById("introduction-update-btn")
    const display_btn = document.getElementById("introduction-display-btn")
    const display_card = document.getElementById("display-card")
    const update_card = document.getElementById("update-card")

    update_btn.addEventListener('click', function(event) {
        api.GET(INTRODUCTION_URI)
            .then(res=>res.json())
            .then(introduction => {
                console.log(introduction)
                document.getElementById("userId").value = introduction.userId
                document.getElementById("education-input").value = checkNull(introduction.education)
                document.getElementById("current-city-input").value = checkNull(introduction.currentCity)
                document.getElementById("hometown-input").value = checkNull(introduction.hometown)
                document.getElementById("company-input").value = checkNull(introduction.company)
                display_card.style.display="none"
                update_card.style.display="block"
                update_btn.style.display="none"
                display_btn.style.display="block"
            })
    })
    display_btn.addEventListener('click', function() {
        const updateIntroductionRequest = serializeObject($('#up'))
        api.PUT(INTRODUCTION_URI, updateIntroductionRequest)
            .then(res=>res.json())
            .then(introduction => {
                for(let key in item_map) {
                    if (introduction[key] == null || introduction[key] == "") {
                        introduction[key] = item_map[key] + '를 입력해주세요.'
                    }
                }
                document.getElementById("education-card").innerHTML = checkNull(introduction.education)
                document.getElementById("current-city-card").innerHTML = checkNull(introduction.currentCity)
                document.getElementById("hometown-card").innerHTML = checkNull(introduction.hometown)
                document.getElementById("company-card").innerHTML = checkNull(introduction.company)
                display_card.style.display="block"
                update_card.style.display="none"
                update_btn.style.display="block"
                display_btn.style.display="none"
            })
    })
}

feedInitLoad()

const checkNull = (string) => {
    if (!string) {
        return ""
    }
    return string
}