class Follow {
    constructor(loginId){
        this.loginId = loginId;
        this.request  = new Request('/api/follow');

    }

    addFollow =  (targetId)=>{
        this.request.post('/'+targetId)
    }
    deleteFollow =  (targetId)=>{
        this.request.delete('/'+targetId)
    }
}