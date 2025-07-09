javac --module-path lib/javafx --add-modules javafx.controls,javafx.fxml,javafx.web -Xlint:unchecked  -cp "class;lib/mysql/mysql-connector-j-9.3.0.jar;lib/junit/junit-4.11.jar;lib/junit/hamcrest-core-1.3.jar" -d class $(Get-ChildItem -Recurse -Filter *.java -Path .\src | ForEach-Object { $_.FullName })
java -cp "class;lib/mysql/mysql-connector-j-9.3.0.jar;lib/junit/junit-4.11.jar;lib/junit/hamcrest-core-1.3.jar" --module-path lib/javafx --add-modules javafx.controls,javafx.fxml,javafx.web app.AdminApp

Read-Host -Prompt "Press Enter to exit"