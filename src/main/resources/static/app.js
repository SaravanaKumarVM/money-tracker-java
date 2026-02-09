// Tab switching logic
function openTab(evt, tabName) {
    var i, tabcontent, tabbuttons;

    tabcontent = document.getElementsByClassName("tab-content");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    tabbuttons = document.getElementsByClassName("tab-btn");
    for (i = 0; i < tabbuttons.length; i++) {
        tabbuttons[i].classList.remove("active");
    }

    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.classList.add("active");
}

// Show expense form based on type
function showExpenseForm() {
    const type = document.getElementById("expenseType").value;
    document.getElementById("expense-form").style.display = type === "expense" ? "block" : "none";
    document.getElementById("expense-card-form").style.display = type === "expense_card" ? "block" : "none";
    document.getElementById("expense-others-form").style.display = type === "expense_others" ? "block" : "none";
}

// ===== INCOME FORM =====
if (document.getElementById("incomeForm")) {
    document.getElementById("incomeForm").onsubmit = e => {
        e.preventDefault();

        fetch("/api/income", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                name: document.getElementById("incomeName").value,
                date: document.getElementById("incomeDate").value,
                amount: document.getElementById("incomeAmount").value,
                notes: document.getElementById("incomeNotes").value
            })
        }).then(res => {
            if (res.ok) {
                alert("Income Saved!");
                document.getElementById("incomeForm").reset();
            }
        }).catch(err => console.error("Error:", err));
    };
}

// ===== EXPENSE MAIN (CASH) FORM =====
if (document.getElementById("expenseMainForm")) {
    document.getElementById("expenseMainForm").onsubmit = e => {
        e.preventDefault();

        fetch("/api/expense-main", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                date: document.getElementById("expenseMainDate").value,
                category: document.getElementById("expenseMainCategory").value,
                amount: document.getElementById("expenseMainAmount").value,
                notes: document.getElementById("expenseMainNotes").value
            })
        }).then(res => {
            if (res.ok) {
                alert("Expense Saved!");
                document.getElementById("expenseMainForm").reset();
            }
        }).catch(err => console.error("Error:", err));
    };
}

// ===== EXPENSE CARD FORM =====
if (document.getElementById("expenseCardForm")) {
    document.getElementById("expenseCardForm").onsubmit = e => {
        e.preventDefault();

        fetch("/api/expense-card", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                date: document.getElementById("expenseCardDate").value,
                bank: document.getElementById("expenseCardBank").value,
                category: document.getElementById("expenseCardCategory").value,
                amount: document.getElementById("expenseCardAmount").value,
                notes: document.getElementById("expenseCardNotes").value
            })
        }).then(res => {
            if (res.ok) {
                alert("Card Expense Saved!");
                document.getElementById("expenseCardForm").reset();
            }
        }).catch(err => console.error("Error:", err));
    };
}

// ===== EXPENSE OTHERS FORM =====
if (document.getElementById("expenseOthersForm")) {
    document.getElementById("expenseOthersForm").onsubmit = e => {
        e.preventDefault();

        fetch("/api/expense-others", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                date: document.getElementById("expenseOthersDate").value,
                category: document.getElementById("expenseOthersCategory").value,
                amount: document.getElementById("expenseOthersAmount").value,
                notes: document.getElementById("expenseOthersNotes").value
            })
        }).then(res => {
            if (res.ok) {
                alert("Other Expense Saved!");
                document.getElementById("expenseOthersForm").reset();
            }
        }).catch(err => console.error("Error:", err));
    };
}

// Dashboard category split
if (document.getElementById("categoryList")) {
    fetch("/dashboard/category-summary")
        .then(r => r.json())
        .then(data => {
            const ul = document.getElementById("categoryList");
            for (let key in data) {
                const li = document.createElement("li");
                li.innerText = key + " : ₹ " + data[key];
                ul.appendChild(li);
            }
        });
}

// Dashboard totals
if (document.getElementById("today")) {
    fetch("/dashboard/today")
        .then(r => r.json())
        .then(data => {
            document.getElementById("today").innerText = data || 0;
        });
}

if (document.getElementById("month")) {
    fetch("/dashboard/month")
        .then(r => r.json())
        .then(data => {
            document.getElementById("month").innerText = data || 0;
        });
}

if (document.getElementById("overall")) {
    fetch("/dashboard/overall")
        .then(r => r.json())
        .then(data => {
            document.getElementById("overall").innerText = data || 0;
        });
}