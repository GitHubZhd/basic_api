package com.toefl.basic.io;

import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {
        FileDescriptor descriptor  = FileDescriptor.out;
        FileWriter fileWriter = new FileWriter(descriptor);
        fileWriter.write("hello world");
        fileWriter.flush();
        fileWriter.close();
    }
}