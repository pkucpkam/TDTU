function validateData(){
    var txtEmail= document.getElementById('email');
    var txtPwd= document.getElementById('pwd');

    var email=txtEmail.value;
    var pwd=txtPwd.value;
    var msg='';

    if(email == null || email ==''){
        msg= 'Please enter your email';

    }else if(!email.includes('@')){
        msg='Your email is not correct'
    }
    else if((email.match(/@/g) || []).length!==1){ 
        msg='Your email is not correct'
    }
    else if(pwd==null || pwd==''){
        msg='Please enter your password'
    }
    else if(pwd.length < 6){
        msg='Your password must contain at least 6 characters'
    }

    var lblErrorMessage=document.getElementsByClassName('errorMessage')[0];
    lblErrorMessage.innerText = msg;

    if(msg==''){
        lblErrorMessage.classList.add('d-none')
        return true;
    }

    lblErrorMessage.classList.remove('d-none')
    return false;

}