'use strict';

var app = angular.module('aplikasiOauthClient',[]);

app.config(function($locationProvider){
    $locationProvider.html5Mode(true);
});

app.controller('NavCtrl', function($scope, $window, $location, $http){
    $scope.authUrl = 'http://localhost:10000/auth-server/oauth/authorize?client_id=jsclient&response_type=token&scope=write';
    
    $scope.token;
    
    $scope.login = function(){
        $window.location.href = $scope.authUrl;
        //$window.location.reload();
    };
   
   $scope.logout = function(){
       var token = $window.sessionStorage.getItem('token');
       console.log('Token : '+token);
       $window.sessionStorage.removeItem('token');
   };
   
   $scope.getTokenFromUrl = function(){
       var token;
       var hashParams = $location.hash();
       if(!hashParams) {
           console.log("Tidak ada token di url");
           return;
       }
       console.log(hashParams);
       var eachParam = hashParams.split('&');
       for(var i=0; i<eachParam.length; i++){
           var param = eachParam[i].split('=');
           if('access_token' === param[0]) {
               token = param[1];
           }
       }
       console.log("Access Token : "+token);
       if(token){
           $window.sessionStorage.setItem('token', token);
       }
       $location.hash('');
   };
   
   $scope.checkLogin = function(){
       // check apa ada token di URL
       if($window.sessionStorage.getItem('token')){
           $scope.token = $window.sessionStorage.getItem('token');
           return;
       }
       $scope.getTokenFromUrl();
       if($window.sessionStorage.getItem('token')){
           $scope.token = $window.sessionStorage.getItem('token');
           return;
       }
       
        $scope.login();
   };
   
   $scope.checkLogin();
});

app.controller('OauthCtrl', function($scope, $http, $window){
   $scope.currentUser; 
   $scope.accessToken = $window.sessionStorage.getItem('token');
    
   $scope.adminApi = function(){
       if(!$scope.accessToken) {
           alert("Belum punya token, login dulu ya");
           return;
       }
       console.log("Memanggil API Admin");
       $http.get('http://localhost:10001/resource-server/api/admin?access_token='+$scope.accessToken)
           .success(function(data){
               $scope.adminOutput = data;
               $scope.currentUser = data.user;
            }).error(function(data, status){
                alert("Error bos : "+status);
                console.log(data);
                $scope.adminOutput = data;
            });
   }; 
   
   $scope.staffApi = function(){
       if(!$scope.accessToken) {
           alert("Belum punya token, login dulu ya");
           return;
       }
       $http.get('http://localhost:10001/resource-server/api/staff?access_token='+$scope.accessToken)
            .success(function(data){
               $scope.staffOutput = data;
               $scope.currentUser = data.user;
            }).error(function(data, status){
                alert("Error bos : "+status);
                console.log(data);
                $scope.staffOutput = data;
            });
   };
});