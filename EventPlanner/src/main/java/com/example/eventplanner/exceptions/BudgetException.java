package com.example.eventplanner.exceptions;

public class BudgetException extends RuntimeException {
  public enum ErrorType {
      EVENT_NOT_FOUND,
      BUDGET_NOT_FOUND,
      MERCHANDISE_EXISTS,
      BUDGET_ITEM_NOT_FOUND,
      CATEGORY_NOT_FOUND,
  }

  private ErrorType errorType;

  public BudgetException(String message, ErrorType errorType) {
      super(message);
      this.errorType = errorType;
  }

  public ErrorType getErrorType() { return errorType; }
}
