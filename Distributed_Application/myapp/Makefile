server:
	javac Server.java RemoteInterface.java BookIds.java -d .
	rmiregistry &
	java myapp.Server

client:
	javac Client.java RemoteInterface.java BookIds.java -d .
	java myapp.Client

test1:
	javac Test1.java RemoteInterface.java BookIds.java -d .
	java myapp.Test1

test2:
	javac Test2_main.java Test2.java RemoteInterface.java BookIds.java -d .
	java myapp.Test2_main

clean:
	\rm -rf myapp
