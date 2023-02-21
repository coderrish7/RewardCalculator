package com.transaction.rewardcalculator;


import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.transaction.rewardcalculator.*;


@RestController
@RequestMapping("/reward-points")
public class RewardCalculatorController {

	private final List<Transaction> transactions = new ArrayList<>();

	// @PostMapping
	// public void addTransaction(@RequestBody Transaction transaction) {
//		transactions.add(transaction);
//	}

	
	public void RewardPointsController() throws IOException, CsvValidationException {
		// read transaction data from CSV file
		try (CSVReader csvReader = new CSVReader(new FileReader("C:/Users/Ayush/Downloads/f/rewardcalculator (1)/rewardcalculator/src/main/resources/transactions.csv"))) {
			String[] header = csvReader.readNext();
			if (!Arrays.equals(header, new String[] { "Customer", "Date", "Amount" })) {
				throw new IOException("Invalid CSV file format");
			}

			String[] line;
			while ((line = csvReader.readNext()) != null) {
				String customerName = line[0];
				LocalDateTime transactionDate = LocalDateTime.parse(line[1], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
				double transactionAmount = Double.parseDouble(line[2]);

				transactions.add(new Transaction(customerName, transactionDate, transactionAmount));
			}
		}
	}

	@GetMapping("/ab")
	public Map<String, Map<String, Integer>> getRewardPoints() throws CsvValidationException, IOException {
	    Map<String, Map<String, Integer>> rewardPointsPerMonth = new HashMap<>();

	    RewardPointsController();
	    // calculate reward points for all transactions
	    for (Transaction t : transactions) {
	        // extract customer name, transaction date, and transaction amount from transaction object
	        String customerName = t.getCustomerName();
	        LocalDateTime transactionDateTime = t.getTransactionDate();
	        double transactionAmount = t.getTransactionAmount();

	        // calculate reward points for this transaction
	        int rewardPoints = RewardPointsCalculator.calculateRewardPoints(t); 

	        // get month and year from transaction date
	        String monthYear = transactionDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));

	        // get inner map for this customer and month, creating it if necessary
	        Map<String, Integer> customerRewards = rewardPointsPerMonth.computeIfAbsent(customerName, k -> new HashMap<>());
	        customerRewards.put(monthYear, customerRewards.getOrDefault(monthYear, 0) + rewardPoints);
	    }

	    return rewardPointsPerMonth;
	}
	
}

