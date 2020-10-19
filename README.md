## Introduction
This repository contains a Spring Boot application that demonstrates how to implement and use the JSON PATCH method 
specifications in Spring application along with test cases.

RFC covered are :

- [RFC-5789](https://tools.ietf.org/html/rfc5789) : PATCH Method for HTTP
- [RFC-6902](https://tools.ietf.org/html/rfc6902) : JSON Patch
- [RFC-7396](https://tools.ietf.org/html/rfc7396) : JSON Merge Patch

https://en.wikipedia.org/wiki/Patch_verb
https://developer.mozilla.org/fr/docs/Web/HTTP/M%C3%A9thode/PATCH

### Maven dependencies
Here is the list of libraries used in this project
JSON-P api : Contains models to handle json patch request (Jsonvalue, JsonStructure, ...)
```xml
<!-- JSR-353 (JSON Processing): API -->
<dependency>
    <groupId>javax.json</groupId>
    <artifactId>javax.json-api</artifactId>
</dependency>
```
 
Jackson module : Contains the module that enable marshalling of patch operations with ObjectMapper
```xml
<!-- JSR-353 (JSON Processing): Apache Johnzon implementation -->
<dependency>
    <groupId>org.apache.johnzon</groupId>
    <artifactId>johnzon-core</artifactId>
</dependency>

``` 
Apache Jonhzon : JSON-P api implementation
````xml
<!-- Jackson module for the JSR-353 (JSON Processing) -->
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr353</artifactId>
</dependency>
````

 
### Main concepts
