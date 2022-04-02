# Mediscreen Patient

Base PostGres SQL

https://www.pgadmin.org/download/pgadmin-4-windows/

Une fois PgAdmin installé et lancé
la base est créée automatiquement lors d'une création d'un patient

Contraintes d'ajout d'un patient

Family: la longueur de ce champ doit être entre  min = 2, max = 60 et non vide,

Given : la longueur de ce champ doit être entre  min = 2, max = 60 et non vide,

Dob : le format de date yyyy-MM-dd et non null,

Sex : la longueur de ce champ doit être entre  min = 1, max = 1 et non vide,

Adress : la longueur de ce champ doit être entre  min = 2, max = 100,

Phone : la longueur de ce champ doit être entre  min = 2, max = 20.

API Docs : http://localhost:8081/swagger-ui.html#