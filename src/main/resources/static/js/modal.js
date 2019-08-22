const modalTemplate =
    `<div class="modal inactive modal-animation">
        <div class="modal-dialog-inner">
            <div class="modal-dialog-custom common-flex">
                <button class="create-modify-btn" tabindex="0">수정하기</button>
                <button class="contents-remove-btn delete-btn font-cap" type="button" tabindex="0">삭제하기</button>
            </div>
        </div>
    </div>`

class Modal {
    constructor() {
    }

    init = () => {
        document.body.insertAdjacentHTML('beforeend', modalTemplate);
        document.querySelector('.modal').addEventListener('click', () => {
            this.inactive();
        });
    }

    inactive = () => {
        const modalContainer = document.querySelector('.modal');
        modalContainer.classList.add('inactive');
    }

    active = () => {
        const modalContainer = document.querySelector('.modal');
        modalContainer.classList.remove('inactive')
    }
}
