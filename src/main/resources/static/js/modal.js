const modal = {
    inactive() {
        const modalContainer = document.querySelector('.modal');
        modalContainer.classList.add('pace-inactive');
    },
    active() {
        const modalContainer = document.querySelector('.modal');
        modalContainer.classList.remove('pace-inactive')
    }
}

document.querySelector('.modal').addEventListener('click', function () {
    modal.inactive();
});
