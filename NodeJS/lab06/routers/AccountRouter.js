const express = require('express')
const app = express.Router()
const bcrypt = require('bcrypt')
app.use(express.urlencoded({ extended: true }));
const bodyParser = require('body-parser');


var mysql = require('mysql');
var db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'lab6',
    port: 3306
});

db.connect((err) => {
    if (err) {
        console.error('Database connection failed: ' + err.stack);
        return;
    }
    console.log('Connected to database as id ' + db.threadId);
});

app.get('/login', (req, res) => res.render('login'))
app.post('/login', (req, res) => {
    const { email, password } = req.body;
    console.log("Login attempt with email:", email);
    
    let selectUserQuery = 'SELECT * FROM account WHERE email = ?';
    let selectUserParams = [email];
    
    db.query(selectUserQuery, selectUserParams, (error, result) => {
        if (error) {
            console.error("Error during login query:", error);
            return res.status(500).send("Login failed");
        }

        if (result.length === 0) {
            console.log("Invalid username");
            return res.status(401).send("Invalid username");
        }

        let hashed = result[0].userpassword; 
        console.log("Stored hash:", hashed); 

        let matched = bcrypt.compareSync(password, hashed);
        console.log("Password match result:", matched);  



        // if (!matched) {
        //     console.log("Invalid password");
        //     return res.status(401).send("Invalid password");
        // }

        req.session.user = {
            id: result[0].id,
            email: result[0].email,
            name: result[0].name
        };

        return res.redirect('/');
    });
});




app.get('/register', (req, res) => res.render('register'))
app.post('/register', (req, res) => {
    console.log(req.body);
    let { name, email, password } = req.body
    console.log(name, email, password)
    let hashed = bcrypt.hashSync(password, 10);
    console.log(name, email, hashed)

    let insertUserQuery = 'insert into account(name, userpassword,email)values(?, ?, ?)'
    let insertUserParams = [name, hashed, email]
    db.query(insertUserQuery, insertUserParams,
        (error, result, fields) => {
            console.log(error, result, fields)
        })

    res.redirect('/');
})

module.exports = app