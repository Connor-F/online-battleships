import java.awt.Color;

/**
 * Utility class that stores constants needed for reliable transmission of data between
 * the client and the server
 */
public abstract class CommunicationConstants
{
    public static final String MY_TURN = "your_turn";
    public static final String NOT_MY_TURN = "!your_turn";
    public static final Color COLOUR_MY_TURN = Color.GREEN;
    public static final Color COLOUR_NOT_MY_TURN = Color.LIGHT_GRAY;
    public static final String HIT = "hit";
    public static final String MISS = "miss";
    /** used as a marker to indicate either data is ready to be sent or read */
    public static final String DATA_READY = "data_ready";
}
