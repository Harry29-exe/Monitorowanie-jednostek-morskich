<h1>Aplikacja do monitorowania jednostek morskich</h1>
<h2>Kamil Wójcik</h2>
<h2>Pomysł</h2>
  <span>
    Aplikacja pozwala na monitorowanie położenia statków w okół wybrzerza norwegii (na podstawie danych udostępnionych przez API BarentsWatch). Użytkownik aplikacji ma również możliwość rejestracji oraz logowania dzięki czemu uzyskuje możliwość do włączenia śledzenia wybranych przez niego statków.
  </span>
  
  
  
<h2>Jak zainstalować</h2>
  <h3>Uwagi</h3>
    <ul>
      <li>Aplikacja została wykonana w opraciu o poniższe api 
        </br>https://www.barentswatch.no/en/about/open-data-via-barentswatch/
        </br>https://www.barentswatch.no/minside/
      </li>
      <li>
        Aby zalogować się do powyższego api potrzebne są client oraz secret które w poniższych instrukcjach uruchomienia aplikacji nalezy podać
        zamist następujących tagów: &lt;secret>, &lt;client>
      </li>
      <li>
        W filmikach pokazujących uruchamianie aplikacji oraz zgłoszeniu zostały podane przykładowe dane
        logowania do api które moga być wykorzstane do przetestowania aplikacji
      </li>
    </ul>
  <h3>Sposób 1 (dla systemów z obługą bash'a)</h3>
  <h4>Wymagania</h4>
  <ul>
    <li>zainstalowany doker</li>
    <li>system obługujący bash</li>
  </ul>
  <h4>Filmik pokazujący poniższe instrukcje: https://youtu.be/luM0zmKOpQ4</h4>
  
   [![Film pokazujący instalacje](https://img.youtube.com/vi/luM0zmKOpQ4/0.jpg)](https://www.youtube.com/watch?v=luM0zmKOpQ4)
  <h4>Instrukcja</h4>
  <span>
    #Kopjujemy niniejsze repozytorim do dowolnego folderu
    git clone https://github.com/Harry29-exe/Monitorowanie-jednostek-morskich

    #Wchodzimy do folderu ze skryptem uruchamiającym</br>
    cd Monitorowanie-jednostek-morskich/backend

    #uruchamiamy skrypt (przywileje administratora potrzebne są do uruchomienia kontenerów docker)

    sudo bash ./build-start.sh <secret> <client>
  </span>
  <h4></h4>
  
  <h3>Sposób 2 (dla pozostalych)</h3>
  <h4>Wymagania</h4>
  <ul>
    <li>zainstalowany doker</li>
  </ul>
  <h4>Filmik pokazujący poniższe instrukcje: https://youtu.be/luM0zmKOpQ4</h4>
  
   [![Film pokazujący instalacje](https://img.youtube.com/vi/CKhZpAm48Kg/0.jpg)](https://www.youtube.com/watch?v=CKhZpAm48Kg)
  <h4>Instrukcja</h4>
  <span>

    #Kopjujemy niniejsze repozytorim do dowolnego folderu
    git clone https://github.com/Harry29-exe/Monitorowanie-jednostek-morskich

    #Wchodzimy do folderu z backendem aplikcji</br>
    cd Monitorowanie-jednostek-morskich/backend/postgres
    #budujemy oraz uruchamiamy bazę danych
    sudo docker build ./postgres --tag mjm-db;
    sudo docker run -p 5432:5432 -d --name mjm-db mjm-db;
    
    #budujemy oraz uruchamiamy aplikację
	  sudo docker build ./ --tag mjm;
    sudo docker run -p 8080:8080 -e secret=<secret> -e clientId=<client> --net=host --name mjm mjm

  </span>
  
  
