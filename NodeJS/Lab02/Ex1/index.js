const http = require("http")
const URL = require("url")
const queryString = require("querystring")
const server = http.createServer((req, res) => {
    const url = URL.parse(req.url) 
    
    res.writeHead(200, {
        'Content-Type': 'text/html charset=utf-8'
    })
    if (url.pathname === '/') {
        return res.end(`<!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Exercise 1</title>
                <style>
                    #num1, #num2, #ope {
                        margin-bottom: 10px;    
                    }
            
                    #subBtn {
                        margin-left: 65px;
                    }
                </style>
            </head>
            <body>
                <form action="/result">
                    <label for="num1">Số hạng 1</label>
                    <input type="number" name="num1" id="num1">
                    <br>
                    <label for="num2">Số hạng 2</label>
                    <input type="number" name="num2" id="num2"> 
                    <br>
                    <label for="ope">Phép tính</label>
                    <select name="ope" id="ope">
                        <option value="">Chọn phép tính</option>
                        <option value="+">+</option>
                        <option value="-">-</option>
                        <option value="*">*</option>
                        <option value="/">/</option>
                    </select>
                    <br>
                    <button id="subBtn" type="submit">Tính</button>
                </form>
            </body>
            </html>`)
    }
    else if (url.pathname === '/result') {

        let url = URL.parse(req.url)
        let query = queryString.decode(url.query)
        let {num1, num2, ope } = query

        if (num1 == "" || num2 == "" || !ope) {
            return res.end(`Ban chua chon phep toan`)
        }

        num1 = parseInt(num1)
        num2 = parseInt(num2)


        if (Number.isNaN(num1) || Number.isNaN(num2)) {
            return res.end(`Phep toan khong chinh xac`)
        }

        let result = undefined

        switch (ope) {
            case '+':
                result = num1 + num2
                break
            case '-':
                result = num1 - num2
                break
            case '*':
                result = num1 * num2;
                break
            case '/':
                result = num1 / num2
        }
        return res.end(`result: ${num1} ${ope} ${num2} = ${result}`)
    }
    else {
        return res.end('Đường dẫn không hợp lệ')
    }
})
server.listen(8080, () => {
    console.log("Server is running");
})



