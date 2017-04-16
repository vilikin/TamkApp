package in.vilik.tamkapp.utils;

import okhttp3.Request;

/**
 * Created by vili on 16/04/2017.
 */

public interface API {
    enum Type {
        TIMETABLE, CAMPUSRAVITA_MENU, PIRTERIA_MENU
    }

    Request getRequest();
    Type getType();
    String getCacheKey();
}
