/*
 * Example programs for Chips-n-Salsa library.
 * Copyright (C) 2020-2023 Vincent A. Cicirello
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
 
package org.cicirello.examples.chipsnsalsa;

import java.util.random.RandomGenerator;
import java.util.SplittableRandom;
import org.cicirello.search.operators.bits.BitFlipMutation;
import org.cicirello.search.operators.bits.BitVectorInitializer;
import org.cicirello.search.representations.BitVector;
import org.cicirello.search.Configurator;
import static org.cicirello.examples.chipsnsalsa.ExamplesShared.*;

/**
 * This example demonstrates how to use the Configurator class to configure
 * the pseudorandom number generator used by the library.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, 
 * <a href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public class ConfigureRandomness {
  
  /**
	 * Runs the example program.
	 * @param args Ignored, because there are no command line arguments.
	 */
	public static void main(String[] args) {
		printCopyrightAndLicense();
    
    // The Configurator class provides a couple static methods that can be
    // used to configure the pseudorandom number generator (PRNG) used by 
    // the library. There is no requirement to do so, as the library has a 
    // default PRNG. But if you'd prefer to use a different PRNG or if you
    // have the need to seed the PRNG to enable replicating runs of your
    // program, then the Configurator class enable this.
    //
    // Note: The methods of the Configurator class only affect the PRNG
    // used by components of the library that have been created afterwards.
    // For example, if you construct a CrossoverOperator instance first,
    // and then call one of the methods of the Configurator, it will have
    // no effect on the PRNG used by that CrossoverOperator that was already
    // instantiated.
    //
    // Therefore, if you choose to use the Configurator, it is recommended
    // that you use a single call to one of its methods at the start of your
    // program before you start instantiating metaheuristics, and operators,
    // etc.
    //
    // There are two methods that you can use to configure the PRNG. The first
    // specifies the PRNG instance from which all other instances will be
    // derived. It requires a RandomGenerator.SplittableGenerator because the
    // library utilizes split() to provide each operator, metaheuristic, etc
    // with its own PRNG instance, and also utilizes split() when using 
    // parallel metaheuristic.
    
    Configurator.configureRandomGenerator(RandomGenerator.SplittableGenerator.of("L64X128MixRandom"));
    
    // If you don't care about the specific PRNG algorithm, but want to instead
    // specify a seed for the default PRNG, then you can configure with a long 
    //valued seed:
    
    Configurator.configureRandomGenerator(42L);
    
    // Note that in this case, we've now seeded the factory that generates PRNG
    // instances, and have essentially undone the earlier call where we changed
    // the PRNG algorithm.
    
    // Here's a demonstration of the effects of the seed. Let's now initialize
    // a BitVectorInitializer that creates random BitVectors of length 32, and
    // a BitFlipMutation with a mutation rate of 0.25 (deliberately higher than
    // typical to demonstrate the replicability of the randomness with a seed).
    
    BitVectorInitializer initializer = new BitVectorInitializer(32);
    BitFlipMutation mutation = new BitFlipMutation(0.25);
    
    // Now let's create a BitVector the initializer.
    
    BitVector x = initializer.createCandidateSolution();
    
    // Next, let's treat it as a 32-bit int, perform a sequence of mutations,
    // treating each result as 32-bit ints, storing the ints in an array (for
    // later inspection).
    
    int[] firstRun = new int[10];
    firstRun[0] = x.get32(0);
    for (int i = 1; i < firstRun.length; i++) {
      mutation.mutate(x);
      firstRun[i] = x.get32(0);
    }
    
    // Now reconfigure the PRNG with the same seed as earlier:
    
    Configurator.configureRandomGenerator(42L);
    
    // Construct new instances of BitVectorInitializer and BitFlipMutation
    // but parameterized the same as before.
    
    initializer = new BitVectorInitializer(32);
    mutation = new BitFlipMutation(0.25);
    
    // Use the initializer to create a new random BitVector:
    
    x = initializer.createCandidateSolution();
    
    // Now repeat the sequence of mutations, treating results as 32-bit ints,
    // and storing in an array (for later inspection).
    
    int[] secondRun = new int[10];
    secondRun[0] = x.get32(0);
    for (int i = 1; i < secondRun.length; i++) {
      mutation.mutate(x);
      secondRun[i] = x.get32(0);
    }
    
    // Finally, output results to demonstrate that seeding the PRNG produced
    // same sequence of random operations.
    
    System.out.println("Demonstrating Configurator for specifying seed to exactly replicate behavior.");
    for (int i = 0; i < firstRun.length; i++) {
      String isSame = firstRun[i] == secondRun[i] ? "same as expected" : "different (uh oh, please report bug)";
      System.out.printf("%d\t%d\t%s\n", firstRun[i], secondRun[i], isSame);
    }
    System.out.println();
    
    // If you want to both change the PRNG algorithm, as well as seed it, then 
    // you would use the first approach and pass a pre-seeded PRNG, such as the
    // following:
    
    Configurator.configureRandomGenerator(new SplittableRandom(100L));
  }
}
