package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import models.Group;
import models.Student;


public class DataAccessObject {
    private static final String URL = "jdbc:mysql://localhost:3306/educational_department";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "SHTRIh2004";

    private final Connection connection;
    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            throw new IllegalStateException("Can not find the driver in the classpath",e);
        }
    }
    public DataAccessObject(){
        this.connection = connectToDatabase();
    }
    public void closeConnection(){
        try{
            this.connection.close();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private static Connection connectToDatabase(){
        try{
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void createNewGroup(Group group){
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `group` (name,studying_program)  VALUES(?,?) ");
            ps.setString(1,group.getName());
            ps.setString(2, group.getStudyingProgram());
            ps.executeUpdate();
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void createNewStudent(Student student){
        if(student.getGroup()==null){
            throw new IllegalArgumentException("Student must have a group");
        }
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO student (first_name,last_name,average_mark,group_id) VALUES (?,?,?,?)");
            ps.setString(1,student.getFirstName());
            ps.setString(2,student.getLastName());
            ps.setDouble(3,student.getAverageMark());
            ps.setInt(4,student.getGroup().getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void deleteGroup(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM `group` WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteStudent(int id){
        try{
            PreparedStatement ps = connection.prepareStatement("DELETE FROM student WHERE id=?");
            ps.setInt(1,id);
            ps.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void updateGroup(Group group){
        try{
            PreparedStatement ps = connection.prepareStatement("UPDATE `group` SET name=?,studying_program=? WHERE id=?");
            ps.setString(1,group.getName());
            ps.setString(2,group.getStudyingProgram());
            ps.setInt(3,group.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void updateStudent(Student student){
        if(student.getGroup()==null){
            throw new IllegalArgumentException("Student must have a group");
        }
        try{
            PreparedStatement ps = connection.prepareStatement("UPDATE student SET first_name=?,last_name=?,average_mark=?,group_id=? WHERE id=?");
            ps.setString(1,student.getFirstName());
            ps.setString(2,student.getLastName());
            ps.setDouble(3,student.getAverageMark());
            ps.setInt(4,student.getGroup().getId());
            ps.setInt(5,student.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Optional<Group> findGroupById(int id){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM `group` WHERE id=?");
            ps.setInt(1,id);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                return Optional.of(new Group(result.getInt("id"),result.getString("name"),result.getString("studying_program")));
            }
            else
                return Optional.empty();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Optional<Student> findStudentById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student WHERE id = ?");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next())
                return Optional.of(new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDouble("average_mark"),
                        findGroupById(resultSet.getInt("group_id")).get())
                );
            else
                return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Group> findAllGroups(){
        try{
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM `group`");
        ResultSet resultSet = ps.executeQuery();

        List<Group> groups = new ArrayList<>();
        while(resultSet.next()){
            groups.add(new Group(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("studying_program")));
        }
        return groups;
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Student> findAllStudentsWithGroupId(int group_id){
        try{
            Optional<Group> groupOptional = findGroupById(group_id);
            if(groupOptional.isEmpty()){
                return new ArrayList<>();
            }
            Group group = groupOptional.get();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student WHERE group_id=?");
            ps.setInt(1,group_id);
            ResultSet resultSet = ps.executeQuery();

            List<Student> students = new ArrayList<>();
            while(resultSet.next()){
                students.add(new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDouble("average_mark"),
                        group
                ));
            }
            return students;
        }
        catch (SQLException |RuntimeException e){
            throw new RuntimeException(e);
        }
    }

}
