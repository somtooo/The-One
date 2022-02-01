<H1>The One - A Home Automation System Simulation</H1>

### 1. Goal
*To simulate a home automation system using threads and events patterns.*

### 2. System Requirements
1. Monitoring (e.g What is the temp, is the door locked, are lights on.)
2. Control (e.g Turn up the heating, Lock the Door, Turn Light on or off.)
3. Automation (e.g Turn on the heating if the outside temperature falls below a certain temperature.)


##### 2.1 System Devices
1. Door Locks: Can lock or unlock based on command.
2. Lights: Can turn on or off based on command.
3. AC: Can turn on or off & change temperature based on command.
4. Sensors: Does a singular task depending on type, e.g motion sensors turn on if someone is in the room
5. Outdoor Camera. Sends Live video or picture depicting the state of its surroundings.


##### 2.2 User Requirements
*An interactive GUI that displays the live state of each system device and allows the user to interact with each system device*

### 3.0 Interaction Between System Processes 

##### 3.1 User Interaction
User interacts with the system through the cmd using keyboard commands. It maniplates devices by talking to the system server or the device directly(virtual & physical interface). Check out the java client library that can also be simulated to speak to the server - https://googleapis.dev/java/google-api-services-smartdevicemanagement/latest/. 
User/testers also have a backdoor into the system thats used to help simulate failures. e.g the user can cause the light bulb to burn out or the motherboard in the ac to fry. Ideally we want the user to enter a coomand through the keyboard, this will then put the simulation in an unsafe state and cause different failures randomly. The goal is to see how the server handles such failures. (gracefully or catastrophically).


##### 3.1 Server Interaction
The server is the cto of the system. It handles dynamic configuration, automation and keeping the user up to date on state of curent devices.
<p><u> Server and User </u></p>
The server executes commands from the user and also keeps the user up to date on the current state of each component.
<p> Server and Devices </p>
1. The server is able to communicate with each device connected to it through its interface. It's also gives the user the capabilities to connect a new component to it and configure old device.

2. The server handles automation requirements. At a high level it can be explained using this example. if(nobodyHome) {targetTemp = 74, turn of all lights}. This entails some type of cordination between the sensor, thermostat and light devices.

##### 3.2 Devices
Devices are separate entites and for now do not know about each other. After some research an idea is to simulate google nest devices since an api has been defined. Heres an example of the nest thermostat api. https://developers.google.com/nest/device-access/api/thermostat. All or some functionality per the java client library. 
To view the view API guide for devices go here - https://developers.google.com/nest/device-access/api.


