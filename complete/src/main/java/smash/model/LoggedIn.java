package smash.model;

/**
 * Created by Lauren on 5/18/2017.
 */
public class LoggedIn {

    private static boolean nowLoggedIn;

    public LoggedIn() {

    }

    public static boolean isNowLoggedIn() {
        return nowLoggedIn;
    }

    public static void setNowLoggedIn(boolean nowLoggedIn) {
        LoggedIn.nowLoggedIn = nowLoggedIn;
    }
}
