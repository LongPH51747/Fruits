const mongoose = require('mongoose');
const FruitsInCart = mongoose.Schema;
const FruitsUserHave = new FruitsInCart({
    nameFruitsUserHave: {type: String, maxLength: 225},
    quantityFruitsUserHave: {type: Number},
    priceFruitsUserHave: {type: Number},
    imageFruitsuserHave: {type: Array},
    id_user: {type: FruitsInCart.Types.ObjectId, ref: 'users'}
},{
    timestamps: true
})

module.exports = mongoose.model('fruitshaveids', FruitsUserHave);