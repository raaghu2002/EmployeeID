const API_URL = "/api/employees";

// Fetch and display employees
async function fetchEmployees() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("Failed to fetch employees");

        const employees = await response.json();
        const tableBody = document.getElementById("employeeTable");
        tableBody.innerHTML = "";

        employees.forEach(emp => {
            tableBody.innerHTML += `
                <tr>
                    <td>${emp.id}</td>
                    <td>${emp.firstName} ${emp.lastName}</td>
                    <td>${emp.dateOfBirth}</td>
                    <td>${emp.email}</td>
                    <td>${emp.phoneNumber}</td>
                    <td><img src="${emp.qrCodePath}" width="50"></td>
                    <td>
                        <button onclick="location.href='update.html?id=${emp.id}'">Update</button>
                        <button onclick="deleteEmployee('${emp.id}')">Delete</button>
                    </td>
                </tr>
            `;
        });
    } catch (error) {
        console.error("Error:", error);
        alert("Failed to load employees");
    }
}

// Add Employee
document.getElementById("addEmployeeForm")?.addEventListener("submit", async (e) => {
    e.preventDefault();

    const employee = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        dateOfBirth: document.getElementById("dateOfBirth").value,
        email: document.getElementById("email").value,
        phoneNumber: document.getElementById("phoneNumber").value
    };

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(employee)
        });

        if (!response.ok) throw new Error("Failed to add employee");

        alert("Employee added successfully!");
        location.href = "index.html";
    } catch (error) {
        console.error("Error:", error);
        alert("Failed to add employee");
    }
});

// Update Employee
document.getElementById("updateEmployeeForm")?.addEventListener("submit", async (e) => {
    e.preventDefault();
    const id = document.getElementById("employeeId").value;

    const updatedData = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        dateOfBirth: document.getElementById("dateOfBirth").value,
        email: document.getElementById("email").value,
        phoneNumber: document.getElementById("phoneNumber").value
    };

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedData)
        });

        if (!response.ok) throw new Error("Failed to update employee");

        alert("Employee updated successfully!");
        location.href = "index.html";
    } catch (error) {
        console.error("Error:", error);
        alert("Failed to update employee");
    }
});

// Search for employee by ID
async function searchEmployee() {
    const searchId = document.getElementById("searchId").value;
    const tableBody = document.getElementById("employeeTable");
    const noResults = document.getElementById("noResults");

    if (!searchId) {
        alert("Please enter an employee ID");
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${searchId}`);
        tableBody.innerHTML = "";
        
        if (!response.ok) {
            noResults.classList.remove("hidden");
            return;
        }

        const emp = await response.json();
        noResults.classList.add("hidden");
        
        tableBody.innerHTML = `
            <tr>
                <td>${emp.id}</td>
                <td>${emp.firstName} ${emp.lastName}</td>
                <td>${emp.dateOfBirth}</td>
                <td>${emp.email}</td>
                <td>${emp.phoneNumber}</td>
                <td><img src="${emp.qrCodePath}" width="50" alt="QR Code"></td>
                <td>
                    <button class="btn btn-edit" onclick="location.href='update.html?id=${emp.id}'">Update</button>
                    <button class="btn btn-delete" onclick="deleteEmployee('${emp.id}')">Delete</button>
                </td>
            </tr>
        `;
    } catch (error) {
        console.error("Error:", error);
        alert("Failed to search for employee");
    }
}

// Delete Employee
async function deleteEmployee(id) {
    if (!confirm("Are you sure you want to delete this employee?")) return;

    try {
        const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });

        if (!response.ok) throw new Error("Failed to delete employee");

        document.getElementById("employeeTable").innerHTML = "";
        document.getElementById("searchId").value = "";
        document.getElementById("noResults").classList.add("hidden");
        alert("Employee deleted successfully!");
    } catch (error) {
        console.error("Error:", error);
        alert("Failed to delete employee");
    }
}

// Clear search on page load
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("searchId").value = "";
    document.getElementById("employeeTable").innerHTML = "";
    document.getElementById("noResults").classList.add("hidden");
    fetchEmployees();
});
