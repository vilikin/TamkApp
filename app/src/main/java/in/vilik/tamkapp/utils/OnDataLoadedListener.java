package in.vilik.tamkapp.utils;

/**
 * Implements an interface for listening events of DataLoader.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public interface OnDataLoadedListener {

    /**
     * Triggers when data was loaded successfully.
     *
     * @param data Response string
     */
    void onSuccess(String data);

    /**
     * Triggers when data could not be loaded.
     */
    void onFailure();
}
