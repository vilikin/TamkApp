package in.vilik.tamkapp.menus;

/**
 * Implements a class representing a diet.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0503
 * @since 1.7
 */
public class Diet {

    /**
     * Abbreviation of a diet.
     */
    private String abbreviation;

    /**
     * Name of a diet.
     */
    private String name;

    /**
     * Constructs diet with null values.
     */
    public Diet() {
    }

    /**
     * Constructs diet with all necessary values.
     *
     * @param abbreviation  Abbreviation for the diet
     * @param name          Name for the diet
     */
    public Diet(String abbreviation, String name) {
        this.abbreviation = abbreviation;
        this.name = name;
    }

    /**
     * Gets abbreviation of the diet.
     *
     * @return Abbreviation of the diet
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Sets abbreviation for the diet.
     *
     * @param abbreviation Abbreviation for the diet
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * Gets name of the diet.
     *
     * @return Name of the diet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name for the diet.
     *
     * @param name Name for the diet
     */
    public void setName(String name) {
        this.name = name;
    }
}
