package com.ruchi.engine.sentiment.machineLearning;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentiWordNet {

    private Map<String, Double> dictionary;
    static List<String> polarity= new ArrayList<String>();
    static List<String> sentences= new ArrayList<String>();
    static String dataFileLocation= "IOFile/sentiment_train.txt";

    public SentiWordNet(String pathToSWN) throws IOException {
        // This is our main dictionary representation
        dictionary = new HashMap<String, Double>();

        // From String to list of doubles.
        HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();

        BufferedReader csv = null;
        try {
            csv = new BufferedReader(new FileReader(pathToSWN));
            int lineNumber = 0;

            String line;
            while ((line = csv.readLine()) != null) {
                lineNumber++;

                // If it's a comment, skip this line.
                if (!line.trim().startsWith("#")) {
                    // We use tab separation
                    String[] data = line.split("\t");
                    String wordTypeMarker = data[0];

                    // Example line:
                    // POS ID PosS NegS SynsetTerm#sensenumber Desc
                    // a 00009618 0.5 0.25 spartan#4 austere#3 ascetical#2
                    // ascetic#2 practicing great self-denial;...etc

                    // Is it a valid line? Otherwise, through exception.
                    if (data.length != 6) {
                        throw new IllegalArgumentException(
                                "Incorrect tabulation format in file, line: "
                                        + lineNumber);
                    }

                    // Calculate synset score as score = PosS - NegS
                    Double synsetScore = Double.parseDouble(data[2])
                            - Double.parseDouble(data[3]);

                    // Get all Synset terms
                    String[] synTermsSplit = data[4].split(" ");

                    // Go through all terms of current synset.
                    for (String synTermSplit : synTermsSplit) {
                        // Get synterm and synterm rank
                        String[] synTermAndRank = synTermSplit.split("#");
                        String synTerm = synTermAndRank[0] + "#"
                                + wordTypeMarker;

                        int synTermRank = Integer.parseInt(synTermAndRank[1]);
                        // What we get here is a map of the type:
                        // term -> {score of synset#1, score of synset#2...}

                        // Add map to term if it doesn't have one
                        if (!tempDictionary.containsKey(synTerm)) {
                            tempDictionary.put(synTerm,
                                    new HashMap<Integer, Double>());
                        }

                        // Add synset link to synterm
                        tempDictionary.get(synTerm).put(synTermRank,
                                synsetScore);
                    }
                }
            }

            // Go through all the terms.
            for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary
                    .entrySet()) {
                String word = entry.getKey();
                Map<Integer, Double> synSetScoreMap = entry.getValue();

                // Calculate weighted average. Weigh the synsets according to
                // their rank.
                // Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
                // Sum = 1/1 + 1/2 + 1/3 ...
                double score = 0.0;
                double sum = 0.0;
                for (Map.Entry<Integer, Double> setScore : synSetScoreMap
                        .entrySet()) {
                    score += setScore.getValue() / (double) setScore.getKey();
                    sum += 1.0 / (double) setScore.getKey();
                }
                score /= sum;

                dictionary.put(word, score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (csv != null) {
                csv.close();
            }
        }
    }

    public double extract(String word, String pos) {
      //  System.out.println(word + "#" + pos);
        if(dictionary.get(word + "#" + pos)==null)
            return 0;
        else
            return dictionary.get(word + "#" + pos);


    }

    public static void main(String [] args) throws IOException {
       /* if(args.length<1) {
            System.err.println("Usage: java SentiWordNetDemoCode <pathToSentiWordNetFile>");
            return;
        }*/
        int correct=0,incorrect=0,notidentify=0;

        String pathToSWN = "IOFile/senti.txt";

        SentiWordNet sentiwordnet = new SentiWordNet(pathToSWN);

        System.out.println("good#a "+sentiwordnet.extract("good", "v"));
        readFromActualFile(dataFileLocation);
       String sentence ="food is good";
        for(int i=0; i<polarity.size();i++) {
            System.out.println(sentences.get(i));
            System.out.println(polarity.get(i));
            String[] words = sentences.get(i).split("\\s+");
            double totalScore = 0, averageScore;
            for (String word : words) {
                word = word.replaceAll("([^a-zA-Z\\s])", "");
                if (extract(word, sentiwordnet) == null)
                    continue;
                totalScore += extract(word, sentiwordnet);
            }
            averageScore = totalScore;

            if(averageScore>0&&sentences.get(i).equalsIgnoreCase("pos")||averageScore<0&&sentences.get(i).equalsIgnoreCase("neg"))
                correct++;
            if(averageScore<0&&sentences.get(i).equalsIgnoreCase("pos")||averageScore>0&&sentences.get(i).equalsIgnoreCase("neg"))
                incorrect++;
            if(averageScore==0)
                notidentify++;

            if (averageScore >= 0.75)
                System.out.println("very positive");
            else if (averageScore > 0.25 && averageScore < 0.5)
                System.out.println("positive");
            else if (averageScore >= 0.5)
                System.out.println("positive");
            else if (averageScore < 0 && averageScore >= -0.25)
                System.out.println("negative");
            else if (averageScore < -0.25 && averageScore >= -0.5)
                System.out.println("negative");
            else if (averageScore <= -0.75)
                System.out.println("very negative");
        }
       System.out.println(correct+"****"+incorrect+"****"+notidentify);
    }

    public static Double extract(String word, SentiWordNet sentiwordnet)
    {
        Double total = new Double(0);
        if(sentiwordnet.extract(word, "n") != 0)
            total = sentiwordnet.extract(word, "n") + total;
        if(sentiwordnet.extract(word, "a") != 0)
            total = sentiwordnet.extract(word, "a") + total;
        if(sentiwordnet.extract(word, "r") != 0)
            total = sentiwordnet.extract(word, "r") + total;
        if(sentiwordnet.extract(word, "v") != 0)
            total = sentiwordnet.extract(word, "v") + total;
        return total;
    }


    public static void readFromActualFile(String location) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(new File(location)));
        String line;

        while ((line = br.readLine()) != null) {
            String key=line.substring(0, 3);
            String val=line.substring(3).trim();
            polarity.add(key);
            sentences.add(val);
        }
        br.close();
    }
}
