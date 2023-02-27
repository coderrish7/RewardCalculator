package com.transaction.rewardcalculator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@RestController
@RequestMapping("/reward-points")
public class RewardCalculatorController {

	public List<CustomerTransaction> RewardPointsController() throws IOException, CsvValidationException {
		List<CustomerTransaction> transactions = new ArrayList<>();
		// read transaction data from CSV file

		Resource resource = new ClassPathResource("transactions.csv");
		File file = resource.getFile();

		try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
			String[] header = csvReader.readNext();
			if (!Arrays.equals(header, new String[] { "Customer", "Date", "Amount" })) {
				throw new IOException("Invalid CSV file format");
			}

			String[] line;
			while ((line = csvReader.readNext()) != null) {
				String customerName = line[0];
				LocalDateTime transactionDate = LocalDateTime.parse(line[1], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
				double transactionAmount = Double.parseDouble(line[2]);

				transactions.add(new CustomerTransaction(customerName, transactionDate, transactionAmount));
			}
		}
		return transactions;
	}

	@GetMapping("/monthly-reward")
	public Map<String, Map<String, Integer>> getRewardPoints()
			throws CsvValidationException, IOException, TransactionProcessingException {
		Map<String, Map<String, Integer>> rewardPointsPerMonth = new HashMap<>();
		List<CustomerTransaction> transactions = RewardPointsController();
		// calculate reward points for all transactions
		for (CustomerTransaction t : transactions) {
			// extract customer name, transaction date, and transaction amount from
			// transaction object
			String customerName = t.getCustomerName();
			LocalDateTime transactionDateTime = t.getTransactionDate();

			int rewardPoints = 0;
			// calculate reward points for this transaction
			try {
				rewardPoints = RewardPointsCalculator.calculateRewardPoints(t);
			} catch (Exception e) {
				throw new TransactionProcessingException("Error processing transaction: " + t.toString(), e);
			}

			// get month and year from transaction date
			String monthYear = transactionDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));

			// get inner map for this customer and month, creating it if necessary
			Map<String, Integer> customerRewards = rewardPointsPerMonth.computeIfAbsent(customerName,
					k -> new HashMap<>());
			customerRewards.put(monthYear, customerRewards.getOrDefault(monthYear, 0) + rewardPoints);
		}

		return rewardPointsPerMonth;
	}

	@GetMapping("/total")
	public Map<String, Integer> getTotalRewardPointsPerCustomer()
			throws CsvValidationException, IOException, TransactionProcessingException {
		Map<String, Integer> totalRewardPointsPerCustomer = new HashMap<>();

		List<CustomerTransaction> transactions = RewardPointsController();
		// calculate reward points for all transactions and sum them up for each
		// customer
		for (CustomerTransaction t : transactions) {
			// extract customer name from transaction object
			String customerName = t.getCustomerName();

			int rewardPoints = 0;
			// calculate reward points for this transaction
			try {
				rewardPoints = RewardPointsCalculator.calculateRewardPoints(t);
			} catch (Exception e) {
				throw new TransactionProcessingException("Error processing transaction: " + t.toString(), e);
			}

			// add reward points for this transaction to total reward points for this
			// customer
			int totalRewardPoints = totalRewardPointsPerCustomer.getOrDefault(customerName, 0) + rewardPoints;
			totalRewardPointsPerCustomer.put(customerName, totalRewardPoints);
		}

		return totalRewardPointsPerCustomer;
	}

	@GetMapping("/health")
	public String healthCheck() {
		return "Service is up and running";
	}

}
