const getFileTemplate = (fileType, src) => {
    if (fileType !== null && fileType.startsWith("image")) {
        return imageTemplate(src)
    }

    if (fileType !== null && fileType.startsWith("video")) {
        return videoTemplate(src, fileType)
    }

    throw new Error('허용되지 않는 파일 형식입니다')
}

const imageTemplate = (src) => `
<img src="${src}">
`

const videoTemplate = (src, fileType) => `
<video src="${src}" type="${fileType}" controls> </video>
`