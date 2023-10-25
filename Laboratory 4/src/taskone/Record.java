package taskone;
public class Record {
    public Record(String first,String middle,String last,String phone){
        this.firstName = first;
        this.middleName = middle;
        this.lastName = last;
        this.phoneNumber = phone;
    }
    private final String firstName,middleName,lastName,phoneNumber;
    public String toString(){
        return String.format("%s %s %s %s\n",firstName,middleName,lastName,phoneNumber);
    }
    public static Record fromString(String record_string){
        String[] parts = record_string.split(" ");
        try{
        return new Record(parts[0],parts[1],parts[2],parts[3]);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
            return new Record("","","","");
        }

    }
    public String getFullName(){
        return String.format("%s %s %s",firstName,middleName,lastName);
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public String getLastName(){
        return this.lastName;
    }


}
