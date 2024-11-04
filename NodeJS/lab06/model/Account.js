const {DataTypes} = require('sequelize')

module.exports = (sequelize) => {
    const Account = sequelize.define('Account', {
        id: {
            type: DataTypes.INTEGER,
            primaryKey: true, 
            allowNull: false,
            autoIncrement: true
        },
        fullname: {
            type: DataTypes.STRING,
            allowNull: false,         
        }
    })
    return Account
}