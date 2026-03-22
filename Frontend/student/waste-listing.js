// ============================================================
//  waste-listing.js — Submit a waste listing
// ============================================================

function submitListing() {
    const wasteType  = document.getElementById('wasteType').value;
    const quantity   = document.getElementById('quantity').value;
    const address    = document.getElementById('address').value.trim();
    const pickupDate = document.getElementById('pickupDate').value;
    const successMsg = document.getElementById('successMsg');
    const errorMsg   = document.getElementById('errorMsg');

    successMsg.textContent = '';
    errorMsg.textContent   = '';

    // Validation
    if (!quantity || quantity <= 0) {
        errorMsg.textContent = 'Please enter a valid quantity.';
        return;
    }
    if (!address) {
        errorMsg.textContent = 'Please enter your address.';
        return;
    }
    if (!pickupDate) {
        errorMsg.textContent = 'Please select a pickup date.';
        return;
    }

    // Create a listing object
    const listing = {
        id:       'LST' + Date.now(),   // simple unique ID
        type:     wasteType,
        qty:      parseFloat(quantity),
        address:  address,
        date:     pickupDate,
        status:   'Listed'
    };

    // Save to localStorage (simulates saving to database)
    const existing = JSON.parse(localStorage.getItem('wasteListings') || '[]');
    existing.push(listing);
    localStorage.setItem('wasteListings', JSON.stringify(existing));

    successMsg.textContent = `✅ Listing submitted! ID: ${listing.id}`;

    // Clear form
    document.getElementById('quantity').value   = '';
    document.getElementById('address').value    = '';
    document.getElementById('pickupDate').value = '';
}
