# Utilise une image Java officielle
FROM openjdk:17

# Définit le répertoire de travail
WORKDIR /app

# Copie tous les fichiers du projet dans le conteneur
COPY . .

# Compile le projet (si ton code est dans "src", adapte cette ligne)
RUN javac -d out $(find . -name "*.java")

# Exécute le serveur (remplace "jeu.Server" par ta classe main)
CMD ["java", "-cp", "out", "jeu.Server"]