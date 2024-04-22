package nl.rug.aoop.command;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains methods that are used to handle commands.
 */
@Slf4j
public class CommandHandler {

    @Getter
    private final Map<String, Command> commandMap;

    /**
     * Constructor used to initialize a Command Handler.
     */
    public CommandHandler() {
        this.commandMap = new HashMap<>();
    }

    /**
     * This method is used to add commands to a map.
     *
     * @param name    is the name of the command
     * @param command is the command itself
     */
    public void registerCommand(String name, Command command) {
        commandMap.put(name, command);
    }

    /**
     * This method is used to execute commands.
     *
     * @param command is the name of the command that needs to be found to be executed
     * @param parms   is the list in which the command is searched for and then executed
     */
    public void execute(String command, Map<String, Object> parms) {
        if (commandMap.containsKey(command)) {
            commandMap.get(command).execute(parms);
            return;
        }
        log.error("Command not found");
    }
}