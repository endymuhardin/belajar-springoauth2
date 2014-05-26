# Belajar Spring Security dengan OAuth 2 #

Fitur aplikasi:

* Grant type user password
* Grant type implicit

## Menjalankan Aplikasi ##

Aplikasi terdiri dari 3 URL : 

* `http://localhost:8080/belajar-spring-oauth2/api/halo` : boleh diakses tanpa otentikasi
* `http://localhost:8080/belajar-spring-oauth2/api/admin` : hanya boleh diakses role admin
* `http://localhost:8080/belajar-spring-oauth2/api/staff` : hanya boleh diakses role staff

User yang tersedia :

| Username | Password | Role  |
|:--------:|:--------:|:-----:|
| endy     | 123      | admin |
| anton    | 123      | staff |


### Flow Grant Type User Password ##

* Jalankan Aplikasi : `mvn clean tomcat7:run`
* Akses url terproteksi tanpa login dulu

        curl http://localhost:8080/belajar-spring-oauth2/api/admin 
    
    Ini akan menghasilkan response code `401`

* Request token dulu : 

        curl -X POST -vu clientapp:123456 http://localhost:8080/belajar-spring-oauth2/oauth/token -H "Accept: application/json" -d "client_id=clientapp&grant_type=password&username=endy&password=123" 

    Ini akan menghasilkan response JSON berisi token, misalnya tokennya adalah `initokensaya`

* Akses lagi url terproteksi dengan menggunakan token : 

        curl -H "Authorization: Bearer initokensaya" http://localhost:8080/belajar-spring-oauth2/api/admin

    Ini akan menghasilkan response sukses.


## Referensi ##

* http://tools.ietf.org/id/draft-ietf-oauth-v2-31.html
* https://auth0.com/blog/2014/01/07/angularjs-authentication-with-cookies-vs-token/
* https://auth0.com/blog/2014/01/27/ten-things-you-should-know-about-tokens-and-cookies/
* https://developers.google.com/accounts/docs/OAuth2UserAgent
* http://techblog.hybris.com/2012/08/10/oauth2-server-side-implementation-using-spring-security-oauth2-module/
* http://projects.spring.io/spring-security-oauth/docs/oauth2.html
* https://github.com/royclarkson/spring-rest-service-oauth
