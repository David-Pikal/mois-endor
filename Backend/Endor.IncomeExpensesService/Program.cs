using Endor.IncomeExpensesService.Database;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Steeltoe.Discovery.Client;

var builder = WebApplication.CreateBuilder(args);

builder.Services
    .AddAuthentication(options =>
    {
        options.DefaultScheme = JwtBearerDefaults.AuthenticationScheme;
    })
    .AddJwtBearer(options =>
    {
        options.Authority = builder.Configuration["Okta:Issuer"];
        options.Audience = "api://default";
        options.RequireHttpsMetadata = false;
    });

builder.Services.AddCors(options =>
{
    options.AddDefaultPolicy(builder => builder
        .AllowAnyOrigin()
        .AllowAnyMethod()
        .AllowAnyHeader());
});

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddDiscoveryClient(builder.Configuration);

builder.Services.AddDbContext<AppDbContext>(options =>
{
    options.UseNpgsql(builder.Configuration["ConnectionStrings:DatabaseConnection"]);
});

var app = builder.Build();

app.UseAuthentication();
app.UseAuthorization();

app.UseCors();

app.UseSwagger();
app.UseSwaggerUI();

app.MapControllers();

using var scope = app.Services.CreateScope();
using var dbContext = scope.ServiceProvider.GetService<AppDbContext>();
if (dbContext != null)
{
    var databse = dbContext.Database;
    await databse.EnsureCreatedAsync();
}

app.Run();
