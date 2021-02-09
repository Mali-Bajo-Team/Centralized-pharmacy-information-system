[![Build Status](https://dev.azure.com/milanatodorovic/ISA/_apis/build/status/Pufke.ISA?branchName=develop)](https://dev.azure.com/milanatodorovic/ISA/_build/latest?definitionId=22&branchName=develop)
## Centralized-pharmacy-information-system

| Our members          | 
| -------------------- | 
| Ana Perišić          | 
| Milana Todorović     | 
| Nemanja Pualić       | 
| Vladislav Maksimović | 

### Heroku deployment
- Frontend: (https://isa-2020-tim29.herokuapp.com/)[https://isa-2020-tim29.herokuapp.com/]
- Backend: (https://isa-2020-tim29-backend.herokuapp.com/)[https://isa-2020-tim29-backend.herokuapp.com/]

### Help

<details>
  <summary> Instalation Guide for IDEA Intelij Maven </summary>
  <br>
  
   - Skini Intelij i instaliraj ga: https://www.jetbrains.com/idea/download/#section=windows 
   - Backend-spring-boot projekat pulluj sa ovog repozitorijuma
   - Otvori pom.xml 
    <p align="left">
      <img src="https://user-images.githubusercontent.com/49925421/98149271-7a111180-1ecd-11eb-92d7-9e9938f51ff8.png" width="1000" title="hover text">
    </p> 
  
   - Selektuj Open as Project, to importuje projekat baziran na POM fajlu
    <p align="left">
      <img src="https://user-images.githubusercontent.com/49925421/98149547-a62c9280-1ecd-11eb-8473-ef04b9727491.png" width="500" title="hover text">
    </p>
  
   - Nakon toga InteliJ ce da importuje projekat i sve potrebne dependencije, ovo moze da potraje vise minuta u zavisnosti od vase internet konekcije
   
   - Skini jdk 11 i instaliraj ga, meni je radilo na javi 11 nije na javi 15 https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
   - Dodaj JDK 11 
   
     <p align="left">
      <img src="https://user-images.githubusercontent.com/49925421/98150290-b002c580-1ece-11eb-8fe1-f96f640bc622.png" width="700" title="hover text">
     </p>
   - Postavi JDK 11: 
     File -> Project structure -> Project -> Project SDK -> 11.
   - Otvori konzolu tj komande za Maven tako sto ces ispratiti sledeca 3 koraka: 

      <p align="left">
        <img src="https://user-images.githubusercontent.com/49925421/98150929-b0e82700-1ecf-11eb-9ca1-52216f69fbc3.png" width="700" title="hover text">
      </p>
   - Izvršavanjem Maven životnih ciklusa dobicete generisan .jar ili .war fajl, u nasem konkretnom slucaju .jar, više o životnim ciklusima možeš pročitati ispod...
   
     <p align="left">
       <img src="https://user-images.githubusercontent.com/49925421/98151241-27852480-1ed0-11eb-9434-c3013eed5be5.png" width="700" title="hover text">
     </p>

### Maven životni ciklusi
Proces generisanja artifakata podrazumeva nekoliko koraka i zadataka koji se moraju izvršiti. Primeri tih zadataka uključuju kompajliranje koda, pokretanje unit testova i       pakovanje artifakata. Maven koristi koncept ciljeva (goals) da predstavi te granularne zadatke. Ciljevi se pakuju u plug-inove koji predstavljaju kolekciju sačinjenu od jednog ili više ciljeva. Ciljevi se mogu pokrenuti unošenjem komandi sa sledećom sintaksom:
mvn plugin_identifikator:goal_identifikator
Maven prati ustanovljeni niz koraka koji se izvršavaju u istom redosledu nezavisno od artifakta koji se pravi. Postoje tri ugrađena životna ciklusa:
1. Default: barata fazama kompajliranja, pakovanja i deployovanja Maven projekata
2. Clean: barata brisanjem privremenih fajlova i generisanih artifakata iz target foldera
3. Site: barata generisanjem dokumentacije
Svaki životni ciklus ima svoje faze:
1. Validate: proverava da li u projektu postoje greške i da li su sve biblioteke dostupne
2. Compile: kompajlira kod
3. Test: pokreće unit testove
4. Package: pakuje kompajliran kod u neku od arhiva
5. Install: instalira arhivu na lokalni repozitorijum. Tada je arhiva dostupna svakom projektu koji se nalazi na toj mašini
6. Deploy: smeša arhivu na udaljeni repozitorijum svima na korišćenje
U svakoj fazi se izvršavaju određeni zadaci i svaka faza je povezana sa jednim ili više ciljeva. Faze delegiraju zadatke svojim ciljevima koje izvršavaju plug-inovi.

</details>

<details>
  <summary> Instalation Guide for VueJS </summary>
  <br>
  <h1 align="center"> Vue.js </h1>
  
![image](https://user-images.githubusercontent.com/49925421/79354142-4de77080-7f3c-11ea-88c3-7ca101204e40.png)

![image](https://user-images.githubusercontent.com/49925421/79359013-53e05000-7f42-11ea-9b86-9f543144959a.png)

<h1 align="center"> STEP 1 </h1>
<h3 > Install Node.js </h3>

![image](https://user-images.githubusercontent.com/49925421/79358986-475bf780-7f42-11ea-8d93-e000e26a07fa.png)

It's recomended to install this extension 
  
![image](https://user-images.githubusercontent.com/49925421/79359330-c18c7c00-7f42-11ea-9b2d-3b20e526984a.png)


<h1 align="center"> STEP 2 </h1>
Install Vue CLI
It's same for windows, just type in CMD
  
![image](https://user-images.githubusercontent.com/49925421/79359657-29db5d80-7f43-11ea-82d0-1eadee743f9d.png)

<h1 align="center"> STEP 3 </h1>
Pull project frontend-vue-js from this repository 


<h1 align="center"> STEP 4 </h1>

Change diretory to frontend-vue-js (cd path/frontend-vue-js), and start server

![image](https://user-images.githubusercontent.com/49925421/103670533-f8e00480-4f79-11eb-89d7-d689f96da7c6.png)

<h1 align="center"> CONGRATS </h1>


![image](https://user-images.githubusercontent.com/49925421/103670684-2af16680-4f7a-11eb-96aa-c67588231188.png)

![image](https://user-images.githubusercontent.com/49925421/103670847-6ee46b80-4f7a-11eb-9d7a-b3f9accc8308.png)




</details>
