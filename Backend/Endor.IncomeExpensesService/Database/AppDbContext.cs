using Endor.IncomeExpensesService.Models;
using Microsoft.EntityFrameworkCore;

namespace Endor.IncomeExpensesService.Database;

public class AppDbContext : DbContext
{
    public DbSet<IncomeExpenseDbModel> IncomeExpenses { get; set; }

    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
    }
}
