package aoc2022.day20;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Day20 {

    public static void main(String[] args) throws IOException {
//        findCoordinates(Utils.readTestInput("day20"));
//        findDecryptedCoordinates(Utils.readTestInput("day20"));
//        findCoordinates(Utils.readInput("day20"));
        findDecryptedCoordinates(Utils.readInput("day20"));
    }

    private static void findCoordinates(List<String> input) {
        EncryptedFile encryptedFile = EncryptedFile.init(input);
        encryptedFile.decrypt();
        encryptedFile.print();
        System.out.println(encryptedFile.findCoordinates());
    }

    private static void findDecryptedCoordinates(List<String> input) {
        EncryptedFile encryptedFile = EncryptedFile.init(input);
        encryptedFile.decrypt(10, 811589153, false);
        System.out.println(encryptedFile.findCoordinates(true));
    }

}