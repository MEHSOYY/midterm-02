/* Project: Java Collections and Generics Lab
 * Class: Midterm2.java
 * Author: MEHMET SOYDAN
 * Date: 04/30/2025
 * This program demonstrates advanced Java concepts, including generics and functional interfaces
 * by implementing methods to merge collections of different types using BiFunctions.
 */
package academy.javapro;

import java.util.*;
import java.util.function.BiFunction;

public class Midterm2 {

    // ---------------------------------------------------------------
    // Simple Person class used for demonstration
    static class Person {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // ---------------------------------------------------------------
        // Provides meaningful string representation of Person objects
        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }

    // ---------------------------------------------------------------
    // Merges two collections of different types into a new collection
    public static <T, S, R> List<R> mergeCollections(
            Collection<T> firstCollection,
            Collection<S> secondCollection,
            BiFunction<T, S, R> mergeFunction) {

        // Validate collection sizes before merging
        if (firstCollection.size() != secondCollection.size()) {
            throw new IllegalArgumentException(
                    "Collections must have the same size for element-wise merging. " +
                    "First collection size: " + firstCollection.size() +
                    ", Second collection size: " + secondCollection.size());
        }

        // Initialize result list with appropriate capacity
        List<R> mergedResults = new ArrayList<>(firstCollection.size());

        // Convert collections to arrays for indexed access
        T[] firstArray = (T[]) firstCollection.toArray();
        S[] secondArray = (S[]) secondCollection.toArray();

        // Merge elements pairwise using the provided function
        for (int index = 0; index < firstArray.length; index++) {
            R mergedElement = mergeFunction.apply(firstArray[index], secondArray[index]);
            mergedResults.add(mergedElement);
        }

        return mergedResults;
    }

    // ---------------------------------------------------------------
    // Alternative implementation that pairs elements until exhaustion
    public static <T, S, R> List<R> zipCollections(
            Collection<T> firstCollection,
            Collection<S> secondCollection,
            BiFunction<T, S, R> mergeFunction) {

        // Initialize result list with minimum capacity
        List<R> zippedResults = new ArrayList<>(
            Math.min(firstCollection.size(), secondCollection.size())
        );

        // Get iterators for both collections
        Iterator<T> firstIterator = firstCollection.iterator();
        Iterator<S> secondIterator = secondCollection.iterator();

        // Process elements while both collections have items
        while (firstIterator.hasNext() && secondIterator.hasNext()) {
            R zippedElement = mergeFunction.apply(
                firstIterator.next(), 
                secondIterator.next()
            );
            zippedResults.add(zippedElement);
        }

        return zippedResults;
    }

    // ---------------------------------------------------------------
    // Demonstrates collection merging with various examples
    public static void main(String[] args) {
        demonstrateNumberWordMerging();
        demonstratePersonCreation();
        demonstrateMathematicalOperations();
        demonstrateDifferentSizedCollections();
    }

    // ---------------------------------------------------------------
    // Example 1: Merging numbers with their word representations
    private static void demonstrateNumberWordMerging() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<String> words = Arrays.asList("one", "two", "three", "four", "five");

        BiFunction<Integer, String, String> numberWordMerger =
                (number, word) -> number + " = " + word;

        List<String> combinedNumberWords = mergeCollections(numbers, words, numberWordMerger);

        System.out.println("Example 1: Merging numbers with their word representations");
        combinedNumberWords.forEach(System.out::println);
        System.out.println();
    }

    // ---------------------------------------------------------------
    // Example 2: Creating Person objects from names and ages
    private static void demonstratePersonCreation() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<Integer> ages = Arrays.asList(25, 30, 22);

        BiFunction<String, Integer, Person> personCreator = Person::new;
        List<Person> people = mergeCollections(names, ages, personCreator);

        System.out.println("Example 2: Creating Person objects from names and ages");
        people.forEach(System.out::println);
        System.out.println();
    }

    // ---------------------------------------------------------------
    // Example 3: Mathematical operations demonstration
    private static void demonstrateMathematicalOperations() {
        List<Double> firstNumbers = Arrays.asList(1.5, 2.5, 3.5);
        List<Double> secondNumbers = Arrays.asList(0.5, 1.0, 1.5);

        BiFunction<Double, Double, Double> sum = Double::sum;
        BiFunction<Double, Double, Double> product = (a, b) -> a * b;
        BiFunction<Double, Double, String> comparisonResult =
                (a, b) -> a + " vs " + b + ": " + 
                    (a > b ? "First is larger" : a < b ? "Second is larger" : "Both are equal");

        List<Double> sums = zipCollections(firstNumbers, secondNumbers, sum);
        List<Double> products = zipCollections(firstNumbers, secondNumbers, product);
        List<String> comparisons = zipCollections(firstNumbers, secondNumbers, comparisonResult);

        System.out.println("Example 3: Mathematical operations");
        System.out.println("Sums: " + sums);
        System.out.println("Products: " + products);
        System.out.println("Comparisons:");
        comparisons.forEach(System.out::println);
        System.out.println();
    }

    // ---------------------------------------------------------------
    // Example 4: Handling different sized collections
    private static void demonstrateDifferentSizedCollections() {
        List<Character> letters = Arrays.asList('A', 'B', 'C', 'D', 'E');
        List<Integer> positions = Arrays.asList(1, 2, 3);

        BiFunction<Character, Integer, String> positionedLetter =
                (letter, position) -> position + ". " + letter;

        List<String> lettersWithPositions = zipCollections(letters, positions, positionedLetter);

        System.out.println("Example 4: Using different sized collections");
        lettersWithPositions.forEach(System.out::println);
    }
}
