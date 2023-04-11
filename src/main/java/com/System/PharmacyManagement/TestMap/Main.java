package com.System.PharmacyManagement.TestMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
//        List<Person> people = Arrays.asList(
//                new Person("Alice", 25, "Engineer"),
//                new Person("Bob", 30, "Teacher"),
//                new Person("Charlie", 35, "Doctor")
//        );
//
//        Map<String, List<String>> nameToInfoMap = people.stream()
//                .collect(Collectors.toMap(
//                        Person::getName,
//                        p -> Arrays.asList(Integer.toString(p.getAge()), p.getJob())
//                ));
//
//        System.out.println(nameToInfoMap);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println(evenNumbers);
        System.out.println(numbers.stream().count());

    }
}
