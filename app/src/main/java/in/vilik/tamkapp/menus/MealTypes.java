package in.vilik.tamkapp.menus;

import android.content.Context;
import android.content.res.Resources;

/**
 * Implements utilities to determine meal types.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0503
 * @since 1.7
 */
public class MealTypes {

    /**
     * Gets meal type for given JSON representation of a meal type.
     *
     * @param context           Context
     * @param mealTypesJsonId   ID of string array resource for JSON representations of meal types
     * @param mealTypesId       ID of string array resource for meal types
     * @param json              JSON representation of a meal type
     * @return                  Meal type for the jSON representation of meal type
     */
    public static String getMealTypeForJson(Context context, int mealTypesJsonId, int mealTypesId, String json) {
        Resources res = context.getResources();
        String[] mealTypesJson = res.getStringArray(mealTypesJsonId);
        String[] mealTypes = res.getStringArray(mealTypesId);

        for (int i = 0; i < mealTypesJson.length; i++) {
            if (mealTypesJson[i].equals(json)) {
                return mealTypes[i];
            }
        }

        return "unknown";
    }

    /**
     * Gets readable meal type for given meal type.
     *
     * @param context               Context
     * @param mealTypesId           ID of string array resource for meal types
     * @param mealTypesReadableId   ID of string array resource for readable meal types
     * @param mealType              Meal type
     * @return                      Readable meal type for given meal type
     */
    public static String getReadableMealType(Context context, int mealTypesId, int mealTypesReadableId, String mealType) {
        Resources res = context.getResources();
        String[] mealTypes = res.getStringArray(mealTypesId);
        String[] mealTypesReadable = res.getStringArray(mealTypesReadableId);

        for (int i = 0; i < mealTypes.length; i++) {
            if (mealTypes[i].equals(mealType)) {
                return mealTypesReadable[i];
            }
        }

        return "Unknown meal";
    }
}
