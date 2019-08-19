const modal = {
    inactive() {
        const modalContainer = document.querySelector('.modal');
        modalContainer.classList.add('inactive');
    },
    active() {
        const modalContainer = document.querySelector('.modal');
        modalContainer.classList.remove('inactive')
    }
}

document.querySelector('.modal').addEventListener('click', function () {
    modal.inactive();
});
