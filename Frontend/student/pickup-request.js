// ============================================================
//  pickup-request.js — Submit and view pickup requests
// ============================================================

function submitRequest() {
    const wasteType = document.getElementById('wasteType').value;
    const quantity  = document.getElementById('quantity').value;
    const location  = document.getElementById('location').value.trim();
    const notes     = document.getElementById('notes').value.trim();
    const successMsg = document.getElementById('successMsg');
    const errorMsg   = document.getElementById('errorMsg');

    successMsg.textContent = '';
    errorMsg.textContent   = '';

    // Validation
    if (!quantity || quantity <= 0) {
        errorMsg.textContent = 'Please enter a valid quantity.';
        return;
    }
    if (!location) {
        errorMsg.textContent = 'Please enter your location.';
        return;
    }

    // Get logged in user
    const user = JSON.parse(localStorage.getItem('loggedInUser') || '{}');

    // Create request object
    const request = {
        id:        'REQ' + Date.now(),
        studentId: user.userId || 'UNKNOWN',
        type:      wasteType,
        qty:       parseFloat(quantity),
        location:  location,
        notes:     notes,
        status:    'Pending',
        date:      new Date().toISOString().split('T')[0]
    };

    // Save to localStorage (simulates sending to backend/database)
    const existing = JSON.parse(localStorage.getItem('pickupRequests') || '[]');
    existing.push(request);
    localStorage.setItem('pickupRequests', JSON.stringify(existing));

    successMsg.textContent = `✅ Request submitted! ID: ${request.id} — A ragpicker will be assigned soon.`;

    // Refresh the request list below
    loadMyRequests();

    // Clear form
    document.getElementById('quantity').value = '';
    document.getElementById('location').value = '';
    document.getElementById('notes').value    = '';
}

function loadMyRequests() {
    const container = document.getElementById('myRequests');
    const requests  = JSON.parse(localStorage.getItem('pickupRequests') || '[]');

    if (requests.length === 0) {
        container.innerHTML = '<p>No requests yet.</p>';
        return;
    }

    let html = '<table border="1"><tr><th>ID</th><th>Type</th><th>Qty</th><th>Location</th><th>Status</th><th>Date</th></tr>';
    requests.forEach(r => {
        html += `<tr>
            <td>${r.id}</td>
            <td>${r.type}</td>
            <td>${r.qty} kg</td>
            <td>${r.location}</td>
            <td>${r.status}</td>
            <td>${r.date}</td>
        </tr>`;
    });
    html += '</table>';
    container.innerHTML = html;
}

// Load existing requests when page opens
loadMyRequests();
