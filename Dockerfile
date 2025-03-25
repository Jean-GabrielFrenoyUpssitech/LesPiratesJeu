# Utiliser l'image officielle OpenJDK
FROM openjdk:17

# Définir le répertoire de travail
WORKDIR /app

# Copier tout le code source dans le conteneur
COPY . .

# Créer un dossier de sortie pour les fichiers compilés
RUN mkdir -p out

# Compiler les fichiers Java (sans utiliser find)
RUN javac -d out $(ls jeu/*.java)

# Définir le point d'entrée pour exécuter le serveur
CMD ["java", "-cp", "out", "jeu.Server"]
