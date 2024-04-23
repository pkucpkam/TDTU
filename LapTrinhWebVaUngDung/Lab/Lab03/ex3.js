
document.addEventListener("DOMContentLoaded", function() {
    const messageInput = document.getElementById("message");
    const colorSelect = document.getElementById("color");
    const boldCheckbox = document.getElementById("bold");
    const italicCheckbox = document.getElementById("italic");
    const underlineCheckbox = document.getElementById("underline");
    const restoreButton = document.querySelector("button.btn-success");
    const alertBox = document.querySelector(".alert.alert-success");

    function updateAlertBox() {
        let newText = messageInput.value;
        let newColor = colorSelect.value.toLowerCase();
        let textStyle = '';
        if (boldCheckbox.checked) {
            textStyle += 'font-weight: bold;';
        } else {
            textStyle += 'font-weight: normal;';
        }
        if (italicCheckbox.checked) textStyle += 'font-style: italic;';
        else {
            textStyle += 'font-style: normal;'
        }
        if (underlineCheckbox.checked) textStyle += 'text-decoration: underline;';
        else {
            textStyle += 'text-decoration: none;'
        }
        alertBox.style.color = newColor;
        alertBox.style.cssText += textStyle;
        alertBox.innerText = newText;
    }

    messageInput.addEventListener("input", updateAlertBox);
    colorSelect.addEventListener("change", updateAlertBox);
    boldCheckbox.addEventListener("change", updateAlertBox);
    italicCheckbox.addEventListener("change", updateAlertBox);
    underlineCheckbox.addEventListener("change", updateAlertBox);

    function restoreDefaults() {
        messageInput.value = '';
        colorSelect.value = 'Black';
        boldCheckbox.checked = false;
        italicCheckbox.checked = false;
        underlineCheckbox.checked = false;
        alertBox.style.color = "black";
        alertBox.style.cssText -= 'font-weight: bold;';
        alertBox.style.cssText -= 'font-style: italic;';
        alertBox.style.cssText -= 'text-decoration: underline;';
        alertBox.innerText = "";
        updateAlertBox();
    }

    restoreButton.addEventListener("click", restoreDefaults);

    updateAlertBox();
});
