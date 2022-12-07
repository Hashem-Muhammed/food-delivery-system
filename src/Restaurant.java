import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    public String name;
    public String location;
    private int id;
    DBManager dbManager = DBManager.createInstance();
    
    public Restaurant(){} 

    public Restaurant(String name, String location, int id) {
        this.name = name;
        this.location = location.toUpperCase();
        this.id = id;
    }

    public List<Item> getMenu(String nameٍ)
    {
        return  dbManager.getMenu(name);

    }


    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }
}
