

let editingExpenseId = null;

window.onload = initExpensePage;

async function initExpensePage() {
    await Promise.all([
        loadExpenseAccounts(),
        loadExpenses()
    ]);
}

async function loadExpenseAccounts() {
    try {
        const userId = localStorage.getItem("userId");
        const response = await fetch(BASE_URL + "/accounts/user/" + userId);
        const result = await response.json();
        const accountSelect = document.getElementById("accountId");
        accountSelect.innerHTML = "<option value=''>Select Account</option>";

        if (result.success) {
            result.data.forEach(account => {
                accountSelect.innerHTML += `\n                    <option value="${account.accountId}">${account.bankName} (${account.accountNumber})</option>`;
            });
        } else {
            console.warn("Unable to load accounts:", result.message);
        }
    } catch (error) {
        console.error(error);
    }
}

// =======================
// Load Expenses
// =======================

async function loadExpenses() {

    try {

        const userId =
            localStorage.getItem("userId");

        const response =
            await fetch(BASE_URL + "/expenses/user/" + userId);

        const result =
            await response.json();

        const table =
            document.getElementById("expenseTable");

        table.innerHTML = "";

        if (result.success) {

            result.data.forEach(expense => {

                table.innerHTML += `

                <tr>

                    <td>${expense.expenseId}</td>

                    <td>${expense.title}</td>

                    <td>${expense.category}</td>

                    <td>₹ ${expense.amount}</td>

                    <td>${expense.date}</td>

                    <td>${expense.note}</td>

                    <td>

                        <button
                            class="btn btn-warning btn-sm"
                            onclick="editExpense(${expense.expenseId})">

                            Edit

                        </button>

                        <button
                            class="btn btn-danger btn-sm ms-2"
                            onclick="deleteExpense(${expense.expenseId})">

                            Delete

                        </button>

                    </td>

                </tr>

                `;

            });

        } else {

            alert(result.message);

        }

    } catch (error) {

        console.error(error);

    }

}

// =======================
// Add / Update Expense
// =======================

async function saveExpense() {

    try {

        const userId =
            localStorage.getItem("userId");

        const accountId = parseInt(
            document.getElementById("accountId").value);

        if (!accountId) {
            alert("Please select an account.");
            return;
        }

        const expense = {

            userId: parseInt(userId),

            accountId: accountId,

            title:
                document.getElementById("expenseName").value.trim(),

            category:
                document.getElementById("category").value,

            amount:
                parseFloat(document.getElementById("amount").value),

            date:
                document.getElementById("expenseDate").value,

            note:
                document.getElementById("description").value.trim()

        };

        if (editingExpenseId != null) {
            expense.expenseId = editingExpenseId;
        }

        if (!expense.title) {
            alert("Expense name cannot be empty.");
            return;
        }

        if (!expense.date) {
            alert("Please select an expense date.");
            return;
        }

        if (isNaN(expense.amount) || expense.amount <= 0) {
            alert("Amount must be greater than zero.");
            return;
        }

        let url =
            BASE_URL + "/expenses";

        let method = "POST";

        if (editingExpenseId != null) {

            url += "/" + editingExpenseId;

            method = "PUT";

        }

        const response =
            await fetch(url, {

                method: method,

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(expense)

            });

        const result =
            await response.json();

        if (result.success) {

            alert(result.message);

            clearForm();

            loadExpenses();

        } else {

            alert(result.message);

        }

    } catch (error) {

        console.error(error);

    }

}

// =======================
// Edit Expense
// =======================

async function editExpense(expenseId) {

    try {

        const response =
            await fetch(BASE_URL + "/expenses/" + expenseId);

        const result =
            await response.json();

        if (result.success && result.data) {

            const expense = result.data;

            editingExpenseId =
                expense.expenseId;

            document.getElementById("accountId").value =
                expense.accountId || "";

            document.getElementById("expenseName").value =
                expense.title || "";

            document.getElementById("category").value =
                expense.category || "Food";

            document.getElementById("amount").value =
                expense.amount || "";

            document.getElementById("expenseDate").value =
                expense.date || "";

            document.getElementById("description").value =
                expense.note || "";

            document.getElementById("saveButton").innerText =
                "Update Expense";

        } else {
            alert(result.message || "Expense not found.");
        }

    } catch (error) {

        console.error(error);

    }

}

// =======================
// Delete Expense
// =======================

async function deleteExpense(expenseId) {

    if (!confirm("Are you sure you want to delete this expense?")) {

        return;

    }

    try {

        const userId = localStorage.getItem("userId");

        const response =
            await fetch(
                BASE_URL + "/expenses/" + expenseId + "?userId=" + userId,
                {
                    method: "DELETE"
                });

        const result =
            await response.json();

        alert(result.message);

        loadExpenses();

    } catch (error) {

        console.error(error);

    }

}

// =======================
// Clear Form
// =======================

function clearForm() {

    editingExpenseId = null;

    document.getElementById("accountId").value = "";

    document.getElementById("expenseName").value = "";

    document.getElementById("category").value = "Food";

    document.getElementById("amount").value = "";

    document.getElementById("expenseDate").value = "";

    document.getElementById("description").value = "";

    document.getElementById("saveButton").innerText =
        "Add Expense";

}

// =======================
// Logout
// =======================

function logout() {

    localStorage.clear();

    window.location.href = "login.html";

}