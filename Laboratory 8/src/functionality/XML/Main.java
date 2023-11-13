package XML;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
public class Main {
    public static void main(String[] args){
        EducationalDepartment educationalDepartment = new EducationalDepartment();
        educationalDepartment.loadFromFile("C:\\Distibuted Calculations\\Laboratory 7\\src\\resources\\xml\\groups.xml",
                "C:\\Distibuted Calculations\\Laboratory 7\\src\\resources\\xml\\schema.xsd");

        educationalDepartment.print();

        String operation = "0";

        while(!operation.equals("14")){
            System.out.println("\t1. Add group;");
            System.out.println("\t2. Get number of groups;");
            System.out.println("\t3. Get group by id;");
            System.out.println("\t4. Update group;");
            System.out.println("\t5. Delete group by id(cascade deletion)");
            System.out.println("\t6. Add student");
            System.out.println("\t7. Get number of students by group id;");
            System.out.println("\t8. Get number of students;");
            System.out.println("\t9. Get student by id;");
            System.out.println("\t10.Delete student by id;");
            System.out.println("\t11. Print all");
            System.out.println("\t12. Save all");
            System.out.println("\t13. Refresh from XML file;");
            System.out.println("\t14. Exit");

            System.out.println("\nChoose your operation:");

            Scanner scanner = new Scanner(System.in);
            operation = scanner.nextLine();
            int groupId;
            String groupName;
            int studentId;
            switch(Integer.parseInt(operation)){
                case 1:
                    System.out.println("Enter group name:");
                    groupName = scanner.nextLine();
                    System.out.println("Enter group studying program:");
                    String studyingProgram = scanner.nextLine();
                    System.out.println("Enter group id:");
                    groupId = Integer.parseInt(scanner.nextLine());
                    educationalDepartment.addGroup(groupId,groupName,studyingProgram);
                    break;
                case 2:
                    System.out.println(educationalDepartment.countGroups());
                    break;
                case 3:
                    System.out.println("Enter group id:");
                    groupId = Integer.parseInt(scanner.nextLine());
                    System.out.println(educationalDepartment.getGroup(groupId));
                    break;
                case 4:
                    System.out.println("Enter group id to update");
                    groupId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new name(enter space to save the previous version):");
                    groupName = scanner.nextLine();
                    System.out.println("Enter new studying program(enter space to save the previous version):");
                    String groupStudyingProgram = scanner.nextLine();
                    try {
                        educationalDepartment.updateGroup(groupId, groupName, groupStudyingProgram);
                        System.out.println("Updated successfully");
                    }
                    catch (NoSuchElementException e){
                        System.out.println(e.getMessage());
                    }

                    break;
                case 5:
                    System.out.println("Enter group id:");
                    groupId = Integer.parseInt(scanner.nextLine());
                    try {
                        educationalDepartment.deleteGroup(groupId);
                        System.out.println("Deleted successfully");
                    }
                    catch (NoSuchElementException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Enter student's id:");
                    studentId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter student's first name:");
                    String studentFirstName = scanner.nextLine();
                    System.out.println("Enter student's last name:");
                    String studentLastName = scanner.nextLine();
                    System.out.println("Enter student's average mark");
                    double studentAverageMark = Double.parseDouble(scanner.nextLine());
                    System.out.println("Enter student's group id:");
                    groupId = Integer.parseInt(scanner.nextLine());
                    educationalDepartment.addStudent(studentId,studentFirstName,studentLastName,studentAverageMark,groupId);
                    break;
                case 7:
                    System.out.println("Enter a group id:");
                    groupId = Integer.parseInt(scanner.nextLine());
                    System.out.println(educationalDepartment.countStudentsInGroup(groupId));
                    break;
                case 8:
                    System.out.println(educationalDepartment.countStudents());
                    break;
                case 9:
                    System.out.println("Enter student's id:");
                    studentId = Integer.parseInt(scanner.nextLine());
                    System.out.println(educationalDepartment.getStudent(studentId));
                    break;
                case 10:
                    System.out.println("Enter student's id:");
                    studentId = Integer.parseInt(scanner.nextLine());
                    try {
                        educationalDepartment.deleteStudent(studentId);
                        System.out.println("Deleted successfully");
                    }
                    catch (NoSuchElementException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 11:
                    educationalDepartment.print();
                    break;
                case 12:
                    try{
                        educationalDepartment.saveToFile("C:\\Distibuted Calculations\\Laboratory 7\\src\\resources\\xml\\groups.xml");
                    }
                    catch (ParserConfigurationException|TransformerException e){
                        throw new RuntimeException(e);
                    }
                case 13:
                    educationalDepartment.clearDepartment();
                    educationalDepartment.loadFromFile("C:\\Distibuted Calculations\\Laboratory 7\\src\\resources\\xml\\groups.xml",
                                "C:\\Distibuted Calculations\\Laboratory 7\\src\\resources\\xml\\schema.xsd");
                default:
                    break;
            }
            System.out.println();

        }
    }
}
