Ao dar run no App.java, é necessário entrar no http://localhost:8080/ para acessar o
sistema no navegador.

Por padrão há um carregamento de dados que cria um contexto fictício no sistema.
Caso queira tirar este carregamento para criação de um contexto do zero, basta
renomear o arquivo data.sql para qualquer outro nome. Ele se encontra em:
dellmegasena\src\main\resources\db\data.sql

Também, para simular um sorteio com várias apostas, uma dica é fazer uma aposta 
surpresinha e então atualizar a página de confirmação da aposta, criando novas
apostas aleatórias. Isso é útil para aumentar a chance de não apurar 25 rodadas
adicionando números ao resultado.

Caso queira, utilize também o botão manipular sorteio, presente no encerramento
das apostas. Ele força o resultado ser [1,2,3,4,5].
