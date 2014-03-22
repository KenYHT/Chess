package chess;

/**
 * A Command interface that specifies what methods are needed in order for an object
 * to have a Command behavior.
 * @author Ken Tian, ytian13@illinois.edu
 */

public interface Command {
	public void execute(); // do the command
	public void undo(); // undo the command
}
