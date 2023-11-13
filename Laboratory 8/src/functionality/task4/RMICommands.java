package task4;
import models.Group;
import models.Student;
import DB.DataAccessObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
public class RMICommands extends UnicastRemoteObject implements RMICommandsInterface {
    private DataAccessObject dao;
    public RMICommands() throws RemoteException{
    }
    public void createNewGroup(Group group) throws RemoteException{
        dao.createNewGroup(group);
    }
    public void createNewStudent(Student student) throws RemoteException{
        dao.createNewStudent(student);
    }
    public void deleteGroup(int id) throws RemoteException{
        dao.deleteGroup(id);
    }
    public void deleteStudent(int id) throws RemoteException{
        dao.deleteStudent(id);
    }
    public void updateStudentGroup(Student student,int newGroupId) throws RemoteException
    {
        dao.updateStudentGroup(student,newGroupId);
    }
    public void updateStudent(Student student) throws RemoteException{
        dao.updateStudent(student);
    }
    public List<Student> findAllStudents() throws RemoteException{
        return dao.findAllStudents();
    }
    public List<Group> findAllGroups() throws RemoteException{
        return dao.findAllGroups();
    }
    public List<Student> findStudentsWithGroupId(int groupId) throws RemoteException{
        return dao.findAllStudentsWithGroupId(groupId);
    }
    public void connectToDataBase() throws RemoteException{
        dao = new DataAccessObject();
    }
    public void closeConnection() throws RemoteException{
        dao.closeConnection();
    }

}
