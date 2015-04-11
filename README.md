# YouTubeDatabaseProject
Installation Instructions -Windows7

1. Make sure you have connection to internet.

2. Put all java files, mysql-connector-java-5.1.31-bin.jar, log.txt (empty), stopwords.txt in one folder, then in cd to that folder in Terminal.

3. Copy and paste the following commands at command line:

javac -classpath .;mysql-connector-java-5.1.31-bin.jar GUISearchAdv.java GUISearchBasic.java GUISearchYT.java MainGUI.java MainMenuHandler.java Globals.java SearchDBButtonListener.java SearchYTButtonListener.java MySQLUtility.java LinkFinder.java LoginListener.java LoginGUI.java Main.java GUIUpdate.java UpdateListener.java DeleteListener.java GUIDelete.java

java -cp .;mysql-connector-java-5.1.31-bin.jar Main GUISearchAdv GUISearchBasic GUISearchYT MainGUI MainMenuHandler Globals SearchDBButtonListener SearchYTButtonListener MySQLUtility LinkFinder LoginListener LoginGUI GUIUpdate UpdateListener DeleteListener GUIDelete


Note: If for some reason above javac command does not work--though it did on my machine--try:

javac -Xlint:unchecked -classpath .;mysql-connector-java-5.1.31-bin.jar GUISearchAdv.java GUISearchBasic.java GUISearchYT.java MainGUI.java MainMenuHandler.java Globals.java SearchDBButtonListener.java SearchYTButtonListener.java MySQLUtility.java LinkFinder.java LoginListener.java LoginGUI.java Main.java GUIUpdate.java UpdateListener.java DeleteListener.java GUIDelete.java

The examples used to create the User Manual were done with this application compiled on Windows 7 Professional SP1 on a MacBookPro, using JDK 1.8.0_05. Log.txt is empty. There is no existing youtubedatabase on machine initially.

