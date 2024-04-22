package nl.rug.aoop.command;

import java.util.Map;

/**
 * An interface that a class can implement so that it can execute commands.
 */
public interface Command {
    /**
     * This method is used to execute commands.
     *
     * @param options is a map containing messages whose headers are taken as commands
     */
    void execute(Map<String, Object> options);
}
