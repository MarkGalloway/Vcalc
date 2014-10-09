make:
	export CLASSPATH=$CLASSPATH:.:/usr/local/lib/antlr-3.3-complete.jar
	antlr3 *\.g
	javac -cp antlr-3.3-complete.jar *\.java
