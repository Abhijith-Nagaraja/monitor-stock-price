# monitor-stock-price
A project to monitor stock price

###Prerequisites

1. Msp-db-installer: https://github.com/Abhijith-Nagaraja/msp-db-installer

###Steps

1. Clone the project
    
    git clone https://github.com/Abhijith-Nagaraja/monitor-stock-price.git
<br/>![Setp 1](./images/step1.jpg)<br/>
2. Go inside the cloned directory and navigate to WebContent/WEB-INF and open web.xml
<br/>![Setp 2](./images/step2.jpg)<br/><br/>
3. Change the <param-value> for url, username and password in the <context-param> to include your MySQL details
 <br/>![Setp 3](./images/step3.jpg)<br/>
4. Navigate back to project root folder and exceute maven install
 
      mvn clean install
<table>
        <tbody>
          <tr>
            <td>![Step 4a](./images/step4a.jpg)</td>
            <td>![Step 4b](./images/step4b.jpg)</td>
          </tr>
        </tbody>
      </table><br/>
5. Copy the war folder from projects target directory into your Tomcat server webapp directory and rename if required
 <table>
        <tbody>
          <tr>
            <td>![Step 5a](./images/step5a.jpg)</td>
            <td>![Step 5b](./images/step5b.jpg)</td>
            <td>![Step 5c](./images/step5c.jpg)</td>
          </tr>
        </tbody>
      </table><br/>
6. Start the Tomcat server
<br/>![Setp 6](./images/step6.jpg)<br/><br/>
7. Open the browser, go to "[your server address with port]://[war-name]/"
<br/>![Setp 7](./images/step7.jpg)<br/><br/>

###Screenshots
1. Trying to login with registering
<br/>![1](./screenshots/1.jpg)<br/><br/>
2. Register Page
<br/>![2](./screenshots/2.jpg)<br/><br/>
3. Register confirmation
<br/>![3](./screenshots/3.jpg)<br/><br/>
4. Dashboard
<br/>![4](./screenshots/4.jpg)<br/><br/>
5. Company Dropdown
<br/>![5](./screenshots/5.jpg)<br/><br/>
6. After adding three companies
<br/>![6](./screenshots/6.jpg)<br/><br/>
7. Historical data
<br/>![7](./screenshots/7.jpg)<br/><br/>
8. Logout
<br/>![8](./screenshots/8.jpg)<br/><br/>
