

"use strict";
const postForm = document.querySelector("#post-form");
const text = document.querySelector("[name='text']");
const csrfToken = document.querySelector("input[name='_csrf']").value;
postForm.addEventListener("submit",async(e)=>{
	 e.preventDefault();
	 const formData = new FormData(postForm);
	 const errorEl = document.querySelectorAll(".error-el");
	
	 const serialized = new URLSearchParams(formData).toString();
	
	 if(errorEl.length > 0) {
		errorEl.forEach(val=>val.remove());
	 }
	
	
	 fetch("/main/insert/rest",{
		 method: "POST",
		  headers: {
	      "Content-Type": "application/x-www-form-urlencoded"
	    },body: serialized
	 }) 
	 .then(response => response.json())
	 .then(data => {
		 
	    if (data.redirectUrl) {
	      window.location.href = data.redirectUrl;
	    } else {
			
			for(const[key,value] of Object.entries(data.errors)){
				postForm.insertAdjacentHTML("afterbegin",
	    	`<div class="error-el"><p style = "color:red;">${value}</p></div>`)
			}
	    }
	  })
	  .catch(error => console.error('Error:', error));
})


