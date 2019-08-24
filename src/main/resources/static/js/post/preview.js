const previewTemplate = (fileInfos) => {
    const previewTemplates = []
    const div = document.createElement('div')

    fileInfos.forEach(function (fileInfo) {
        div.innerHTML = getFileTemplate(fileInfo.type, fileInfo.src)

        const fileHtml = div.firstElementChild
        fileHtml.setAttribute('class', 'preview-file')
        fileHtml.setAttribute('width', '20%')

        previewTemplates.push(fileHtml.outerHTML)
    })

    return previewTemplates.join('')
}