class Modal {
    constructor(buttons, num = 0) {
        this.modalHtml = getModalTemplate(buttons, num);
        this.num = num;
    }

    init = () => {
        document.body.insertAdjacentHTML('beforeend', this.modalHtml);
        document.querySelector(`.modal-num-${this.num}`).addEventListener('click', () => {
            this.inactive();
        });
    };

    inactive = () => {
        const modalContainer = document.querySelector(`.modal-num-${this.num}`);
        modalContainer.classList.add('inactive');
    };

    active = () => {
        const modalContainer = document.querySelector(`.modal-num-${this.num}`);
        modalContainer.classList.remove('inactive')
    }
}
