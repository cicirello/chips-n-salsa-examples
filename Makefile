.PHONY: build
build:
	mvn package

.PHONY: examples
examples:
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.BitVectorExample
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.GeneticAlgorithmExamples
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.IntegerVectorExample
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.RootFindingExample
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.PermutationExample
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.ConfigureRandomness
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.ParallelPermutationExample
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.TimedParallelExample
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.CustomProblemExample
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.CustomIntegerCostProblemExample
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.PostHillclimbExample
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.PreHillclimbExample
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.SchedulingExample
	mvn exec:java -q -Dexec.mainClass=org.cicirello.examples.chipsnsalsa.SchedulingWithVBSS
	
.PHONY: clean
clean:
	mvn clean	
	