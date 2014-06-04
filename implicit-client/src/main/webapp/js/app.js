'use strict';

var app = angular.module('aplikasiOauthClient',[]);

app.config(function($locationProvider){
    $locationProvider.html5Mode(true);
});

app.controller('NavCtrl', function($scope, $window){
    $scope.login = function(){
        $window.location.href = 'http://localhost:10000/auth-server/oauth/authorize?client_id=jsclient&response_type=token&scope=write';
    };
});

app.controller('OauthCtrl', function($scope, $location, $window){
   $scope.currentUser; 
   
   $scope.accessToken;
    
   $scope.adminApi = function(){
       alert("Memanggil API Admin");
   }; 
   
   $scope.staffApi = function(){
       alert("Memanggil API Staff");
   }; 
   
   $scope.getTokenFromUrl = function(){
       var hashParams = $location.hash();
       console.log(hashParams);
       var eachParam = hashParams.split('&');
       for(var i=0; i<eachParam.length; i++){
           var param = eachParam[i].split('=');
           if('access_token' === param[0]) {
               $scope.accessToken = param[1];
           }
       }
       console.log("Access Token : "+$scope.accessToken);
       if($scope.accessToken){
           $window.sessionStorage.setItem('token', $scope.accessToken);
       }
   };
});