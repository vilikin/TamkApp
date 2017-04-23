package in.vilik.tamkapp.bottomsheet;

import java.util.List;

/**
 * Created by vili on 23/04/2017.
 */

public class Category {
    private String name;
    private List<Option> options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
