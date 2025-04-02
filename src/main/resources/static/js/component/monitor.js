'use strict';

app.component('monitor', {
    controller: function ($scope, $rootScope, $transitions, $interval, websocketService) {

        let tick = function () {
            $scope.clock = Date.now();
        }

        tick();

        $interval(tick, 1000);

        // loading bar
        $scope.loading_bar_max = 100;
        $scope.loading_bar_current = 0;
        $interval(function () {
            $scope.loading_bar_current = ($scope.loading_bar_current + 1) % $scope.loading_bar_max;
        }, 40);

        // websocket service
        $scope.sensorData = {};

        websocketService.connect();

        $scope.$watch(function () {
            return websocketService.data;
        }, function (newData) {
            $scope.sensorData = newData;
            // Raspberry pi controller
            $rootScope.piController = newData.info
            // loading bar
            $scope.loading_bar_current = 0;
        });

        // round-progress settings
        $scope.maxTemp = 50;
        $scope.maxHumidity = 100;
        $scope.maxTVOC = 10000;
        $scope.maxECO2 = 10000;

        $scope.getSensorTemperatureData = function (sensor) {
            let data = sensor.data.find(d => d.key === 'temperature_celsius');
            return data ? data.val : null;
        };

        $scope.getSensorHumidityData = function (sensor) {
            let data = sensor.data.find(d => d.key === 'humidity');
            return data ? data.val : null;
        };

        $scope.getSensorTVOCData = function (sensor) {
            let data = sensor.data.find(d => d.key === 'tvoc');
            return data ? data.val : null;
        };

        $scope.getSensorECO2Data = function (sensor) {
            let data = sensor.data.find(d => d.key === 'eco2');
            return data ? data.val : null;
        };

    }, template: `
<div class="container">
   <div class="row" style="padding-top: 40px;padding-bottom: 40px">
      <div class="col-12">
         <div class="card text-center">
            <div class="card-header">
               <p>Environmental Monitoring: Temperature, Humidity & Air Quality Sensors</p>
               <svg class="blink_1_second bd-placeholder-img rounded me-2" width="10" height="10" 
                  xmlns="http://www.w3.org/2000/svg" 
                  aria-hidden="true" 
                  preserveAspectRatio="xMidYMid slice" focusable="false">
                  <rect width="100%" height="100%" fill="cornflowerblue"></rect>
               </svg>
               Real time 
               <br>
               {{ clock | date:'medium'}}
               <br>
               <br>
            </div>
            <div class="card-body">
               <div ng-if="!sensorData.sensors || sensorData.sensors.length === 0">
                  <br><br>
                  <round-progress
                     max="loading_bar_max"
                     current="loading_bar_current"
                     color="#a8dcff"
                     bgcolor="#eaeaea"
                     radius="80"
                     stroke="15"
                     clockwise="false"
                     responsive="false"
                     duration="500"
                     animation="easeInBounce"
                     animation-delay="0">
                  </round-progress>
                  <p style="font-size: 18px; margin-top: 10px;">Loading ...</p>
                  <br>
                  <br>
               </div>
               <div ng-repeat="sensor in sensorData.sensors track by $index">
                  <p> Sensor: <b>{{ sensor.deviceId }}</b> ({{ sensor.type }})</p>
                  <br>
                  <div ng-if="sensor.type !== 'CCS811'">
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
                  </div>
                  <!-- Если сенсор CCS811, отображаем TVOC и eCO2 -->
                  <div ng-if="sensor.type === 'CCS811'">
                     <div class="row justify-content-md-center">
                        <div class="col-12 col-lg-4">
                           <h5>TVOC</h5>
                           <round-progress
                              max="maxTVOC"
                              current="getSensorTVOCData(sensor)"
                              color="#ff9900"
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
                           <p style="font-size: 20px; margin-top: 10px;"><strong>{{ (sensor.data | filter:{key:'tvoc'})[0].val }} ppb</strong></p>
                        </div>
                        <div class="col-12 col-lg-4">
                           <h5>eCO₂</h5>
                           <round-progress
                              max="maxECO2"
                              current="getSensorECO2Data(sensor)"
                              color="#ff3333"
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
                           <p style="font-size: 20px; margin-top: 10px;"><strong>{{ (sensor.data | filter:{key:'eco2'})[0].val }} ppm</strong></p>
                        </div>
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