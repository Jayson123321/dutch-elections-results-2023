export default {
    mounted(el, binding) {
        translate(el, binding.instance.$t);
    },
    updated(el, binding) {
        translate(el, binding.instance.$t);
    }
};

function translate(element, translateFn) {
    if (element.nodeType === Node.TEXT_NODE) {
        element.textContent = translateFn(element.textContent.trim());
    } else {
        element.childNodes.forEach(child => translate(child, translateFn));
    }
}