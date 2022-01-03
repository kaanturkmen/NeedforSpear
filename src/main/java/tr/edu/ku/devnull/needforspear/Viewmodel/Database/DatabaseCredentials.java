package tr.edu.ku.devnull.needforspear.Viewmodel.Database;

/**
 * DatabaseCredentials is created to store information for accessing database to authenticate, load and save operations.
 */
public interface DatabaseCredentials {
    int DATABASE_UNKNOWN = 0;
    int DATABASE_FAIL = -1;
    int DATABASE_SUCCESS = 1;

    String AUTH_FILE_PATH = "INSERT_AUTH_FILE_PATH_HERE";
    String DATABASE_URL = "INSERT_DATABASE_URL_HERE";
    String DATABASE_USERS_PATH = "USERS_PATH_HERE";
    String DATABASE_USERS_CHILD_PATH = "CHILD_PATH_HERE";
    String DATABASE_MAPS_PATH = "MAPS_PATH_HERE";
    String DATABASE_MAPS_CHILD_PATH = "CHILD_PATH_HERE";
}
