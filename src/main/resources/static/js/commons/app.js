const serializeObject = form => {
  let result = {}
  let extend = function(i, element) {
    let node = result[element.name]
    if ("undefined" !== typeof node && node !== null) {
      if ($.isArray(node)) {
        node.push(element.value)
      } else {
        result[element.name] = [node, element.value]
      }
    } else {
      result[element.name] = element.value
    }
  }
  $.each(form.serializeArray(), extend)
  return result
}