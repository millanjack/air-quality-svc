'use strict';

let app = angular.module('app', ['ui.router', 'ngSanitize', 'chart.js', 'angular-svg-round-progressbar']);

app.config(function ($locationProvider, $stateProvider, $urlRouterProvider) {
    $locationProvider.html5Mode(true);

    $locationProvider.hashPrefix('');

    let states = [{
        name: 'monitor', url: '/', component: 'monitor'
    }, {
        name: 'statistics', url: '/statistics', component: 'statistics'
    }];

    states.forEach(function (state) {
        $stateProvider.state(state);
    });

    $urlRouterProvider.otherwise("/");
});