import javax.swing.*;

/**
 * 
 */

/**
 * @author Chedli
 *
 */
public interface Game {
	public abstract String getName();
	public abstract Icon getIcon();
	public abstract JPanel play();
	public abstract Boolean isFavorite();
	public abstract boolean IsFinished();
	public abstract long bestFinishTime();
}
