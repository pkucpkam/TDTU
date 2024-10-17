const http = require('http');
const URL = require('url');

let students = new Map();
students.set(1, { id: 1, name: "Nguyen Van A" });
students.set(2, { id: 2, name: "Nguyen Van B" });
students.set(3, { id: 3, name: "Tran Van D" });
students.set(4, { id: 4, name: "Le Thi C" });
students.set(5, { id: 5, name: "Nguyen Van E" });
students.set(6, { id: 6, name: "Hoang Van F" });
students.set(7, { id: 7, name: "Nguyen Thi G" });
students.set(8, { id: 8, name: "Phan Van H" });
students.set(9, { id: 9, name: "Doan Van I" });
students.set(10, { id: 10, name: "Vu Thi J" });
students.set(11, { id: 11, name: "Bui Van K" });
students.set(12, { id: 12, name: "Ngo Thi L" });


const server = http.createServer((req, res) => {
    const url = URL.parse(req.url);
    const method = req.method;

    if (url.pathname === '/students') {
        if (method === 'GET') {
            res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' });
            return res.end(JSON.stringify(Array.from(students.values())));
        } else if (method === 'POST') {
            let body = '';
            req.on('data', chunk => {
                body += chunk.toString(); 
            });
            req.on('end', () => {
                const newStudent = JSON.parse(body);
                const newId = students.size + 1; 
                newStudent.id = newId;
                students.set(newId, newStudent);
                res.writeHead(201, { 'Content-Type': 'application/json; charset=utf-8' });
                return res.end(JSON.stringify(newStudent));
            });
        }
    }
    else {
        const pattern = /^\/students\/\d+$/g;
        if (url.pathname.match(pattern)) {
            const id = parseInt(url.pathname.split('/')[2]);

            if (method === 'GET') {
                if (students.has(id)) {
                    res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' });
                    return res.end(JSON.stringify(students.get(id)));
                } else {
                    res.writeHead(404, { 'Content-Type': 'application/json; charset=utf-8' });
                    return res.end(JSON.stringify({ message: 'Sinh viên không tìm thấy' }));
                }
            } else if (method === 'PUT') {
                let body = '';
                req.on('data', chunk => {
                    body += chunk.toString();
                });
                req.on('end', () => {
                    if (students.has(id)) {
                        const updatedData = JSON.parse(body);
                        students.set(id, { ...students.get(id), ...updatedData });
                        res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' });
                        return res.end(JSON.stringify(students.get(id)));
                    } else {
                        res.writeHead(404, { 'Content-Type': 'application/json; charset=utf-8' });
                        return res.end(JSON.stringify({ message: 'Sinh viên không tìm thấy' }));
                    }
                });
            } else if (method === 'DELETE') {
                if (students.has(id)) {
                    students.delete(id);
                    res.writeHead(204); 
                    return res.end();
                } else {
                    res.writeHead(404, { 'Content-Type': 'application/json; charset=utf-8' });
                    return res.end(JSON.stringify({ message: 'Sinh viên không tìm thấy' }));
                }
            }
        } else {
            res.writeHead(404, { 'Content-Type': 'application/json; charset=utf-8' });
            return res.end(JSON.stringify({ code: 104, message: "Invalid url" }));
        }
    }
});

server.listen(8080, () => {
    console.log('http://localhost:8080/students');
});
