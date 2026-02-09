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


// Add expense
if (document.getElementById("expenseForm")) {
    document.getElementById("expenseForm").onsubmit = e => {
        e.preventDefault();

        fetch("/expense", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                date: date.value,
                category: category.value,
                amount: amount.value,
                notes: notes.value
            })
        }).then(() => alert("Saved!"));
    };
}


// Add income
if (document.getElementById("incomeForm")) {
    document.getElementById("incomeForm").onsubmit = e => {
        e.preventDefault();

        fetch("/income", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                name: name.value,
                date: date.value,
                amount: amount.value,
                notes: notes.value
            })
        }).then(() => alert("Saved!"));
    };
}


// All expenses table
if (document.getElementById("expenseTable")) {
    fetch("/expense")
        .then(r => r.json())
        .then(data => {
            const tbody = document.querySelector("#expenseTable tbody");

            data.forEach(e => {
                const row = `
                    <tr>
                        <td>${e.date}</td>
                        <td>${e.category}</td>
                        <td>${e.amount}</td>
                        <td><button onclick="deleteExpense(${e.id})">Delete</button></td>
                    </tr>
                `;
                tbody.innerHTML += row;
            });
        });
}

function deleteExpense(id) {
    fetch("/expense/" + id, {method: "DELETE"})
        .then(() => location.reload());
}
