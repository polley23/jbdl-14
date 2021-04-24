package com.gfg;

import com.gfg.exceptions.CustomException;
import com.gfg.modelsClass.Aeroplane;

import java.io.*;
import java.util.*;

import static java.lang.System.exit;
import static java.lang.System.in;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //creating an object of Aeroplane class
//        Aeroplane aeroplane = new Aeroplane(
//                "air india", new Date(),100 );
//        System.out.println("number of seats : "+ aeroplane.getNumberOfSeats());

//        Bird bird = new Bird(2,100);
//        main.showHowItWalks(bird);
//        Butterfly butterfly = new Butterfly(2,10);
//       showHowItWalks(butterfly);
//       System.out.println("number of wings : "+ butterfly.getNumberOfWings() );
//        showHowItFlies(butterfly);
//
//        try {
//            calculator(-10, 0, "divide");
//        }catch (ArithmeticException | CustomException e) {
//            System.out.println(e.getMessage());
//        }
//     finally {
//        System.out.println("In finally block");
//    }

        //Autoboxing
//        Integer i = 10;
//        System.out.println("haschcode is : "+ i .hashCode());
//        i = 20 ; //creates a new object
//        System.out.println("haschcode is : "+ i .hashCode());
//        //unboxing
//        int j  = i;

        //Set<Integer> set = new HashSet<>();
        //Set<Integer> set = new LinkedHashSet<>();
//        Set<Integer> set = new TreeSet<>();
//        set.add(10); set.add(30); set.add(20);
//        for(Integer value : set){
//            System.out.println(value);
//        }
//        //List<Integer> list  = new Vector<>();
//        List<Integer> list  = new LinkedList<>();
//        list.add(10); list.add(30); list.add(20);
//        Collections.sort(list, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o2-o1;
//            }
//        });
//        for(Integer value : list){
//            System.out.println(value);
//        }

        //input stream
//        Scanner scanner = new Scanner(System.in);
//        while(scanner.hasNext()){
//            System.out.println(scanner.next());
//        }
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            String input = bufferedReader.readLine();
//            while (input != null){
//                System.out.println(input);
//                input = bufferedReader.readLine();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File file  = new File("myFile.txt");
//        FileWriter fileWriter = null;
//        try {
//             fileWriter = new FileWriter(file,true);
//            String stringToWrite = "Hello World ";
//
//            for(Character c : stringToWrite.toCharArray()){
//                fileWriter.append(c);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                fileWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            try {
            String input = bufferedReader.readLine();
            while (input != null){
                System.out.println(input);
                input = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    //polymorphysim
    static void showHowItWalks(FlyingSpecies flyingSpecies){

        flyingSpecies.walk();

    }
    //abstraction. We don't want to understand how it is implemented
    //We just want to have a behaviour;
    static void showHowItFlies( IFly ifly){
        ifly.fly();
    }

    static void calculator(int a , int b , String opt) throws CustomException {
        Float c = 0f;
        if (opt.equals("divide")){
            if (a < 0 || b < 0){
                throw new CustomException("input is negative");
            }
            c = Float.valueOf(a / b);
            System.out.println(c); }
    }
}
