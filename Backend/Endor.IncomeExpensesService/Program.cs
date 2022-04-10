using Endor.IncomeExpensesService.Database;
using Microsoft.EntityFrameworkCore;
using Steeltoe.Discovery.Client;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// discoverable by eureka server
builder.Services.AddDiscoveryClient(builder.Configuration);

builder.Services.AddDbContext<AppDbContext>(options =>
{
    options.UseNpgsql(builder.Configuration["ConnectionStrings:DatabaseConnection"]);
});

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();


using var scope = app.Services.CreateScope();
using var dbContext = scope.ServiceProvider.GetService<AppDbContext>();
if (dbContext != null)
{
    var databse = dbContext.Database;
    await databse.EnsureCreatedAsync();
}

app.Run();
