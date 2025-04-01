'use strict';

app.component('sensors', {
    controller: function ($location, $scope, restService) {

        $scope.sensors = [];

        restService.get('/sensors').then(function (response) {
            $scope.sensors = response.data.map(sensor => {
                let tempData = sensor.data.find(d => d.key === "temperature_celsius");
                let humidityData = sensor.data.find(d => d.key === "humidity");

                return {
                    ...sensor,
                    labels: tempData ? tempData.dates.map(d => new Date(d).toLocaleTimeString()) : [],
                    chartData: tempData ? [tempData.values] : [[]],
                    series: 'Temperature (°C)',
                    humidityLabels: humidityData ? humidityData.dates.map(d => new Date(d).toLocaleTimeString()) : [],
                    humidityData: humidityData ? [humidityData.values] : [[]],
                    humiditySeries: 'Humidity (%)'
                };
            });
        });

    }, template: `
<div class="container">
   <div class="row" style="padding-top: 40px;padding-bottom: 40px">
      <div class="col-12">
         <div class="card text-center">
            <div class="card-header">
               Sensors
               <br>
            </div>
            <div class="card-body">
               <div ng-repeat="sensor in sensors track by $index">
                  <h5>{{ sensor.deviceId }} ({{ sensor.type }})</h5>
                  <div class="row">
                     <div class="col-12 col-md-6">
                         {{sensor.series}}
                        <canvas 
                           class="chart chart-line" 
                           chart-data="sensor.chartData" 
                           chart-labels="sensor.labels">
                        </canvas>
                     </div>
                     <div class="col-12 col-md-6">
                        {{sensor.humiditySeries}}
                        <canvas 
                           class="chart chart-line" 
                           chart-data="sensor.humidityData" 
                           chart-labels="sensor.humidityLabels">
                        </canvas>
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