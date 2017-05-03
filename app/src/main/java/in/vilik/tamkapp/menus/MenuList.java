package in.vilik.tamkapp.menus;

import android.content.Context;

import java.util.ArrayList;

import in.vilik.tamkapp.utils.API;
import in.vilik.tamkapp.utils.DataLoader;
import in.vilik.tamkapp.utils.OnDataLoadedListener;

/**
 * Implements an abstract class representing a list of menus.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0503
 * @since 1.7
 */
public abstract class MenuList extends DataLoader {

    /**
     * List of all menus.
     */
    private ArrayList<Menu> menus;

    /**
     * Context.
     */
    private Context context;

    /**
     * API Type.
     */
    private API.Type apiType;

    /**
     * Listener for menu updates.
     */
    private OnMenuUpdatedListener listener;

    /**
     * Initializes MenuList with context and API details.
     *
     * @param context   Context
     * @param api       API to use for fetching menu
     */
    public MenuList(Context context, API api) {
        super(context, api);

        this.apiType = api.getType();
        this.context = context;
        this.menus = new ArrayList<>();

        setOnDataLoadedListener(new OnDataLoadedListener() {
            @Override
            public void onSuccess(String data) {
                boolean parsedSuccessfully = handleSuccessfulResponse(data);
                triggerOnMenuUpdatedListener(parsedSuccessfully);
            }

            @Override
            public void onFailure() {
                triggerOnMenuUpdatedListener(false);
            }
        });
    }

    /**
     * Triggers OnMenuUpdatedListener based on whether update succeeded or not.
     *
     * @param success   If update succeeded or not
     */
    private void triggerOnMenuUpdatedListener(boolean success) {
        if (success && listener != null) {
            listener.onSuccess();
        } else if (listener != null) {
            listener.onError();
        }
    }

    /**
     * Sets OnMenuUpdatedListener for the MenuList.
     *
     * @param listener  OnMenuUpdatedListener for the MenuList
     */
    public void setOnMenuUpdatedListener(OnMenuUpdatedListener listener) {
        this.listener = listener;
    }

    /**
     * Removes OnMenuUpdatedListener from the MenuList.
     */
    public void removeOnMenuUpdatedListener() {
        this.listener = null;
    }

    /**
     * Handles successful responses from the server.
     *
     * This method should be implemented by all menu lists to parse the server response
     * and refresh the list of menus.
     *
     * @param response  Response body
     * @return          If parsing succeeded or not
     */
    abstract boolean handleSuccessfulResponse(String response);

    /**
     * Gets context.
     *
     * @return Context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Sets context.
     *
     * @param context Context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Gets the list of menus.
     *
     * @return List of menus
     */
    public ArrayList<Menu> getMenus() {
        return menus;
    }

    /**
     * Gets the API type of the MenuList.
     *
     * @return  API Type of the MenuList
     */
    public API.Type getApiType() {
        return apiType;
    }
}
