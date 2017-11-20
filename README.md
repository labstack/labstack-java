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

import com.labstack.ApiException;
import com.labstack.Barcode;
import com.labstack.Client;

public class Main {
    public static void main(String[] args) throws Exception {
        Client client = new Client("<API_KEY>");
        try {
            Barcode.GenerateResponse response = client.barcodeGenerate(new Barcode.GenerateRequest()
                .setFormat("qr_code")
                .setContent("https://labstack.com"));
            client.download(response.getId(), "/tmp/" + response.getName());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
```

From IntelliJ run Main.main()

## [API](https://labstack.com/api) | [Forum](https://forum.labstack.com)
