require('dotenv').config();
const PORT = process.env.PORT || 8080

const express = require('express')
const path = require('path')
const app = express()
const bodyParser = require('body-parser')
const multer = require('multer');

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
var auth = (req, res, next) => {
    let { user } = req.session
    if (!user) return res.redirect('/login');
    next();
}

// Cau hinh multer
const upload = multer({dest:'uploads'})
app.post('/add', upload.single('image'), (req, res) => {
 let { name, price, desc } = req.body
 let image = req.file;
 console.log(name, price, desc, image);
})

var session = require('express-session');
app.use(session({
    secret: 'keyboard cat',
    resave: false,
    saveUninitialized: false,
    cookie: { secure: false }
}));


// Login
app.use(bodyParser.urlencoded({ extended: false }))

app.get('/login', (req, res) => {
    res.render('login')
})

app.post('/login', (req, res) => {
    let { email, password } = req.body;
    
    // Kiểm tra email và password từ file .env
    const adminEmail = process.env.ADMIN_EMAIL;
    const adminPassword = process.env.ADMIN_PASSWORD;

    if (!email || email !== adminEmail)
        return res.end("Invalid email");
    if (!password || password !== adminPassword)
        return res.end("Invalid password");

    // Lưu thông tin user vào session
    req.session.user = { email };
    return res.redirect('/');
});

app.get('/', (req, res) => {
    let { user } = req.session;
    if (!user) return res.redirect('/login');
    res.render('index', { products })
})

app.get('/add', auth, (req, res) => {
    return res.render('add');
})

app.post('/add', auth, upload.single('image'), (req, res) => {
    const { name, price, desc } = req.body;
    const image = req.file ? req.file.filename : null;

    // Kiểm tra tính hợp lệ của dữ liệu
    const errors = [];
    if (!name) errors.push("Tên sản phẩm không được để trống.");
    if (!price || isNaN(price)) errors.push("Giá sản phẩm không hợp lệ.");

    // Nếu có lỗi, lưu lỗi vào flash và render lại trang
    if (errors.length > 0) {
        req.flash('errors', errors);
        return res.redirect('/add');
    }

    // Thêm sản phẩm mới vào danh sách sản phẩm
    const newProduct = {
        id: products.length + 1,
        name,
        price: parseFloat(price),
        desc,
        image
    };
    products.push(newProduct);

    // Hiển thị thông báo thành công và chuyển hướng về trang chủ
    req.flash('success', 'Thêm sản phẩm thành công!');
    return res.redirect('/');
});


app.get('/edit/:id', (req, res) => {
    const { id } = req.params;
    const product = products.find(p => p.id == id);

    if (!product) {
        return res.status(404).send('Sản phẩm không tồn tại!');
    }

    res.render('edit', { product });
});

app.post('/edit/:id', (req, res) => {
    const { id } = req.params;
    const { name, price, desc } = req.body;

    const productIndex = products.findIndex(p => p.id == id);

    if (productIndex === -1) {
        return res.status(404).send('Sản phẩm không tồn tại!');
    }

    if (!name || !price || isNaN(price)) {
        req.flash('error', 'Vui lòng nhập tên và giá hợp lệ.');
        return res.redirect(`/edit/${id}`);
    }

    // Cập nhật sản phẩm
    products[productIndex].name = name;
    products[productIndex].price = parseFloat(price);
    products[productIndex].desc = desc;

    // Hiển thị thông báo cập nhật thành công
    req.flash('success', 'Cập nhật sản phẩm thành công!');
    return res.redirect('/');
});

app.get('/product/:id', (req, res) => {
    const { id } = req.params;
    const product = products.find(p => p.id == id);

    if (!product) {
        return res.status(404).send('Sản phẩm không tồn tại!');
    }

    res.render('product', { product });
});




app.listen(8080, () => {
    console.log("Server is runing at http://localhost:8080");
})

