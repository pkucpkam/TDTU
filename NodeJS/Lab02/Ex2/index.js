const http = require("http");
const queryString = require("querystring")
const fs = require("fs");
const URL = require("url")
const path = require("path");

const verifyAccount = { email: 'admin@gmail.com', password: '123456' }

const server = http.createServer((req, res) => {
    const url = URL.parse(req.url)

    if (url.pathname === "/") {
        const filePath = path.join(__dirname, 'index.html');
        fs.readFile(filePath, (err, data) => {
            if (err) {
                res.writeHead(500, { 'Content-Type': 'text/plain' });
                res.end('Khong tim thay trang login');
            } else {
                res.writeHead(200, { 'Content-Type': 'text/html' });
                res.end(data);
            }
        });
    }
    else if (url.pathname === '/login') {
        console.log(req.method)
        if (req.method === "GET") {
            const filePath = path.join(__dirname, 'notSupport.html');
            fs.readFile(filePath, (err, data) => {
                if (err) {
                    res.writeHead(500, { 'Content-Type': 'text/plain' });
                    res.end('Khong tim thay trang login');
                } else {
                    res.writeHead(200, { 'Content-Type': 'text/html' });
                    res.end(data);
                }
            });
        }
        else {
            let body = '';
            req.on('data', chunk => {
                body += chunk.toString();
            });
            req.on('end', () => {
                const formData = queryString.parse(body);
                const { userEmail, userPassword } = formData;

                if (userEmail === verifyAccount.email && userPassword === verifyAccount.password) {
                    const filePath = path.join(__dirname, 'success.html');
                    fs.readFile(filePath, (err, data) => {
                        if (err) {
                            res.writeHead(500, { 'Content-Type': 'text/plain' });
                            res.end('Khong tim thay trang');
                        } else {
                            res.writeHead(200, { 'Content-Type': 'text/html' });
                            res.end(data);
                        }
                    })
                } else {
                    const filePath = path.join(__dirname, 'unsuccess.html');
            fs.readFile(filePath, (err, data) => {
                if (err) {
                    res.writeHead(500, { 'Content-Type': 'text/plain' });
                    res.end('Khong tim thay trang');
                } else {
                    res.writeHead(200, { 'Content-Type': 'text/html' });
                    res.end(data);
                }
        })
                }
            });
        }
    }
    else {
        const filePath = path.join(__dirname, 'invalid.html');
        fs.readFile(filePath, (err, data) => {
            if (err) {
                res.writeHead(500, { 'Content-Type': 'text/plain' });
                res.end('Khong tim thay trang');
            } else {
                res.writeHead(200, { 'Content-Type': 'text/html' });
                res.end(data);
            }
        })
    }
})

server.listen(8080, () => {
    console.log("http://localhost:8080/");
})