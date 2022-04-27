namespace Endor.IncomeExpensesService.Models;

public record IncomeExpenseApiModel(
    Guid? Id,
    IncomeExpenseType? IncomeExpenseType,
    string? Date,
    decimal? Value);
