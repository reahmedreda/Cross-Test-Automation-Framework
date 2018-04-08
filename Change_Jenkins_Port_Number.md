# How to change Jenkins localhost port number ?

- Go to the directory where you installed the Jenkins (In default, it is under Program Files/Jenkins)
- Open the Jenkins.xml
- Search for --httpPort=8080 and replace the 8080 with the new port number that you wish ex: 9090

# To apply changes after you have modified the port 

- Press Windows + R on the keyboard 
- Type "services.msc"
- Right click on the "Jenkins" line > Restart
- Type http://localhost:9090/ in your browser to test the change

[Source](https://stackoverflow.com/questions/23769478/how-to-change-port-for-jenkins-window-service-when-8080-is-being-used)
