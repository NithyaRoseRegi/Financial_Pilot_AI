


// ================================
// Page Load
// ================================

window.onload = function () {

    setCurrentMonthAndYear();

    loadFinancialReport();

};


// ================================
// Set Current Month / Year
// ================================

function setCurrentMonthAndYear() {

    const today =
        new Date();


    const currentMonth =
        today.getMonth() + 1;


    const currentYear =
        today.getFullYear();


    document.getElementById("month").value =
        currentMonth;


    document.getElementById("year").value =
        currentYear;

}


// ================================
// Load Financial Report
// ================================

async function loadFinancialReport() {

    try {

        const userId =
            localStorage.getItem("userId");


        if (!userId) {

            alert(
                "User session not found. Please login again."
            );

            window.location.href =
                "login.html";

            return;

        }


        const month =
            document.getElementById("month").value;


        const year =
            document.getElementById("year").value;


        if (!month || !year) {

            alert(
                "Please select month and year."
            );

            return;

        }


        const url =
            BASE_URL +
            "/reports/monthly/" +
            userId +
            "/" +
            month +
            "/" +
            year;


        console.log(
            "Request URL:",
            url
        );


        const response =
            await fetch(url);


        const responseText =
            await response.text();

        let result;

        try {
            result = JSON.parse(responseText);
        } catch (parseError) {
            throw new Error(
                "Report endpoint returned an invalid response (HTTP " +
                response.status + ")."
            );
        }


        console.log(
            "Report response:",
            result
        );


        if (response.ok && result.success) {

            displayFinancialReport(
                result.data
            );

        }

        else {

            alert(
                result.message ||
                "Unable to load financial report."
            );

        }

    }

    catch (error) {

        console.error(
            "Report error:",
            error
        );


        alert(
            error.message ||
            "Unable to load financial report."
        );

    }

}


// ================================
// Display Report
// ================================

function displayFinancialReport(
    data
) {


    // ============================
    // Summary
    // ============================

    document.getElementById(
        "totalBankBalance"
    ).innerText =
        "₹ " +
        formatNumber(
            data.totalBankBalance
        );


    document.getElementById(
        "totalExpenses"
    ).innerText =
        "₹ " +
        formatNumber(
            data.totalExpenses
        );


    document.getElementById(
        "budgetAmount"
    ).innerText =
        "₹ " +
        formatNumber(
            data.budgetAmount
        );


    document.getElementById(
        "remainingBudget"
    ).innerText =
        "₹ " +
        formatNumber(
            data.remainingBudget
        );


    // ============================
    // Highest Expense
    // ============================

    document.getElementById(
        "highestExpense"
    ).innerText =
        "₹ " +
        formatNumber(
            data.highestExpense
        );


    document.getElementById(
        "highestExpenseCategory"
    ).innerText =
        data.highestExpenseCategory ||
        "-";


    // ============================
    // Expense Count
    // ============================

    document.getElementById(
        "expenseCount"
    ).innerText =
        data.expenseCount;


    // ============================
    // Budget Progress
    // ============================

    updateBudgetProgress(
        data.budgetAmount,
        data.totalExpenses
    );

}


// ================================
// Budget Progress
// ================================

function updateBudgetProgress(
    budget,
    expenses
) {

    const progress =
        document.getElementById(
            "budgetProgress"
        );


    const status =
        document.getElementById(
            "budgetStatus"
        );


    // No budget

    if (!budget || budget <= 0) {

        progress.style.width =
            "0%";

        progress.innerText =
            "No Budget";


        status.innerText =
            "No budget has been set for this month.";

        return;

    }


    let percentage =
        (expenses / budget) * 100;


    // Limit visual bar to 100%

    const displayPercentage =
        Math.min(
            percentage,
            100
        );


    progress.style.width =
        displayPercentage + "%";


    progress.innerText =
        Math.round(
            percentage
        ) + "%";


    // ============================
    // Status
    // ============================

    if (expenses > budget) {

        status.innerText =
            "⚠️ You have exceeded your budget by ₹ " +
            formatNumber(
                expenses - budget
            );

    }

    else {

        status.innerText =
            "You have ₹ " +
            formatNumber(
                budget - expenses
            ) +
            " remaining from your budget.";

    }

}


// ================================
// Number Formatting
// ================================

function formatNumber(
    number
) {

    if (
        number === null ||
        number === undefined ||
        isNaN(number)
    ) {

        return "0";

    }


    return Number(number)
        .toLocaleString("en-IN", {

            maximumFractionDigits: 2

        });

}


// ================================
// Logout
// ================================

function logout() {

    localStorage.clear();

    window.location.href =
        "login.html";

}