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


### Flow Grant Type Implicit ###

* Generate random variabel `state` dulu supaya aman

        curl http://localhost:8080/belajar-spring-oauth2/api/state/new
        
    Misalnya, nilai variabel `state` yang didapatkan `blablabla`. Variabel `state` ini disimpan sebagai session variable di sisi server pada aplikasi client. Kita akan gunakan nilai ini untuk verifikasi pada langkah selanjutnya.

* Login dulu supaya boleh generate token. Browse ke `http://localhost:8080/belajar-spring-oauth2/login`

* Setelah dapat variabel `state` dari request di atas, gunakan untuk generate token

        curl http://localhost:8080/belajar-spring-oauth2/oauth/authorize?client_id=jsclient&response_type=token&scope=write&state=blablabla

    Authorization server akan melakukan redirect ke url yang kita daftarkan, yaitu `http://localhost:8080/belajar-spring-oauth2/api/state/verify`. URL ini akan ditambahkan hash variable berisi token, sehingga isi lengkapnya seperti ini
    
        http://localhost:8080/belajar-spring-oauth2/api/state/verify#access_token=667aadee-883c-439f-9f18-50ef77e3fad6&token_type=bearer&state=blablabla&expires_in=43199

* Di aplikasi client kita, URL redirect ini kita program supaya mengembalikan nilai variabel `state` yang sudah kita set pada langkah pertama. Selanjutnya, kita bandingkan nilai state yang dikembalikan server kita dengan nilai state yang dikembalikan authorization server. Karena isinya sama-sama `blablabla`, kita yakin bahwa `access_token` yang dihasilkan memang benar-benar valid.

* Sekarang kita bisa mengakses URL yang kita inginkan

        curl http://localhost:8080/belajar-spring-oauth2/api/admin?access_token=667aadee-883c-439f-9f18-50ef77e3fad6

## Referensi ##

* http://tools.ietf.org/id/draft-ietf-oauth-v2-31.html
* https://auth0.com/blog/2014/01/07/angularjs-authentication-with-cookies-vs-token/
* https://auth0.com/blog/2014/01/27/ten-things-you-should-know-about-tokens-and-cookies/
* https://developers.google.com/accounts/docs/OAuth2UserAgent
* http://techblog.hybris.com/2012/08/10/oauth2-server-side-implementation-using-spring-security-oauth2-module/
* http://projects.spring.io/spring-security-oauth/docs/oauth2.html
* https://github.com/royclarkson/spring-rest-service-oauth
