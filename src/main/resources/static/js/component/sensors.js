'use strict';

app.component('sensors', {
    controller: function ($location, $scope, restService) {

        let response = restService.get('/sensors')

        console.log("re" + response.data)

        $scope.labels = ["Chocolate", "Rhubarb compote", "Crêpe Suzette", "American blueberry", "Buttermilk"];
        $scope.data = [[65, 59, 80, 81, 56, 55, 40], [28, 48, 40, 19, 86, 27, 90]];

        $scope.options = {
            responsive: true, legend: {
                display: true
            }, elements: {
                arc: {
                    backgroundColor: ["#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF"]
                }
            }
        };

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
            <div class="card-footer text-muted"><br></div>
            </div>
         </div>
      </div>
   </div>
</div>
    `
});