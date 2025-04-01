namespace StudentManagementWebApp.Controllers
{
    using Microsoft.AspNetCore.Mvc;
    using System.Data;
    using System.Data.Common;
    using DatabaseFactoryLibrary;
    using StudentManagementWebApp.Models;
    using System.Data.SqlClient;


    public class StudentController : Controller
    {
        private readonly DatabaseFactory _databaseFactory;

        public StudentController(DatabaseFactory databaseFactory)
        {
            _databaseFactory = databaseFactory;
        }

        // Action để hiển thị danh sách sinh viên
        public IActionResult Index()
        {
            var students = GetStudents();
            return View(students);
        }

        // Action để hiển thị form thêm sinh viên
        public IActionResult Create()
        {
            return View();
        }

        // Action để lưu sinh viên mới
        [HttpPost]
        public IActionResult Create(Student student)
        {
            AddStudent(student);
            return RedirectToAction("Index");
        }

        // Action để hiển thị form sửa sinh viên
        public IActionResult Edit(string id)
        {
            var student = GetStudentById(id);
            if (student == null)
            {
                return NotFound();
            }
            return View(student);
        }

        // Action để lưu sửa sinh viên
        [HttpPost]
        public IActionResult Edit(Student student)
        {
            UpdateStudent(student);
            return RedirectToAction("Index");
        }

        // Action để xóa sinh viên
        public IActionResult Delete(string id)
        {
            var student = GetStudentById(id);
            if (student == null)
            {
                return NotFound();
            }
            DeleteStudent(id);
            return RedirectToAction("Index");
        }

        // Các phương thức hỗ trợ

        private List<Student> GetStudents()
        {
            // Tương tự như trước, bạn lấy dữ liệu từ database và trả về danh sách sinh viên
            var students = new List<Student>();
            var connectionString = "Server=PHUCPHAM\\SQLEXPRESS;Database=StudentDB;Trusted_Connection=True;TrustServerCertificate=True;";

            using (var connection = new SqlConnection(connectionString))
            {
                connection.Open();
                var command = _databaseFactory.CreateCommand("SELECT * FROM Students", connection);
                var reader = _databaseFactory.CreateDataReader(command);
                while (reader.Read())
                {
                    students.Add(new Student
                    {
                        MSSV = reader["MSSV"].ToString(),
                        HoTen = reader["HoTen"].ToString(),
                        NgaySinh = Convert.ToDateTime(reader["NgaySinh"]),
                        Lop = reader["Lop"].ToString()
                    });
                }
            }
            return students;
        }

        private void AddStudent(Student student)
        {
            var connectionString = "Server=PHUCPHAM\\SQLEXPRESS;Database=StudentDB;Trusted_Connection=True;TrustServerCertificate=True;";

            using (var connection = new SqlConnection(connectionString))
            {
                connection.Open();
                var command = _databaseFactory.CreateCommand("INSERT INTO Students (MSSV, HoTen, NgaySinh, Lop) VALUES (@MSSV, @HoTen, @NgaySinh, @Lop)", connection);
                command.Parameters.Add(_databaseFactory.CreateParameter("@MSSV", student.MSSV));
                command.Parameters.Add(_databaseFactory.CreateParameter("@HoTen", student.HoTen));
                command.Parameters.Add(_databaseFactory.CreateParameter("@NgaySinh", student.NgaySinh));
                command.Parameters.Add(_databaseFactory.CreateParameter("@Lop", student.Lop));
                command.ExecuteNonQuery();
            }
        }

        private void UpdateStudent(Student student)
        {
            var connectionString = "Server=PHUCPHAM\\SQLEXPRESS;Database=StudentDB;Trusted_Connection=True;TrustServerCertificate=True;";

            using (var connection = new SqlConnection(connectionString))
            {
                connection.Open();
                var command = _databaseFactory.CreateCommand("UPDATE Students SET HoTen = @HoTen, NgaySinh = @NgaySinh, Lop = @Lop WHERE MSSV = @MSSV", connection);
                command.Parameters.Add(_databaseFactory.CreateParameter("@MSSV", student.MSSV));
                command.Parameters.Add(_databaseFactory.CreateParameter("@HoTen", student.HoTen));
                command.Parameters.Add(_databaseFactory.CreateParameter("@NgaySinh", student.NgaySinh));
                command.Parameters.Add(_databaseFactory.CreateParameter("@Lop", student.Lop));
                command.ExecuteNonQuery();
            }
        }

        private void DeleteStudent(string id)
        {
            var connectionString = "Server=PHUCPHAM\\SQLEXPRESS;Database=StudentDB;Trusted_Connection=True;TrustServerCertificate=True;";

            using (var connection = new SqlConnection(connectionString))
            {
                connection.Open();
                var command = _databaseFactory.CreateCommand("DELETE FROM Students WHERE MSSV = @MSSV", connection);
                command.Parameters.Add(_databaseFactory.CreateParameter("@MSSV", id));
                command.ExecuteNonQuery();
            }
        }

        private Student GetStudentById(string id)
        {
            var connectionString = "Server=PHUCPHAM\\SQLEXPRESS;Database=StudentDB;Trusted_Connection=True;TrustServerCertificate=True;";

            using (var connection = new SqlConnection(connectionString))
            {
                connection.Open();
                var command = _databaseFactory.CreateCommand("SELECT * FROM Students WHERE MSSV = @MSSV", connection);
                command.Parameters.Add(_databaseFactory.CreateParameter("@MSSV", id));
                var reader = _databaseFactory.CreateDataReader(command);
                if (reader.Read())
                {
                    return new Student
                    {
                        MSSV = reader["MSSV"].ToString(),
                        HoTen = reader["HoTen"].ToString(),
                        NgaySinh = Convert.ToDateTime(reader["NgaySinh"]),
                        Lop = reader["Lop"].ToString()
                    };
                }
                return null;
            }
        }
    }


}
