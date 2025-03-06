const mongoose = require('mongoose');
const UserScheme = mongoose.Schema;
const User = new UserScheme({
    userName: {type: String, maxLength: 225},
    passWord: {type: String, maxLength:225},
    nameUser: {type: String, maxLength: 225},
    avatar: {type: Array},
    role: {type: String, default: 'user'}
},{
    timestamps: true
})

module.exports = mongoose.model('users',User);