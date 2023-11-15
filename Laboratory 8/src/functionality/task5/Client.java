package task5;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


import models.Group;
import models.Student;

import static task5.RabbitMQUtil.serializeObject;

public class Client {
    static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try(Connection rabbitmqConnection= connectionFactory.newConnection()){
            Channel channel = rabbitmqConnection.createChannel();
            String commands = """
                1. Create new group
                2. Create student
                3. Delete student
                4. Get all students
                5. Exit
                """;
            System.out.println(commands);
            channel.queueDeclare("server-queue",false,false,false,null);
            channel.queueDeclare("client-queue",false,false,false,null);
            try {
                while (true) {
                    channel.basicConsume("client-queue", true, (consumerTag, delivery) -> {
                        String receivedData = new String(delivery.getBody());
                        System.out.println("Received from server:" + receivedData);
                    }, consumerTag -> {
                    });

                    DataObject dataObjectInput = getDataObjectInput();
                    if (dataObjectInput == null || dataObjectInput.getCommand() == 5) break;
                    channel.basicPublish("", "server-queue", null, serializeObject(dataObjectInput));

                    System.out.println("Message has been sent to server");
                    Thread.sleep(10);
                }

            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            channel.close();
        }
        catch (IOException|TimeoutException e){

            throw new RuntimeException(e);

        }
    }
    public static DataObject getDataObjectInput(){
        Integer operation = null;
        while(operation==null||operation!=5){
            System.out.println("Input operation:");
            operation = Integer.parseInt(scanner.nextLine());
            try{
                switch (operation){
                    case 1->{
                        Group group = getGroupInput(false,true,true);
                        return new DataObject(operation,group);
                    }
                    case 2->{
                        Student student = getStudentInput(false,true,true,false,false);
                        return new DataObject(operation,student);
                    }
                    case 3->{
                        int studentId = getNumberInput("Input student id:");
                        return new DataObject(operation,studentId);
                    }
                    case 4->{
                        return new DataObject(operation,null);
                    }
                    case 5->{
                        System.out.println("Program stopped");
                        return new DataObject(operation,null);
                    }
                }
            }catch (Exception e){
                System.out.println("Error happened:"+e.getMessage());
            }
        }
        return null;
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
