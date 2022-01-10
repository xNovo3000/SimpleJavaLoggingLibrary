# SimpleJavaLoggingLibrary

After the Log4Shell exploit I decided to create my own simple but
powerful logging library.<br>
This library has no fancy lookups, no network configurations, nothing
that a logging library shouldn't take care of.

---

## Getting started

Write this code in your application main class...

```java
import io.github.xnovo3000.sjll.logger.Logger;

public class MyApplicationMainClass {
	
    public static final Logger LOGGER = Logger.getLogger("Default");
	
}
```

...and you are ready to go.

---

## Configuration files

SJLL provides a default configuration file
in <code>resources/SJLL.json</code> that prints per default to the
console. You can override this configuration by creating the same
file in the <code>resources</code> folder of your application.<br>
Here the default configuration file:

```json
{
  "loggers": [
    {
      "name": "Default",
      "targets": ["Default"]
    }
  ],
  "targets": [
    {
      "name": "Default",
      "type": "console",
      "format": "%d [%l] %c: %m\n"
    }
  ]
}
```

### Loggers

A logger contains only two parameters:
- <code>name</code>: used to identify a logger
- <code>targets</code>: used to get all the targets with the same name

A logger is responsible to pass all the log messages to the targets.

### Targets

A target contains these parameters:
- <code>name</code>: used to identify a target
- <code>type</code>: the type of the target. Can be:
  - <code>console</code>: prints to the console
  - <code>file</code>: prints to a file. <code>filename</code> required
- <code>filename</code> (optional): the location of the output file
- <code>format</code>: how the output message is formatted

#### Formats:
- <code>%d</code>: Current date and time
- <code>%l</code>: Importance level (single character)
- <code>%L</code>: Importance level (entire string)
- <code>%t</code>: Name of the thread that generated this log message
- <code>%c</code>: Caller part of the message (specified in Logger)
- <code>%m</code>: Message part of the message (specified in Logger)
- Other strings: These are static strings

Don't forget to add <code>\n</code> at the end to go to a new line