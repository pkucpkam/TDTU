using DatabaseFactoryLibrary;

var builder = WebApplication.CreateBuilder(args);

// Cấu hình DI cho DatabaseFactory
var useSqlServer = true;  // Chọn loại cơ sở dữ liệu
if (useSqlServer)
{
    builder.Services.AddSingleton<DatabaseFactory, SqlServerDatabaseFactory>();
}
else
{
    builder.Services.AddSingleton<DatabaseFactory, AccessDatabaseFactory>();
}

// Thêm các dịch vụ MVC
builder.Services.AddControllersWithViews();

// Thêm các dịch vụ khác nếu cần
// builder.Services.AddRazorPages(); // Nếu bạn sử dụng Razor Pages

var app = builder.Build();

// Cấu hình middleware
app.UseRouting();

// Cấu hình Static Files để tải CSS, JS và các tài nguyên tĩnh khác
app.UseStaticFiles();  // Cần thiết để tải tệp CSS, JS từ thư mục wwwroot

// Cấu hình các route cho Controller
app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Student}/{action=Index}/{id?}");

// Nếu bạn sử dụng Razor Pages, cần thêm dòng này:
// app.MapRazorPages();

app.Run();
