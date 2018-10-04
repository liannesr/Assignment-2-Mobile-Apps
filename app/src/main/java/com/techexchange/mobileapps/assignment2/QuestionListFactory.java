package com.techexchange.mobileapps.assignment2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.InputStream;
import java.util.Random;
public class QuestionListFactory {
    public static ArrayList<String> countries;
    public static ArrayList<Question> questionFactory;
    public static ArrayList<String> generateCountriesList(InputStream ins){
        String csvLine="";
        String split = ",";
        countries = new ArrayList<>();
        try(BufferedReader read = new BufferedReader(new InputStreamReader(ins))){
            csvLine = read.readLine();
            while((csvLine = read.readLine())!= null){
                String[] countryCapital = csvLine.split(split);
                countries.add(countryCapital[0].replace('"',' ' ));
            }
            ins.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return countries;
    }
    public static ArrayList<Question> genratingList(InputStream ins){
        String csvLine=" ";
        String split = ",";
        questionFactory = new ArrayList<>();
        try(BufferedReader read = new BufferedReader(new InputStreamReader(ins))){
            csvLine = read.readLine();
            while((csvLine = read.readLine())!= null){
                String[] countryCapital = csvLine.split(split);
                questionFactory.add(new Question(countryCapital[1].replace('"',' ' )+"is the capital of which country?",
                        countryCapital[0].replace('"',' ' ),
                        makeWrongAnswers(countryCapital[0].replace('"',' ' ))));
            }
            ins.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return questionFactory;
    }
    public static String[] makeWrongAnswers(String correctAnswer){
        String[] wrongAnswers = new String[3];
        int currentIndex =0;
        Random gen = new Random();
        int random = 0;
        while(currentIndex<wrongAnswers.length){
            random = gen.nextInt(248);
            if(!(countries.get(random).equals(correctAnswer))){
                wrongAnswers[currentIndex]= countries.get(random);
                currentIndex++;
            }
        }
        return wrongAnswers;
    }
}
