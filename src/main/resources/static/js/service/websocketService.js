'use strict';

app.factory('websocketService', function ($rootScope, $http, $location) {
    let baseUrl = $location.absUrl().concat('config');
    let socket;
    let service = {
        data: {}, connect: function () {
            return $http.get(baseUrl).then(function (response) {
                let wsUrl = response.data.webSocketUrl;
                if (!wsUrl) {
                    console.error("WebSocket URL not found in config");
                    return;
                }

                socket = new WebSocket(wsUrl);

                socket.onopen = function () {
                    console.log("WebSocket connected!");
                };

                socket.onmessage = function (event) {
                    let data = JSON.parse(event.data);
                    $rootScope.$apply(function () {
                        service.data = data;
                    });
                };

                socket.onerror = function (error) {
                    console.error("WebSocket Error:", error);
                };

                socket.onclose = function () {
                    console.log("WebSocket closed!");
                };
            }).catch(function (error) {
                console.error("Error loading config:", error);
            });
        }
    };

    return service;
});