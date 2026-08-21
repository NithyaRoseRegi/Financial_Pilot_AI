
// ======================================================
// GLOBAL VARIABLE
// ======================================================

let editingBudget = false;


// ======================================================
// PAGE LOAD
// ======================================================

window.onload = function () {

    setCurrentMonthAndYear();

    loadBudgets();

    loadRemainingBudget();

};


// ======================================================
// SET CURRENT MONTH AND YEAR
// ======================================================

function setCurrentMonthAndYear() {

    const today = new Date();

    const currentMonth =
        today.getMonth() + 1;

    const currentYear =
        today.getFullYear();


    document.getElementById("month").value =
        currentMonth;

    document.getElementById("year").value = currentYear;
}


// ======================================================
// LOAD ALL BUDGETS
// ======================================================

async function loadBudgets() {

    try {

        const userId = localStorage.getItem("userId");

        if (!userId) {
            alert("User not logged in.");
            window.location.href = "login.html";
            return;
        }

        const table = document.getElementById("budgetTable");
        const response = await fetch(
            BASE_URL + "/budget/user/" + userId
        );
        const result = await response.json();

        table.innerHTML = "";

        if (response.ok && result.success && Array.isArray(result.data)) {
            result.data.forEach(budget => {
                table.innerHTML += `
                    <tr>
                        <td>${getMonthName(budget.month)}</td>
                        <td>${budget.year}</td>
                        <td>₹ ${budget.amount}</td>
                        <td>
                            <button class="btn btn-warning btn-sm"
                                onclick="editBudget(${budget.month}, ${budget.year}, ${budget.amount})">
                                Edit
                            </button>
                            <button class="btn btn-danger btn-sm ms-2"
                                onclick="deleteBudget(${budget.month}, ${budget.year})">
                                Delete
                            </button>
                        </td>
                    </tr>`;
            });
        }

        if (!result.success || !result.data || result.data.length === 0) {
            table.innerHTML = `
                <tr>
                    <td colspan="4" class="text-center">No budgets found.</td>
                </tr>`;
        }

    } catch (error) {
        console.error("Error loading budgets:", error);
    }
}
// ======================================================
// SAVE BUDGET
// ======================================================

async function saveBudget() {

    try {

        const userId =
            localStorage.getItem("userId");


        const month =
            parseInt(
                document.getElementById(
                    "month"
                ).value
            );


        const year =
            parseInt(
                document.getElementById(
                    "year"
                ).value
            );


        const amount =
            parseFloat(
                document.getElementById(
                    "amount"
                ).value
            );


        // Validation

        if (!month ||
            !year ||
            !amount ||
            amount <= 0) {

            alert(
                "Please enter valid budget details."
            );

            return;

        }


        const budget = {

            userId: parseInt(userId),

            month: month,

            year: year,

            amount: amount

        };


        let url =
            BASE_URL + "/budget";


        let method =
            "POST";


        // If editing, use PUT

        if (editingBudget) {

            method =
                "PUT";

        }


        const response =
            await fetch(
                url,
                {

                    method: method,

                    headers: {

                        "Content-Type":
                            "application/json"

                    },

                    body:
                        JSON.stringify(
                            budget
                        )

                }
            );


        const result =
            await response.json();


        if (result.success) {

            alert(
                result.message
            );


            clearForm();


            await loadBudgets();


            await loadRemainingBudget();

        }

        else {

            alert(
                result.message
            );

        }

    }

    catch (error) {

        console.error(
            "Error saving budget:",
            error
        );

    }

}


// ======================================================
// EDIT BUDGET
// ======================================================

function editBudget(
    month,
    year,
    amount
) {


    // Put existing values
    // into the form

    document.getElementById(
        "month"
    ).value = month;


    document.getElementById(
        "year"
    ).value = year;


    document.getElementById(
        "amount"
    ).value = amount;


    // Change mode

    editingBudget = true;


    // Change button text

    document.getElementById(
        "saveButton"
    ).innerText =
        "Update Budget";


    // Show cancel button

    document.getElementById(
        "cancelButton"
    ).style.display =
        "inline-block";


    // Load selected month summary

    loadRemainingBudget();

}


// ======================================================
// DELETE BUDGET
// ======================================================

async function deleteBudget(
    month,
    year
) {


    const confirmation =
        confirm(
            "Are you sure you want to delete this budget?"
        );


    if (!confirmation) {

        return;

    }


    try {

        const userId =
            localStorage.getItem(
                "userId"
            );


        const response =
            await fetch(

                BASE_URL +
                "/budget/" +
                userId +
                "/" +
                month +
                "/" +
                year,

                {

                    method:
                        "DELETE"

                }

            );


        const result =
            await response.json();


        if (result.success) {

            alert(
                result.message
            );


            await loadBudgets();


            await loadRemainingBudget();

        }

        else {

            alert(
                result.message
            );

        }

    }

    catch (error) {

        console.error(
            "Error deleting budget:",
            error
        );

    }

}


// ======================================================
// LOAD REMAINING BUDGET
// ======================================================

async function loadRemainingBudget() {

    try {

        const userId =
            localStorage.getItem(
                "userId"
            );


        const month =
            document.getElementById(
                "month"
            ).value;


        const year =
            document.getElementById(
                "year"
            ).value;


        if (!month || !year) {

            return;

        }


        const response =
            await fetch(

                BASE_URL +
                "/budget/remaining/" +
                userId +
                "/" +
                month +
                "/" +
                year

            );


        const result =
            await response.json();


        if (result.success) {


            /*
             * These property names must match
             * the JSON returned by your
             * getRemainingBudget() method.
             */

            const data =
                result.data;


            document.getElementById(
                "budgetValue"
            ).innerText =
                "₹ " +
                (data.budgetAmount ?? 0);


            document.getElementById(
                "expenseValue"
            ).innerText =
                "₹ " +
                (data.totalExpenses ?? 0);


            document.getElementById(
                "remainingValue"
            ).innerText =
                "₹ " +
                (data.remainingBudget ?? 0);

        }

        else {

            document.getElementById(
                "budgetValue"
            ).innerText =
                "₹ 0";


            document.getElementById(
                "expenseValue"
            ).innerText =
                "₹ 0";


            document.getElementById(
                "remainingValue"
            ).innerText =
                "₹ 0";

        }

    }

    catch (error) {

        console.error(
            "Error loading remaining budget:",
            error
        );

    }

}


// ======================================================
// CLEAR FORM
// ======================================================

function clearForm() {

    editingBudget =
        false;


    const today =
        new Date();


    document.getElementById(
        "month"
    ).value =
        today.getMonth() + 1;


    document.getElementById(
        "year"
    ).value =
        today.getFullYear();


    document.getElementById(
        "amount"
    ).value =
        "";


    document.getElementById(
        "saveButton"
    ).innerText =
        "Add Budget";


    document.getElementById(
        "cancelButton"
    ).style.display =
        "none";


    loadRemainingBudget();

}


// ======================================================
// GET MONTH NAME
// ======================================================

function getMonthName(month) {

    const months = [

        "January",

        "February",

        "March",

        "April",

        "May",

        "June",

        "July",

        "August",

        "September",

        "October",

        "November",

        "December"

    ];


    return months[month - 1];

}


// ======================================================
// WHEN MONTH OR YEAR CHANGES
// ======================================================

document.getElementById(
    "month"
).addEventListener(
    "change",
    loadRemainingBudget
);


document.getElementById(
    "year"
).addEventListener(
    "change",
    loadRemainingBudget
);


// ======================================================
// LOGOUT
// ======================================================

function logout() {

    localStorage.clear();

    window.location.href =
        "login.html";

}