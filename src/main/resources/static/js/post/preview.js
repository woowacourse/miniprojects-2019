const previewTemplate = (fileSrcs) => {
    let previewTemplates = []
    fileSrcs.forEach(function (src) {
        previewTemplates.push(imageTemplate(src))
    })
    return previewTemplates.join('')
}

const imageTemplate = (src) => `
<img src="${src}" width="20%"">
`