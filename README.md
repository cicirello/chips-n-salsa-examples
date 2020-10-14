# Example Programs for Usage of the Chips-n-Salsa Library

Copyright (C) 2020 Vincent A. Cicirello. https://www.cicirello.org/

[![build](https://github.com/cicirello/chips-n-salsa-examples/workflows/build/badge.svg)](https://github.com/cicirello/chips-n-salsa-examples/actions?query=workflow%3Abuild)

This repository contains several example programs of the usage of the [Chips-n-Salsa](https://chips-n-salsa.cicirello.org) 
library. Chips-n-Salsa is a Java library of customizable, hybridizable, iterative, parallel, stochastic, and self-adaptive 
local search algorithms. [Chips-n-Salsa's source code](https://github.com/cicirello/Chips-n-Salsa) is maintained on GitHub,
and the prebuilt jars of the library can be imported from [Maven Central](https://search.maven.org/artifact/org.cicirello/chips-n-salsa) 
using maven or other build tools. The purpose of this repository is to demonstrate usage of the major functionality of the
Chips-n-Salsa library.

## Versioning Scheme

The version numbers for the example programs mirror the versions of
Chips-n-Salsa. The version may or may not be incremented upon every new release
of the Chips-n-Salsa library. The version number of the examples corresponds
to the version of the Chips-n-Salsa library used in that release.
The examples should run against newer versions of the Chips-n-Salsa library
provided the MAJOR portion of the version number is the same.  

The Chips-n-Salsa library uses [Semantic Versioning](https://semver.org/) with 
version numbers of the form: MAJOR.MINOR.PATCH, where differences 
in MAJOR correspond to incompatible API changes, differences in MINOR 
correspond to introduction of backwards compatible new functionality, 
and PATCH corresponds to backwards compatible bug fixes.

## Prebuilt Jars of the Examples

Since the purpose of the example programs is to demonstrate usage of the
Chips-n-Salsa library, you will most likely want to build the examples
directly from the source (see the sections that follow).  However, we
also provide jars of the compiled examples, as well as of the source and javadocs, 
in a variety of ways:
* [GitHub releases](https://github.com/cicirello/chips-n-salsa-examples/releases)
* [Maven Central](https://search.maven.org/artifact/org.cicirello/chips-n-salsa-examples)
* [GitHub Packages](https://github.com/cicirello?tab=packages&repo_name=chips-n-salsa-examples)

If you use the example programs in precompiled form, you will also need
a compatible version of the Chips-n-Salsa library, and its dependencies. If you
build the examples from the source, the build process will take care of downloading these
for you. But if you choose to use the prebuilt jars, you can find the jars
of the [Chips-n-Salsa library](https://github.com/cicirello/Chips-n-Salsa) 
likewise in Maven Central, GitHub Releases, and GitHub Packages.  In particular,
you will find a jarred version of Chips-n-Salsa that includes all of its dependencies.

## Requirements to Build and Run the Example Programs from the Source

To build and run the examples on your own machine, you will need the following:
* __JDK 11__: I used OpenJDK 11, but you should be fine with Oracle's JDK as well. Technically, there is nothing in the code that strictly requires Java 11, so you should be able to build and run with JDK 8 or later. However, the Maven pom.xml provided in the repository assumes Java 11, so you will need to modify the pom.xml if you want to compile with earlier JDK versions. 
* __Apache Maven__: In the root of the repository, there is a pom.xml for building the example programs. Using this pom.xml, Maven will take care of downloading the most recent version of the [Chips-n-Salsa](https://chips-n-salsa.cicirello.org/) library for which the examples have been tested, as well as Chips-n-Salsa's dependencies. The examples should also work with more recent versions of the library.
* __Make__ (optional): The repository also contains a Makefile to simplify running the build, and running the example programs. If you are familiar with using the Maven build tool, then you can just run these directly, although the Makefile may be useful to see the specific commands needed, such as the main classes to execute for the example programs.

## Building the Example Programs with Maven

The source code of the example programs are
in the [src/main/java](src/main/java) directory.  You can build the example 
programs in one of the following ways:
* Execute `mvn package` at the root of the repository (`mvn compile` should also be sufficient, but only generates `class` files, while `mvn package` generates `jar` files as well as the javadocs).
* Execute `make` or `make build` at the root of the repository (which simply executes a `mvn package`). 

This build process follows the usual Maven directory structure, so 
the `.class` files, `.jar` files, etc will be found in a `target` 
directory that is created by the build process.

## Running the Example Programs with Maven

Once you have successfully executed the build above, you can run the 
examples by executing `make examples` at the root of the 
repository. This will run each of the example programs in sequence.
Be aware that the examples are intended to illustrate how to use
the Chips-n-Salsa library, so will be most meaningful if you read the
source code, which includes comments explaining what they are doing.

If you would rather run them one at a time, see the `examples` target
in the `Makefile` for the main classes to execute.

## License

The example programs in this repository are licensed under 
the [GNU General Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).