package com.transaction.rewardcalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.transaction.rewardcalculator.TransactionProcessingException;

@ExtendWith(SpringExtension.class)
public class RewardCalculatorControllerTest {

	private RewardCalculatorController controller;

	@BeforeEach
	public void setUp() {
		controller = new RewardCalculatorController();
	}

	@Test
	@DisplayName("Test monthly reward points calculation")
	public void testGetRewardPoints() throws CsvValidationException, IOException, TransactionProcessingException {
		Map<String, Map<String, Integer>> expectedRewardPoints = new HashMap<>();
		Map<String, Integer> rahulPoints = new HashMap<>();
	    rahulPoints.put("2022-01", 50);
	    rahulPoints.put("2022-02", 250);
	    rahulPoints.put("2022-03", 250);
	    Map<String, Integer> andyPoints = new HashMap<>();
	    andyPoints.put("2022-01", 450);
	    andyPoints.put("2022-02", 0);
	    andyPoints.put("2022-03", 30);
	    Map<String, Integer> rubenPoints = new HashMap<>();
	    rubenPoints.put("2022-01", 450);
	    rubenPoints.put("2022-02", 650);
	    rubenPoints.put("2022-03", 850);
	    expectedRewardPoints.put("Rahul", rahulPoints);
	    expectedRewardPoints.put("Andy", andyPoints);
	    expectedRewardPoints.put("Ruben", rubenPoints);

		Map<String, Map<String, Integer>> actualRewardPoints = controller.getRewardPoints();

		assertEquals(expectedRewardPoints, actualRewardPoints);
	}

	@Test
	@DisplayName("Test total reward points calculation")
	public void testGetTotalRewardPointsPerCustomer() throws CsvValidationException, IOException, TransactionProcessingException {
		Map<String, Integer> expectedTotalPoints = new HashMap<>();
		expectedTotalPoints.put("Rahul", 550);
	    expectedTotalPoints.put("Andy", 480);
	    expectedTotalPoints.put("Ruben", 1950);


		Map<String, Integer> actualTotalPoints = controller.getTotalRewardPointsPerCustomer();

		assertEquals(expectedTotalPoints, actualTotalPoints);
	}

	@Test
	@DisplayName("Test CSV file format")
	public void testCsvFileFormat() throws CsvValidationException, IOException {
		Resource resource = new ClassPathResource("transactions.csv");
		File file = resource.getFile();
		try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
			String[] header = csvReader.readNext();
			assertTrue(Arrays.equals(header, new String[] { "Customer", "Date", "Amount" }));
		}
	}
}


