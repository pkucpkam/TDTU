const express = require('express');
const path = require('path');
const UserAPI = require('./student');

const app = express();

// Middleware để xử lý dữ liệu JSON và URL-encoded
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));


// Trang chủ
app.get('/', async (req, res) => {
    let users = await UserAPI.getUsers();
    res.render('index', { users });
});

// Trang thêm người dùng
app.get('/add', (req, res) => {
    res.render('add', { error: null });
});


app.get('/profile/:id', async (req, res) => {
    const id = req.params.id;
    try {
        let user = await UserAPI.getUserById(id);
        
        if (user.code === 2) {
            return res.status(404).render('profile', { 
                error: 'Người dùng không tồn tại', 
                user: null 
            });
        }
                res.render('profile', { user });
    } catch (error) {
        console.error(error);
        res.status(500).send('Có lỗi xảy ra');
    }
});

app.delete('/students/:id', async (req, res) => {
    const id = req.params.id;
    try {
        await UserAPI.deleteUser(id);
        res.status(200).send('User deleted successfully');
    } catch (error) {
        console.error(error);
        res.status(500).send('Có lỗi xảy ra khi xóa người dùng');
    }
});

app.put('/students/:id', async (req, res) => {
    const id = req.params.id;
    const { fullName, gender, age, email } = req.body;
    const updatedUser = { fullName, gender, age, email };

    try {
        await UserAPI.updateUser(id, updatedUser);
        res.status(200).send('User updated successfully');
    } catch (error) {
        console.error(error);
        res.status(500).send('Có lỗi xảy ra khi cập nhật người dùng');
    }
});

// Xử lý thêm người dùng
app.post('/add', async (req, res) => {
    const { name, gender, age, email } = req.body;

    if (!name || !gender || !age || !email) {
        return res.render('add', { error: 'Vui lòng nhập đầy đủ thông tin' });
    }

    try {
        const users = await UserAPI.getUsers();

        const maxId = users.reduce((max, user) => Math.max(max, user.id), 0);

        const newUser = { id: maxId + 1, fullName: name, gender, age, email };

        await UserAPI.addUser(newUser);


        res.redirect('/');
    } catch (err) {
        console.error(err);
        res.render('add', { error: 'Đã xảy ra lỗi khi thêm người dùng' });
    }
});

app.use((req, res) => {
    res.status(404).render('error', { errorTitle: '404' ,errorDetail: 'Trang bạn tìm không tồn tại' });
});


// Khởi chạy server
app.listen(3000, () => {
    console.log("Server is running on http://localhost:3000");
});
