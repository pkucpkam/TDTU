namespace StudentManagementWebApp.Models

{
    using Microsoft.Data.SqlClient;  // Cho .NET Core/5/6

    public class Student
    {
        public string MSSV { get; set; }
        public string HoTen { get; set; }
        public DateTime NgaySinh { get; set; }
        public string Lop { get; set; }
    }

}
