package XML;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;



import models.Group;
import models.Student;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class EducationalDepartment {
    private final String ROOT_TAG = "educationaldepartment";
    private final String GROUP_TAG = "group";
    private final String GROUP_ID_ATTRIBUTE = "id";
    private final String GROUP_NAME_ATTRIBUTE = "name";
    private final String GROUP_STUDYING_PROGRAM_ATTRIBUTE = "studying_program";
    private final String STUDENT_TAG = "student";
    private final String STUDENT_ID_ATTRIBUTE = "id";
    private final String STUDENT_FNAME_ATTRIBUTE = "first_name";
    private final String STUDENT_LNAME_ATTRIBUTE = "last_name";
    private final String STUDENT_AVERAGE_MARK_ATTRIBUTE = "averageMark";

    private final ArrayList<Group> groups = new ArrayList<>();
    private final ArrayList<Student> students = new ArrayList<>();

    public void saveToFile(String filename) throws ParserConfigurationException, TransformerException{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element root = doc.createElement(ROOT_TAG);
        doc.appendChild(root);

        for (Group group:groups){
            Element groupElem = doc.createElement(GROUP_TAG);
            groupElem.setAttribute(GROUP_ID_ATTRIBUTE,String.valueOf(group.getId()));
            groupElem.setAttribute(GROUP_NAME_ATTRIBUTE,group.getName());
            if (!Objects.equals(group.getStudyingProgram(), "")){
                groupElem.setAttribute(GROUP_STUDYING_PROGRAM_ATTRIBUTE,group.getStudyingProgram());
            }
            root.appendChild(groupElem);

            for(Student student:students){
                if(Objects.equals(student.getGroup().getId(), group.getId())){
                    Element studentElem = doc.createElement(STUDENT_TAG);
                    studentElem.setAttribute(STUDENT_ID_ATTRIBUTE,String.valueOf(student.getId()));
                    studentElem.setAttribute(STUDENT_FNAME_ATTRIBUTE,student.getFirstName());
                    studentElem.setAttribute(STUDENT_LNAME_ATTRIBUTE,student.getLastName());
                    studentElem.setAttribute(STUDENT_AVERAGE_MARK_ATTRIBUTE,String.valueOf(student.getAverageMark()));
                    groupElem.appendChild(studentElem);
                }
            }
        }

        try(FileOutputStream output = new FileOutputStream(filename)){
            writeXml(doc,output);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void writeXml(Document doc, OutputStream output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
        transformer.setOutputProperty(OutputKeys.ENCODING,"WINDOWS-1251");
        transformer.transform(source,result);
    }
    public void loadFromFile(String filename,String schema){
        DocumentBuilderFactory dbf;
        DocumentBuilder db;

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema s;
        try{
            s = sf.newSchema(new File(schema));
        }
        catch (SAXException e){
            throw new RuntimeException(e);
        }
        try{
            dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            dbf.setSchema(s);
            db = dbf.newDocumentBuilder();
            db.setErrorHandler(new SimpleErrorHandler());
        }
        catch (ParserConfigurationException e){
            throw new RuntimeException(e);
        }

        Document doc;

        try{
            doc = db.parse(new File(filename));
        }
        catch (SAXException|IOException e){
            throw new RuntimeException(e);
        }

        Element root = doc.getDocumentElement();

        if(root.getTagName().equals(ROOT_TAG)){
            NodeList listGroups = root.getElementsByTagName(GROUP_TAG);
            for(int i=0;i<listGroups.getLength();i++){
                Element groupElement = (Element) listGroups.item(i);
                int groupID = Integer.parseInt(groupElement.getAttribute(GROUP_ID_ATTRIBUTE));
                String groupName = groupElement.getAttribute(GROUP_NAME_ATTRIBUTE);
                String group_studying_program = groupElement.getAttribute(GROUP_STUDYING_PROGRAM_ATTRIBUTE);

                Group group = new Group(groupID,groupName,group_studying_program);
                groups.add(group);

                NodeList listStudents = groupElement.getElementsByTagName(STUDENT_TAG);

                for(int j=0;j<listStudents.getLength();j++){
                    Element studentElement = (Element) listStudents.item(j);
                    int studentID = Integer.parseInt(studentElement.getAttribute(STUDENT_ID_ATTRIBUTE));
                    String studentFirstName = studentElement.getAttribute(STUDENT_FNAME_ATTRIBUTE);
                    String studentLastName = studentElement.getAttribute(STUDENT_LNAME_ATTRIBUTE);
                    double studentAverageMark = Double.parseDouble(studentElement.getAttribute(STUDENT_AVERAGE_MARK_ATTRIBUTE));

                    students.add(new Student(studentID,studentFirstName,studentLastName,studentAverageMark,group));


                }
            }
        }

    }
    public void addGroup(int groupId,String groupName,String groupStudyingProgram){
        boolean isIdPresent = groups.stream().anyMatch(group -> group.getId()==groupId);
        if(isIdPresent) {
            System.out.println("Entered ID is already present in groups list");
            return;
        }
        groups.add(new Group(groupId,groupName,groupStudyingProgram));
    }
    public Group getGroup(int groupId) {
        try {
            return groups.stream().filter(group -> group.getId() == groupId).findFirst().orElseThrow(() -> new NoSuchElementException("No group found with ID:" + groupId));
        }
        catch (NoSuchElementException e){
            System.out.println("No group found with ID:" + groupId);
            return null;
        }
    }
    public void updateGroup(int groupId,String name,String studying_program){

        if (groups.stream().anyMatch(group->group.getId()==groupId)){
            for(Group group:groups){
                if(group.getId()==groupId){
                    if(!Objects.equals(name, " "))
                        group.setName(name);
                    if(!Objects.equals(studying_program,""))
                        group.setStudyingProgram(studying_program);
                }
            }
        }
        else{
            throw new NoSuchElementException("There is no such a group with a given ID to update");
        }

    }
    public void deleteGroup(int groupId){
        try {
            students.removeIf(student -> student.getGroup().getId() == groupId);
        }
        catch (NullPointerException e){
            System.out.println("There are no students for this group to delete");
        }
        try{
            groups.removeIf(group -> group.getId() == groupId);
        }
        catch (NullPointerException e){
            throw new NoSuchElementException("No such a group with this id");
        }
    }


    public int countGroups(){
        return groups.size();
    }
    public int countStudentsInGroup(int groupId){
        return (int)students.stream().filter(student -> student.getGroup().getId()==groupId).count();
    }
    public int countStudents() {
        return students.size();
    }

    public void addStudent(int studentId,String first_name,String last_name,double average_mark,int groupId){
        if (students.stream().anyMatch(student->student.getId()==studentId)) {
            System.out.println("Student with entered id already exists");
            return;
        }
        if(groups.stream().filter(group->group.getId()==groupId).findFirst().orElse(null)==null) {
            System.out.println("There is no such a group with entered id");
            return;
        }
        students.add(new Student(studentId,first_name,last_name,average_mark,groups.stream().filter(group->group.getId()==groupId).findFirst().orElse(null)));
    }
    public Student getStudent(int studentId){
        try {
            return students.stream().filter(student -> student.getId() == studentId).findFirst().orElseThrow(() -> new NoSuchElementException("No student found with ID:" + studentId));

        }
        catch (NoSuchElementException e){
            System.out.println("No student found with ID:" + studentId);
            return null;
        }
    }

    public void deleteStudent(int studentId){
        try{
            students.removeIf(student->student.getId()==studentId);
        }
        catch (NullPointerException e){
            throw new NoSuchElementException("There is no student with ID:"+studentId);
        }
    }
    public void print(){
        for(Group group:groups){
            System.out.println(" - "+group);
            for(Student student: students){
                if(Objects.equals(student.getGroup().getId(), group.getId())){
                    System.out.println(" - - "+student);
                }
            }
        }
        System.out.println();
    }
    public void clearDepartment(){
        this.students.clear();
        this.groups.clear();
    }

    static class SimpleErrorHandler implements ErrorHandler{
        @Override
        public void warning(SAXParseException e) {
            System.out.println("Warning in line number "+e.getLineNumber()+":"+e.getMessage());
        }
        @Override
        public void error(SAXParseException e) throws SAXException{
            System.out.println("Error in line number "+e.getLineNumber()+":"+e.getMessage());
            throw new SAXException("Error in xml file due to the schema");
        }
        @Override
        public void fatalError(SAXParseException e) throws SAXException{
            System.out.println("Fatal error in line number "+e.getLineNumber()+":"+e.getMessage());
            throw new SAXException("Fatal error in xml file due to the schema");
        }
    }

}
