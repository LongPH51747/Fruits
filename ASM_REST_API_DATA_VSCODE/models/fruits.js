const mongoose = require('mongoose')
const Fruit = mongoose.Schema;
const Fruits = new Fruit({
    nameFruit: {type: String, maxLength: 225},
    quantityFruits: {type: Number},
    priceFruits: {type: Number},
    statusFruits: {type: Number, default: 1},
    imageFruits: {type: Array},
    descriptionFruits: {type: String}
},{
    timestamps: true
})

module.exports = mongoose.model('fruits', Fruits);