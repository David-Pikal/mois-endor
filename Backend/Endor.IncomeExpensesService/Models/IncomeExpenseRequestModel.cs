namespace Endor.IncomeExpensesService.Models;

public record IncomeExpenseRequestModel(
    IncomeExpenseType? IncomeExpenseType,
    decimal? Value);
