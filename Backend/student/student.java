// ============================================================
//  Student.java — Mohit's Module (EcoConnect)
//  OOP Concept  : Encapsulation (private fields + getters/setters)
//  DSA Concept  : HashMap<String, Student> — O(1) login lookup
//
//  HOW TO RUN:
//    javac Student.java
//    java Student
// ============================================================

import java.util.HashMap;

// ============================================================
//  CLASS: Student
//  Demonstrates ENCAPSULATION — all fields are private
//  Access is only through public getters and setters
// ============================================================
class Student {

    // Private attributes — cannot be accessed directly from outside
    private String studentId;
    private String name;
    private String email;
    private String password;
    private int    ecoPoints;

    // Constructor — called when creating a new Student object
    public Student(String studentId, String name, String email, String password) {
        this.studentId = studentId;
        this.name      = name;
        this.email     = email;
        this.password  = password;
        this.ecoPoints = 0;   // starts at 0
    }

    // ---- GETTERS (read private data) ----
    public String getStudentId() { return studentId; }
    public String getName()      { return name;      }
    public String getEmail()     { return email;     }
    public int    getEcoPoints() { return ecoPoints; }

    // ---- SETTERS (update private data with validation) ----
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        }
    }

    // Note: password has NO getter — you can never read it back (security)
    // You can only CHECK if a password matches
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Add eco points (called when a pickup is completed)
    public void addEcoPoints(int points) {
        if (points > 0) {
            this.ecoPoints += points;
        }
    }

    // Display student info
    public void displayInfo() {
        System.out.println("---------------------------");
        System.out.println("Student ID  : " + studentId);
        System.out.println("Name        : " + name);
        System.out.println("Email       : " + email);
        System.out.println("Eco Points  : " + ecoPoints);
        System.out.println("---------------------------");
    }
}

// ============================================================
//  CLASS: PickupRequest
//  Represents one pickup request made by a student
// ============================================================
class PickupRequest {
    private String requestId;
    private String studentId;
    private String wasteType;
    private double quantity;
    private String location;
    private String status;    // Pending / Assigned / Completed

    public PickupRequest(String requestId, String studentId,
                         String wasteType, double quantity, String location) {
        this.requestId = requestId;
        this.studentId = studentId;
        this.wasteType = wasteType;
        this.quantity  = quantity;
        this.location  = location;
        this.status    = "Pending";
    }

    // Getters
    public String getRequestId() { return requestId; }
    public String getStudentId() { return studentId; }
    public String getWasteType() { return wasteType; }
    public double getQuantity()  { return quantity;  }
    public String getLocation()  { return location;  }
    public String getStatus()    { return status;    }

    // Setter for status only (ragpicker updates this)
    public void setStatus(String status) {
        this.status = status;
    }

    public void displayRequest() {
        System.out.println("Request ID : " + requestId);
        System.out.println("Waste Type : " + wasteType + " | Qty: " + quantity + " kg");
        System.out.println("Location   : " + location);
        System.out.println("Status     : " + status);
    }
}

// ============================================================
//  CLASS: StudentService
//  Manages all students using a HashMap (DSA concept)
//  HashMap<String, Student>  →  key=studentId, value=Student object
// ============================================================
class StudentService {

    // THE HASHMAP — this is the DSA part
    // Instead of looping through a list (O(n)),
    // HashMap finds any student instantly in O(1) time
    private HashMap<String, Student> studentMap = new HashMap<>();

    // Register a new student — adds to HashMap
    public void registerStudent(Student s) {
        studentMap.put(s.getStudentId(), s);   // key=ID, value=Student object
        System.out.println("✅ Registered: " + s.getName());
    }

    // LOGIN — O(1) lookup using HashMap.get()
    public Student login(String studentId, String password) {
        Student s = studentMap.get(studentId);   // instant lookup — NO loop needed

        if (s == null) {
            System.out.println("❌ Student ID not found.");
            return null;
        }

        if (!s.checkPassword(password)) {
            System.out.println("❌ Wrong password.");
            return null;
        }

        System.out.println("✅ Login successful! Welcome, " + s.getName());
        return s;
    }

    // Get total number of students registered
    public int getTotalStudents() {
        return studentMap.size();
    }
}

// ============================================================
//  MAIN — Demo: shows OOP + HashMap working together
// ============================================================
public class Student {

    public static void main(String[] args) {

        System.out.println("==============================");
        System.out.println("  EcoConnect — Student Module");
        System.out.println("  OOP: Encapsulation");
        System.out.println("  DSA: HashMap  O(1) login");
        System.out.println("==============================\n");

        // Create StudentService (has the HashMap inside)
        StudentService service = new StudentService();

        // Register students — stored in HashMap
        service.registerStudent(new Student("STU001", "Mohit Purohit", "mohit@eco.com", "stu001"));
        service.registerStudent(new Student("STU002", "Riya Sharma",   "riya@eco.com",  "stu002"));
        service.registerStudent(new Student("STU003", "Amit Rawat",    "amit@eco.com",  "stu003"));

        System.out.println("\nTotal Students: " + service.getTotalStudents());

        // ---- TEST LOGIN ----
        System.out.println("\n--- Login Tests ---");

        // Correct login
        Student loggedIn = service.login("STU001", "stu001");
        if (loggedIn != null) {
            loggedIn.displayInfo();
        }

        // Wrong password
        service.login("STU002", "wrongpass");

        // Student not found
        service.login("STU999", "anypass");

        // ---- TEST ECO POINTS ----
        System.out.println("\n--- Eco Points Test ---");
        if (loggedIn != null) {
            System.out.println("Before pickup: " + loggedIn.getEcoPoints() + " pts");
            loggedIn.addEcoPoints(50);   // ragpicker completed pickup → add points
            loggedIn.addEcoPoints(-10);  // invalid — negative ignored by setter
            System.out.println("After pickup : " + loggedIn.getEcoPoints() + " pts");
        }

        // ---- TEST PICKUP REQUEST ----
        System.out.println("\n--- Pickup Request ---");
        PickupRequest req = new PickupRequest(
            "REQ001", "STU001", "Plastic", 5.0, "Rajpur Road, Dehradun"
        );
        req.displayRequest();

        System.out.println("\nStatus updated by ragpicker...");
        req.setStatus("Assigned");
        System.out.println("New Status: " + req.getStatus());

        System.out.println("\n==============================");
    }
}
