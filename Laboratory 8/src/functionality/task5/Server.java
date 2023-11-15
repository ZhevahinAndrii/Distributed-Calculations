package task5;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


import models.Group;
import models.Student;
import DB.DataAccessObject;

import static task5.RabbitMQUtil.deserializeObject;
public class Server {
    private static final DataAccessObject dao = new DataAccessObject();

    public static void main(String[] args){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        try{
            Connection rabbitmqConnection = connectionFactory.newConnection();

            Channel channel = rabbitmqConnection.createChannel();

            channel.queueDeclare("server-queue",false,false,false,null);
            channel.queueDeclare("client-queue",false,false,false,null);

            channel.basicConsume("server-queue",true,(consumerTag,delivery)->handleRequest(channel,delivery.getBody()),consumerTag->{});
        }
        catch (IOException| TimeoutException e){
            throw new RuntimeException(e);
        }
    }
    private static void handleRequest(Channel channel,byte[] data){
        try{
            DataObject dataObject = deserializeObject(data);
            switch(dataObject.getCommand()){
                case 1->{
                    Group group = (Group) dataObject.getData();
                    dao.createNewGroup(group);
                }
                case 2->{
                    Student student = (Student) dataObject.getData();
                    dao.createNewStudent(student);
                }
                case 3->{
                    int studentId = (int) dataObject.getData();
                    dao.deleteStudent(studentId);

                }
                case 4->{
                    List<Student> students = dao.findAllStudents();
                    channel.basicPublish("","client-queue",null,students.toString().getBytes());
                }
            }
        }
        catch (IOException|ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}

