pwd
PROJECT_DIR=$(pwd)
cd $PROJECT_DIR/discovery-service
pwd
./mvnw spring-boot:run &
cd $PROJECT_DIR/bank-api
pwd
./mvnw spring-boot:run &
#cd $PROJECT_DIR/micro
#pwd
#mvn spring-boot:run &
cd $PROJECT_DIR/Backend/Endor.IncomeExpensesService
pwd
dotnet run &
cd $PROJECT_DIR/api-gateway
pwd
./mvnw spring-boot:run &