import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public  class  DBManager {
    private  String query;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private static DBManager instance = null;
    private String datasource_url = "jdbc:mysql://localhost:3306/food delivery?useUnicode=true&useJDBCCompliant" +
            "TimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";


    private DBManager(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(datasource_url,"root","");
            statement = connection.createStatement();

        }catch (Exception e){
            System.out.println("Error" + e);
        }
    }

    public static DBManager createInstance(){
        if(instance == null)
            instance = new DBManager();

        return instance;
    }

    /**
     * find id of cstomer by email and password
     * @param email
     * @param password
     * @return id
     */
    public int getCSTID(String email, String password){
        String temp_email = "'" + email + "'";
        String temp_pass = "'" + password + "'";
        int id = 0;
        try {
            query = "SELECT cst_id FROM cst WHERE cst_email = " + temp_email  +  "&& cst_password = " + temp_pass;

            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                 id = Integer.parseInt(resultSet.getString("cst_id"));
            }
        }catch (Exception ex) {
            System.out.println(ex);
        }
        return id;
    }

    /**
     * check if email is exsit before at database or not
     * @param email
     * @return true if exist false if not
     */
    public boolean checkEmail(String email){
        String temp = "'"+email+"'";
        try {
            String em = null;
            query = "SELECT cst_email FROM cst WHERE cst_email = " + temp;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                em = resultSet.getString("cst_email");
            }

            if (em != null)
                return true;

        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    /**
     * find customer by id
     * @param id
     * @return
     */
    public ArrayList<String> findCST(int id){
        ArrayList<String> customer = new ArrayList<>();
        try{
            query = "SELECT * FROM cst WHERE cst_id  = " + id ;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String  username = resultSet.getString("cst_username");
                String password = resultSet.getString("cst_password");
                String gender = resultSet.getString("cst_gender");
                String age = resultSet.getString("cst_age");
                String email = resultSet.getString("cst_email");
                String phone_no = resultSet.getString("cst_phone_no");

                customer.add(username);
                customer.add(password);
                customer.add(gender);
                customer.add(age);
                customer.add(email);
                customer.add(phone_no);
            }
        }catch (Exception ex) {
            System.out.println(ex);
        }
        return customer;
    }

    /**
     * add customer to database
     * @param customer
     * @return
     */
    public boolean addCustomer(CST customer){
        String name = "'" + customer.getUsername() + "'";
        String password = "'" + customer.getPassword() + "'";
        String gender = "'" + customer.getGender() + "'";
        int age = customer.getAge();
        String phone = "'" + customer.getPhone_no() + "'";
        String email = "'" + customer.getEmail() + "'";

        int id ;

        try {
            query = "INSERT INTO cst(cst_gender,cst_age,cst_email,cst_phone_no,cst_password,cst_username) VALUES(" +
                    gender + "," + age + "," + email + "," + phone + "," + password + "," + name + ")";
            statement.execute(query);
            return true;

        }catch (Exception ex){
            System.out.println(ex);
        }

        return false;
    }

    public String getItemPrice(String nameٍ){
        String price = null;
        String temp = "'" + nameٍ + "'";
        try{
            query = "SELECT item_price FROM items where item_name =" + temp;
            resultSet = statement.executeQuery(query);
            while (resultSet.next())
                price = resultSet.getString("item_price");
        }catch (Exception e) {
            System.out.println(e);
        }
        return price;
    }

    public String getItemName(int item_code){
        String name = null;
        try{
            query = "SELECT item_name FROM items where item_id = " + item_code;
            resultSet = statement.executeQuery(query);
            while (resultSet.next())
                name = resultSet.getString("item_name");
        }catch (Exception e) {
            System.out.println(e);
        }
        return name;
    }

    /**
     * get restaurant by location
     * @param location
     * @return arraylist of Restaurants object
     */
    public ArrayList<Restaurant> getRestaurants(String location){
        location = location.toUpperCase();
        String temp = "'" + location + "'";
        ArrayList<Restaurant> arrayList = new ArrayList<>();
        try {
            query = "SELECT * FROM restuarants WHERE rest_location = " + temp;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                arrayList.add(new Restaurant(resultSet.getString("rest_name"),
                        resultSet.getString("rest_location"),
                        resultSet.getInt("rest_id")));
            }

        }catch (Exception e){
            System.out.println(e);
        }

        return arrayList;
    }

    public List<Item> getMenu(String restuerant_name)
    {
     List<Item> menu = new ArrayList<>();
     String temp = "'" + restuerant_name + "'";
     try {
         query = "SELECT * FROM items JOIN menu ON item_id = menu_item_id JOIN restuarants ON rest_id = menu_rest_id where rest_name =" + temp;
         resultSet = statement.executeQuery(query);
         while (resultSet.next())
         {
             menu.add(new Item(resultSet.getInt("menu_item_id"),
                     resultSet.getString("item_name"),
                     resultSet.getString("item_price")));
         }
     }catch (Exception e){
         System.out.println(e);
     }
      System.out.println(menu.size());
        return menu;
    }













}
