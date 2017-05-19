package smash.model;

/**
 * Created by Lauren on 5/18/2017.
 */
public class CurrentUser {

    private static int currentUserId;

    public CurrentUser() {

    }

    public void eraseCurrentUserId() {
        CurrentUser.currentUserId = 0;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int currentUserId) {
        CurrentUser.currentUserId = currentUserId;
    }
}
