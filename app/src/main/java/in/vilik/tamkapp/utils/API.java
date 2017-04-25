package in.vilik.tamkapp.utils;

import okhttp3.Request;

/**
 * Defines an interface for all APIs.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0425
 * @since 1.7
 */
public interface API {

    /**
     * Unique type of the API.
     */
    enum Type {
        TIMETABLE, CAMPUSRAVITA_MENU, PIRTERIA_MENU
    }

    /**
     * Gets okhttp request object to call the API with.
     *
     * @return Request to call the API with it
     */
    Request getRequest();

    /**
     * Gets type of the API.
     *
     * @return Type of the API
     */
    Type getType();

    /**
     * Gets cache key of the API.
     *
     * Cache key is used by DataCache so it knows which file to use
     * when caching responses from this API.
     *
     * @return Cache key of the API
     */
    String getCacheKey();
}
