![Telesport](/chatop.png)


# Installation du projet

**1. Cloner le projet**
git clone git@github.com:AllanMont/OC_P3.git

**2. Se diriger vers le back-end**
cd backend

**3. Installer les dépendances**
mvn clean install

**4.Lancer le back-end**
mvn spring-boot:run


**5. Ouvrir un nouveau terminal et se diriger vers le dossier de front-end**
cd ../front

**6. Installer les dépendances**
npm install

**7. Lancer le front-end**
npm run start








# Installation de la BDD

**1. Creation d'un schéma**
CREATE database OCP3;

**2.Se diriger vers le schéma**
use OCP3;

**3.Ajout des valeurs**
Copier-coller l'intégralité du fichier .sql dans front/ressources/sql






# Url de Swagger

http://localhost:8080/swagger-ui/index.html#/