using Endor.IncomeExpensesService.Database;
using Endor.IncomeExpensesService.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Security.Claims;

namespace Endor.IncomeExpensesService.Controllers;

[ApiController]
[Authorize]
[Route("[controller]")]
public class IncomeExpensesController : ControllerBase
{
    private readonly AppDbContext mDbContext;

    public IncomeExpensesController(AppDbContext dbContext)
    {
        mDbContext = dbContext;
    }

    [HttpGet(Name = "GetAllIncomeExpense")]
    public async Task<ActionResult<IEnumerable<IncomeExpenseApiModel>>> GetAll()
    {
        Response.Headers.Add("Access-Control-Allow-Origin", "*");

        return await mDbContext
            .IncomeExpenses
            .Where(x => x.Owner == User.FindFirstValue(ClaimTypes.NameIdentifier))
            .Select(x => DbModelToApiModel(x))
            .ToListAsync();
    }

    [HttpGet("{id}", Name = "GetIncomeExpense")]
    public async Task<ActionResult<IncomeExpenseApiModel>> Get(Guid id)
    {
        var incomeExpenseDbModel = await mDbContext
            .IncomeExpenses
            .FindAsync(id);

        if (incomeExpenseDbModel == null)
        {
            return NotFound();
        }

        if (incomeExpenseDbModel.Owner != User.FindFirstValue(ClaimTypes.NameIdentifier))
        {
            return Unauthorized();
        }

        Response.Headers.Add("Access-Control-Allow-Origin", "*");

        return DbModelToApiModel(incomeExpenseDbModel);

    }

    [HttpPost(Name = "PostIncomeExpense")]
    public async Task<ActionResult<IncomeExpenseApiModel>> Post(
        IncomeExpenseRequestModel incomeExpenseRequestModel)
    {
        if (!incomeExpenseRequestModel.IncomeExpenseType.HasValue ||
            !incomeExpenseRequestModel.Value.HasValue)
        {
            return BadRequest();
        }

        var incomeExpenseDbModel = new IncomeExpenseDbModel()
        {
            IncomeExpenseType = incomeExpenseRequestModel.IncomeExpenseType.Value,
            Value = incomeExpenseRequestModel.Value.Value,
            Owner = User.FindFirstValue(ClaimTypes.NameIdentifier)
        };

        var createdIncomeExpenseDbModel = mDbContext
            .IncomeExpenses
            .Add(incomeExpenseDbModel)
            .Entity;

        await mDbContext
            .SaveChangesAsync();

        Response.Headers.Add("Access-Control-Allow-Origin", "*");

        return CreatedAtAction(
            nameof(Get),
            new { id = createdIncomeExpenseDbModel },
            DbModelToApiModel(createdIncomeExpenseDbModel));
    }

    [HttpPut("{id}", Name = "PutIncomeExpense")]
    public async Task<ActionResult> Put(
        Guid id,
        IncomeExpenseRequestModel incomeExpenseRequestModel)
    {
        var incomeExpenseDbModel = await mDbContext
            .IncomeExpenses
            .FindAsync(id);

        if (incomeExpenseDbModel == null)
        {
            return NotFound();
        }

        if (incomeExpenseDbModel.Owner != User.FindFirstValue(ClaimTypes.NameIdentifier))
        {
            return Unauthorized();
        }

        if (incomeExpenseRequestModel.IncomeExpenseType.HasValue)
        {
            incomeExpenseDbModel.IncomeExpenseType =
                incomeExpenseRequestModel.IncomeExpenseType.Value;
        }

        if (incomeExpenseRequestModel.Value.HasValue)
        {
            incomeExpenseDbModel.Value =
                incomeExpenseRequestModel.Value.Value;
        }

        mDbContext.Update(incomeExpenseDbModel);

        try
        {
            await mDbContext.SaveChangesAsync();
        }
        catch (DbUpdateConcurrencyException) when (!IncomeExpenseExists(id))
        {
            return NotFound();
        }

        Response.Headers.Add("Access-Control-Allow-Origin", "*");

        return NoContent();
    }

    [HttpDelete("{id}", Name = "DeleteIncomeExpense")]
    public async Task<ActionResult> Delete(Guid id)
    {
        var incomeExpenseDbModel = await mDbContext
            .IncomeExpenses
            .FindAsync(id);

        if (incomeExpenseDbModel == null)
        {
            return NotFound();
        }

        if (incomeExpenseDbModel.Owner != User.FindFirstValue(ClaimTypes.NameIdentifier))
        {
            return Unauthorized();
        }

        mDbContext
            .IncomeExpenses
            .Remove(incomeExpenseDbModel);

        await mDbContext
            .SaveChangesAsync();

        Response.Headers.Add("Access-Control-Allow-Origin", "*");

        return NoContent();
    }

    private bool IncomeExpenseExists(Guid id)
    {
        return mDbContext
            .IncomeExpenses
            .Any(x => x.Id == id);
    }

    private static IncomeExpenseApiModel DbModelToApiModel(IncomeExpenseDbModel model)
    {
        return new IncomeExpenseApiModel(
            model.Id,
            model.IncomeExpenseType,
            model.Value);
    }
}
