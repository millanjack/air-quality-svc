'use strict';

app.component('statistics', {
    controller: function ($location, $scope, $interval) {
        let url = $location.absUrl().concat('-ui.html');

        $scope.labels = ["Chocolate", "Rhubarb compote", "Crêpe Suzette", "American blueberry", "Buttermilk"];
        $scope.data = [
            [65, 59, 80, 81, 56, 55, 40],
            [28, 48, 40, 19, 86, 27, 90]
        ];

        $scope.options = {
            responsive: true,
            legend: {
                display: true
            },
            elements: {
                arc: {
                    backgroundColor: ["#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF"]
                }
            }
        };


        function updateValues() {
            $scope.temperature = Math.floor(20 + Math.random() * 10); // Температура (°C)
            $scope.humidity = Math.floor(40 + Math.random() * 20); // Влажность (%)
        }

        // Обновление данных каждую секунду
        $interval(updateValues, 1000);

        // Настройки кругов
        $scope.maxTemp = 50;  // Максимальная температура
        $scope.maxHumidity = 100; // Максимальная влажность

        $scope.stroke = 12;  // Толщина круга

    },
    template: `
<div class="container">
   <div class="row" style="padding-top: 40px;padding-bottom: 40px">
      <div class="col-12">
         <div class="card text-center">
            <div class="card-header">
               Statistics
               <br>
            </div>
            <div class="card-body">
               <h5>DHT11:017</h5>
               <div class="row">
                  <div class="col-12 col-md-6">
                     <canvas 
                        class="chart chart-line" 
                        chart-data="data" 
                        chart-labels="labels"  
                        chart-series="series" 
                        chart-click="onClick">
                     </canvas>
                  </div>
                  <div class="col-12 col-md-6">
                     <canvas 
                        class="chart chart-line" 
                        chart-data="data" 
                        chart-labels="labels"  
                        chart-series="series" 
                        chart-click="onClick">
                     </canvas>
                  </div>
               </div>
               <br>
               <br>
            </div>
            <div class="card-footer text-muted">
            </div>
            <div class="container" style="display: flex; justify-content: space-around; text-align: center;">
               <div>
                  <h3>Temperature</h3>
                  <round-progress
                     max="maxTemp"
                     current="temperature"
                     color="#369cf7"
                     bgcolor="#eaeaea"
                     radius="100"
                     stroke="20"
                     semi="true"
                     rounded="true"
                     clockwise="true"
                     responsive="false"
                     duration="800"
                     animation="easeInOutQuad"
                     animation-delay="0"></round-progress>
                  <p style="font-size: 20px; margin-top: 10px;"><strong>{{ temperature }}°C</strong></p>
               </div>
               <div>
                  <h3>Humidity</h3>
                  <round-progress
                  <round-progress
                     max="maxHumidity"
                     current="humidity"
                     color="#6b5cc2"
                     bgcolor="#eaeaea"
                     radius="100"
                     stroke="20"
                     semi="true"
                     rounded="true"
                     clockwise="true"
                     responsive="false"
                     duration="800"
                     animation="easeInOutQuad"
                     animation-delay="0"></round-progress>
                  <p style="font-size: 20px; margin-top: 10px;"><strong>{{ humidity }}%</strong></p>
               </div>
            </div>
         </div>
      </div>
   </div>
</div>
    `
});