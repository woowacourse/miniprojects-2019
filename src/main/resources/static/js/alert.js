class Alert {
    constructor(message){
        this.message = message;
    }
    active=()=>{
        const a = document.body.insertAdjacentHTML('beforeend',getAlertTemplate(this.message)) ;
        setTimeout(()=>{
            document.querySelector(".alert-con").remove();
        },2000)
    }
}