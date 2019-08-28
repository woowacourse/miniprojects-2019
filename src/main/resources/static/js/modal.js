class Modal {

    constructor(buttons, num = 0) {
        this.modalHtml = getModalTemplate(buttons, num);
        this.num = num;
    }

    init = () => {
        document.body.insertAdjacentHTML('beforeend', this.modalHtml);
        if (this.num > 0) {
            document.querySelector(`.modal-num-${this.num}`).addEventListener('click', () => {
                this.inactive();
            });
        } else {
            document.querySelector('.modal').addEventListener('click', () => {
                this.inactive();
            });
        }
    };

    inactive = () => {
        if (this.num > 0) {
            const modalContainer = document.querySelector(`.modal-num-${this.num}`);
            modalContainer.classList.add('inactive');
        } else {
            const modalContainer = document.querySelector('.modal');
            modalContainer.classList.add('inactive');
        }
    };

    active = () => {
        if (this.num > 0) {
            const modalContainer = document.querySelector(`.modal-num-${this.num}`);
            modalContainer.classList.remove('inactive')
        } else {
            const modalContainer = document.querySelector('.modal');
            modalContainer.classList.remove('inactive')
        }
    }
}
