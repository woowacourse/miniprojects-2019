
const detail = (function(){
    const createImage = ()=>
        axios.get('/api/mediafiles/'+mediaFileId)
        .then(response=>{
            console.log(response);
            document.getElementById("item-preview").src = "data:image/jpeg;base64,"+ Base64.getEncoder().encodeToString(response);
        })
        return{
            createImage:createImage
        };
}());

window.onload= function(){
    detail.createImage();
}