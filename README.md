# BUDGET-APP

Aplikacja webowa do zarządzania i analizowania wydatków, dochodów i budżetu domowego.  

###Logowanie
Użytkownik standardowy:
* login: user
* hasło: user123

Admin:
* login: admin
* hasło: admin123

###Uruchamianie
URL apliakcji: https://localhost:8443/ 

URL managera bazy danych: https://localhost:8443/h2-console/

Live (może być czasowo niedostępny): https://budget-app-jklata.herokuapp.com/  

![img1](https://github.com/jklata/budget-app/blob/master/src/main/resources/static/images/img1.JPG)
<br/>
![img2](https://github.com/jklata/budget-app/blob/master/src/main/resources/static/images/img2.JPG)
<br/>
![img4](https://github.com/jklata/budget-app/blob/master/src/main/resources/static/images/img4.JPG)

## Użyte technologie
* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 
* [Maven](https://maven.apache.org/)
* [Spring (Boot, MVC, Data JPA, Security)](https://spring.io)
* [H2](https://www.h2database.com/html/main.html) 
* [MySQL](https://www.mysql.com/) 
* [Thymeleaf](https://www.thymeleaf.org/)
* [Bootstrap](https://getbootstrap.com)  
* [Lombok](https://projectlombok.org/) 


## To-Do 
[Szczegółowy plan pracy - Issues](https://github.com/jklata/budget-app/issues) 
- [x] Schemat bazy danych
- [x] Bootstrap - CSS
- [x] Podstawowe kontrolery do obsługi podstron
- [x] Security
- [ ] Logika do raportowania i analizy transakcji
- [ ] Import transakcji z pliku CSV - wyciąg z banku (mBank)
- [ ] Eksport historii transakcji do CSV/PDF
