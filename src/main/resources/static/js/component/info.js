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
               <h6>Core Components of an IoT System:</h6>
               <b> 1. Sensors:</b>
               <br>
               Collect real-world data (e.g., temperature, humidity, motion)<br>
               <br>
               <br>
               <b>2. Controller Devices:</b><br><br>
               Controller: Microcontrollers or edge computing devices that process sensor data and communicate with the cloud or other systems<br>
               Functions: Read sensor data, process or transform it (optional), and send it to a server<br>
               Programming: Controllers run code written in languages like Python, java, etc.<br>
               <br>
               <br>
               <b>3. Connectivity:</b><br>
               <br>
               Network Protocols: Ensures communication between devices, controllers, and the cloud<br>
               Data Transfer: Sends data from the controller to the cloud or backend system for processing and storage<br>
               <br>
               <br>
               <b>4. Cloud/Backend System:</b><br>
               Backend Server: A system that receives data from edge devices, processes it, stores it, and might trigger actions based on it<br>
               Databases: Stores sensor data for future analysis and access<br>
               <br><br>
               <b>5. Data Storage:</b><br>
               Databases: Stores incoming data for long-term storage and easy retrieval<br>
               Function: Ensures the data is stored and accessible for analysis or reporting<br>
               <br><br>
               <b>6. Processing & Analytics:</b><br>
               Data Analysis: Processes the collected data, often involving machine learning or analytics to gain insights or trigger actions<br>
               <br><br>
               <b>7. User Interface (Dashboard):</b><br>
               Visualization & Control: Provides a graphical interface to monitor data or interact with the system<br>
               Function: Users can view sensor data in real-time, analyze trends, or manually control devices<br>
               <br><br>
            </div>
            <div class="card-footer text-muted"><br></div>
         </div>
      </div>
   </div>
</div>
`
});