'use strict';

app.component('statistics', {
    controller: function ($location,  $scope) {

        let url = $location.absUrl().concat('-ui.html');

        $scope.myData = [
            ["Chocolate", 5],
            ["Rhubarb compote", 2],
            ["Crêpe Suzette", 2],
            ["American blueberry", 2],
            ["Buttermilk", 1]
        ];

    }, template: `
  <div class="container">

      <div
            anychart
            ac-type="pie"
            ac-data="myData"
            ac-title="Simple Pie Chart"
            ac-legend="true"
            style="width: 100%; height: 400px;">
      </div>
    
  </div>
`
});