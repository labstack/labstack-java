<a href="https://labstack.com"><img height="80" src="https://cdn.labstack.com/images/labstack-logo.svg"></a>

## Java Client

## Installation

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.labstack:labstack-java:0.6.30'
}
```

## Quick Start

[Sign up](https://labstack.com/signup) to get an API key

Create a file `Main.java` with the following content:

```java
package main;

import com.labstack.Client;
import com.labstack.Document;
import com.labstack.Store;

public class Main {
    public static void main(String[] args) throws Exception {
        Client client = new Client("<ACCOUNT_ID>", "<API_KEY>");
        Store store = client.store();
        Document document = store.insert("users", new Document()
               .add("name", "Jack")
               .add("location", "Disney"));
    }
}
```

From IntelliJ run Main.main()

## [Docs](https://labstack.com/docs) | [Forum](https://forum.labstack.com)
