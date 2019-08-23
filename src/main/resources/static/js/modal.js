class Modal {
    constructor(buttons) {
        this.modalHtml = getModalTemplate(buttons)
    }

    init = () => {
        document.body.insertAdjacentHTML('beforeend', this.modalHtml);
        document.querySelector('.modal').addEventListener('click', () => {
            this.inactive();
        });
    };

    inactive = () => {
        const modalContainer = document.querySelector('.modal');
        modalContainer.classList.add('inactive');
    };

    active = () => {
        const modalContainer = document.querySelector('.modal');
        modalContainer.classList.remove('inactive')
    }
}
