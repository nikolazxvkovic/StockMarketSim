package nl.rug.aoop.command;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCommandHandler {

    @Test
    public void testConstructor() {
        CommandHandler commandHandler = new CommandHandler();
        assertEquals(0, commandHandler.getCommandMap().size());
    }

    @Test
    public void testRegisterOneCommand() {
        CommandHandler commandHandler = new CommandHandler();
        Command mockCommand = Mockito.mock(Command.class);
        commandHandler.registerCommand("MqPut", mockCommand);
        assertTrue(commandHandler.getCommandMap().containsKey("MqPut"));
    }

    @Test
    public void testRegisterMultipleCommands() {
        CommandHandler commandHandler = new CommandHandler();
        Command mockCommand = Mockito.mock(Command.class);
        commandHandler.registerCommand("MqPut", mockCommand);
        assertTrue(commandHandler.getCommandMap().containsKey("MqPut"));
        commandHandler.registerCommand("MqGet", mockCommand);
        assertTrue(commandHandler.getCommandMap().containsKey("MqGet"));
        commandHandler.registerCommand("MqTest", mockCommand);
        assertTrue(commandHandler.getCommandMap().containsKey("MqTest"));
    }

    @Test
    public void testRegisterMultipleCommandsSameKey() {
        CommandHandler commandHandler = new CommandHandler();
        Command mockCommand1 = Mockito.mock(Command.class);
        Command mockCommand2 = Mockito.mock(Command.class);
        commandHandler.registerCommand("MqPut", mockCommand1);
        commandHandler.registerCommand("MqPut", mockCommand2);
        assertEquals(commandHandler.getCommandMap().get("MqPut"), mockCommand2);
    }

    @Test
    public void testExecute() {
        CommandHandler commandHandler = new CommandHandler();
        Command mockCommand = Mockito.mock(Command.class);
        commandHandler.registerCommand("MqPut", mockCommand);
        Map<String, Object> mockMap = Mockito.mock(Map.class);
        commandHandler.execute("MqPut", mockMap);
        Mockito.verify(mockCommand).execute(mockMap);
    }

}
