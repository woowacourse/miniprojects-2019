
const detail = (function(){
    const createContents = ()=>
        axios.get('/articles/'+articleId + "/api")
        .then(response=>{
            console.log(response);
            document.getElementById("pic").src = response.data.imageUrl;
            document.getElementById("contents-para").innerText = response.data.contents;
        })
        return{
            createContents:createContents
        };
}());

window.onload= function(){
    detail.createContents();
}