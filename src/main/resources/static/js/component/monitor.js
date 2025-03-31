'use strict';

app.component('monitor', {
    controller: function (RestAPI, $scope, $transitions, $interval, websocketService) {

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
                 <p> Device: <b>{{ sensor.deviceId }}</b> </p>
                 <p> Type: <b>{{ sensor.type }}</b></p>
                 <br>
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
             
                 <br>
                 <br>
               </div>
            </div>
         </div>
            <div class="card-footer text-muted"></div>
      </div>
   </div>
</div>
`
});