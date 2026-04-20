# Pokémon Combat - Java

Simulation de combats Pokémon en ligne de commande, avec gestion de base de données, équipes personnalisées et adversaire IA.

---

## Aperçu

Le projet reproduit le système de combat au tour par tour de la série Pokémon. Le joueur constitue son équipe en choisissant parmi les 151 Pokémon de la première génération, puis affronte soit un autre joueur soit une IA dont l'équipe est générée aléatoirement.

Les données (Pokémon, attaques, types, efficacités) sont stockées en base de données SQL et chargées à la volée via des classes DAO dédiées.

---

## Prérequis

- Java 11 ou supérieur
- Une base de données SQL accessible (MySQL / MariaDB recommandé)
- Un driver JDBC correspondant à votre SGBD dans le classpath
- Les tables `pokemons`, `attaques`, `pokemon_attaque`, `types` et `efficacite` populées

---

## Structure du projet

```
Pokemon-Project-main/
└── src/
    ├── Main.java           # Point d'entrée
    ├── Partie.java         # Orchestration complète (menus, tours de combat)
    ├── Combat.java         # Logique de victoire
    ├── Joueur.java         # Classe de base joueur
    ├── JoueurHumain.java   # Joueur humain (badges, victoires)
    ├── JoueurIA.java       # Adversaire IA (arène, ville)
    ├── Pokemon.java        # Entité Pokémon avec stats et attaques
    ├── Attaque.java        # Entité attaque
    ├── Etat.java           # États altérés (poison, brûlure, sommeil…)
    ├── Type.java           # Type élémentaire
    ├── AttaqueDAO.java     # Accès BDD — attaques
    ├── PokemonDAO.java     # Accès BDD — Pokémon
    ├── TypeDAO.java        # Accès BDD — types et efficacités
    └── DatabaseManager.java # Gestion de la connexion JDBC
```

---

## Lancer le projet

1. Compilez tous les fichiers `.java` :
   ```bash
   javac -cp .:<driver>.jar src/*.java -d out/
   ```

2. Exécutez la classe principale :
   ```bash
   java -cp out/:<driver>.jar Main
   ```

3. Suivez les instructions dans le terminal : choix du mode (1 joueur / 2 joueurs), sélection des équipes, puis le combat démarre automatiquement.

---

## Déroulement d'une partie

1. **Choix du mode** — 1 joueur contre l'IA ou 2 joueurs en local.
2. **Constitution des équipes** — chaque joueur humain choisit 6 Pokémon parmi les 151 disponibles. L'IA pioche les siens aléatoirement.
3. **Sélection du Pokémon de départ** — chaque joueur désigne son premier combattant.
4. **Tours de combat** — à chaque tour : attaquer, changer de Pokémon, ou fuir. L'ordre d'action dépend de la statistique Vitesse.
5. **Fin de partie** — le combat s'arrête quand tous les Pokémon d'un camp sont K.O. ou qu'un joueur fuit.

---

## Schéma de base de données (attendu)

| Table             | Colonnes principales                                      |
|-------------------|-----------------------------------------------------------|
| `pokemons`        | id, nom, pv, pvMax, attaque, defense, vitesse             |
| `attaques`        | id, libelle, puissance, type_id                           |
| `pokemon_attaque` | fkPokemon, fkAttaque                                      |
| `types`           | id, libelle                                               |
| `efficacite`      | fkAtt, fkDef, multi                                       |

---

## Améliorations envisageables

- Intégration complète des effets d'états (poison, paralysie, sommeil…) dans le calcul des dégâts
- Application du multiplicateur de type dans `Pokemon.attaquer()`
- Sauvegarde du score / historique des parties
- Interface graphique (Swing ou JavaFX)

---

## Auteurs

Projet réalisé dans le cadre d'un cours de développement Java orienté objet.
