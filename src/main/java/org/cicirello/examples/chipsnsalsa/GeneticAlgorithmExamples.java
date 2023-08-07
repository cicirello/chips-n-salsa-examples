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

import static org.cicirello.examples.chipsnsalsa.ExamplesShared.*;

import org.cicirello.search.SolutionCostPair;
import org.cicirello.search.evo.GeneticAlgorithm;
import org.cicirello.search.evo.InverseCostFitnessFunction;
import org.cicirello.search.evo.MutationOnlyGeneticAlgorithm;
import org.cicirello.search.evo.SimpleGeneticAlgorithm;
import org.cicirello.search.evo.StochasticUniversalSampling;
import org.cicirello.search.evo.TournamentSelection;
import org.cicirello.search.operators.bits.TwoPointCrossover;
import org.cicirello.search.problems.OneMax;
import org.cicirello.search.representations.BitVector;

/**
 * This example demonstrates some of the functionality of three of the genetic algorithm
 * implementations of the library, including the SimpleGeneticAlgorithm (single-point crossover, bit
 * flip mutation, roulette wheel selection), the GeneticAlgorithm class that enables configuring
 * different crossover operators and selection operators among other things, as well as the
 * MutationOnlyGeneticAlgorithm class. The examples use the classic OneMax problem for its
 * simplicity for the sake of the example.
 *
 * @author <a href=https://www.cicirello.org/ target=_top>Vincent A. Cicirello</a>, <a
 *     href=https://www.cicirello.org/ target=_top>https://www.cicirello.org/</a>
 */
public class GeneticAlgorithmExamples {

  /**
   * Runs the example program.
   *
   * @param args Ignored, because there are no command line arguments.
   */
  public static void main(String[] args) {
    printCopyrightAndLicense();

    // For the examples in this program, we use a constant to define
    // the population size to make it easy for you to change.
    final int POP_SIZE = 100;

    // We've defined a constant here for the number of generations
    // that we run later in the example.
    final int NUM_GENERATIONS = 100;

    // This constant is defined to make it easy to change the
    // length of the OneMax problem used in the example.
    final int BIT_LENGTH = 100;

    // Construct an instance of the OneMax problem.
    // The constructor requires no parameters.  The length
    // of the BitVectors is configured when you configure
    // the genetic algorithm.
    OneMax problem = new OneMax();

    // The OneMax class above implements the interface
    // IntegerCostOptimizationProblem. The library assumes problems
    // are such that we must minimize a cost function, which is the
    // usual way algorithms like simulated annealing, etc are described.
    // However, with a genetic algorithm and other forms of evolutionary
    // computation, we instead aim to maximize a fitness function. The
    // genetic algorithms, and more generally evolutionary algorithm,
    // classes of the library require a fitness function via a class
    // that implements either the FitnessFunction.Double or
    // FitnessFunction.Integer interfaces (the difference between these
    // two interfaces is simply the type of fitness value). Since much
    // of the library requires problems to be defined in terms of
    // minimizing cost, we also provide a couple different classes
    // that can transform costs to fitness values. Here is an example
    // that we use in this example program to transform the OneMax
    // cost function to a fitness function. It is a generic type
    // so requires the type of object under optimization, in this case
    // BitVector.
    InverseCostFitnessFunction<BitVector> fitness =
        new InverseCostFitnessFunction<BitVector>(problem);

    // We also define a couple constants for the examples for the
    // mutation rate, and crossover rate, that we use for all of the
    // example genetic algorithms that we configure later in this
    // example program. These were set rather arbitrarily, so I don't
    // claim these to be the best options. Mutation rate is often set
    // to lead to an expected number of bits flipped per population member
    // equal to 1 (or some other small number of bits).
    final double MUTATION_RATE = 1.0 / BIT_LENGTH;
    final double CROSSOVER_RATE = 0.7;

    // Let's now define a SimpleGeneticAlgorithm, which uses Bit Flip Mutation,
    // Single-Point Crossover, and Fitness Proportional Selection (a.k.a., weighted
    // roulette wheel).
    SimpleGeneticAlgorithm sga =
        new SimpleGeneticAlgorithm(POP_SIZE, BIT_LENGTH, fitness, MUTATION_RATE, CROSSOVER_RATE);

    // What if we don't want the Simple GA? Perhaps we want to use a different crossover
    // operator than single-point, and/or a different selection operator than fitness
    // proportional. The library includes all of the most common crossover operators for
    // bit vectors, including single-point, two-point, k-point, and uniform. Look in the
    // documentation for classes that implement CrossoverOperator, and in particular those that
    // are implement CrossoverOperator<BitVector>. It also includes all of the most common
    // selection operators (see the various classes that implement SelectionOperator). In this
    // example, we use TwoPointCrossover and we use the selection operator
    // StochasticUniversalSampling.
    // To use these, we also use the GeneticAlgorithm class.
    GeneticAlgorithm ga =
        new GeneticAlgorithm(
            POP_SIZE,
            BIT_LENGTH,
            fitness,
            MUTATION_RATE,
            new TwoPointCrossover(),
            CROSSOVER_RATE,
            new StochasticUniversalSampling());

    // Perhaps we only want to use mutation. The library also has a class
    // MutationOnlyGeneticAlgorithm
    // for that purpose. In this example, we also demonstrate another of the selection operators,
    // Tournament Selection, with a tournament size of 4 (which we chose arbitrarily for the sake of
    // the
    // example). For this example, we also increased the mutation rate slightly since all it has
    // is mutation and no crossover in this example).
    MutationOnlyGeneticAlgorithm moga =
        new MutationOnlyGeneticAlgorithm(
            POP_SIZE, BIT_LENGTH, fitness, 2.0 * MUTATION_RATE, new TournamentSelection(4));

    // We can now run each of these genetic algorithms using the optimize method.
    // This method returns a SolutionCostPair, where the type parameter is the type
    // of object we are optimizing, in this case a BitVector. The solution contained
    // in this object is the best found across all generations.
    SolutionCostPair<BitVector> sgaSolution = sga.optimize(NUM_GENERATIONS);
    SolutionCostPair<BitVector> gaSolution = ga.optimize(NUM_GENERATIONS);
    SolutionCostPair<BitVector> mogaSolution = moga.optimize(NUM_GENERATIONS);

    // We can then use the getSolution method to access the solution.
    BitVector sgaBest = sgaSolution.getSolution();
    BitVector gaBest = gaSolution.getSolution();
    BitVector mogaBest = mogaSolution.getSolution();

    // The SolutionCostPair object returned by optimize also contains
    // the cost of that solution (in terms of the optimization criteria).
    // In this example, the cost will be equal to the number of 0 bits.
    // A cost of 0 means that all bits are equal to 1, the optimal solution.
    int sgaCost = sgaSolution.getCost();
    int gaCost = gaSolution.getCost();
    int mogaCost = mogaSolution.getCost();

    // The OneMax class that implements the optimization function, as well as
    // any class that implements IntegerCostOptimizationProblem, has a cost method
    // that would provide the same cost as above, but also includes a value method,
    // which computes the natural optimization function, which for a minimization
    // problem would be the same as cost, but for a maximization problem like
    // OneMax would be the value we are maximizing. So we can pass the solution
    // we earlier obtained from the SolutionCostPair to the value method.
    int sgaValue = problem.value(sgaBest);
    int gaValue = problem.value(gaBest);
    int mogaValue = problem.value(mogaBest);

    // If you want to know the fitness value that was used by the GA, you can
    // call the fitness method of the FitnessFunction object. The fitness
    // function that we used was via the InverseCostFitnessFunction class, which
    // computes fitness as: a / (a + cost(s), for some constant a, in this case
    // 1 / (1 + cost(s)). The reason we didn't use a fitness function simply
    // equal to the number of 1-bits, which we were maximizing is that some of
    // the selection operators of the library assume fitness values that are
    // positive, with undefuned behavior if fitness is negative or zero, and
    // since 0 is a possible count of 1-bits, we used a transformation that results
    // in positive fitness. We did this in the example because FitnessProportionalSelection,
    // used by the SimpleGeneticAlgorithm class, and StochasticUniversalSampling,
    // which we used in one of the other examples both have this requirement.
    // TournamentSelection does not have this requirement and works fine with zero and
    // negative fitness. Here's an example of getting the fitness of a solution.
    double sgaFitness = fitness.fitness(sgaBest);
    double gaFitness = fitness.fitness(gaBest);
    double mogaFitness = fitness.fitness(mogaBest);

    // Let's now just output the results.
    System.out.println("\nComparison of Three GA Variations on a OneMax Problem");
    System.out.printf("%8s %12s %12s %12s%n", "Metric", "SimpleGA", "GA", "MutationOnly");
    System.out.printf("%8s %12d %12d %12d%n", "Cost", sgaCost, gaCost, mogaCost);
    System.out.printf("%8s %12d %12d %12d%n", "Value", sgaValue, gaValue, mogaValue);
    System.out.printf("%8s %12.8f %12.8f %12.8f%n", "Fitness", sgaFitness, gaFitness, mogaFitness);
    System.out.println();
  }
}
