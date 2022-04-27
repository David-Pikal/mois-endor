namespace Endor.IncomeExpensesService.Models;

public record IncomeExpenseRequestModel(
    IncomeExpenseType? IncomeExpenseType,
    string? Date,
    decimal? Value);
