# monitor-stock-price
A project to monitor stock price

###Prerequisites

1. Msp-db-installer: https://github.com/Abhijith-Nagaraja/msp-db-installer

###Steps

1. Clone the project
    
    git clone https://github.com/Abhijith-Nagaraja/monitor-stock-price.git
2. Go inside the clone directory and execute maven install
 
      mvn clean install
3. Copy the war folder from projects target directory into your Tomcat server webapp directory and renmae if required
4. Start the Tomcat server
5. Open the browser, go to "[your server address with port]://[war-name]/"
