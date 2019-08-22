const FILE_LOAD_APP = (() => {

    const FileLoadService = function () {

        const b64StringToBlob = b64Data => {
            const byteCharacters = atob(b64Data);
            const byteArrays = [];
            const sliceSize = 512;

            for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
                const slice = byteCharacters.slice(offset, offset + sliceSize);
                const byteNumbers = new Array(slice.length);
                for (let i = 0; i < slice.length; i++) {
                    byteNumbers[i] = slice.charCodeAt(i);
                }
                const byteArray = new Uint8Array(byteNumbers);
                byteArrays.push(byteArray);
            }
            return new Blob(byteArrays, {type: ''});
        };

        function setSrcAttribute(url, fileName, articleId) {
            let words = fileName.split('.');
            let extension = words[words.length - 1];

            let imageExtensions = ['jpg', 'png', 'jpeg'];
            let videoExtensions = ['avi', 'mp4'];

            if (imageExtensions.includes(extension)) {
                let articleImage = document.getElementById('article-image-' + articleId);
                articleImage.style.display = "block";
                articleImage.src = url;
            }
            if (videoExtensions.includes(extension)) {
                let articleVideo = document.getElementById('article-video-' + articleId);
                articleVideo.style.display = "block";
                articleVideo.src = url;
            }
        }

        return {
            b64StringToBlob: b64StringToBlob,
            setSrcAttribute: setSrcAttribute
        }
    };

    return {
        FileLoadService: FileLoadService
    }
})();
