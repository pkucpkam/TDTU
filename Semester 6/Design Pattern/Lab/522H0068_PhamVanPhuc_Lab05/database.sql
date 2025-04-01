create database StudentDB

use StudentDB

CREATE TABLE Students (
    MSSV NVARCHAR(10) PRIMARY KEY,
    HoTen NVARCHAR(100),
    NgaySinh DATE,
    Lop NVARCHAR(20)
);

-- Danh sách sinh viên mẫu
INSERT INTO Students (MSSV, HoTen, NgaySinh, Lop) VALUES
('SV001', N'Nguyễn Văn A', '2002-01-15', 'CNTT1'),
('SV002', N'Trần Thị B', '2001-12-22', 'CNTT2'),
('SV003', N'Lê Văn C', '2003-05-30', 'CNTT1');

