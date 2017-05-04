package in.vilik.tamkapp.bottomsheet;

import java.util.List;

/**
 * Implements a category of options for a bottom sheet.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0504
 * @since 1.7
 */
public class Category {

    /**
     * Name of the category.
     */
    private String name;

    /**
     * List of all options in the category.
     */
    private List<Option> options;

    /**
     * Gets name of the category.
     *
     * @return Name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name for the category.
     *
     * @param name Name for the category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets list of all options in the category.
     *
     * @return List of all options in the category
     */
    public List<Option> getOptions() {
        return options;
    }

    /**
     * Sets list of all options in the category.
     *
     * @param options List of all options for the category
     */
    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
