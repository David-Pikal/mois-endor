namespace Endor.IncomeExpensesService.Models;

public record IncomeExpenseApiModel(
    Guid? Id,
    IncomeExpenseType? IncomeExpenseType,
    decimal? Value);
