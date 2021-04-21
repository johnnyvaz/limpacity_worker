VERSION=$(mvn -q com.smartcodeltd:release-candidate-maven-plugin:LATEST:version -DoutputTemplate="{{ version }}")​


echo "publish app $2 $VERSION in cluster $1"

mkdir package

mv pom.xml package
mv Procfile package
mv target/*.jar package
mv system.properties package

