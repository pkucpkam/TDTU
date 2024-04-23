function validateData(firstName,lastName, email){ 
    var errorMessage = ''
    if (firstName == null || firstName == '') {
        errorMessage = "Enter the first name, please !"
    }
    else if (lastName == null || lastName == '') {
        errorMessage = "Enter the last name, please !"
    }
    else 
    if(!email.includes('@')){
        errorMessage='Your email is not correct'
    }
    else if((email.match(/@/g) || []).length!==1){ 
        errorMessage='Your email is not correct'
    }

    var message = document.getElementsByClassName('message')[0]
    message.innerText = errorMessage;

    if (errorMessage == ''){
        message.classList.add('d-none')
        return true
    }

    message.classList.remove('d-none')
    return false 

}

function add() {
    var txtfirstName = document.getElementById('firstname');
    var txtlastName = document.getElementById('lastname');
    var txtemail = document.getElementById('email')

    var firstName = txtfirstName.value;
    var lastName = txtlastName.value;
    var email = txtemail.value;

    if (validateData(firstName,lastName,email)) {
        var tab = document.getElementsByTagName('table')[0]
        var newRow = tab.insertRow()
        var newCell1 = newRow.insertCell()
        newCell1.innerText = (firstName)
        var newCell2 = newRow.insertCell()
        newCell2.innerText = (lastName)
        var newCell3 = newRow.insertCell()
        newCell3.innerText = (email)
        var newCell4 = newRow.insertCell()
        newCell4.innerHTML = '<button onclick="Del(this)" class="btn btn-danger btn-sm">Delete</button>'
    }
}

function Del(aButton) {
    let td = aButton.parentElement;
    let tr = td.parentElement;
    tr.remove()
}