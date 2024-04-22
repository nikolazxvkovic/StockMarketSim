module messagequeue {
    // Needed for gson to work. If your message queue resides in a sub-package,
    // be sure to open this to com.google.gson as well.
    // If you want to allow this module to be used in other modules, uncomment the following line:
    //    exports nl.rug.aoop.command;
    // Note that this will not include any sub-level packages. If you want to export more, then add those as well:
    //    exports nl.rug.aoop.command.example;
    opens nl.rug.aoop.messagequeue.messageClasses.messageHandlers to com.google.gson;
    opens nl.rug.aoop.messagequeue.messageClasses.message to com.google.gson;
    requires static lombok;
    requires org.slf4j;
    requires org.mockito;
    requires com.google.gson;
    requires networking;
    requires command;
    exports nl.rug.aoop.messagequeue.messageInterfaces;
    exports nl.rug.aoop.messagequeue.messageClasses.queues;
    exports nl.rug.aoop.messagequeue.messageClasses.messageHandlers;
    exports nl.rug.aoop.messagequeue.messageClasses.consumers;
    exports nl.rug.aoop.messagequeue.messageClasses.producers;
    exports nl.rug.aoop.messagequeue.messageClasses.message;
    exports nl.rug.aoop.messagequeue.messageClasses.factories;
}