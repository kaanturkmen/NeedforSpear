package tr.edu.ku.devnull.needforspear.View.PlayViews;

/**
 * Observer pattern on Switch to Running Mode button
 * where the subscriber is the GamePanel to animate obstacles
 * and sphere depending on if the game is in running or build mode
 *
 * @author Melis Oktayoğlu
 */
public interface SwitchModeSubscriber {
    void update();
}
