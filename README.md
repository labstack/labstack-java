<a href="https://labstack.com"><img height="80" src="https://cdn.labstack.com/images/labstack-logo.svg"></a>

## Java Client

## Installation

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.labstack:labstack-java:0.10.0'
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
        Client client = new Client("<API_KEY>");
        Barcode.GenerateResponse response = client.barcodeGenerate(new Barcode.GenerateRequest()
            .setFormat("qr_code")
            .setContent("https://labstack.com"));
    }
}
```

From IntelliJ run Main.main()

## [Documentation](https://labstack.com/docs) | [Forum](https://forum.labstack.com)
