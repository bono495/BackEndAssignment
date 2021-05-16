# Installatie handleiding

The code in this application is build on java version openjdk 15.0.2 2021-01-19
* Stappenplan
* Lijst van benodigdheden
* Testgebruikers
* Userrollen en rest endpoints
## Inhoudsopgave
1. [Inleiding](###Inleiding)
2. [Software](###Software)
3. [Benodigdheden](###Benodigdheden)
4. [Users](###Test Gebruikers)
5. [Authentication](###Authentication)
6. [Stappenplan](###Stappenplan)
6. [Rest endpoints](###Rest endpoints)

### Inleiding

In dit bestand staan alle nodige gegevens voor het installeren en gebruiken van deze applicatie.

### Benodigdheden

* Postgresql
* Java
* Een IDE naar keuze [meer info](https://spring.io/quickstart)

### Software

Er zijn verschillende programmas nodig om de applicatie te kunnen draaien.
Om te beginnen starten we met Postgres hierop draaien de databases. Postgres is eenvoudig te installeren met behulp van de [officele website hier](https://www.postgresql.org/download/). Let op dat de poort op 5432 staat en schrijf het wachtwoord wat je gebruikt op want dit hebben we later nodig. Naar postgres verbinden we via java.

Ook java moeten we downloaden en installeren dat kan [hier](https://java.com/en/download/manual.jsp). Java is de programmeertaal waarin de applicatie is geschreven, het is makkelijk om een IDE te gebruiken voor het installeren van de packages. Zelf gebruik ik Intellij om de applicatie te runnen in een test omgeving dus dat gebruiken we hier ook. Installatie vind je [hier](https://www.jetbrains.com/idea/download/).

### Test Gebruikers
Deze gebruikers zijn standaard beschikbaar om te testen, op productie zijn deze niet aanwezig. De authenticatie verloopt middels basic http authentication. Meer over de authenticatie staat verder naar onder

* Monteur

*Gebruikersnaam:* engineer

*Wachtwoord:* password


* Administration

*Gebruikersnaam:* administration

*Wachtwoord:* password


* Cashier

*Gebruikersnaam:* cashier

*Wachtwoord:* password

* Backoffice

*Gebruikersnaam:* backoffice

*Wachtwoord:* password
  

### Authenticatie

Om de applicatie veilig te laten runnen willen we gebruik maken van https. Vooral in de productieomgeving is dit van extreem belang. Om lokaal te testen kunnen we zelf een certificaat aanmaken met het volgende commando.


`keytool -genkey -keyalg RSA -alias certificate -keystore certificate.jks -storepass password -validity 365 -keysize 4096 -storetype pkcs12`


### Stappenplan

1. Installaeren Postgresql
2. Installaeren Java
3. Installaeren IDE
4. Https installeren
5. Project runnen middels IDE

### Rest endpoints

Meer info over de rest endpoints kun je vinden op url /info.