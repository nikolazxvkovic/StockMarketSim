# Question 1

Suppose you are developing a similar (if not identical) project for a company. One teammate poses the following:

> "We do not have to worry about logging. The application is very small and tests should take care of any potential bugs. If we really need it, we can print some important data and just comment it out later."

Do you agree or disagree with the proposition? Please elaborate on your reason to agree or disagree. (~50-100 words)

___

**Answer**: We disagree with this statement. Even though the application is small and logging might not seem essential in this state of development, it should still be chosen over print statements as it presents a number of advantages. One of the biggest advantages of logging is the ease with which bugs can be traced to their roots and the flexibility that it offers to a person working on the program, allowing them to chose what to include in the log (such as timestamps). In addition, logging offers multiple leveles of information to be presented, which print statements cannot do.

___

# Question 2

One of your requirements is to create a message class where `key` and `value` are strings. How could you modify your class so that the key and value could be any different data types and do not require casting by the developer? Preferably, provide the code of the modified class in the answer.
___

**Answer**:

```java
class Message <T> {
    private T key, value;
    
    public Message(T key, T value) {
        this.key = key;
        this.value = value;
    }
    
    public T getKey() {
        return key;
    }
    
    public T getValue() {
        return value;
    }
}
```

___

# Question 3

How is Continuous Integration applied to (or enforced on) your assignment? (~30-100 words)

___

**Answer**: Continuous Integration is enforced in our assignment as we need to be able to pass the maven checks. Jenkins is a quality gate in terms of continuous integration. We have to make sure it compiles correctly before uploading. We also need to check if it passes all the code style checks and tests.

___

# Question 4

One of your colleagues wrote the following class:

```java
import java.util.*;

public class MyMenu {

    private Map<Integer, PlayerAction> actions;

    public MyMenu() {
        actions = new HashMap<>();
        actions.put(0, DoNothingAction());
        actions.put(1, LookAroundAction());
        actions.put(2, FightAction());
    }

    public void printMenuOptions(boolean isInCombat) {
        List<String> menuOptions = new ArrayList<>();
        menuOptions.add("What do you want to?");
        menuOptions.add("\t0) Do nothing");
        menuOptions.add("\t1) Look around");
        if(isInCombat) {
            menuOptions.add("\t2) Fight!");
        }
    }

    public void doOption() {
        int option = getNumber();
        if(actions.containsKey(option)) {
            actions.get(option).execute();
        }
    }

    public int getNumber() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
```
List at least 2 things that you would improve, how it relates to test-driven development and why you would improve these things. Provide the improved code below.

___

**Answer**:

In the method doOption() there is nothing that happens if the user inputs a number that is not an option. For example if 3 is inputted the method does nothing. We don't want this to jappen.
Another thing that is wrong is that in the getter for getNumber() A new scanner is initialized everytime the getter is called. The issue with this is that after the first time the program asks for for an input from the user for an option it will cause an error because you can't have more than one Scanner. So we can join doOption and getNumber together.

Improved code:
```java
public void doOption() {
         Scanner scanner = new Scanner(System.in);
        if(actions.containsKey(option)) {
            actions.get(option).execute();
            scanner.nextInt();
        }
        
        else{
            System.out.println("Please type in a number within the range.");
            scan.reset();
            scan.next();
        }
}
           


