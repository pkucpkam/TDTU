const express = require('express')
const app = express.Router()

app.use(express.urlencoded())

app.get('/login', (req, res) => res.render('login'))
app.get('/register', (req, res) => res.render('register'))
app.post('/register', (req, res) => {
    const {confirmPassword, name, email, password} = req.body
    res.json(req.body)
})

module.exports = app