# MOIS-ENDOR

## .NET certificate - Linux

There is a problem on Linux with .NET certificate. Generate certificate using appropriate 
script in create-dotnet-devcert folder:

`./ubuntu-create-dotnet-devcert.sh -s`

Import certificate to java keystore:

`keytool -import -alias dotnet -keystore $JAVA_HOME/lib/security/cacerts -file home/$USER/dotnet-devcert.crt`

Default keytool password: `changeit`

Maybe restart needed.

* * *

## Order of microservices run:
1. Discovery service
2. IncomeExpenses + Bank-api
3. Api-gateway