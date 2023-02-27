package com.transaction.rewardcalculator;

import java.math.BigDecimal;

public class RewardPointsCalculator {
    private static final BigDecimal BASE_POINTS_THRESHOLD = new BigDecimal("50");
    private static final BigDecimal BONUS_POINTS_THRESHOLD = new BigDecimal("100");
    private static final int BASE_POINTS_PER_DOLLAR = 1;
    private static final int BONUS_POINTS_PER_DOLLAR = 1;

    public static int calculateRewardPoints(CustomerTransaction transaction) {
        BigDecimal transactionAmount =  BigDecimal.valueOf(transaction.getTransactionAmount());
        BigDecimal basePointsAmount = transactionAmount.subtract(BASE_POINTS_THRESHOLD).max(BigDecimal.ZERO);
        BigDecimal bonusPointsAmount = transactionAmount.subtract(BONUS_POINTS_THRESHOLD).max(BigDecimal.ZERO);

        int basePoints = basePointsAmount.multiply(new BigDecimal(BASE_POINTS_PER_DOLLAR)).intValue();
        int bonusPoints = bonusPointsAmount.multiply(new BigDecimal(BONUS_POINTS_PER_DOLLAR)).intValue();

        return basePoints + bonusPoints;
    }
}


