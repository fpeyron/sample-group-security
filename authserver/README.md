Sample `Scala` project to expose Web Service in html page   
It uses [Spring Boot](https://github.com/spring-projects/spring-boot) to provide an embedded servlet container and for defaulting a load of
configuration.

## Building

You need Java (1.7 or better) and Maven (3.0.5 or better):

```
$ mvn package
$ java -jar target/*.jar
...
<app starts and listens on port 8080>
```


```
$ curl 'http://user:password@localhost:9999/uaa/oauth/authorize?response_type=code&client_id=acme&redirect_uri=http://example.com'

$ curl acme:acmesecret@localhost:9999/uaa/oauth/token  \
-d grant_type=authorization_code -d client_id=acme     \
-d redirect_uri=http://example.com -d code=jYWioI
{"access_token":"2219199c-966e-4466-8b7e-12bb9038c9bb","token_type":"bearer","refresh_token":"d193caf4-5643-4988-9a4a-1c03c9d657aa","expires_in":43199,"scope":"openid"}
```