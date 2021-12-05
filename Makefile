# DO NOT EDIT THIS FILE

SUBMIT := $(wildcard src/main/java/org/cis120/**/*.java src/test/java/org/cis120/**/*.java files/** README.txt)

HWNAME := hw09
ts := $(shell /bin/date "+%Y-%m-%d-%H:%M:%S")

ZIPNAME := $(HWNAME)-submit($(ts)).zip

.PHONY: all compile run zip test test-stack-trace checkstyle format clean

all:	

compile:
	mvn compile

run: compile
	mvn exec:java

zip: $(SUBMIT)
	zip -j '$(ZIPNAME)' $(SUBMIT)

test: 
	mvn test

test-stack-trace:
	mvn -DtrimStackTrace=false test

checkstyle: 
	mvn checkstyle:checkstyle

format:
	mvn formatter:format

clean:
	rm -f src/*.class bin/* test/*.class
	rm -rf *.zip
	mvn clean
