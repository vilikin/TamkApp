package in.vilik.tamkapp.menus;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by vili on 10/04/2017.
 */

public class MealTypes {
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
