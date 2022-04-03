using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Endor.IncomeExpensesService.Models;

public class IncomeExpenseDbModel
{
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    [Key]
    [Required]
    public Guid Id { get; set; }

    [Required]
    public IncomeExpenseType IncomeExpenseType { get; set; }

    [Required]
    public decimal Value { get; set; }

    [Required]
    public string Owner { get; set; }
}
