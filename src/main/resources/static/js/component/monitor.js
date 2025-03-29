'use strict';

app.component('monitor', {
    controller: function (RestAPI, $scope, $transitions, $interval, websocketService) {

        let tick = function () {
            $scope.clock = Date.now();
        }
        tick();
        $interval(tick, 1000);

        $scope.sensorData = {};

        websocketService.connect().then(function () {
            $scope.sensorData = websocketService.data;
        });

        $scope.$watch(function () {
            return websocketService.data;
        }, function (newData) {
            $scope.sensorData = newData;
        }, true);


    }, template: `
<div class="container">
   <div class="row" style="padding-top: 40px;padding-bottom: 40px">
      <div class="col-12">
         <div class="card text-center">
            <div class="card-header">
               <svg class="blink_1_second bd-placeholder-img rounded me-2" width="10" height="10" 
                  xmlns="http://www.w3.org/2000/svg" 
                  aria-hidden="true" 
                  preserveAspectRatio="xMidYMid slice" focusable="false">
                  <rect width="100%" height="100%" fill="cornflowerblue"></rect>
               </svg>
               Real time monitor 
               <br>
               {{ clock | date:'medium'}}
            </div>
            <div class="card-body">
               <h5 class="card-title">Raspberry PI controller</h5>
               <pre>deviceId: {{ sensorData.info.deviceId }}</pre>
               <pre>boardModel: {{ sensorData.info.boardModel }}</pre>
               <pre>operatingSystem: {{ sensorData.info.operatingSystem }}</pre>
               <pre>javaVersions: {{ sensorData.info.javaVersions }}</pre>
               <pre>jvMemoryMb: {{ sensorData.info.jvMemoryMb }}</pre>
               <pre>boardTemperature: {{ sensorData.info.boardTemperature }}</pre>
               
               <br>
               <br>
               
               <div ng-repeat="sensor in sensorData.sensors track by $index">
                 <p> deviceId: {{ sensor.deviceId }} </p>
                 <p> type: {{ sensor.type }} </p>
                <p> 
                    temperature_celsius: 
                    {{ (sensor.data | filter:{key:'temperature_celsius'})[0].val }} 
                </p>
                <p> 
                    humidity: 
                    {{ (sensor.data | filter:{key:'humidity'})[0].val }} 
                </p>
                 <br>
               <br>
               </div>
               
  
            </div>
            <div class="card-footer text-muted">
            </div>
         </div>
      </div>
   </div>
</div>
`
});