# Documentation — Pokémon Combat (Java)

Documentation complète de toutes les classes et méthodes du projet.  

⚠️⚠️Toute les Méthodes ne sont pas implementer au jeu mais nous vous partageons notre vision de celle-ci ⚠️⚠️

---

## Sommaire

1. [Main](#main)
2. [Partie](#partie)
3. [Combat](#combat)
4. [Joueur](#joueur)
5. [JoueurHumain](#joueurhumain)
6. [JoueurIA](#joueuriia)
7. [Pokemon](#pokemon)
8. [Attaque](#attaque)
9. [Etat](#etat)
10. [Type](#type)
11. [AttaqueDAO](#attaquedao)
12. [PokemonDAO](#pokemondao)
13. [TypeDAO](#typedao)

---

## Main

**Fichier :** `Main.java`  
Point d'entrée du programme. Initialise le gestionnaire de base de données, instancie la partie et enchaîne la phase de setup avec la phase de combat.

### `main(String[] args)`

| | |
|---|---|
| **Visibilité** | `public static` |
| **Retour** | `void` |

Lance l'application. Crée un `DatabaseManager` et une `Partie`, appelle `debutPartie()` pour constituer les équipes, puis passe le résultat à `combat()`.

---

## Partie

**Fichier :** `Partie.java`  
Classe centrale qui orchestre l'intégralité d'une partie : création des joueurs, sélection des équipes, boucle de combat et gestion des tours.

### Constantes

| Constante | Valeur | Rôle |
|---|---|---|
| `NB_POKEMON_PAR_EQUIPE` | `6` | Taille fixe d'une équipe |

### `debutPartie(DatabaseManager dbm)`

| | |
|---|---|
| **Visibilité** | `public` |
| **Paramètre** | `dbm` — instance active du gestionnaire de BDD |
| **Retour** | `Joueur[]` — tableau de deux joueurs prêts à combattre |

Affiche le menu de sélection du mode de jeu (1 joueur vs IA, ou 2 joueurs). Crée les instances `JoueurHumain` et/ou `JoueurIA`, charge leurs équipes et retourne les deux joueurs dans un tableau.

---

### `combat(Joueur[] tabJoueur)`

| | |
|---|---|
| **Visibilité** | `public` |
| **Paramètre** | `tabJoueur` — les deux joueurs issus de `debutPartie()` |
| **Retour** | `void` |

Affiche les règles, déclenche la sélection du Pokémon de départ pour chaque joueur, puis entre dans la boucle principale qui appelle `tourCombat()` en continu jusqu'à ce qu'un résultat soit atteint.

---

### `tourCombat(Joueur j1, Joueur j2)`

| | |
|---|---|
| **Visibilité** | `public` |
| **Paramètres** | `j1`, `j2` — les deux joueurs |
| **Retour** | `boolean` — `true` si le combat continue, `false` s'il est terminé |

Cœur de la boucle de jeu. Pour chaque tour :
1. Affiche l'état du combat (PV des deux Pokémon actifs).
2. Collecte les actions de chaque joueur (attaque, changement, fuite) — aléatoirement pour l'IA.
3. Détermine l'ordre d'attaque en comparant les statistiques de vitesse.
4. Résout les attaques via `resoudreAttaque()`.
5. Gère les K.O. et demande au joueur concerné de choisir un remplaçant.
6. Retourne `false` si un camp n'a plus de Pokémon ou si un joueur fuit.

---

### `creationJH(DatabaseManager dbm)` *(privée)*

| | |
|---|---|
| **Visibilité** | `private` |
| **Paramètre** | `dbm` — gestionnaire de BDD |
| **Retour** | `JoueurHumain` |

Demande le nom du joueur humain dans la console, puis délègue à `choisirPokemonJH()` pour constituer l'équipe. Retourne l'instance `JoueurHumain` construite.

---

### `choisirPokemonJH(DatabaseManager dbm)` *(privée)*

| | |
|---|---|
| **Visibilité** | `private` |
| **Paramètre** | `dbm` — gestionnaire de BDD |
| **Retour** | `Pokemon[]` — équipe de 6 Pokémon |

Affiche la liste complète des 151 Pokémon disponibles, puis demande 6 fois au joueur de saisir un identifiant valide. Charge chaque Pokémon avec ses attaques via `PokemonDAO` et `AttaqueDAO`.

---

### `choisirPokemonCombat(Joueur joueur)`

| | |
|---|---|
| **Visibilité** | `public` |
| **Paramètre** | `joueur` — le joueur qui doit choisir |
| **Retour** | `Pokemon` — le Pokémon sélectionné, ou `null` si l'équipe est entièrement K.O. |

Comportement différencié selon le type de joueur :
- **`JoueurIA`** : sélectionne aléatoirement un Pokémon encore en vie dans l'équipe.
- **`JoueurHumain`** : affiche l'équipe avec les PV restants et attend un choix valide (ne propose que les Pokémon ayant des PV > 0).

Met à jour le Pokémon actif du joueur via `setPokemonActif()`.

---

### `afficherEtatCombat(Joueur j1, Joueur j2)` *(privée)*

| | |
|---|---|
| **Visibilité** | `private` |
| **Paramètres** | `j1`, `j2` |
| **Retour** | `void` |

Affiche dans la console un récapitulatif visuel de l'état en cours : nom du dresseur, nom du Pokémon actif et ses PV actuels / maximum, pour les deux camps.

---

### `choisirAction(Joueur joueur)` *(privée)*

| | |
|---|---|
| **Visibilité** | `private` |
| **Paramètre** | `joueur` |
| **Retour** | `int` — 1 (attaquer), 2 (changer de Pokémon), 3 (fuir) |

Affiche le menu d'actions et lit le choix de l'utilisateur. Boucle tant que la saisie est hors de la plage 1-3.

---

### `choisirAttaque(Joueur joueur)` *(privée)*

| | |
|---|---|
| **Visibilité** | `private` |
| **Paramètre** | `joueur` |
| **Retour** | `int` — index de l'attaque choisie (0 à 3) |

Affiche les 4 attaques du Pokémon actif avec leur puissance. Retourne l'index correspondant au choix du joueur (valeur saisie − 1).

---

### `tousKO(Joueur joueur)` *(privée)*

| | |
|---|---|
| **Visibilité** | `private` |
| **Paramètre** | `joueur` |
| **Retour** | `boolean` — `true` si tous les Pokémon ont 0 PV |

Parcourt l'équipe du joueur et retourne `true` dès que tous les membres ont leurs PV à zéro.

---

### `resoudreAttaque(Joueur attaquant, Joueur defenseur, int indexAttaque)` *(privée)*

| | |
|---|---|
| **Visibilité** | `private` |
| **Paramètres** | `attaquant`, `defenseur`, `indexAttaque` — index dans le tableau d'attaques |
| **Retour** | `boolean` — `true` si le Pokémon défenseur tombe K.O. |

Récupère l'attaque ciblée sur le Pokémon actif de l'attaquant, appelle `Pokemon.attaquer()`, affiche les dégâts infligés et retourne `true` si les PV du défenseur tombent à 0.

---

### `pause(int ms)`

| | |
|---|---|
| **Visibilité** | `public static` |
| **Paramètre** | `ms` — durée en millisecondes |
| **Retour** | `void` |

Suspend l'exécution du thread courant pendant la durée indiquée. Utilisé dans tout le projet pour rythmer l'affichage dans la console.

---

## Combat

**Fichier :** `Combat.java`  
Classe utilitaire qui encapsule les deux joueurs d'un combat et expose la logique de détection de victoire.

### `Combat(Joueur joueur1, Joueur joueur2)`

Constructeur. Stocke les deux participants.

---

### `victoire(Joueur joueur1, Joueur joueur2)`

| | |
|---|---|
| **Visibilité** | `public` |
| **Paramètres** | `joueur1`, `joueur2` |
| **Retour** | `Joueur` vainqueur, ou `null` si le combat est encore en cours |

Vérifie si le nombre de Pokémon K.O. d'un joueur est égal à la taille d'équipe. Si `joueur1` a tous ses Pokémon K.O., retourne `joueur2` comme vainqueur, et vice versa. Retourne `null` si aucune condition n'est remplie.

---

## Joueur

**Fichier :** `Joueur.java`  
Classe abstraite de base pour tous les types de joueurs.

### Constantes

| Constante | Valeur |
|---|---|
| `TAILLE_EQUIPE` | `6` |

### `Joueur(String nom, Pokemon[] equipe)`

Constructeur. Initialise le nom, l'équipe et positionne automatiquement `equipe[0]` comme Pokémon actif.

---

### `afficherJoueur()`

| | |
|---|---|
| **Visibilité** | `public` |
| **Retour** | `void` |

Affiche dans la console le nom du dresseur, puis pour chaque Pokémon de l'équipe : son nom, ses PV et la liste de ses attaques. Des pauses sont insérées pour un rendu progressif.

---

### `pause(int ms)`

| | |
|---|---|
| **Visibilité** | `public static` |

Identique à `Partie.pause()`. Suspend le thread courant pendant `ms` millisecondes.

---

### Getters / Setters

| Méthode | Rôle |
|---|---|
| `getNom()` | Retourne le nom du joueur |
| `getEquipe()` | Retourne le tableau des 6 Pokémon |
| `getPokemonActif()` / `setPokemonActif()` | Pokémon actuellement en combat |
| `getPokemonKO()` / `setPokemonKO()` | Compteur de Pokémon K.O. |
| `getTailleEquipe()` *(static)* | Retourne la constante `TAILLE_EQUIPE` |

---

## JoueurHumain

**Fichier :** `JoueurHumain.java`  
Étend `Joueur`. Représente un joueur humain avec un suivi de progression (badges, victoires).

### `JoueurHumain(String nom, Pokemon[] equipe)`

Constructeur. Appelle `super(nom, equipe)`.

---

### Getters / Setters spécifiques

| Méthode | Rôle |
|---|---|
| `getNbBadges()` / `setNbBadges()` | Nombre de badges remportés |
| `getNbVictoires()` / `setNbVictoires()` | Nombre de victoires |
| `isBadges()` / `setBadges()` | Indicateur booléen de possession de badges |

---

## JoueurIA

**Fichier :** `JoueurIA.java`  
Étend `Joueur`. Représente un adversaire contrôlé par le programme, rattaché à une arène.

### `JoueurIA(String nom, Pokemon[] equipe)`

Constructeur. Appelle `super(nom, equipe)`.

---

### Getters / Setters spécifiques

| Méthode | Rôle |
|---|---|
| `getArene()` / `setArene()` | Numéro d'arène associé à l'IA |
| `getNomArene()` / `setNomArene()` | Nom de l'arène |
| `getNomVille()` / `setNomVille()` | Ville de l'arène |

---

## Pokemon

**Fichier :** `Pokemon.java`  
Entité centrale du projet. Regroupe toutes les statistiques d'un Pokémon, ses attaques et son état.

### `Pokemon(int id, String nom, int pv, int pvMax, int attaque, int defense, int vitesse, Attaque[] attaques)`

Constructeur complet. Toutes les statistiques sont `final` sauf `pv` (qui évolue pendant le combat) et `types` / `etat`.

---

### `attaquer(Attaque attaque, Pokemon cible)`

| | |
|---|---|
| **Visibilité** | `public` |
| **Paramètres** | `attaque` — l'attaque utilisée ; `cible` — le Pokémon qui reçoit les dégâts |
| **Retour** | `int` — dégâts effectivement infligés |

Calcule les dégâts selon la formule :

```
dégâts = puissance_attaque + stat_attaque_attaquant - stat_defense_cible
```

Le résultat est plafonné à 0 (ne peut pas être négatif). Retire les dégâts des PV de la cible, puis s'assure que les PV ne descendent pas en dessous de 0.

> **Note :** Le multiplicateur de type (`TypeDAO.recupMulti()`) n'est pas encore appliqué dans cette formule.

---

### `attaqueByIndex(int index)`

| | |
|---|---|
| **Visibilité** | `public` |
| **Paramètre** | `index` — position dans le tableau d'attaques (0 à 3) |
| **Retour** | `Attaque` correspondante |

Accès direct à une attaque par son indice.

---

### `getAttaques()`

| | |
|---|---|
| **Visibilité** | `public` |
| **Retour** | `String` — concaténation du `toString()` des 4 attaques |

Retourne une représentation textuelle des 4 attaques du Pokémon.

---

### Getters / Setters

| Méthode | Rôle |
|---|---|
| `getId()` | Identifiant BDD |
| `getNom()` | Nom du Pokémon |
| `getPv()` / `setPv()` | Points de vie courants |
| `getPvMax()` | Points de vie maximum |
| `getAttaque()` | Statistique d'attaque |
| `getDefense()` | Statistique de défense |
| `getVitesse()` | Statistique de vitesse (détermine l'ordre d'attaque) |
| `getTypes()` / `setTypes()` | Types élémentaires |
| `getEtat()` | État altéré actuel |

---

## Attaque

**Fichier :** `Attaque.java`  
Représente une attaque avec son libellé, sa puissance et son type.

### `Attaque(String libelle, int puissance, int typeId)`

Constructeur. Initialise les trois attributs.

---

### `toString()`

Retourne une chaîne formatée : `Attaque : <libelle>, Puissance=<puissance>, TypeID=<typeId>`.

---

### Getters / Setters

| Méthode | Rôle |
|---|---|
| `getLibelle()` / `setLibelle()` | Nom de l'attaque |
| `getPuissance()` / `setPuissance()` | Valeur de puissance |
| `getTypeId()` / `setTypeId()` | Identifiant du type en BDD |

---

## Etat

**Fichier :** `Etat.java`  
Gère les états altérés d'un Pokémon via une énumération interne.

### États disponibles

| Nom | Abréviation | Inflige des dégâts |
|---|---|---|
| `NORMAL` | — | Non |
| `POISON` | PSN | Oui |
| `TOXIC` | PSN | Oui |
| `BRULURE` | BRN | Oui |
| `PARALYSIE` | PAR | Oui |
| `SOMMEIL` | SLP | Non |
| `CONGELATION` | FRZ | Non |
| `KO` | — | Non |

### `Etat()`

Constructeur. Initialise l'état à `NORMAL`.

---

### `setEtat(String etat)`

| | |
|---|---|
| **Visibilité** | `public` |
| **Paramètre** | `etat` — chaîne insensible à la casse (`"poison"`, `"brulure"`, etc.) |
| **Retour** | `void` |

Convertit la chaîne reçue en valeur d'énumération correspondante. Une valeur `null` ou non reconnue réinitialise l'état à `NORMAL`.

---

### `getEtat()`

Retourne la valeur courante de l'énumération `EtatEnum`.

---

## Type

**Fichier :** `Type.java`  
Entité simple représentant un type élémentaire (Feu, Eau, Plante…).

### `Type(String nom)`

Constructeur. Stocke le nom du type.

---

### `getNom()`

Retourne le nom du type.

---

## AttaqueDAO

**Fichier :** `AttaqueDAO.java`  
Couche d'accès aux données pour la table `attaques` et la table de liaison `pokemon_attaque`.

### Constantes

| Constante | Valeur | Rôle |
|---|---|---|
| `MAX_ATTAQUES` | `4` | Nombre max d'attaques par Pokémon |
| `MAX_ATTAQUE_OVERSIZE` | `165` | Taille du tableau pour charger toutes les attaques |

### `AttaqueDAO()`

Constructeur. Instancie un `DatabaseManager` et ouvre la connexion.

---

### `chargeAttaque()`

| | |
|---|---|
| **Visibilité** | `public` |
| **Retour** | `Attaque[]` — tableau de toutes les attaques en base |

Exécute `SELECT * FROM attaques` et hydrate un tableau d'objets `Attaque`. Les cases non remplies restent `null`.

---

### `recupAttaquesPokemon(int fkPokemon)`

| | |
|---|---|
| **Visibilité** | `public` |
| **Paramètre** | `fkPokemon` — identifiant du Pokémon |
| **Retour** | `Attaque[]` — tableau de 4 attaques (peut contenir des `null` si le Pokémon a moins de 4 attaques) |

Effectue une jointure entre `attaques` et `pokemon_attaque` pour récupérer les attaques associées à un Pokémon donné, dans la limite de `MAX_ATTAQUES`.

---

## PokemonDAO

**Fichier :** `PokemonDAO.java`  
Couche d'accès aux données pour la table `pokemons`.

### Constantes

| Constante | Valeur | Rôle |
|---|---|---|
| `GEN1` | `151` | Nombre de Pokémon de la première génération |

### `chargerTous()`

| | |
|---|---|
| **Visibilité** | `public` |
| **Retour** | `void` |

Charge et affiche dans la console tous les Pokémon présents en base (`id`, `nom`, `pv`, `pvMax`, `attaque`, `defense`, `vitesse`). Une pause de 50 ms entre chaque ligne fluidifie la lecture. Gère l'ouverture et la fermeture de la connexion en interne.

---

### `chargerParId(int id, Attaque[] tabAttaque)`

| | |
|---|---|
| **Visibilité** | `public` |
| **Paramètres** | `id` — identifiant du Pokémon ; `tabAttaque` — tableau d'attaques préchargé |
| **Retour** | `Pokemon` instancié, ou `null` si l'id n'existe pas |

Exécute un `SELECT * FROM pokemons WHERE id = ?` et construit l'objet `Pokemon` avec les statistiques issues de la base et les attaques fournies en paramètre. Ouvre et ferme la connexion à chaque appel.

---

### `pause(int ms)`

| | |
|---|---|
| **Visibilité** | `public static` |

Suspend le thread courant. Même comportement que `Partie.pause()`.

---

## TypeDAO

**Fichier :** `TypeDAO.java`  
Couche d'accès aux données pour les tables `types` et `efficacite`.

### Constantes

| Constante | Valeur | Rôle |
|---|---|---|
| `GEN1` | `15` | Nombre de types de la première génération |

### `TypeDAO()`

Constructeur. Instancie un `DatabaseManager` et tente d'ouvrir la connexion.

---

### `chargeType(DatabaseManager dbm)`

| | |
|---|---|
| **Visibilité** | `public` |
| **Paramètre** | `dbm` — connexion active |
| **Retour** | `Type[]` — tableau des types disponibles |

Exécute `SELECT * FROM types` et remplit un tableau d'objets `Type` avec les libellés récupérés.

---

### `recupMulti(int fkAtt, int fkDef, DatabaseManager dbm)`

| | |
|---|---|
| **Visibilité** | `public` |
| **Paramètres** | `fkAtt` — id du type attaquant ; `fkDef` — id du type défenseur ; `dbm` — connexion active |
| **Retour** | `double` — multiplicateur d'efficacité (ex. 2.0, 0.5, 0.0). Retourne `1` par défaut si aucun résultat. |

Interroge la table `efficacite` pour retrouver le coefficient de dégâts entre deux types. Cette valeur est destinée à être utilisée dans le calcul de dégâts de `Pokemon.attaquer()`.
