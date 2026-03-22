// ============================================================
//  dashboard.js — Student Dashboard logic
// ============================================================

// Hardcoded dummy pickup requests for demo
const dummyRequests = [
    { id: 'REQ001', type: 'Plastic',  qty: 5,  status: 'Completed', date: '2026-03-10', points: 50  },
    { id: 'REQ002', type: 'Paper',    qty: 3,  status: 'Pending',   date: '2026-03-18', points: 0   },
    { id: 'REQ003', type: 'Metal',    qty: 8,  status: 'Assigned',  date: '2026-03-20', points: 0   }
];

function loadDashboard() {
    // Get logged in user from localStorage
    const raw = localStorage.getItem('loggedInUser');

    if (!raw) {
        alert('Please login first!');
        window.location.href = 'login.html';
        return;
    }

    const user = JSON.parse(raw);

    // Fill student info
    document.getElementById('studentName').textContent = user.name;
    document.getElementById('studentId').textContent   = user.userId;

    // Calculate total eco points from completed requests
    const totalPoints = dummyRequests
        .filter(r => r.status === 'Completed')
        .reduce((sum, r) => sum + r.points, 0);

    document.getElementById('ecoPoints').textContent = totalPoints + ' pts';

    // Fill requests table
    const tbody = document.getElementById('requestsBody');
    tbody.innerHTML = '';

    dummyRequests.forEach(req => {
        const row = `<tr>
            <td>${req.id}</td>
            <td>${req.type}</td>
            <td>${req.qty}</td>
            <td>${req.status}</td>
            <td>${req.date}</td>
        </tr>`;
        tbody.innerHTML += row;
    });
}

function logout() {
    localStorage.removeItem('loggedInUser');
}

// Run when page loads
loadDashboard();
