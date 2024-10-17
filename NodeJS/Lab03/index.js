require('dotenv').config();
const PORT = process.env.PORT || 8080

const express = require('express')
const path = require('path')
const app = express()
const bodyParser = require('body-parser')

// View engine
app.set('view engine', 'ejs');


// Product
const products = [
    { id: 1, code: 'P001', name: 'Macbook Air M1 256GB', price: 1200 },
    { id: 2, code: 'P002', name: 'Product 2', price: 200 },
    { id: 3, name: "iPhone 12 Pro", price: 30000000 },
    { id: 4, name: "iPhone 11", price: 17000000 },
    { id: 5, name: "iPhone Xr", price: 11000000 },
];

//Middleware check Auth
let isAuth = true;
function checkAuth(req, res, next) {
    if (!isAuth) {
        res.redirect("/login");
    } else {
        next();
    }
}

// Login
app.use(bodyParser.urlencoded({ extended: false }))

app.get('/login', (req, res) => {
    res.render('login')
})


app.get('/', checkAuth, (req, res) => {
    res.render('index', { products })
})

app.listen(8080, () => {
    console.log("Server is runing at http://localhost:8080");
})

