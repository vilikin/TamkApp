package in.vilik.tamkapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import in.vilik.tamkapp.Debug;
import in.vilik.tamkapp.R;

/**
 * Created by vili on 18/04/2017.
 */

public class AppPreferences {
    private static String key_first_launch;
    private static String key_campusravita_meals;
    private static String key_pirteria_meals;
    private static String key_menu_show_diets;
    private static String key_timetable_student_group;
    private static String key_timetable_period;
    private static String key_timetable_show_weekends;

    private static boolean default_first_launch;
    private static Set<String> default_campusravita_meals;
    private static Set<String> default_pirteria_meals;
    private static boolean default_menu_show_diets;
    private static String default_timetable_student_group;
    private static String default_timetable_period;
    private static boolean default_timetable_show_weekends;

    private static Resources resources;
    private SharedPreferences preferences;

    public AppPreferences(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (AppPreferences.resources == null) {
            AppPreferences.resources = context.getResources();

            key_first_launch = resources
                    .getString(R.string.preference_key_first_launch);

            key_campusravita_meals = resources
                    .getString(R.string.preference_key_campusravita_meals);

            key_pirteria_meals = resources
                    .getString(R.string.preference_key_pirteria_meals);

            key_menu_show_diets = resources
                    .getString(R.string.preference_key_menu_show_diets);

            key_timetable_student_group = resources
                    .getString(R.string.preference_key_timetable_student_group);

            key_timetable_period = resources
                    .getString(R.string.preference_key_timetable_period);

            key_timetable_show_weekends = resources
                    .getString(R.string.preference_key_timetable_show_weekends);

            default_first_launch = true;

            String[] campusravita_meals = resources
                    .getStringArray(R.array.campusravita_meals);

            default_campusravita_meals = new HashSet<>(Arrays.asList(campusravita_meals));

            String[] pirteria_meals = resources
                    .getStringArray(R.array.pirteria_meals);

            default_pirteria_meals = new HashSet<>(Arrays.asList(pirteria_meals));

            default_menu_show_diets = resources
                    .getBoolean(R.bool.preference_default_menu_show_diets);

            default_timetable_student_group = resources
                    .getString(R.string.preference_default_timetable_student_group);

            default_timetable_period = resources
                    .getString(R.string.preference_default_timetable_period);

            default_timetable_show_weekends = resources
                    .getBoolean(R.bool.preference_default_timetable_show_weekends);
        }
    }

    public String getKeyCampusravitaMeals() {
        return key_campusravita_meals;
    }

    public String getKeyPirteriaMeals() {
        return key_pirteria_meals;
    }

    public String getKeyShowDiets() {
        return key_menu_show_diets;
    }

    public String getKeyTimetableStudentGroup() {
        return key_timetable_student_group;
    }

    public String getKeyTimetablePeriod() {
        return key_timetable_period;
    }

    public String getKeyTimetableShowWeekends() {
        return key_timetable_show_weekends;
    }

    public boolean isFirstLaunch() {
        return preferences.getBoolean(key_first_launch, default_first_launch);
    }

    public void setFirstLaunch(boolean isFirstLaunch) {
        preferences.edit().putBoolean(key_first_launch, isFirstLaunch).apply();
    }

    public Set<String> getCampusravitaMeals() {
        return preferences.getStringSet(key_campusravita_meals, default_campusravita_meals);
    }

    public Set<String> getPirteriaMeals() {
        return preferences.getStringSet(key_pirteria_meals, default_pirteria_meals);
    }

    public boolean areDietsShown() {
        return preferences.getBoolean(key_menu_show_diets, default_menu_show_diets);
    }

    public String getTimetableStudentGroup() {
        return preferences.getString(key_timetable_student_group, default_timetable_student_group);
    }

    public String getTimetablePeriod() {
        return preferences.getString(key_timetable_period, default_timetable_period);
    }

    public boolean areWeekendsShown() {
        return preferences.getBoolean(key_timetable_show_weekends, default_timetable_show_weekends);
    }
}
