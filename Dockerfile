# Utiliser l'image officielle OpenJDK
FROM openjdk:17

# Installer findutils avec apk (le gestionnaire de paquets d'Alpine)
RUN apk update && apk add findutils

# Définir le répertoire de travail
WORKDIR /app

# Copier tout le code source dans le conteneur
COPY . .

# Créer un dossier de sortie pour les fichiers compilés
RUN mkdir -p out

# Utiliser un script de compilation
RUN find src/jeu -name "*.java" > sources.txt && javac -d out @sources.txt

# Définir le point d'entrée pour exécuter le serveur
CMD ["java", "-cp", "out", "jeu.Server"]
