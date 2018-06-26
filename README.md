#SoccerSPB

Projeto de Sistemas Distribuidos utilizando bigdata de Jogos de Football europeu

Utiliza as seguintes bibliotecas:

httpcore-4.4.9 - Biblioteca para requests http
spymemcached - Biblioteca cliente para o Memcached Server
JSON simple - Biblioteca de Json
sqlite-jdbc - Biblioteca para controlar o arquivo de dados

Compilar servidor.ServerManager e db_creator.DBLoader com as bibliotecas acima.
Rodar db_creator.DBLoader com o arquivo database.sqlite na raiz
Rodar servidor.ServerManager com o config.json e o arquivo de dados criado anteriormente "databaseSD_new.sqlite" na raiz