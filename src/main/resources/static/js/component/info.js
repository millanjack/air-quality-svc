'use strict';

app.component('info', {
    controller: function () {
    }, template: `
<div class="container">
   <div class="row" style="padding-top: 40px;padding-bottom: 40px">
      <div class="col-12">
         <div class="card text-center">
            <div class="card-header">
               Info
               <br>
            </div>
            <div class="card-body">
               <br>
               <h5>DHT 11 Humidity & Temperature
                  Sensor
               </h5>
               <br>
               <p>DHT11 Temperature & Humidity Sensor features a
                  temperature & humidity sensor complex with a
                  calibrated digital signal output.
               </p>
               <p>The DHT11 is a widely used digital temperature and humidity sensor that offers a simple interface 
               and easy integration into various electronic projects, including DIY projects and home automation systems. 
               It measures the surrounding air and provides a digital signal with the temperature and humidity readings. 
               Its cost-effectiveness and simplicity make it an ideal choice for hobbyists 
               and educators who are introducing concepts of environmental sensing.</p>
               <br>
               <div style="display: inline-block;">
                  <img alt="DHT 11" width="300" style="border-radius: 5px;" 
                     src="https://abacasstorageaccnt.blob.core.windows.net/cirkit/2c628a0e-39a1-4f8c-b1e5-dbd8acbd8310.png">
               </div>
               <br><br>
               <br>
               <h5>CCS811 Indoor Air Quality Sensor</h5>
               <br>
               <p>The CCS811 digital gas sensor detects volatile organic compounds (VOCs) for indoor air quality measurements. </p>
               <p>VOCs are often categorized as pollutants and/or sensory irritants and can come from a variety 
               of sources such as construction materials (paint and carpet), 
               machines (copiers and processors), and even people (breathing and smoking).
               It estimates carbon dioxide (CO2) levels where the main source of VOCs is human presence.</p>
               <br>
               <div  style="display: inline-block;">
                  <img alt="CCS811" width="300" style="border-radius: 5px;" 
                    src="https://abacasstorageaccnt.blob.core.windows.net/cirkit/7dbcbc01-7271-445f-9928-deee3f469544.png">
               </div>
               <br>
               <br>
            </div>
            <div class="card-footer text-muted"><br></div>
         </div>
      </div>
   </div>
</div>
`
});