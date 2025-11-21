//import java.util.Scanner;
//public class CollegeAttendancePredictorApp {
//    public static void main(String[] args) {
//        Scanner sc=new Scanner(System.in);
//        System.out.println("===============================");
//        System.out.println(" College Attendance Predictor ");
//        System.out.println("===============================");
//
//        System.out.println("Total classes held: ");
//        int totalClassesHeld=sc.nextInt();
//
//        System.out.println("Total classes Attended: ");
//        int totalClassesAttended=sc.nextInt();
//
//        while(true){
//            System.out.println("==========================MENU========================");
//            System.out.println("1.View Current Attendance");
//            System.out.println("2.Attendance If Bunked Next Class");
//            System.out.println("3.Attendance If Attended Next Class");
//            System.out.println("4.How Many Classes Can I Bunk for>75% Attendance");
//            System.out.println("5.How Many Classes Should I Attend for 75% Attendance");
//            System.out.println("6.Exit The System");
//            System.out.println("==========================MENU========================");
//            System.out.println("Choose an Option: ");
//
//            int choice=sc.nextInt();
//            switch(choice){
//                case 1:
//                    showCurrentAttendance(totalClassesAttended,totalClassesHeld);
//                    break;
//
//                case 2:
//                    totalClassesHeld++;
//                    showCurrentAttendance(totalClassesAttended,totalClassesHeld);
//                    break;
//
//                case 3:
//                    totalClassesHeld++;
//                    totalClassesAttended++;
//                    showCurrentAttendance(totalClassesAttended,totalClassesHeld);
//                    break;
//
//                case 4:
//                    howManyBunks(totalClassesAttended,totalClassesHeld);
//                    break;
//
//                case 5:
//                    classesRequired(totalClassesAttended,totalClassesHeld);
//                    break;
//
//                case 6:
//                    System.out.println("Thank You...!!!");
//                    sc.close();
//                    System.exit(0);
//
//                default:
//                    System.out.println("Invalid Input..!");
//            }
//
//
//        }
//
//    }
//    public static void showCurrentAttendance(int totalClassesAttended,int totalClassesHeld){
//        double attPercentage=(totalClassesAttended*100/totalClassesHeld);
//        System.out.println("Current Attendance: "+attPercentage+"%");
//    }
//
//    public static void howManyBunks(int totalClassesAttended,int totalClassesHeld){
//        int bunks=0;
//        while((totalClassesAttended*100/totalClassesHeld+1)>=75){
//            totalClassesHeld++;
//            bunks++;
//        }
//        System.out.println("You can Bunk: "+bunks+" more classes");
//    }
//
//    public static void classesRequired(int totalClassesAttended,int totalClassesHeld){
//        if((totalClassesAttended*100)/totalClassesHeld>=75){
//            System.out.println("Already You Have 75% Attendance");
//        }
//        int extraClass=0;
//        while((totalClassesAttended+extraClass/totalClassesHeld+extraClass)<75){
//            extraClass++;
//        }
//        System.out.println("You Must attend: "+extraClass+" more classes");
//    }
//}

//Updated Code with Improvements
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.DecimalFormat;

/**
 * CollegeAttendancePredictorApp
 * Author:@Onkar Ganjure
 * OOP features used:
 *  - StudentAttendance class (encapsulation of state and behavior)
 *  - Methods to operate on the state (getPercentage, bunkNext, attendNext, etc.)
 *  - Constructor and getters/setters (controlled access to fields)
 *  - Single-responsibility: main handles I/O & menu; StudentAttendance handles calculations
 */
public class CollegeAttendancePredictorApp{

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===============================");
        System.out.println(" College Attendance Predictor ");
        System.out.println("===============================");

        int totalHeld = readPositiveInt(sc, "Total classes held: ");
        int totalAttended = readNonNegativeInt(sc, "Total classes attended: ", totalHeld);

        StudentAttendance student = new StudentAttendance(totalHeld, totalAttended);

        DecimalFormat df = new DecimalFormat("0.00");

        while (true) {
            System.out.println();
            System.out.println("==========================MENU========================");
            System.out.println("1. View Current Attendance");
            System.out.println("2. Attendance If Bunked Next Class");
            System.out.println("3. Attendance If Attended Next Class");
            System.out.println("4. How Many Classes Can I Bunk and remain >= 75%");
            System.out.println("5. How Many Classes Should I Attend to reach 75%");
            System.out.println("6. Update totals manually");
            System.out.println("7. Exit The System");
            System.out.println("=====================================================");
            System.out.print("Choose an Option: ");

            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException ime) {
                System.out.println("Please enter a valid number.");
                sc.nextLine(); // clear
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Classes Held: " + student.getTotalClassesHeld()
                            + " | Classes Attended: " + student.getTotalClassesAttended());
                    System.out.println("Current Attendance: " + df.format(student.getAttendancePercentage()) + " %");
                    break;

                case 2:
                    StudentAttendance copyIfBunk = student.copy();
                    copyIfBunk.bunkNext();
                    System.out.println("If you bunk next class -> Attendance: "
                            + df.format(copyIfBunk.getAttendancePercentage()) + " % (Held: "
                            + copyIfBunk.getTotalClassesHeld() + ", Attended: "
                            + copyIfBunk.getTotalClassesAttended() + ")");
                    break;

                case 3:
                    StudentAttendance copyIfAttend = student.copy();
                    copyIfAttend.attendNext();
                    System.out.println("If you attend next class -> Attendance: "
                            + df.format(copyIfAttend.getAttendancePercentage()) + " % (Held: "
                            + copyIfAttend.getTotalClassesHeld() + ", Attended: "
                            + copyIfAttend.getTotalClassesAttended() + ")");
                    // apply change to actual totals? Ask user:
                    System.out.print("Do you want to apply this attendance (mark attended) to your totals? (y/n): ");
                    String ans = sc.next();
                    if (ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("yes")) {
                        student.attendNext();
                        System.out.println("Updated totals applied.");
                    } else {
                        System.out.println("No change made.");
                    }
                    break;

                case 4:
                    int maxBunks = student.maxBunksMaintainThreshold(75.0);
                    if (maxBunks <= 0) {
                        System.out.println("If you want to keep attendance >= 75%, you cannot bunk any classes now.");
                    } else {
                        System.out.println("You can bunk up to " + maxBunks + " more class"
                                + (maxBunks > 1 ? "es" : "") + " and remain >= 75%.");
                    }
                    break;

                case 5:
                    int needed = student.classesToReachThreshold(75.0);
                    if (needed == 0) {
                        System.out.println("You already have >= 75% attendance.");
                    } else {
                        System.out.println("You must attend " + needed + " more class"
                                + (needed > 1 ? "es" : "") + " (consecutively) to reach 75%.");
                    }
                    break;

                case 6:
                    // allow manual update
                    System.out.println("Update Totals Manually:");
                    int newHeld = readPositiveInt(sc, "New total classes held: ");
                    int newAttended = readNonNegativeInt(sc, "New total classes attended: ", newHeld);
                    student.setTotals(newHeld, newAttended);
                    System.out.println("Totals updated.");
                    break;

                case 7:
                    System.out.println("Thank You...!!!");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Input..!");
            }
        }
    }

    // Helper methods for safe integer input
    private static int readPositiveInt(Scanner sc, String prompt) {
        int val;
        while (true) {
            System.out.print(prompt);
            try {
                val = sc.nextInt();
                if (val <= 0) {
                    System.out.println("Value must be a positive integer.");
                    continue;
                }
                return val;
            } catch (InputMismatchException ime) {
                System.out.println("Please enter a valid integer.");
                sc.nextLine();
            }
        }
    }

    private static int readNonNegativeInt(Scanner sc, String prompt, int maxAllowed) {
        int val;
        while (true) {
            System.out.print(prompt);
            try {
                val = sc.nextInt();
                if (val < 0) {
                    System.out.println("Value cannot be negative.");
                    continue;
                }
                if (val > maxAllowed) {
                    System.out.println("Attended cannot be more than held (" + maxAllowed + ").");
                    continue;
                }
                return val;
            } catch (InputMismatchException ime) {
                System.out.println("Please enter a valid integer.");
                sc.nextLine();
            }
        }
    }
}

/**
 * StudentAttendance encapsulates attendance state and provides calculation methods.
 */
class StudentAttendance {
    private int totalClassesHeld;
    private int totalClassesAttended;

    public StudentAttendance(int totalClassesHeld, int totalClassesAttended) {
        if (totalClassesHeld <= 0) {
            throw new IllegalArgumentException("Total classes held must be positive.");
        }
        if (totalClassesAttended < 0 || totalClassesAttended > totalClassesHeld) {
            throw new IllegalArgumentException("Invalid attended value.");
        }
        this.totalClassesHeld = totalClassesHeld;
        this.totalClassesAttended = totalClassesAttended;
    }

    // getters
    public int getTotalClassesHeld() {
        return totalClassesHeld;
    }

    public int getTotalClassesAttended() {
        return totalClassesAttended;
    }

    // setter (controlled)
    public void setTotals(int held, int attended) {
        if (held <= 0) throw new IllegalArgumentException("held must be positive");
        if (attended < 0 || attended > held) throw new IllegalArgumentException("attended invalid");
        this.totalClassesHeld = held;
        this.totalClassesAttended = attended;
    }

    // mark the next class as bunked (held increases by 1)
    public void bunkNext() {
        this.totalClassesHeld++;
    }

    // mark the next class as attended (both held and attended increase by 1)
    public void attendNext() {
        this.totalClassesHeld++;
        this.totalClassesAttended++;
    }

    // return percentage as double
    public double getAttendancePercentage() {
        if (totalClassesHeld == 0) return 0.0;
        return ((double) totalClassesAttended * 100.0) / totalClassesHeld;
    }

    // returns the maximum number of classes you can bunk now and still remain >= thresholdPercent
    public int maxBunksMaintainThreshold(double thresholdPercent) {
        if (thresholdPercent <= 0 || thresholdPercent >= 100) {
            throw new IllegalArgumentException("thresholdPercent must be between 0 and 100");
        }
        double threshold = thresholdPercent / 100.0;
        // after bunking b classes: attended / (held + b) >= threshold
        // => b <= attended/threshold - held
        double maxB = ((double) totalClassesAttended / threshold) - totalClassesHeld;
        int maxBunks = (int) Math.floor(maxB + 1e-9); // floor with tolerance
        return Math.max(0, maxBunks);
    }

    // returns how many consecutive classes must be attended to reach at least thresholdPercent
    public int classesToReachThreshold(double thresholdPercent) {
        if (thresholdPercent <= 0 || thresholdPercent >= 100) {
            throw new IllegalArgumentException("thresholdPercent must be between 0 and 100");
        }
        double threshold = thresholdPercent / 100.0;
        double currentPercent = getAttendancePercentage() / 100.0;
        if (currentPercent >= threshold) return 0;

        // Find minimal x such that (attended + x) / (held + x) >= threshold
        // Solve: attended + x >= threshold*(held + x)
        // => attended + x >= threshold*held + threshold*x
        // => x - threshold*x >= threshold*held - attended
        // => x*(1 - threshold) >= threshold*held - attended
        // => x >= (threshold*held - attended) / (1 - threshold)
        double numerator = threshold * totalClassesHeld - totalClassesAttended;
        double denominator = 1.0 - threshold;
        double x = Math.ceil(numerator / denominator - 1e-9); // ceil with tolerance
        int required = (int) Math.max(0, x);
        return required;
    }

    // create a copy of the object (useful for simulation)
    public StudentAttendance copy() {
        return new StudentAttendance(this.totalClassesHeld, this.totalClassesAttended);
    }
}

