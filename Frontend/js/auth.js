document
.getElementById("loginButton")
.addEventListener("click", loginUser);

async function loginUser() {

    const email =
        document.getElementById("emailInput").value.trim();

    const password =
        document.getElementById("passwordInput").value.trim();

    if (email === "" || password === "") {

        alert("Please enter Email and Password.");

        return;
    }

    const user = {

        email: email,

        password: password
    };

    try {

        const response =
            await fetch(BASE_URL + "/users/login", {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(user)

            });

        const result =
            await response.json();

        if (result.success) {

            alert(result.message);

            console.log(result.data);

           

            
            // Save logged-in user information
            localStorage.setItem("userId", result.data.userId);
            localStorage.setItem("userName", result.data.name);
            localStorage.setItem("userEmail", result.data.email);

            window.location.href = "dashboard.html";
         
           
        } else {

            alert(result.message);

        }

    } catch (error) {

        console.error(error);

        alert("Unable to connect to server.");

    }

}