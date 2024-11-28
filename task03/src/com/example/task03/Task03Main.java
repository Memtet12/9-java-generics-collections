package com.example.task03;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.nio.*;
import java.util.stream.Collectors;

public class Task03Main {

    public static void main(String[] args) throws IOException {

        List<Set<String>> anagrams = findAnagrams(new FileInputStream("task03/resources/singular.txt"), Charset.forName("windows-1251"));
        for (Set<String> anagram : anagrams) {
            System.out.println(anagram);
        }

    }

    public static List<Set<String>> findAnagrams(InputStream inputStream, Charset charset)
    {
        Map<String, Set<String>> anagramMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset)))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim().toLowerCase();
                if (isValidWord(word)) {
                    String sortedWord = sortWord(word);
                    anagramMap.computeIfAbsent(sortedWord, k -> new HashSet<>()).add(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return anagramMap.values().stream()
                .filter(set -> set.size() > 1)
                .map(set -> set.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new)))
                .sorted(Comparator.comparing(set -> set.iterator().next()))
                .collect(Collectors.toList());
    }

    private static boolean isValidWord(String word) {
        if (word.length() < 3) {
            return false;
        }
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c) || !Character.UnicodeBlock.of(c).equals(Character.UnicodeBlock.CYRILLIC)) {
                return false;
            }
        }
        return true;
    }

    private static String sortWord(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}