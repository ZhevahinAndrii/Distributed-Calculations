package task4;

import models.Group;
import models.Student;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
public class RMIClient {
    private static final Scanner scanner  = new Scanner(System.in);
    public static void main(String[] args){
        try{
            Registry registry = LocateRegistry.getRegistry();
            RMICommandsInterface rmiCommands = (RMICommandsInterface) registry.lookup("Dao");
            rmiCommands.connectToDataBase();

            String commands = """
                1. Create new group
                2. Delete group
                3. Create student
                4. Delete student
                5. Update student's group
                6. Update student
                7. Get all students with group id
                8. Get all groups
                9. Get all students
                10. Exit
                """;
            Integer operation = null;

            System.out.println(commands);
            while(operation==null||operation!=11){
                System.out.println("Input operation:");
                operation = Integer.parseInt(scanner.nextLine());
                try{
                    switch (operation){
                        case 1->{
                            Group group = getGroupInput(false,true,true);
                            rmiCommands.createNewGroup(group);
                            System.out.println("Operation completed successfully");
                        }

                        case 2->{
                            int groupId = getNumberInput("Input group id:");
                            rmiCommands.deleteGroup(groupId);
                            System.out.println("Operation completed successfully");
                        }
                        case 3->{
                            Student student = getStudentInput(false,true,true,false,false);
                            rmiCommands.createNewStudent(student);
                            System.out.println("Operation completed successfully");
                        }
                        case 4->{
                            int id = getNumberInput("Input student id:");
                            rmiCommands.deleteStudent(id);
                            System.out.println("Operation completed successfully");
                        }
                        case 5->{
                            int studentId = getNumberInput("Input student id:");
                            int newGroupId = getNumberInput("Input new group id:");
                            Student student = new Student(studentId,null,null,null,null);
                            rmiCommands.updateStudentGroup(student,newGroupId);
                            System.out.println("Operation completed successfully");
                        }
                        case 6->{
                            Student student = getStudentInput(true,true,true,false,false);
                            rmiCommands.updateStudent(student);
                            System.out.println("Operation completed successfully");
                        }
                        case 7->{
                            int groupId = getNumberInput("Input group id:");
                            System.out.println(rmiCommands.findStudentsWithGroupId(groupId));
                            System.out.println("Operation completed successfully");
                        }
                        case 8->{
                            System.out.println(rmiCommands.findAllGroups());
                            System.out.println("Operation completed successfully");
                        }
                        case 9->{
                            System.out.println(rmiCommands.findAllStudents());
                            System.out.println("Operation completed successfully");
                        }
                        case 10-> System.out.println("Program stopped");
                    }
                }
                catch (Exception e){
                    System.out.println("Error:" + e.getMessage());
                }
            }
            rmiCommands.closeConnection();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public static int getNumberInput(String msg){
        System.out.println(msg);
        return Integer.parseInt(scanner.nextLine());
    }
    public static String getStringInput(String msg){
        System.out.println(msg);
        return scanner.nextLine();
    }
    public static double getDoubleInput(String msg){
        System.out.println(msg);
        return Double.parseDouble(scanner.nextLine());
    }
    public static Group getGroupInput(boolean withId,boolean withName,boolean withSP){

        Integer id =null;
        String name = null;
        String sp = null;
        if(withId){
            id = getNumberInput("Input group id:");
        }
        if(withName) {
            name = getStringInput("Input group name:");
        }
        if(withSP){
            sp = getStringInput("Input group studying program:");
        }
        return new Group(id,name,sp);
    }
    public static Student getStudentInput(boolean withId,boolean withGroup,boolean withGroupId,boolean withGroupName,boolean withSP){
        String first_name = getStringInput("Enter student first name:");
        String last_name = getStringInput("Enter student last name:");
        double average_mark = getDoubleInput("Enter student average mark");
        int id = 0;
        if(withId){
            id = getNumberInput("Enter student id:");
        }
        if(withGroup){
            return new Student(id,first_name,last_name,average_mark,getGroupInput(withGroupId,withGroupName,withSP));
        }
        else
            return new Student(id,first_name,last_name,average_mark,null);
    }

}
