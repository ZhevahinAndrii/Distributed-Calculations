package XML;
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

        while(!operation.equals("9")){
            System.out.println("\t1. Add group;");
            System.out.println("\t2.Get group by id;");
            System.out.println("\t3. Get group by index;");
            System.out.println("\t4. Get number of groups;");
            System.out.println("\t5. Delete group by id");
            System.out.println("\t6. Add student");
            System.out.println("\t7. Print all");
            System.out.println("\t8. Save all");
            System.out.println("\t9. Exit");

            System.out.println("\nChoose your operation:");

            Scanner scanner = new Scanner(System.in);
            operation = scanner.nextLine();

            switch(Integer.parseInt(operation)){
                case 1:
                    System.out.println("Enter group name:");
                    String groupName = scanner.nextLine();
                    System.out.println("Enter group studying program:");
                    String studyingProgram = scanner.nextLine();
                    System.out.println("Enter group id:");
                    int groupId = Integer.parseInt(scanner.nextLine());
                    educationalDepartment.addGroup(groupId,groupName,studyingProgram);
                    break;
                case 2:
                    System.out.println("Enter group id:");
                    int groupId = Integer.parseInt(scanner.nextLine());
                    System.out.println(educationalDepartment.getGroup(groupId));
                    break;

            }

        }
    }
}
