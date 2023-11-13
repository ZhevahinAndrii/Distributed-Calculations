package task3;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


import models.Group;
import models.Student;
import DB.DataAccessObject;






public class Server {
    static ObjectInputStream in;
    static ObjectOutputStream out;
    static Socket socket;
    static ServerSocket serverSocket;

    public static void main(String[] args){
        try{
            System.out.println("Waiting for connection");
            serverSocket = new ServerSocket(8888);
            socket = serverSocket.accept();
            System.out.println("Client from port "+socket.getPort()+" connected");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            out.flush();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }

        DataAccessObject dao = new DataAccessObject();
        int operation = 0;
        do{
            try{
                operation = (Integer) in.readObject();
                switch(operation){
                    case 1->{
                       String name,studyingProgram;
                       int id;
                       sendMessage("Enter group id");
                       id = Integer.parseInt((String)in.readObject());
                       sendMessage("Enter group name:");
                       name = (String)in.readObject();
                       sendMessage("Enter group studying program:");
                       studyingProgram = (String)in.readObject();
                       Group group = new Group(id,name,studyingProgram);
                       dao.createNewGroup(group);
                       System.out.println("Request from client " + socket.getPort()+" has completed successfully");
                    }
                    case 2->{
                        sendMessage("Enter group id:");
                        int id = Integer.parseInt((String)in.readObject());
                        dao.deleteGroup(id);
                        System.out.println("Request from client " + socket.getPort()+" has completed successfully");
                    }
                    case 3->{
                        String firstName,lastName;
                        double averageMark;
                        int groupId;

                        sendMessage("Enter first name:");
                        firstName = (String)in.readObject();
                        sendMessage("Enter last name:");
                        lastName = (String)in.readObject();
                        sendMessage("Enter average mark:");
                        averageMark = Double.parseDouble((String)in.readObject());
                        sendMessage("Enter group id:");
                        groupId = Integer.parseInt((String)in.readObject());

                        Group group = new Group(groupId,null,null);
                        Student student = new Student(null,firstName,lastName,averageMark,group);
                        dao.createNewStudent(student);
                        System.out.println("Request from client " + socket.getPort()+" has completed successfully");
                    }

                    case 4->{
                        sendMessage("Enter student id:");
                        int id =Integer.parseInt((String)in.readObject());
                        dao.deleteStudent(id);
                        System.out.println("Request from client " + socket.getPort()+" has completed successfully");
                    }
                    case 5->{
                        sendMessage("Enter student id:");
                        int id = Integer.parseInt((String)in.readObject());
                        sendMessage("Enter new group id:");
                        int newGroupId = Integer.parseInt((String)in.readObject());
                        Group group = new Group(newGroupId,null,null);
                        Student student = new Student(id,null,null,null,group);
                        dao.updateStudentGroup(student,newGroupId);
                        System.out.println("Request from client " + socket.getPort()+" has completed successfully");

                    }
                    case 6->{
                        sendMessage("Enter student id");
                        int id = Integer.parseInt((String)in.readObject());
                        sendMessage("Enter student's first name:");
                        String firstName = (String) in.readObject();
                        sendMessage("Enter student's last name:");
                        String lastName = (String) in.readObject();
                        sendMessage("Enter student's averageMark:");
                        Double averageMark = Double.parseDouble((String)in.readObject());
                        sendMessage("Enter student's group id:");
                        int groupId = Integer.parseInt((String)in.readObject());

                        Group group = new Group(groupId,null,null);
                        Student student = new Student(id,firstName,lastName,averageMark,group);
                        dao.updateStudent(student);
                        System.out.println("Request from client " + socket.getPort()+" has completed successfully");
                    }
                    case 7->{
                        sendMessage("Enter group id:");
                        int groupId = Integer.parseInt((String)in.readObject());
                        sendMessage(dao.findAllStudentsWithGroupId(groupId));
                        System.out.println("Answer for client "+socket.getPort()+":"+dao.findAllStudentsWithGroupId(groupId));
                        System.out.println("Request from client " + socket.getPort()+" has completed successfully");
                    }
                    case 8->{
                        sendMessage(dao.findAllGroups());
                        System.out.println("Answer for client "+socket.getPort()+":"+dao.findAllGroups());
                        System.out.println("Request from client " + socket.getPort()+" has completed successfully");
                    }
                    case 9->{
                        sendMessage(dao.findAllStudents());
                        System.out.println("Answer for client "+socket.getPort()+":"+dao.findAllStudents());
                        System.out.println("Request from client " + socket.getPort()+" has completed successfully");
                    }
                    case 10->{

                        System.out.println("Program stopped");
                        dao.closeConnection();
                    }

                }
                sendMessage("stop");

            }
            catch (Exception e){
                System.out.println("Error:"+e.getMessage());
            }
        }while(operation!=11);
    }
    private static void sendMessage(Object msg){
        try{
            out.writeObject(msg);
            out.flush();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
