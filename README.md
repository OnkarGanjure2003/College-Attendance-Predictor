# College-Attendance-Predictor
A console-based Attendance Prediction System built using Java and Object-Oriented Programming (OOP) concepts.
This project helps students calculate their current attendance, predict future attendance, plan bunks, and estimate required classes to achieve a minimum percentage (e.g., 75%).

🚀 Features :
✅ 1. View Current Attendance
Displays the present attendance percentage based on classes held and attended.

✅ 2. Predict Attendance If Bunked
Shows how your attendance will change if you bunk the next class.

✅ 3. Predict Attendance If Attended
Shows attendance if you attend the next class.

✅ 4. How Many Classes Can Be Bunked
Calculates how many more classes you can miss while keeping attendance ≥ 75%.

✅ 5. How Many Classes Needed to Reach 75%
Estimates the exact number of classes you must attend consecutively to reach the target attendance percentage.

✅ 6. Clean User-Friendly Menu
Clear, easy-to-use console navigation.

🧠 OOP Concepts Used:
✔ Encapsulation
The StudentAttendance class hides attendance details and exposes only necessary methods.

✔ Abstraction
Complex calculations are hidden behind methods like:
         getAttendancePercentage()
         maxBunksMaintainThreshold()
         classesToReachThreshold()

✔ Object Reusability
StudentAttendance.copy() allows prediction without affecting real attendance.

✔ Clean Class Design
main() handles only user interaction, while all calculations are handled in a separate class.
