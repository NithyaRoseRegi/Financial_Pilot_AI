


// =================================
// Register User
// =================================

async function registerUser() {

    try {

        // =============================
        // Get values from HTML
        // =============================

        const name =
            document.getElementById("name").value.trim();

        const email =
            document.getElementById("email").value.trim();

        const password =
            document.getElementById("password").value;

        const confirmPassword =
            document.getElementById("confirmPassword").value;


        // =============================
        // Validation
        // =============================

        if (!name) {

            alert("Please enter your name.");

            return;
        }


        if (!email) {

            alert("Please enter your email.");

            return;
        }


        if (!password) {

            alert("Please enter a password.");

            return;
        }


        if (!confirmPassword) {

            alert("Please confirm your password.");

            return;
        }


        if (password !== confirmPassword) {

            alert("Passwords do not match.");

            return;
        }


        // =============================
        // Create user object
        // =============================

        const user = {

            name: name,

            email: email,

            password: password

        };


        console.log("Registering user:", user);


        // =============================
        // Send request to backend
        // =============================

        const response =
            await fetch(
                BASE_URL + "/users/register",
                {

                    method: "POST",

                    headers: {

                        "Content-Type":
                            "application/json"

                    },

                    body:
                        JSON.stringify(user)

                }
            );


        // =============================
        // Convert response to JSON
        // =============================

        const result =
            await response.json();


        console.log(
            "Registration response:",
            result
        );


        // =============================
        // Handle response
        // =============================

        if (result.success) {

            alert(
                result.message
            );


            // Go to login page

            window.location.href =
                "login.html";

        }

        else {

            alert(
                result.message
            );

        }

    }

    catch (error) {

        console.error(
            "Registration error:",
            error
        );


        alert(
            "Unable to register user."
        );

    }

}