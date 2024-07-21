import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class lambdaPratice101 {
    public static void main(String[] args) {
        

        Employee[] arrayOfEmps = {
            new Employee(1, "Jeff Bezos", 100000.0), 
            new Employee(2, "Bill Gates", 200000.0), 
            new Employee(3, "Mark Zuckerberg", 300000.0)
        };

        //Ways to build stream
        //Way 1
        Stream.of(arrayOfEmps);
        //Way 2
        Stream.of(arrayOfEmps[0], arrayOfEmps[1], arrayOfEmps[2]);
        //Way 3
        Stream.Builder<Employee> empStreamBuilder = Stream.builder();
        empStreamBuilder.accept(arrayOfEmps[0]);
        empStreamBuilder.accept(arrayOfEmps[1]);
        empStreamBuilder.accept(arrayOfEmps[2]);
        Stream<Employee> empStream = empStreamBuilder.build();


        /*
         * Java Stream Operations
            Let’s now see some common usages and operations we can perform
            on and with the help of the stream support in the language.

            1. forEach
         */
        Stream.of(arrayOfEmps).forEach(e -> e.salaryIncrement(10.0));

        Stream.of(arrayOfEmps).forEach(e -> System.err.println(e.toString()));

        /*
         * forEach() is a terminal operation, which means that, after the operation is performed,
         * the stream pipeline is considered consumed, and can no longer be used.
         * We’ll talk more about terminal operations in the next section.
         */

         /*
        * 2.    map
            map() produces a new stream after applying a function to each element of
            the original stream. The new stream could be of different type.
          */

        Integer[] empIds = { 1, 2, 3 };
        List<Employee> empList = Stream.of(empIds).map(employeeRepository::findById).collect(Collectors.toList());
        List<Employee> empListNonFunctional = Stream.of(empIds).map((i) -> employeeRepository.findById(i)).collect(Collectors.toList());
        List<Employee> empListName = Stream.of(empIds).map(employeeRepository::findByName).collect(Collectors.toList());
        List<Employee> empListNonFunctionalName = Stream.of(empIds).map((i) -> employeeRepository.findByName(i)).collect(Collectors.toList());
        empList.stream().forEach((e) -> System.out.println(e.toString()));
        



        List<List<String>> namesNested = Arrays.asList( 
        Arrays.asList("Jeff", "Bezos"), 
        Arrays.asList("Bill", "Gates"), 
        Arrays.asList("Mark", "Zuckerberg"));

        List<String> namesFlatStream = namesNested.stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList());


        Employee[] employees = empList.stream().toArray(Employee[]::new);


        List<Employee> empList1 = Arrays.asList(arrayOfEmps);
    
        List<Employee> empList2 = empList1.stream()
        .peek(e -> e.salaryIncrement(10.0))
        .peek(System.out::println)
        .collect(Collectors.toList());

        //sum(), average(), range() 
        try{
            Double avgSal = empList1.stream()
            .mapToDouble(Employee::getSalary)
            .average()
            .orElseThrow(NoSuchFieldException::new);

            Integer sumSal = empList1.stream()
            .mapToInt(Employee::getId)
            .sum();

            Double sumSalDouble = empList.stream()
            .map(Employee::getSalary)
            .reduce(0.0, Double::sum);

            String empNames = empList.stream()
            .map(Employee::getName)
            .collect(Collectors.joining(", "))
            .toString();
            Set<String> empNamesSet = empList.stream()
            .map(Employee::getName)
            .collect(Collectors.toSet());

            Vector<String> empNamesVector = empList.stream()
            .map(Employee::getName)
            .collect(Collectors.toCollection(Vector::new));
        }catch(Exception e){
            System.err.println(e);
        }
        

        learnStream101();

    }

    private static void learnStream101() {
        //Making a list to pracice
        List<Integer> listOfInteger = new ArrayList<>(Arrays.asList(
            1,2,3,4,5,6,7,8,9,10
        ));

        //For Each loop to print all the list
        listOfInteger.forEach((i) -> System.out.print(i+" "));
        //Using Consumer Interface accept method to use lambda.
        //It take 1 input of generic type and return type is void.
        //Inside forEach method that accept method is called.

        //Keeps Count of the total elements in the list.
        System.out.println(listOfInteger.size());
        listOfInteger.add(11);
        //If you add it you add one if remove it will remove 1;
        listOfInteger.forEach((i) -> System.out.print(i+" "));
        System.out.println(listOfInteger.size());
        //.remove method remove an element at desired index if we remove
        //some middle element it re adjusts the index.
        //as it implements a arrays of arrayslist
        //so it copy paste to new place or what?
        //Checks if index is there if yes then calls fast remove method which
        //copies the arrays to new place and ignore the new index
        //As shifting is costly.
        listOfInteger.remove(5);
        System.out.println(listOfInteger.size());
        listOfInteger.forEach((i) -> System.out.print(i+" "));
        //Checks the size counter.
        System.out.println(listOfInteger.isEmpty());
        //Searching in o(n)
        listOfInteger.contains(2);
        
        //Stream to filter all elements greater than 3.
        List<Integer> listOfIntegerNew = listOfInteger.stream().filter((i) -> i>3).collect(Collectors.toList());
        listOfIntegerNew.forEach((i) -> System.out.print(i+" "));
        
        //Deletes ever element
        listOfInteger.clear();

        //Sorting with own comprator
        listOfIntegerNew.sort((i,j)->i-j);
        listOfIntegerNew.forEach((i) -> System.out.print(i+" "));

        List<Integer> number = Arrays.asList(2,3,4,5);
        number.forEach((i) -> System.out.print(i+" "));
        List<Integer> square = number.stream().map( x-> x*x ).collect(Collectors.toList());
        square.forEach((i) -> System.out.print(i+" "));

        List<String> names = Arrays.asList("Reflection","Collection","Stream");
        names.forEach((i) -> System.out.print(i+" "));
        List<String> newName = names.stream().filter(s->s.startsWith("S")).collect(Collectors.toList());
        newName.forEach((i) -> System.out.print(i+" "));

        List<String> result1 = names.stream().sorted().collect(Collectors.toList());
        result1.forEach((i) -> System.out.print(i+" "));

        List<Integer> numberList = Arrays.asList(2,3,4,5,3);
        Set<Integer> squareList = numberList.stream().map(x->x*x).sorted().collect(Collectors.toSet());
        Set<Integer> squareListSorted = squareList.stream().sorted().collect(Collectors.toSet());
        List<Integer> squareListSortedList = squareList.stream().sorted().collect(Collectors.toList());
        squareList.stream().sorted().forEach((i)-> System.out.println(i));

        List<Integer> numberListMapForEach = Arrays.asList(2,3,4,5);
        numberListMapForEach.stream().map(x->x*x).forEach(y->System.out.println(y));

        List<Integer> numberListMapReduce = Arrays.asList(2,3,4,5);
        int even = numberListMapReduce.stream().filter(x->x%2==0).reduce(0,(ans,i)-> ans+i);
    }
}
