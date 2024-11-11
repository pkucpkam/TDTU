const express = require('express')
const AccountRouter = require('./routers/AccountRouter')
const session = require('express-session');
const app = express()
require('dotenv').config();
const path = require('path')
const fs = require("fs")
const USER_FILES_DIR = process.env.USER_FILES_DIR
console.log("USER_FILES_DIR:", USER_FILES_DIR);  // Kiểm tra giá trị của biến

app.set('view engine', 'ejs')

app.use(session({
    secret: '123',
    resave: false,
    saveUninitialized: true,
    cookie: { secure: false }
}));

app.set('view engine', 'ejs');

function checkAuthentication(req, res, next) {
    if (req.session.user) {
        next();
    } else {
        res.redirect('/account/login');
    }
}

app.get('/', checkAuthentication, (req, res) => {
    const userId = req.session.user.id;
    console.log(`Reading files for user ID: ${userId}`);

    let filesOrDirs = [];
    try {
        filesOrDirs = fs.readdirSync(path.join(USER_FILES_DIR, `${userId}`));  
    } catch (err) {
        console.error('Error reading directory:', err);
        return res.status(500).send('Không thể đọc thư mục');
    }

    const filesOrDirsMap = filesOrDirs.map(fileName => {
        const filePath = path.join(USER_FILES_DIR, `${userId}`, fileName);
        let fileInfo = fs.statSync(filePath);
        return {
            name: fileName,
            path: filePath,
            isDirectory: fileInfo.isDirectory(),
            size: fileInfo.size,
            lastModified: fileInfo.mtime
        };
    });

    console.log('Files or directories:', filesOrDirsMap);

    // Render dữ liệu ra view (EJS)
    res.render('index', { files: filesOrDirsMap });
});


app.use('/account', AccountRouter);

app.use('/account', AccountRouter)
app.listen(12346, () => console.log('http://localhost:12346'))