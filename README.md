E-StoryMaker

Das Projekt ist in zwei Teile aufgeteilt: der Server (im Branch "Server") und der Client (im Branch "Client").
Da der Server nicht online gehostet ist, muss fürs Testen mithilfe von lokalen hosten dementsprechend beim Client die ipv4 des Host-PCs angegeben werden. 
Unter hsfl.project.e_storymaker.models.remoteDataSource.constURL.kt muss die ipv4 des Hosts in Zeile 3 eingefügt werden.

Um den Server zu starten werden die Umgebungsvariablen "JWT_SECRET=storyMaker ;HASH_SECRET_KEY=MySuperSecretHashKey" benötigt
