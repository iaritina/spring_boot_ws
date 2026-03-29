# Utilisation des collections Postman

Ce projet fournit des fichiers Postman pour tester l'API en local et en production :

- `tpWS.local_collection.json` : collection pour l'environnement local
- `tpWS.prod_collection.json` : collection pour l'environnement de production
- `tpWS.environment.json` : environnement partagé contenant les variables d'URL

## Importer dans Postman

1. Ouvrir Postman.
2. Cliquer sur `Import`.
3. Importer les fichiers suivants :
   - `tpWS.local_collection.json`
   - `tpWS.prod_collection.json`
   - `tpWS.environment.json`
4. Vérifier que l'environnement `microservice-spring` apparaît dans Postman.

## Variables d'environnement

Le fichier `tpWS.environment.json` contient les variables suivantes :

- `baseUrl = http://localhost:8080`
- `baseUrlProd = https://spring-boot-ws.onrender.com`

Les collections utilisent ces variables :

- la collection locale utilise `{{baseUrl}}`
- la collection production utilise `{{baseUrlProd}}`

## Utiliser la collection locale

1. Démarrer l'application en local sur `http://localhost:8080`.
2. Dans Postman, sélectionner l'environnement `microservice-spring`.
3. Ouvrir la collection `Spring Boot`.
4. Exécuter la requête `Login` pour récupérer un token si nécessaire.
5. Tester ensuite les autres endpoints de la collection.

## Utiliser la collection de production

1. Dans Postman, sélectionner l'environnement `microservice-spring`.
2. Ouvrir la collection `Spring Boot Online`.
3. Exécuter la requête `Login`.
4. Tester ensuite les endpoints exposés par l'instance de production.

## Authentification

Certaines requêtes contiennent déjà un token Bearer d'exemple. Si ce token a expiré :

1. lancer la requête `Login`
2. copier le token retourné par l'API
3. remplacer le token Bearer dans la requête que vous voulez exécuter

## Remarque

La majorité des requêtes utilisent les variables d'environnement, mais certaines requêtes peuvent encore contenir une URL écrite en dur. Dans ce cas, adaptez l'URL ou remplacez-la par la variable correspondante dans Postman.
