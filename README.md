<a href="https://labstack.com"><img height="80" src="https://cdn.labstack.com/images/labstack-logo.svg"></a>

## Java Client

## Installation

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.labstack:labstack-java:0.8.0'
}
```

## Quick Start

[Sign up](https://labstack.com/signup) to get an API key

Create a file `Main.java` with the following content:

```java
package main;

import com.labstack.Client;
import com.labstack.Jet;
import com.labstack.JetMessage;

public class Main {
    public static void main(String[] args) throws Exception {
        Client client = new Client("ACCOUNT_ID", "<API_KEY>");
        Jet jet = client.Jet();
        JetMessage message = new JetMessage("jack@labstack.com", "LabStack", "Hello");
        message.setBody("Hello");
        message.addAttachment("walle.png");
        message = jet.send(message);
    }
}
```

From IntelliJ run Main.main()

## [Documentation](https://labstack.com/docs) | [Forum](https://forum.labstack.com)
