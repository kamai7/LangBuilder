Set oShell = CreateObject ("Wscript.Shell") 
Dim strArgs
strArgs = "cmd /c java -cp ""app/class;lib/mysql/mysql-connector-j-9.3.0.jar;app/lib/junit/junit-4.11.jar;app/lib/junit/hamcrest-core-1.3.jar"" --module-path app/lib/javafx --add-modules javafx.controls,javafx.fxml,javafx.web app.LangBuilderApp"
oShell.Run strArgs, 0, false