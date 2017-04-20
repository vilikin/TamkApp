package in.vilik.tamkapp.menus.recyclerview;

/**
 * Created by vili on 20/04/2017.
 */

public class Diet {
    private String abbreviation;
    private String name;

    public Diet() {
    }

    public Diet(String abbreviation, String name) {
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
