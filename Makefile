all: run

clean:
	rm -f out/Main.jar out/Invert.jar
	rm -f src/*.class

out/Main.jar: out/parcs.jar src/Main.java src/Invert.java
	@javac -cp out/parcs.jar src/Main.java src/Invert.java
	@jar cf out/Main.jar -C src Main.class -C src Invert.class
	@rm -f src/Main.class src/Invert.class

out/Invert.jar: out/parcs.jar src/Invert.java
	@javac -cp out/parcs.jar src/Invert.java
	@jar cf out/Invert.jar -C src Invert.class
	@rm -f src/Invert.class

build: out/Main.jar out/Invert.jar

run: build
	@cd out && java -cp 'parcs.jar:Main.jar:Invert.jar' Main