echo ""
echo ""


echo "-------- spengo l'esecuzione presente"
docker compose down

current_path_dir=$(basename "$(pwd)")

echo ""

read -p "Vuoi effettuare la build maven del progetto BE? (y/n): " buildMavenBE
if [[ $buildMavenBE == [Yy] ]]; then
    echo ""
    echo "-------- lancio della build del be"
    cd backend
    mvn clean install
    cd ..
    docker rmi "$current_path_dir-be-spring-security"
fi

echo ""

read -p "Vuoi effettuare la build npm del progetto FE? (y/n): " buildNpmFE
if [[ $buildNpmFE == [Yy] ]]; then
    echo ""
    echo "-------- lancio della build del fe"
    cd frontend
    sh npm run build
    cd ..
    docker rmi "$current_path_dir-frontend"
fi

echo ""
echo "-------- lancio del deploy"
docker compose up -d

echo ""
echo ""