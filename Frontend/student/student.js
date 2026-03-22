
function login() {
    let studentId = document.getElementById('studentId').value;
    let password = document.getElementById('password').value;

    if (studentId === '' || password === '') {
        alert('Please fill all fields!');
    }
    else if ((studentId === '2419260' && password === '2419260') || (studentId === '2418537' && password === '2418537') || (studentId === '2418776' && password === '2418776') || (studentId === '2418482' && password === '2418482')) {
        alert('Login successful!');
    }
    else {
        alert('Login failed!');
    }
}
