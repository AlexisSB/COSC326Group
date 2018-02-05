build: src/ice/*.java
	javac src/ice/*.java -d .

run : src/ice/*.java
	javac src/ice/*.java -d .
	java ice/PlayIce

test:
	java ice/PlayIce < sample_input.txt > output.txt
	diff sample_output.txt output.txt

clean:
	rm -rf ice/
	rm output.txt
