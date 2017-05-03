package in.vilik.tamkapp.menus;

/**
 * Defines an interface for menu update listeners.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0503
 * @since 1.7
 */
public interface OnMenuUpdatedListener {

    /**
     * Triggers when update is successful.
     */
    void onSuccess();

    /**
     * Triggers when error is thrown while updating menu.
     */
    void onError();
}
