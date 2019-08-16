
MYAPP.utilities.array = (function () {

    // 의존 관계
    var uobj  = MYAPP.utilities.object,
        ulang = MYAPP.utilities.lang,

        // 비공개 프로퍼티
        array_string = "[object Array]",
        ops = Object.prototype.toString;

    // 공개 API
    return {
        inArray: function (needle, haystack) {
            // ...
        },
        isArray: function (a) {
            return ops.call(a) === array_string;
        }
    };
}());