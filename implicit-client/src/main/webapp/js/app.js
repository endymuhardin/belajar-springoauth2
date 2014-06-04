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

app.controller('OauthCtrl', function($scope, $location, $window, $http){
   $scope.currentUser; 
   $scope.accessToken;
    
   $scope.adminApi = function(){
       if(!$scope.accessToken) {
           alert("Belum punya token, login dulu ya");
           return;
       }
       console.log("Memanggil API Admin");
       $http.get('http://localhost:10001/resource-server/api/admin?access_token='+$scope.accessToken)
           .success(function(data){
               $scope.adminOutput = data;
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
            }).error(function(data, status){
                alert("Error bos : "+status);
                console.log(data);
                $scope.staffOutput = data;
            });
   }; 
   
   $scope.getTokenFromUrl = function(){
       var hashParams = $location.hash();
       if(!hashParams) {
           console.log("Tidak ada token di url")
           return;
       }
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
   
   $scope.getTokenFromUrl();
});