# Mini-projet Spring Boot — Gestion de tickets de support informatique

## 1. Présentation du projet

Ce projet consiste à développer une API REST de gestion de tickets de support informatique avec **Java**, **Spring Boot** et une base de données **H2**.

L’application permet à des utilisateurs de signaler des problèmes techniques sous forme de tickets. Ces tickets peuvent ensuite être consultés, commentés, catégorisés et assignés à des agents support.

Le projet est réaliste, car il correspond à un vrai besoin dans une école, une entreprise, un centre de formation ou un service informatique.

---

## 2. Objectif principal

L’objectif est de créer une API sécurisée permettant :

- à un utilisateur de créer un ticket pour signaler un problème ;
- à un agent de traiter les tickets ;
- à un administrateur de gérer les utilisateurs, les catégories et les assignations.

Le projet respecte l’idée d’un mini-système de helpdesk.

---

## 3. Exemple de cas réel

Un utilisateur rencontre un problème :

- impossibilité de se connecter au wifi ;
- mot de passe oublié ;
- imprimante en panne ;
- erreur d’installation d’un logiciel.

Il crée alors un ticket avec un titre, une description, une priorité et une catégorie.

Ensuite :
- un agent peut être assigné au ticket ;
- des commentaires peuvent être ajoutés pour suivre le traitement ;
- le statut du ticket évolue jusqu’à sa résolution.

---

## 4. Technologies utilisées

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- H2 Database
- Swagger / OpenAPI
- Postman

---

## 5. Acteurs du système

### USER
Utilisateur simple.

Il peut :
- créer un ticket ;
- consulter ses tickets ;
- consulter le détail d’un ticket ;
- ajouter un commentaire sur un ticket qu’il a créé.

### AGENT
Agent de support informatique.

Il peut :
- consulter les tickets ;
- consulter les tickets qui lui sont assignés ;
- ajouter des commentaires ;
- mettre à jour le statut d’un ticket ;
- participer au traitement du ticket.

### ADMIN
Administrateur du système.

Il peut :
- gérer les utilisateurs ;
- gérer les catégories ;
- consulter tous les tickets ;
- assigner des agents aux tickets ;
- accéder aux statistiques ;
- supprimer certaines ressources si nécessaire.

---

## 6. Modélisation des données

Pour simplifier, chaque utilisateur possède un **seul rôle**.

### Entités principales

- User
- Category
- Ticket
- TicketComment
- TicketAssignment

### Enumérations

- RoleName
- TicketStatus
- TicketPriority

---

## 7. Description des entités

### User
Représente une personne utilisant le système.

Attributs possibles :
- id
- name
- email
- password
- role

### Category
Permet de classer les tickets.

Exemples :
- Réseau
- Logiciel
- Matériel
- Compte utilisateur
- Sécurité

Attributs :
- id
- name
- description

### Ticket
Entité principale du projet.

Attributs :
- id
- title
- description
- status
- priority
- createdAt
- updatedAt
- creator
- category

### TicketComment
Permet de suivre les échanges sur un ticket.

Attributs :
- id
- content
- createdAt
- ticket
- author

### TicketAssignment
Permet d’assigner un agent à un ticket.

Attributs :
- id
- assignedAt
- ticket
- agent

---

## 8. Relations entre les entités

Cette partie est importante car le projet demande explicitement des relations entre entités.

### 8.1 Relations 1-n

#### 1. User -> Ticket
**Type :** 1-n

**Signification :**  
Un utilisateur peut créer plusieurs tickets, mais un ticket est créé par un seul utilisateur.

**Exemple :**  
L’utilisateur Alice peut créer :
- Ticket 1 : Problème de wifi
- Ticket 2 : Mot de passe oublié

Mais chaque ticket n’a qu’un seul créateur.

---

#### 2. Category -> Ticket
**Type :** 1-n

**Signification :**  
Une catégorie peut contenir plusieurs tickets, mais un ticket appartient à une seule catégorie.

**Exemple :**
La catégorie `Réseau` peut contenir :
- ticket wifi
- ticket câble Ethernet
- ticket accès Internet

Mais un ticket donné appartient à une seule catégorie.

---

#### 3. Ticket -> TicketComment
**Type :** 1-n

**Signification :**  
Un ticket peut avoir plusieurs commentaires, mais un commentaire appartient à un seul ticket.

**Exemple :**
Le ticket `Impossible de se connecter au wifi` peut contenir :
- commentaire utilisateur
- commentaire agent
- commentaire de clôture

---

#### 4. User -> TicketComment
**Type :** 1-n

**Signification :**  
Un utilisateur peut écrire plusieurs commentaires, mais un commentaire est écrit par un seul utilisateur.

**Exemple :**
L’agent Bob peut commenter plusieurs tickets différents.

---

### 8.2 Relation n-n

#### Ticket <-> User via TicketAssignment
**Type réel métier :** n-n  
**Type en base de données :** n-n modélisé avec une table intermédiaire

**Signification :**
- un ticket peut être assigné à plusieurs agents ;
- un agent peut être assigné à plusieurs tickets.

Comme une relation n-n directe est moins propre à gérer en pratique, on utilise l’entité intermédiaire **TicketAssignment**.

---

### 8.3 Pourquoi utiliser TicketAssignment

L’entité `TicketAssignment` permet de transformer la relation n-n en deux relations 1-n :

- **Ticket -> TicketAssignment** : 1-n
- **User (agent) -> TicketAssignment** : 1-n

Donc :

- un ticket possède plusieurs assignations ;
- un agent possède plusieurs assignations ;
- chaque assignation relie exactement un ticket et un agent.

---

### 8.4 Exemple concret de relation n-n

Supposons :

- Ticket A : problème réseau
- Ticket B : problème logiciel

- Agent Bob
- Agent Marie

Cas possibles :
- Ticket A assigné à Bob
- Ticket A assigné à Marie
- Ticket B assigné à Bob

Alors :
- Ticket A a plusieurs agents
- Bob traite plusieurs tickets

C’est donc bien une relation **n-n**.

---

### 8.5 Résumé des relations

#### Relations 1-n
- **User -> Ticket**
- **Category -> Ticket**
- **Ticket -> TicketComment**
- **User -> TicketComment**
- **Ticket -> TicketAssignment**
- **User -> TicketAssignment**

#### Relation n-n
- **Ticket <-> User** via **TicketAssignment**

---

### 8.6 Schéma logique simplifié

```text
User (1) -------- (n) Ticket
User (1) -------- (n) TicketComment
Category (1) ---- (n) Ticket
Ticket (1) ------ (n) TicketComment

Ticket (1) ------ (n) TicketAssignment (n) ------ (1) User

=> ce qui représente en réalité :
Ticket (n) <------> (n) User
```

---

## 9. Exemple de fonctionnement

1. Un utilisateur s’inscrit ou se connecte.
2. Il crée un ticket avec :
   - un titre ;
   - une description ;
   - une priorité ;
   - une catégorie.
3. Le ticket est enregistré avec le statut `OPEN`.
4. Un administrateur assigne un agent.
5. L’agent ajoute un commentaire.
6. Le statut devient `IN_PROGRESS`.
7. Une fois le problème résolu, le statut devient `RESOLVED` ou `CLOSED`.

---

## 10. Sécurité

L’API utilise **Spring Security** avec **JWT**.

### Fonctionnement
- l’utilisateur se connecte ;
- il reçoit un token JWT ;
- ce token est envoyé dans le header `Authorization` à chaque requête protégée.

Exemple :
`Authorization: Bearer <token>`

---

## 11. Endpoints principaux

### Authentification
- `POST /api/auth/register`
- `POST /api/auth/login`

### Users
- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`
- `GET /api/users/me`

### Categories
- `GET /api/categories`
- `GET /api/categories/{id}`
- `POST /api/categories`
- `PUT /api/categories/{id}`
- `DELETE /api/categories/{id}`

### Tickets
- `GET /api/tickets`
- `GET /api/tickets/{id}`
- `POST /api/tickets`
- `PUT /api/tickets/{id}`
- `DELETE /api/tickets/{id}`

### Commentaires
- `GET /api/tickets/{ticketId}/comments`
- `POST /api/tickets/{ticketId}/comments`

### Assignations
- `GET /api/tickets/{ticketId}/assignments`
- `POST /api/tickets/{ticketId}/assignments`
- `DELETE /api/assignments/{id}`

### Statistiques
- `GET /api/tickets/stats/by-category`
- `GET /api/tickets/stats/by-status`

---

## 12. Filtres possibles

Pour rendre l’API plus utile, il est possible d’ajouter des filtres sur les tickets :

- par statut ;
- par priorité ;
- par catégorie ;
- par créateur ;
- par date ;
- par agent assigné.

Exemple :
`GET /api/tickets?status=OPEN&priority=HIGH`

---

## 13. Endpoints d’agrégation

Le projet demande au moins deux endpoints avec des retours plus complexes.

### 1. Nombre de tickets par catégorie
`GET /api/tickets/stats/by-category`

Exemple de réponse :
```json
[
  { "category": "Réseau", "total": 8 },
  { "category": "Logiciel", "total": 5 }
]
```

### 2. Nombre de tickets par statut
`GET /api/tickets/stats/by-status`

Exemple de réponse :
```json
[
  { "status": "OPEN", "total": 4 },
  { "status": "IN_PROGRESS", "total": 6 },
  { "status": "RESOLVED", "total": 2 }
]
```

---

## 14. HATEOAS

Le projet peut aussi intégrer HATEOAS sur certaines ressources, surtout sur les tickets.

Exemple :
un ticket peut contenir des liens vers :
- lui-même ;
- sa catégorie ;
- ses commentaires ;
- ses assignations.

Cela permet de rendre l’API plus navigable.

---

## 15. Pourquoi ce projet est un bon choix

Ce projet est intéressant car il est :

- réaliste ;
- professionnel ;
- faisable dans le cadre d’un mini-projet ;
- compatible avec Spring Boot et JPA ;
- suffisamment riche pour respecter toutes les consignes.

Il permet aussi de montrer plusieurs compétences :
- modélisation de données ;
- conception REST ;
- sécurité JWT ;
- gestion des rôles ;
- filtrage ;
- agrégation ;
- documentation Swagger.

---

## 16. Conclusion

La gestion de tickets de support informatique est un excellent sujet de mini-projet.

Il est assez simple pour rester faisable, mais assez sérieux pour donner une bonne impression.  
Il couvre les besoins techniques du projet tout en restant cohérent sur le plan métier.

C’est donc un très bon compromis entre simplicité, réalisme et qualité de conception.
