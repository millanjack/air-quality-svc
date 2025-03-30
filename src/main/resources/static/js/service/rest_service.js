'use strict';

app.factory('RestAPI', ['$http', '$location', function ($http, $location) {
    let Response = {};

    let baseUrl = $location.host() + ":" + $location.port() + '/v1/api';

    Response.get = function (url) {
        return $http.get(baseUrl + url);
    };

    Response.post = function (url, inData) {
        return $http.post(baseUrl + url, inData);
    };

    return Response;
}]);