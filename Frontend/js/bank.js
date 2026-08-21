

let editingAccountId = null;

window.onload = loadBankAccounts;

// =======================
// Load All Bank Accounts
// =======================

async function loadBankAccounts() {

    try {

        const userId = localStorage.getItem("userId");

        const response =
            await fetch(BASE_URL + "/accounts/user/" + userId);

        const result =
            await response.json();

        const table =
            document.getElementById("bankAccountTable");

        table.innerHTML = "";

        if (result.success) {

            result.data.forEach(account => {

                table.innerHTML += `

                <tr>

                    <td>${account.accountId}</td>

                    <td>${account.bankName}</td>

                    <td>${account.accountNumber}</td>

                    <td>${account.accountType}</td>

                    <td>₹ ${account.balance}</td>

                    <td>

                        <button
                            class="btn btn-warning btn-sm"
                            onclick="editBankAccount(${account.accountId})">

                            Edit

                        </button>

                        <button
                            class="btn btn-danger btn-sm ms-2"
                            onclick="deleteBankAccount(${account.accountId})">

                            Delete

                        </button>

                    </td>

                </tr>

                `;

            });

        }

    } catch (error) {

        console.error(error);

    }

}

// =======================
// Add / Update Account
// =======================

async function saveBankAccount() {

    try {

        const userId =
            localStorage.getItem("userId");

        const account = {

            userId: parseInt(userId),

            bankName:
                document.getElementById("bankName").value,

            accountNumber:
                document.getElementById("accountNumber").value,

            accountType:
                document.getElementById("accountType").value,

            balance:
                parseFloat(document.getElementById("balance").value)

        };

        let url = BASE_URL + "/accounts";

        let method = "POST";

        if (editingAccountId != null) {

            url += "/" + editingAccountId;

            method = "PUT";

        }

        const response =
            await fetch(url, {

                method: method,

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(account)

            });

        const result =
            await response.json();

        if (result.success) {

            alert(result.message);

            clearForm();

            loadBankAccounts();

        } else {

            alert(result.message);

        }

    } catch (error) {

        console.error(error);

    }

}

// =======================
// Edit Account
// =======================

async function editBankAccount(accountId) {

    try {

       

        const response =
            await fetch(BASE_URL + "/accounts/" + accountId);

        const result =
            await response.json();

        if (result.success) {

            const account = result.data;

            editingAccountId = account.accountId;

            document.getElementById("bankName").value =
                account.bankName;

            document.getElementById("accountNumber").value =
                account.accountNumber;

            document.getElementById("accountType").value =
                account.accountType;

            document.getElementById("balance").value =
                account.balance;

            document.getElementById("saveButton").innerText =
                "Update Account";

        }

    } catch (error) {

        console.error(error);

    }

}

// =======================
// Delete Account
// =======================

async function deleteBankAccount(accountId) {

    if (!confirm("Are you sure you want to delete this account?")) {

        return;

    }

    try {

        const response =
            await fetch(
                BASE_URL + "/accounts/" + accountId,
                {
                    method: "DELETE"
                });

        const result =
            await response.json();

        alert(result.message);

        loadBankAccounts();

    } catch (error) {

        console.error(error);

    }

}

// =======================
// Clear Form
// =======================

function clearForm() {

    editingAccountId = null;

    document.getElementById("bankName").value = "";

    document.getElementById("accountNumber").value = "";

    document.getElementById("accountType").value = "Savings";

    document.getElementById("balance").value = "";

    document.getElementById("saveButton").innerText =
        "Add Account";

}

function logout() {

    // Remove user information stored in the browser
    localStorage.removeItem("userId");

    // If you store other items later, remove them as well
    // localStorage.removeItem("userName");
    // localStorage.removeItem("email");

    // Redirect to the login page
    window.location.href = "login.html";
}