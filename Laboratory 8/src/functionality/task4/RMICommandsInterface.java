package task4;
import models.Group;
import models.Student;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface RMICommandsInterface extends Remote {
    void createNewGroup(Group group) throws RemoteException;
    void createNewStudent(Student student) throws RemoteException;
    void deleteGroup(int id) throws RemoteException;
    void deleteStudent(int id) throws RemoteException;
    void updateStudentGroup(Student student,int newGroupId) throws RemoteException;
    void updateStudent(Student student) throws RemoteException;
    List<Student> findAllStudents() throws RemoteException;
    List<Group> findAllGroups() throws RemoteException;
    List<Student> findStudentsWithGroupId(int groupId) throws RemoteException;
    void connectToDataBase() throws RemoteException;
    void closeConnection() throws RemoteException;
}
