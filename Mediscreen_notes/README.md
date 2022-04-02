# Mediscreen Notes 

Base Mongo

https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/

Une fois la base Mongo installée et lancée

la base et les collections sont créés automatiquement lors d'une création d'une Note
si le patient existe.

Contraintes d'ajout d'une Note

patId: Requis, non vide, l'id du patient doit être présent dans la base SQL du MicroService Patient

note: Requis, non vide

API Docs : http://localhost:8082/swagger-ui.html#


