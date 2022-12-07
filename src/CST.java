import java.lang.reflect.Array;
import java.util.ArrayList;

public class CST  { //Customer
    private String username;
    private String password;
    private String email;
    private String phone_no;
    private String gender;
    private int age;
    private int ID;


    private DBManager dbManager = DBManager.createInstance();

    public  CST(){}

    private CST(String username, String password, String email, String phone_no, String gender, int age) {

        passwordValidation(username,password).equals("Password is valid.");
    }

    public boolean logIn(String email, String password){
        int id = dbManager.getCSTID(email,password);
        if(id != 0){
            ArrayList<String> object = dbManager.findCST(id);
            this.username = object.get(0);
            this.password = object.get(1);
            this.gender = object.get(2);
            this.age = Integer.parseInt(object.get(3));
            this.email = object.get(4);
            this.phone_no = object.get(5);
            this.ID = id;

            return true;
        }
        return false;
    }


    /**
     * @param username
     * @param password
     * @param email
     * @param phone_no
     * @param gender
     * @param age
     * @return
     */
    public String register(String username, String password, String email, String phone_no, String gender, int age){
        String validation = chechValidatetion(username,password,email,phone_no);

        if(!validation.equals("validate"))
            return validation;

        this.username = username;
        this.password = password;
        this.email = email;
        this.phone_no = phone_no;
        this.gender = gender;
        this.age = age;

        if(dbManager.addCustomer(this)) {
            this.ID = dbManager.getCSTID(email,password);
            return "true";
        }
        else
            return "false";
    }


    private String passwordValidation(String userName, String password)
    {
        if (password.length() < 8) {
            return "Password should be more than 8 characters in length.";
        }
        if (password.indexOf(userName) > -1) {
            return "Password Should not be same as user name";
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars )) {
            return "Password should contain atleast one upper case alphabet";
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars )) {
            return "Password should contain atleast one lower case alphabet";
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers )) {
            return "Password should contain atleast one number.";
        }
        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        if (!password.matches(specialChars )) {
            return "Password should contain atleast one special character";
        }

        this.password = password;
        return "Password is valid.";
    }


    /**
     *
     * @param username
     * @param password
     * @param email
     * @param phone_no
     * @return
     */
    private String chechValidatetion(String username, String password, String email, String phone_no){
        String passwordvalidation = passwordValidation(username,password);

        if (dbManager.checkEmail(email)){
            return "Email is exsit befor, please use anthor email ";
        }

        if(!passwordvalidation.equals("Password is valid.")){
            return passwordvalidation;
        }


        if (phone_no.length() != 11) {
            return "Please enter validate phone number";
        }

        return "validate";
    }

    public ArrayList<Restaurant> getRestuerantsByLocation(String location)
    {
        return dbManager.getRestaurants(location);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public int getID() {
        return ID;
    }
}
