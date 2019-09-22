# RestTemplate-Logger

Sometimes when our application is running in a server and an error occurs when integrating to a third application via `RestTemplate` we need to simulate the same call using CURL or other HTTP client. `RestTemplate-logger` helps us by logging all `RestTemplate` calls in a format we can easily reuse.

The default logging format is `CURL` but it can be easily changed.

##### How to use

Add the dependency in your maven project

```xml
<dependency>
  <groupId>com.raufferlobo</groupId>
  <artifactId>resttemplate-logger</artifactId>
  <version>1.0.0</version>
</dependency>
```
Enable debug level
```properties
logging.level.com.raufferlobo.restemplate=DEBUG
``` 

Java code example
```java
RestTemplate restTemplate = new RestTemplateLogger(); // CurlRequestLogger is the default logger.

MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
headers.add("Accept", "application/json");
headers.add("x-foo", "bar");

HttpEntity<String> requestEntity = new HttpEntity<>(headers);

restTemplate.exchange("https://httpbin.org/get", HttpMethod.GET, requestEntity, String.class);
```

Using a request logger different than CurlRequestLogger
```
RestTemplate restTemplate = new RestTemplateLogger(new MyCustomRequestLogger());
```

Result in the log:

```bash
curl \
-X GET \
https://httpbin.org/get \
-H 'Accept: application/json' \
-H 'x-foo: bar' \
-H 'Content-Length: 0'
```

## License

RestTemplate-Logger is free and open source (MIT License). Use it as you wish.

[Read License](https://github.com/anotheria/moskito/blob/master/LICENSE)