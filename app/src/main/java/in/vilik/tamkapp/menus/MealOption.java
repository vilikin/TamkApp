package in.vilik.tamkapp.menus;

import java.util.List;

/**
 * Created by vili on 10/04/2017.
 */

public class MealOption implements MenuListItem {
    private String name;
    private String details;

    private List<Diet> diets;

    /*
    private double priceForStudent;
    private double priceForStaff;
    private double priceForOther;
    */

    private boolean showDiets;

    //private boolean showPrices;

    public MealOption(String name) {
        this.name = name;
    }

    public boolean areDietsShown() {
        return showDiets;
    }

    public void setDietsShown(boolean showDiets) {
        this.showDiets = showDiets;
    }

    /*
    public boolean arePricesShown() {
        return showPrices;
    }

    public void setShowPrices(boolean showPrices) {
        this.showPrices = showPrices;
    }
    */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasDetails() {
        return details != null;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Diet> getDiets() {
        return diets;
    }

    public void setDiets(List<Diet> diets) {
        this.diets = diets;
    }

    /*
    public double getPriceForStudent() {
        return priceForStudent;
    }

    public void setPriceForStudent(double priceForStudent) {
        this.priceForStudent = priceForStudent;
    }

    public double getPriceForStaff() {
        return priceForStaff;
    }

    public void setPriceForStaff(double priceForStaff) {
        this.priceForStaff = priceForStaff;
    }

    public double getPriceForOther() {
        return priceForOther;
    }

    public void setPriceForOther(double priceForOther) {
        this.priceForOther = priceForOther;
    }
    */

    private String getDietsFormatted() {
        StringBuilder sb = new StringBuilder();

        for (Diet diet : diets) {
            sb.append(", ").append(diet.getAbbreviation());
        }

        return sb.toString().substring(2);
    }

    @Override
    public String getPrimaryText() {
        if (areDietsShown() && !diets.isEmpty()) {
            return getName() + " (" + getDietsFormatted() + ")";
        } else {
            return getName();
        }
    }

    @Override
    public String getSecondaryText() {
        return getDetails();
    }

    @Override
    public ItemType getItemType() {
        return ItemType.MEAL_OPTION;
    }
}
