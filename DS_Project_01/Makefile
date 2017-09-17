clean:
	-rm -f *.class

compile:
	javac *.java

jar:
	jar cmf DS_01_Server.mf Server.jar *.class *.java 404*

run:
	-java -jar Server.jar

all:
	make clean
	make compile
	make jar
	make -k run

