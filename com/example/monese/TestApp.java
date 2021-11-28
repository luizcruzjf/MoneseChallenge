package com.example.monese;

import java.io.File;
import java.io.IOException;

public class TestApp {
    public static void main(String[] argh) throws IOException {
	populateDummyDatabase();
	manageFolders();
	processFiles();
    }

    private static void processFiles() {
	File[] files = new File("requests").listFiles();
	for (File input : files) {
	    System.out.println("Processing file: " + input.getName());
	    RequestHandler.handleFileRequest(input);
	}
    }

    private static void manageFolders() {
	File responsesFolder = new File("responses");
	if (!responsesFolder.exists()) {
	    responsesFolder.mkdir();
	}
	File[] files = responsesFolder.listFiles();
	for (File responseFiles : files) {
	    responseFiles.delete();
	}
    }

    private static void populateDummyDatabase() {
	DatabaseAccess.insertCustomer(1000);
	DatabaseAccess.insertCustomer(2000);
	DatabaseAccess.insertCustomer(3000);
	DatabaseAccess.insertCustomer(4000);
	DatabaseAccess.insertCustomer(5000);
	DatabaseAccess.insertCustomer(6000);
	DatabaseAccess.insertCustomer(7000);
	DatabaseAccess.insertCustomer(8000);
    }
}
