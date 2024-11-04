const express = require('express')
const AccountRouter = require('./routers/AccountRouter')
const app = express()

app.set('view engine', 'ejs')

app.get('/', (req, res) => res.render('index'))
app.use('/account', AccountRouter)
app.listen(12346, () => console.log('http://localhost:12346'))