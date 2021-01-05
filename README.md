# Centralized-pharmacy-information-system

| Our members          | 
| -------------------- | 
| Ana Perišić          | 
| Milana Todorović     | 
| Nemanja Pualić       | 
| Vladislav Maksimović | 

## Help

<details>
  <summary> Instalation Guide for IDEA Intelij Maven </summary>
  <br>
  
   - Skini Intelij i instalitaj ga: https://www.jetbrains.com/idea/download/#section=windows 
   - Maven-Example projekat pulluj sa linka: https://github.com/katarinaa94/isa/tree/master/Vezbe1/Tools/maven-example
   - Otvori pom.xml 
    <p align="left">
      <img src="https://user-images.githubusercontent.com/49925421/98149271-7a111180-1ecd-11eb-92d7-9e9938f51ff8.png" width="1000" title="hover text">
    </p> 
  
   - Selektuj Open as Project, to importuje projekat baziran na POM fajlu
    <p align="left">
      <img src="https://user-images.githubusercontent.com/49925421/98149547-a62c9280-1ecd-11eb-8473-ef04b9727491.png" width="500" title="hover text">
    </p>
  
   - Nakon toga InteliJ ce da importuje projekat i sve potrebne dependencije, ovo moze da potraje vise minuta u zavisnosti od vase internet konekcije
   - U pom.xml fajlu dodaj ove dve linije ako vec nisu dodate 
     <p align="left">
      <img src="https://user-images.githubusercontent.com/49925421/98149835-04f20c00-1ece-11eb-8769-7d2716832f0c.png" width="700" title="hover text">
     </p>
   
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

  
