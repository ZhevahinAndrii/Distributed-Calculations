package DB;

import models.Group;
import models.Student;

import java.util.Scanner;
import java.util.Optional;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        DataAccessObject dao = new DataAccessObject();

        String commands =
                """
                        1. Create new group
                        2. Create new student
                        3. Delete group
                        4. Delete student
                        5. Update group
                        6. Update student
                        7. Get group by id
                        8. Get student by id
                        9. Get all groups
                        10. Get all students with group id
                        11. Amount of groups
                        12. Amount of students
                        13. Exit
                        """;

        System.out.println(commands);
        Integer operation = null;
        Scanner scanner = new Scanner(System.in);
        while (operation == null || operation != 13) {
            System.out.println("Input operation:");
            operation = Integer.parseInt(scanner.next());
            try {
                switch (operation){
                    case 1->{
                        Group group = getGroupInput(false,true,true);
                        dao.createNewGroup(group);
                        System.out.println("Operation completed succesfully");

                    }
                    case 2->{
                        Student student = getStudentInput(false,true,true,false,false);
                        dao.createNewStudent(student);
                        System.out.println("Operation completed successfully");
                    }
                    case 3->{
                        int groupId = getNumberInput("Input group id:");
                        if (dao.deleteGroup(groupId)==0){
                            System.out.println("There is no such a group with given id");
                        }
                        else
                            System.out.println("Operation completed successfully");

                    }
                    case 4->{
                        int studentId =getNumberInput("Input student id:");
                        if(dao.deleteStudent(studentId)==0)
                            System.out.println("There is no such a student with given id");
                        else
                            System.out.println("Operation completed succesfully");
                    }
                    case 5->{
                        Group group = getGroupInput(true,true,true);
                        dao.updateGroup(group);
                        System.out.println("Operation completed successfully");
                    }
                    case 6->{
                        Student student = getStudentInput(true,true,true,false,false);
                        dao.updateStudent(student);
                        System.out.println("Operation completed successfully");
                    }
                    case 7->{
                        int groupId = getNumberInput("Enter group id:");
                        Optional<Group> group = dao.findGroupById(groupId);
                        if (group.isEmpty()){
                            System.out.println("There is no such a group with given id");
                        }
                        else {
                            System.out.println(group.get());
                            System.out.println("Operation completed successfully");
                        }
                    }
                    case 8->{
                        int studentId = getNumberInput("Enter student id:");
                        Optional<Student> student = dao.findStudentById(studentId);
                        if(student.isEmpty()){
                            System.out.println("There is no such a student with given id");
                        }
                        else{
                        System.out.println(student.get());
                        System.out.println("Operation completed successfully");
                        }
                    }
                    case 9->{
                        System.out.println(dao.findAllGroups());
                        System.out.println("Operation completed successfully");
                    }
                    case 10->{
                        int groupId = getNumberInput("Input group id:");
                        System.out.println(dao.findAllStudentsWithGroupId(groupId));
                        System.out.println("Operation completed successfully");
                    }
                    case 11->{
                        if (dao.countGroups().isEmpty())
                            System.out.println("Amount of groups:0");
                        else
                            System.out.println("Amount of groups:" + dao.countGroups().get());
                    }
                    case 12->{
                        if(dao.countStudents().isEmpty())
                            System.out.println("Amount of students:0");
                        else
                            System.out.println("Amount of students:"+dao.countStudents().get());
                    }
                    case 13-> System.out.println("Program stopped");
                }
            }
            catch (Exception e){
                System.out.println("Error:"+e.getMessage());
            }
            }
        dao.closeConnection();
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


