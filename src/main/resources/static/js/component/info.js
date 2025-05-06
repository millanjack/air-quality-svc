'use strict';

app.component('info', {
    controller: function () {
    }, template: `
<div class="container">
   <div class="row" style="padding-top: 40px;padding-bottom: 40px">
      <div class="col-12">
         <div class="card text-center">
            <div class="card-header">
               <h5>IoT system</h5>
               <br>
            </div>
            <div class="card-body">
               <br>
               <p>An IoT (Internet of Things) system is a network of physical devices (“things”) that collect 
                  and exchange data over the internet or local networks. <br>
                  These devices use sensors, software, and connectivity to interact with their environment, 
                  other devices, or a central system.
               </p>
               <br>
               <img src="img/iot-structure.png" alt="IoT system" style="width: 100%; height: auto;"/>
               <br>
               <br>
               <h6>Components of IoT system:</h6>
               <br>
               <b> 1. Sensors:</b>
               <br>
               Collect real-world data (e.g., temperature, humidity, air quality)
               <br>
               <br>
               <b>2. SBC/Microcontroller:</b>
               <br>
               Microcontrollers computing devices that process sensor data and communicate with the cloud or other systems<br>
               Read sensor data, process and send it to a backend server<br>
               Controllers run code written in languages like Python, Java, etc.
               <br>
               <br>
               <b>3. Connectivity:</b>
               <br>
               Ensures communication between devices, controllers, and the cloud<br>
               Sends data from the controller to the backend system for processing and storage
               <br>
               <br>
               <b>4. Cloud/Backend System:</b>
               <br>
               A system that receives data from controllers/devices, processes it, stores it<br>
               Stores sensor data for future analysis and access
               <br>
               <br>
               <b>5. Data Storage:</b>
               <br>
               Databases: Stores incoming data for long-term storage and easy retrieval<br>
               Function: Ensures the data is stored and accessible for analysis or reporting
               <br>
               <br>
               <b>6. Processing & Analytics:</b>
               <br>
               Processes the collected data, often involving machine learning or analytics to gain insights or trigger actions
               <br>
               <br>
               <b>7. User Interface (Dashboard):</b>
               <br>
               Provides a graphical interface to monitor data or interact with the system<br>
               Users can view sensor data in real-time, analyze trends, or manually control devices
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