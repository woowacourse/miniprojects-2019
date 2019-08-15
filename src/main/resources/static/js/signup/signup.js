const url = "/api/users"
const header = {
	"Content-Type": "application/json; charset=UTF-8"
}

const signup = async e => {
	const form_data = JSON.stringify($("#reg").serializeObject())

	const res = await fetch(url, {
		method: "POST",
		body: form_data,
		headers: header
	})

	const result = await res
	const result_data = await result.json()

	if(result.status === 201) {
		alert('가입이 완료되었습니다. 로그인 해주세요.')
	} else if(result.status === 400) {
		alert(result_data.errorMessage)
	}

}

$.fn.serializeObject = function() {
  var result = {}
  var extend = function(i, element) {
    var node = result[element.name]
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
  $.each(this.serializeArray(), extend)
  return result
}