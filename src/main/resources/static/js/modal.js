const getModalTemplate = (buttons) => {
   return `<div class="modal inactive modal-animation">
        <div class="modal-dialog-inner">
            <div class="modal-dialog-custom common-flex">
               ${buttons}
            </div>
        </div>
    </div>`
};

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
