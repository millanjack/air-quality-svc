'use strict';

app.component('monitor', {
    controller: function (RestAPI, $scope, $rootScope, $transitions, $interval, websocketService) {

        let tick = function () {
            $scope.clock = Date.now();
        }

        tick();

        $interval(tick, 1000);

        // websocket service
        $scope.sensorData = {};

        websocketService.connect();

        $scope.$watch(function () {
            return websocketService.data;
        }, function (newData) {
            $scope.sensorData = newData;
            // Raspberry pi controller
            $rootScope.piController = newData.info
        });

        // round-progress settings
        $scope.maxTemp = 50;
        $scope.maxHumidity = 100;

        $scope.getSensorTemperatureData = function (sensor) {
            let data = sensor.data.find(d => d.key === 'temperature_celsius');
            return data ? data.val : null;
        };

        $scope.getSensorHumidityData = function (sensor) {
            let data = sensor.data.find(d => d.key === 'humidity');
            return data ? data.val : null;
        };

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
               <div ng-repeat="sensor in sensorData.sensors track by $index">
                 <p> Sensor: <b>{{ sensor.deviceId }}</b> ({{ sensor.type }})</p>
                 <br>
                 <div class="row justify-content-md-center">
                  <div class="col-12 col-lg-4">
                  <h5>Temperature</h5>
                  <round-progress
                     max="maxTemp"
                     current="getSensorTemperatureData(sensor)"
                     color="#369cf7"
                     bgcolor="#eaeaea"
                     radius="130"
                     stroke="25"
                     semi="true"
                     rounded="true"
                     clockwise="true"
                     responsive="false"
                     duration="800"
                     animation="easeInOutQuad"
                     animation-delay="0"></round-progress>
                  <p style="font-size: 20px; margin-top: 10px;"><strong>{{ (sensor.data | filter:{key:'temperature_celsius'})[0].val }} °C</strong></p>
                 </div>
                 <div class="col-12 col-lg-4">
                  <h5>Humidity</h5>
                  <round-progress
                     max="maxHumidity"
                     current="getSensorHumidityData(sensor)"
                     color="#8068a1"
                     bgcolor="#eaeaea"
                     radius="130"
                     stroke="25"
                     semi="true"
                     rounded="true"
                     clockwise="true"
                     responsive="false"
                     duration="800"
                     animation="easeInOutQuad"
                     animation-delay="0"></round-progress>
                  <p style="font-size: 20px; margin-top: 10px;"><strong>{{ (sensor.data | filter:{key:'humidity'})[0].val }} %</strong></p>
                 </div>
                 </div>
                 <br>
                 <br>
               </div>
            </div>
            <div class="card-footer text-muted"><br></div>
         </div>
      </div>
   </div>
</div>
`
});