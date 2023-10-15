function forgetSubmit(){
    const email = document.getElementById('email').value;

    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    let bool =  emailPattern.test(email);
    if(bool){
        window.alert(`Reset link send to your emailId (${email}). thankyou`)
    }
    // console.log("this is email",document.getElementById('email'))
}