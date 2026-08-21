window.onload = loadDashboard;

async function loadDashboard() {

    try {

        const userId = localStorage.getItem("userId");

        const response =
            await fetch(BASE_URL + "/summary/" + userId);

        const result =
            await response.json();

        if (result.success) {

            const summary = result.data;

            document.getElementById("totalBalance").innerText =
                "₹ " + summary.totalBankBalance;

            document.getElementById("totalExpenses").innerText =
                "₹ " + summary.totalExpenses;

            document.getElementById("remainingBalance").innerText =
                "₹ " + summary.remainingBalance;

        } else {

            alert(result.message);

        }

    } catch (error) {

        console.error(error);

    }

}
function logout() {

    localStorage.clear();

    window.location.href = "login.html";
}