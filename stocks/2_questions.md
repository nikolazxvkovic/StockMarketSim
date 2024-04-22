# Question 1

In the assignment, you had to create a `MessageHandler` interface. Please answer the following two questions:

1. Describe the benefits of using this `MessageHandler` interface. (~50-100 words)
2. Instead of creating an implementation of `MessageHandler` that invokes a command handler, we could also pass the command handler to the client/server directly without the middle man of the `MessageHandler` implementation. What are the implications of this? (~50-100 words)

___

**Answer**: 

1. By creating and using the MessageHandler interface, we keep the code generic and improve its reusability by eliminating the need to rewrite the most important classes in the networking module (such as Server and Client) in case we decide to change the way that a message should be handled. Instead, we would only need to create a new implementation of the MessageHandler and pass it to our program. In addition, testing the classes that use the aforementioned interface is more convenient since we can use Mockito to verify that the stubbed MessageHandler has been called with a specific message as a parameter instead of having to check that the message has been sent correctly to the other end.

2. Passing the command handler directly to the client/server without using the MessageHandler would result in the networking module depending on the command module, creating circular dependency since the message-queue module already depends on the command and networking modules. Therefore, in order to keep one-way module dependency, it is important that we use the Message Handler as a middle man between the command handler and the client/server.
___

# Question 2

One of your colleagues wrote the following class:

```java
public class RookieImplementation {

    private final Car car;

    public RookieImplementation(Car car) {
        this.car = car;
    }

    public void carEventFired(String carEvent) {
        if("steer.left".equals(carEvent)) {
            car.steerLeft();
        } else if("steer.right".equals(carEvent)) {
            car.steerRight();
        } else if("engine.start".equals(carEvent)) {
            car.startEngine();
        } else if("engine.stop".equals(carEvent)) {
            car.stopEngine();
        } else if("pedal.gas".equals(carEvent)) {
            car.accelerate();
        } else if("pedal.brake".equals(carEvent)) {
            car.brake();
        }
    }
}
```

This code makes you angry. Briefly describe why it makes you angry and provide the improved code below.

___

**Answer**: In our opinion, the code shown above is badly written because it lacks readability by cluttering the method (maintainability) and adding/modifying the code in this state can result in the program becoming unmanageable.


Improved code: 

```java
public interface Command {
    void execute();
}

public interface AbstractCommandHandlerFactory {
    CommandHandler create();
}

public abstract class AbstractCarCommand implements Command {
    protected final Car car;

    protected AbstractCarCommand(Car car) {
        this.car = car;
    }
}

public class CarSteerLeftCommand extends AbstractCarCommand {
    public CarSteerLeftCommand(Car car) {
        super(car);
    }

    @Override
    public void execute() {
        car.steerLeft();
    }
}

public class CarSteerRightCommand extends AbstractCarCommand {
    public CarSteerRightCommand(Car car) {
        super(car);
    }

    @Override
    public void execute() {
        car.steerRight();
    }
}

public class CarStartEngineCommand extends AbstractCarCommand {
    public CarStartEngineCommand(Car car) {
        super(car);
    }

    @Override
    public void execute() {
        car.startEngine();
    }
}

public class CarStopEngineCommand extends AbstractCarCommand {
    public CarStopEngineCommand(Car car) {
        super(car);
    }

    @Override
    public void execute() {
        car.stopEngine();
    }
}

public class CarAccelerateCommand extends AbstractCarCommand {
    public CarAccelerateCommand(Car car) {
        super(car);
    }

    @Override
    public void execute() {
        car.accelerate();
    }
}

public class CarBrakeCommand extends AbstractCarCommand {
    public CarBrakeCommand(Car car) {
        super(car);
    }

    @Override
    public void execute() {
        car.brake();
    }
}

@Slf4j
public class CommandHandler {
    private final Map<String, Command> commandMap;

    public CommandHandler() {
        commandMap = new HashMap<>();
    }

    public void registerCommand(String name, Command command) {
        commandMap.put(name, command);
    }

    public void executeCommand(String command) {
        if (commandMap.containsKey(command)) {
            commandMap.get(command).execute();
            return;
        }
        log.error("Command not found");
    }
}

public class CarCommandHandlerFactory implements AbstractCommandHandlerFactory {
    private final Car car;

    public CarCommandHandlerFactory(Car car) {
        this.car = car;
    }

    @Override
    public CommandHandler create() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("steer.left", new CarSteerLeftCommand(car));
        commandHandler.registerCommand("steer.right", new CarSteerRightCommand(car));
        commandHandler.registerCommand("engine.start", new CarStartEngineCommand(car));
        commandHandler.registerCommand("engine.stop", new CarStopEngineCommand(car));
        commandHandler.registerCommand("pedal.gas", new CarAccelerateCommand(car));
        commandHandler.registerCommand("pedal.brake", new CarBrakeCommand(car));
        return commandHandler;
    }
}

public class ImprovedImplementation {

    private final CommandHandler commandHandler;

    public ImprovedImplementation(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public void carEventFired(String carEvent) {
        commandHandler.executeCommand(carEvent);
    }
}
```
___

# Question 3

You have the following exchange with a colleague:

> **Colleague**: "Hey, look at this! It's super handy. Pretty simple to write custom experiments."

```java
class Experiments {
    public static Model runExperimentA(DataTable dt) {
        CommandHandler commandSequence = new CleanDataTableCommand()
            .setNext(new RemoveCorrelatedColumnsCommand())
            .setNext(new TrainSVMCommand())

        Config config = new Options();
        config.set("broadcast", true);
        config.set("svmdatatable", dt);

        commandSequence.handle(config);

        return (Model) config.get("svmmodel");
    }

    public static Model runExperimentB() {
        CommandHandler commandSequence = new CleanDataTableCommand()
            .setNext(new TrainSGDCommand())

        Config config = new Options();
        config.set("broadcast", true);
        config.set("sgddatatable", dt);

        commandSequence.handle(config);

        return (Model) config.get("sgdmodel");
    }
}
```

> **Colleague**: "I could even create this method to train any of the models we have. Do you know how Jane did it?"

```java
class Processor {
    public static Model getModel(String algorithm, DataTable dt) {
        CommandHandler commandSequence = new TrainSVMCommand()
            .setNext(new TrainSDGCommand())
            .setNext(new TrainRFCommand())
            .setNext(new TrainNNCommand())

        Config config = new Options();
        config.set("broadcast", false);
        config.set(algorithm + "datatable", dt);

        commandSequence.handle(config);

        return (Model) config.get(algorithm + "model");
    }
}
```

> **You**: "Sure! She is using the command pattern. Easy indeed."
>
> **Colleague**: "Yeah. But look again. There is more; she uses another pattern on top of it. I wonder how it works."

1. What is this other pattern? What advantage does it provide to the solution? (~50-100 words)

2. You know the code for `CommandHandler` has to be a simple abstract class in this case, probably containing four methods:
- `CommandHandler setNext(CommandHandler next)` (implemented in `CommandHandler`),
- `void handle(Config config)` (implemented in `CommandHandler`),
- `abstract boolean canHandle(Config config)`,
- `abstract void execute(Config config)`.

Please provide a minimum working example of the `CommandHandler` abstract class.

___

**Answer**:

1. We believe that the other pattern is the Template Method. Since we have the two experiements there are 2 implementations Experiment A and B. There is improved cohesion as the variations of the implementations are seperated. It also improves maintainability so we don't have to have duplicate code. The Template method has a template skeleton to follow. When it comes to the 2 experiments we can do mostly the same stuff but slightly change some things to get the desired outcome. The only difference is that they use different data tables.

2.
	```java
	
	public abstract class CommandHandler {
	
	   private final List<Command> list;
	   
	   public CommandHandler(List list) {
	      this.list = new List<>;
	   }
	
	   void handle(Config config){
	      if(canHandle(config) {
	         execute(config);
	      }
	   }
	
	   CommandHandler setNext(CommandHandler next) {
	      list.add(next);      
	   }
	   
	   abstract boolean canHandle(Config config){
	      if(config.get("broadcast")) {
	         return true;
	      }
	      return false;
	   }
	   
	   abstract void execute(Config config){
	      if(list.contains(Command){
	         list.get(Command).execute();
		 return;
	      }
	      System.out.println("No command was added");
	   }
 
	}

	```
___
