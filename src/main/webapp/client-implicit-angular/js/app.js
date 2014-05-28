'use strict';

var app = angular.module('aplikasiOauthClient',[]);

app.config(function($locationProvider){
    $locationProvider.html5Mode(true);
});

app.controller('NavCtrl', function($scope, $location, $window){
    $scope.login = function(){
        var contextPath = $location.path().substr(1).split('/');
        console.log("Webapp Path : "+contextPath[0]);
        $window.location.href = '/'+contextPath[0]+'/login';
    };
});

app.controller('OauthCtrl', function($scope){
   $scope.currentUser; 
    
   $scope.adminApi = function(){
       alert("Memanggil API Admin");
   }; 
   
   $scope.staffApi = function(){
       alert("Memanggil API Staff");
   }; 
});