package chess;

/**
 * A CommandManager class that keeps track of previous commands. Code taken from
 * http://gamedevelopment.tutsplus.com/tutorials/let-your-players-undo-their-in-game-mistakes-with-the-command-pattern--gamedev-1391
 * @author Andrew Arnott
 */

public class CommandManager {
	private Command lastCommand; // the previous command executed
	
	public CommandManager() {} // empty constructor
	
	/**
	 * Executes the given command and stores it as our previous command.
	 * @param c, the command to execute and store
	 */
	public void executeCommand(Command c) {
		c.execute();
		lastCommand = c;
	}
	
	/**
	 * Checks if there is a previous command to undo.
	 * @return true if lastCommand exists, false otherwise
	 */
	public boolean isUndoAvailable() {
		return lastCommand != null;
	}
	
	/**
	 * Undoes the previous command, and sets the previous command to null.
	 */
	public void undo() {
		if (lastCommand != null) {
			lastCommand.undo();
			lastCommand = null;
		}
	}
}
