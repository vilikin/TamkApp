package in.vilik.tamkapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import in.vilik.tamkapp.R;

/**
 * Handles all preferences of the app.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0425
 * @since 1.7
 */
public class AppPreferences {

    /**
     * Preference key: is first launch.
     */
    private static String key_first_launch;

    /**
     * Preference key: campusravita meals to display.
     */
    private static String key_campusravita_meals;

    /**
     * Preference key: pirteria meals to display.
     */
    private static String key_pirteria_meals;

    /**
     * Preference key: if diets should be shown in menus.
     */
    private static String key_menu_show_diets;

    /**
     * Preference key: student group for timetable.
     */
    private static String key_timetable_student_group;

    /**
     * Preference key: which period to show in the timetable.
     */
    private static String key_timetable_period;

    /**
     * Preference key: if weekends should be visible in the timetable.
     */
    private static String key_timetable_show_weekends;

    /**
     * Preference key: if pirteria menu should be visible when locale is not Finnish.
     */
    private static String key_show_pirteria_on_english_locale;

    /**
     * Preference default value for key: first_launch.
     */
    private static boolean default_first_launch;

    /**
     * Preference default value for key: campusravita_meals.
     */
    private static Set<String> default_campusravita_meals;

    /**
     * Preference default value for key: pirteria_meals.
     */
    private static Set<String> default_pirteria_meals;

    /**
     * Preference default value for key: menu_show_diets.
     */
    private static boolean default_menu_show_diets;

    /**
     * Preference default value for key: timetable_student_group.
     */
    private static String default_timetable_student_group;

    /**
     * Preference default value for key: timetable_period.
     */
    private static String default_timetable_period;

    /**
     * Preference default value for key: timetable_show_weekends.
     */
    private static boolean default_timetable_show_weekends;

    /**
     * Preference default value for key: show_pirteria_on_english_locale.
     */
    private static boolean default_show_pirteria_on_english_locale;

    /**
     * Resources for all localized strings, used to get preference keys and defaults.
     */
    private static Resources resources;

    /**
     * Shared preferences to store and get preferences from.
     */
    private SharedPreferences preferences;

    /**
     * Initializes all static variables if necessary.
     *
     * @param context Context for preferences and resources.
     */
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

            key_show_pirteria_on_english_locale = resources
                    .getString(R.string.preference_key_show_pirteria_on_english_locale);

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

            default_show_pirteria_on_english_locale = resources
                    .getBoolean(R.bool.preference_default_show_pirteria_on_english_locale);
        }
    }

    /**
     * Gets preference key for campusravita_meals.
     *
     * @return Preference key for campusravita_meals.
     */
    public String getKeyCampusravitaMeals() {
        return key_campusravita_meals;
    }

    /**
     * Gets preference key for pirteria meals.
     *
     * @return Preference key for pirteria meals.
     */
    public String getKeyPirteriaMeals() {
        return key_pirteria_meals;
    }

    /**
     * Gets preference key for menu_show_diets.
     *
     * @return Preference key for menu_show_diets.
     */
    public String getKeyShowDiets() {
        return key_menu_show_diets;
    }

    /**
     * Gets preference key for timetable_student_group.
     *
     * @return Preference key for timetable_student_group.
     */
    public String getKeyTimetableStudentGroup() {
        return key_timetable_student_group;
    }

    /**
     * Gets preference key for timetable_period.
     *
     * @return Preference key for timetable_period.
     */
    public String getKeyTimetablePeriod() {
        return key_timetable_period;
    }

    /**
     * Gets preference key for timetable_show_weekends.
     *
     * @return Preference key for timetable_show_weekends.
     */
    public String getKeyTimetableShowWeekends() {
        return key_timetable_show_weekends;
    }

    /**
     * Gets preference key for show_pirteria_on_english_locale.
     *
     * @return Preference key for show_pirteria_on_english_locale.
     */
    public String getKeyShowPirteriaOnEnglishLocale() {
        return key_show_pirteria_on_english_locale;
    }

    /**
     * Gets value of preference first_launch.
     *
     * @return Value of preference first_launch
     */
    public boolean isFirstLaunch() {
        return preferences.getBoolean(key_first_launch, default_first_launch);
    }

    /**
     * Sets value of preference first_launch.
     *
     * @param isFirstLaunch Is first launch
     */
    public void setFirstLaunch(boolean isFirstLaunch) {
        preferences.edit().putBoolean(key_first_launch, isFirstLaunch).apply();
    }

    /**
     * Gets value of preference campusravita_meals.
     *
     * @return List of campusravita meal types to display in menu.
     */
    public Set<String> getCampusravitaMeals() {
        return preferences.getStringSet(key_campusravita_meals, default_campusravita_meals);
    }

    /**
     * Gets value of preference pirteria_meals.
     *
     * @return List of pirteria meal types to display in menu.
     */
    public Set<String> getPirteriaMeals() {
        return preferences.getStringSet(key_pirteria_meals, default_pirteria_meals);
    }

    /**
     * Gets value of preference menu_show_diets.
     *
     * @return If diets should be shown in the menu or not.
     */
    public boolean areDietsShown() {
        return preferences.getBoolean(key_menu_show_diets, default_menu_show_diets);
    }

    /**
     * Gets value of preference timetable_student_group.
     *
     * @return Which student group is selected.
     */
    public String getTimetableStudentGroup() {
        return preferences.getString(key_timetable_student_group, default_timetable_student_group);
    }

    /**
     * Gets value of preference timetable_period.
     *
     * @return Which period of days to show in the timetable.
     */
    public String getTimetablePeriod() {
        return preferences.getString(key_timetable_period, default_timetable_period);
    }

    /**
     * Gets value of preference show_weekends.
     *
     * @return If weekends should be visible in the timetable or not.
     */
    public boolean areWeekendsShown() {
        return preferences.getBoolean(key_timetable_show_weekends, default_timetable_show_weekends);
    }

    /**
     * Gets value of preference show_pirteria_on_english_locale.
     *
     * @return Value of preference show_pirteria_on_english_locale.
     */
    public boolean isPirteriaMenuShownOnEnglishLocale() {
        return preferences.getBoolean(key_show_pirteria_on_english_locale, default_show_pirteria_on_english_locale);
    }

    /**
     * Sets value of preference show_pirteria_on_english_locale.
     *
     * @param isShown If pirteria menu should be shown for users with English locale.
     */
    public void setPirteriaMenuShownOnEnglishLocale(boolean isShown) {
        preferences.edit().putBoolean(key_show_pirteria_on_english_locale, isShown).apply();
    }
}
