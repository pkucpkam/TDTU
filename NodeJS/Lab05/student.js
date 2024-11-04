const https = require('https');

function parseBody(res) {
    return new Promise((resolve, reject) => {
        let body = '';
        res.on('data', (data) => {
            body += data.toString();
        });
        res.on('end', () => {
            try {
                body = JSON.parse(body);
                resolve(body);
            } catch (error) {
                reject(error);
            }
        });
    });
}

// Lấy thông tin tất cả người dùng
function getUsers() {
    return new Promise((resolve, reject) => {
        const options = {
            hostname: 'web-nodejs-502070-wiolshzi6q-uc.a.run.app',
            path: '/students',
            port: 443,
            method: 'GET',
        };

        const request = https.request(options, async (res) => {
            try {
                const users = await parseBody(res);
                resolve(users);
            } catch (error) {
                reject(error);
            }
        });

        request.on('error', (err) => {
            reject(err);
        });
        request.end();
    });
}

// Lấy thông tin người dùng theo ID
function getUserById(userId) {
    return new Promise((resolve, reject) => {
        const options = {
            hostname: 'web-nodejs-502070-wiolshzi6q-uc.a.run.app',
            path: `/students/${userId}`,
            port: 443,
            method: 'GET',
        };

        const request = https.request(options, async (res) => {
            try {
                const user = await parseBody(res);
                resolve(user);
            } catch (error) {
                reject(error);
            }
        });

        request.on('error', (err) => {
            reject(err);
        });
        request.end();
    });
}

// Thêm người dùng mới
function addUser(user) {
    return new Promise((resolve, reject) => {
        const options = {
            hostname: 'web-nodejs-502070-wiolshzi6q-uc.a.run.app',
            path: '/students',
            port: 443,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        };

        const request = https.request(options, async (res) => {
            try {
                const addedUser = await parseBody(res);
                resolve(addedUser);
            } catch (error) {
                reject(error);
            }
        });

        request.write(JSON.stringify(user));
        request.on('error', (err) => {
            reject(err);
        });
        request.end();
    });
}

// Xóa người dùng theo ID
function deleteUser(userId) {
    return new Promise((resolve, reject) => {
        const options = {
            hostname: 'web-nodejs-502070-wiolshzi6q-uc.a.run.app',
            path: `/students/${userId}`,
            port: 443,
            method: 'DELETE',
        };

        const request = https.request(options, (res) => {
            res.on('data', () => {}); // Không cần xử lý data
            res.on('end', () => {
                resolve(`User with ID ${userId} deleted`);
            });
        });

        request.on('error', (err) => {
            reject(err);
        });
        request.end();
    });
}


// Cập nhật người dùng theo ID
function updateUser(userId, userData) {
    return new Promise((resolve, reject) => {
        const options = {
            hostname: 'web-nodejs-502070-wiolshzi6q-uc.a.run.app',
            path: `/students/${userId}`,
            port: 443,
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        };

        const request = https.request(options, async (res) => {
            try {
                const updatedUser = await parseBody(res);
                resolve(updatedUser);
            } catch (error) {
                reject(error);
            }
        });

        request.write(JSON.stringify(userData));
        request.on('error', (err) => {
            reject(err);
        });
        request.end();
    });
}

module.exports = {
    getUsers, getUserById, addUser, deleteUser, updateUser
};
