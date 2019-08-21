const IndexApp = (() => {
    const selectTemplate = (data) => {
        return `<option value="${data.value}">${data.name}</option>`;
    };

    const IndexController = function () {
        const indexService = new IndexService();

        const renderBirth = () => {
            const selectMonth = document.querySelector('select[data-object="select-month"]');
            const selectDay = document.querySelector('select[data-object="select-day"]');
            const selectYear = document.querySelector('select[data-object="select-year"]');

            // month
            for (let i = 1; i <= 12; ++i) {
                let object = {name: `${i}월`, value: i};
                selectMonth.insertAdjacentHTML('beforeend', selectTemplate(object));
            }

            // day
            for (let i = 1; i <= 31; ++i) {
                let object = {name: `${i}일`, value: i};
                selectDay.insertAdjacentHTML('beforeend', selectTemplate(object));
            }

            // year
            for (let i = 2019; i >= 1900; --i) {
                let object = {name: `${i}년`, value: i};
                selectYear.insertAdjacentHTML('beforeend', selectTemplate(object));
            }
        };

        const login = () => {
            const loginBtn = document.getElementById('login-btn');
            loginBtn.addEventListener('click', indexService.login);

        };

        const signUp = () => {
            const signUpBtn = document.getElementById('signup-btn');
            signUpBtn.addEventListener('click', indexService.signUp);
        };

        const init = () => {
            renderBirth();
            login();
            signUp();
        };

        return {
            init: init,
        };
    };

    const IndexService = function () {
        const indexApi = new IndexApi();

        const login = (event) => {
            event.preventDefault();
            event.stopPropagation();

            const email = document.getElementById('login-email');
            const password = document.getElementById('login-password');

            const data = {
                userEmail: email.value,
                userPassword: password.value
            };

            indexApi.login(data)
            .then(response => {
                return response.json();
            }).then(json => {
                if (json.hasOwnProperty('errorMessage')) {
                    alert(json.errorMessage);
                } else {
                    location.href='/newsfeed';
                }
            })
        };

        const signUp = (event) => {
            event.preventDefault();
            event.stopPropagation();

            if(AppStorage.check('sign-up-run')) return;
            AppStorage.set('sign-up-run', true);

            const firstName = document.getElementById('signup-first-name');
            const lastName = document.getElementById('signup-last-name');
            const email = document.getElementById('signup-email');
            const password = document.getElementById('signup-password');

            const data = {
                userName: lastName.value + firstName.value,
                userEmail: email.value,
                userPassword: password.value
            };

            indexApi.signUp(data)
                .then(response => {
                    return response.json();
                }).then(json => {
                    if (json.hasOwnProperty('errorMessage')) {
                        alert(json.errorMessage);
                    } else {
                        firstName.value = "";
                        lastName.value = "";
                        email.value = "";
                        password.value = "";
                        alert('가입을 완료했습니다. 로그인 하세요.');
                    }
                    AppStorage.set('sign-up-run', false);
                })
        };

        return {
            login: login,
            signUp: signUp,
        };
    };

    const IndexApi = function () {
        const login = (data) => {
            return Api.post('/api/signin', data);
        };

        const signUp = (data) => {
            return Api.post('/api/signup', data);
        };

        return {
            login: login,
            signUp: signUp,
        }
    };

    const init = () => {
        const indexController = new IndexController();
        indexController.init();
    };

    return {
        init: init,
    };
})();

IndexApp.init();