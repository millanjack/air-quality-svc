'use strict';

app.component('controller', {
    controller: function ($scope, $rootScope) {

        $scope.$watch(function () {
            return $rootScope.piController;
        }, function (newVal) {
            $scope.piController = newVal;
        });

    }, template: `
<div class="container">
   <div class="row" style="padding-top: 40px;padding-bottom: 40px">
      <div class="col-12">
         <div class="card text-center">
            <div class="card-header">Raspberry PI controller</div>
            <br>
            <div class="card-body">
               <pre><b>Device id:</b> {{ piController.deviceId }}</pre>
               <pre><b>Board model:</b> {{ piController.boardModel }}</pre>
               <pre><b>Operating system:</b> {{ piController.operatingSystem }}</pre>
               <pre><b>Java version:</b> {{ piController.javaVersions }}</pre>
               <pre><b>jvm memory:</b> {{ piController.jvMemoryMb }} Mb</pre>
               <pre><b>Board temperature:</b> {{ piController.boardTemperature }}</pre>
            </div>
            <div class="card-footer text-muted"><br></div>
         </div>
      </div>
   </div>
</div>
    `
});