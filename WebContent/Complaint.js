 function complaintSubmit()
 {
    const email=document.getElementById('email').value;
    const name=document.getElementById('name').value;
    const subject=document.getElementById('subject').value;
    const message=document.getElementById('message').value;
   
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
     
    let bool=emailPattern.test(email);
    console.log("email: ",email,"name:",name,subject,message)
    if(bool && name!=="" && subject!=="" && message!=="")
    {
     window.alert("Your Compalint is successfully send wait for 24 hour::");
    }else
    {
    window.alert("Please Write Correct input data::");
    }
 }